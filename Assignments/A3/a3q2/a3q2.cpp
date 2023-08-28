#include <iostream>
#include <fstream>
#include <sstream>
#include <string>

//Seperate me
class A3Object {
private:
  static int instances;
public:
  A3Object() {
    instances++;
  }
  virtual ~A3Object() {
    instances--;
  }
  static int countInstances() {
    return instances;
  }
};
int A3Object::instances = 0;

//Seperate me
class Comparable : public A3Object {
public:
  virtual int compareTo(Comparable *other) = 0;
  virtual ~Comparable() = 0;
};
inline Comparable::~Comparable() = default;

//Seperate me
class Comparator {
public:
  virtual int compare(Comparable *a, Comparable *b) = 0;
  virtual ~Comparator() = 0;
};
inline Comparator::~Comparator() = default;

//Seperate me
class Iterator {
public:
  virtual bool has_next() = 0;
  virtual Comparable *next() = 0;
  virtual Comparable *remove() = 0;
  virtual ~Iterator() = 0;
};
inline Iterator::~Iterator() = default;

//Seperate me
class Collection : public A3Object {
public:
  virtual void insert(Comparable *item) = 0;
  virtual Iterator *start() = 0;
  virtual void finish(Iterator *i) = 0;
  virtual ~Collection() = 0;
};
inline Collection::~Collection() = default;

//Seperate me
class A3Node : public A3Object {
private:
  Comparable *data;
  A3Node *next;
public:
  A3Node(Comparable *data, A3Node *next) {
    this->data = data;
    this->next = next;
  }
  A3Node *get_next() {
    return next;
  }
  void set_next(A3Node *new_next) {
    next = new_next;
  }
  Comparable *get_data() {
    return data;
  }
};

//Seperate me
class A3List : public Collection {
private:
  A3Node *head;
public:
  A3List();
  Iterator *start() override;
  void finish(Iterator *i) override;
  Comparable *remove(Comparable *item);
  ~A3List();
protected:
  void insert_natural(Comparable *item);
  void insert_comparator(Comparable *item, Comparator *comp);
};

//Seperate me
A3List::A3List() {
  head = nullptr;
}

//Seperate me
A3List::~A3List() {
  A3Node *curr = head, *next;

  while (nullptr != curr) {
    next = curr->get_next();
    delete curr;
    curr = next;
  }
}

void A3List::insert_natural(Comparable *item) {
  class : public Comparator {
  public:
    int compare(Comparable *a, Comparable *b) {
      return a->compareTo(b);
    }
  } natural_comparator;
  insert_comparator(item, &natural_comparator);
}

void A3List::insert_comparator(Comparable *item, Comparator *comp) {
  A3Node *curr = head, *prev = nullptr;

  while (nullptr != curr && comp->compare(item, curr->get_data()) > 0) {
    prev = curr;
    curr = curr->get_next();
  }

  if (nullptr == prev) {
    head = new A3Node(item, head);
  } else {
    prev->set_next(new A3Node(item, curr));
  }
}

Comparable *A3List::remove(Comparable *item) {
  A3Node *curr = head, *prev = nullptr;

  while (nullptr != curr && item != curr->get_data()) {
    prev = curr;
    curr = curr->get_next();
  }

  if (nullptr == curr) {
    return nullptr;
  } else {
    if (nullptr == prev) {
      head = curr->get_next();
    } else {
      prev->set_next(curr->get_next());
    }
    Comparable *data = curr->get_data();
    delete curr;
    return data;
  }
}

//This should be the only method in this file when you are finished.
int main(int argc, char *argv[]) {
  // This should be the last line of your program
  std::cout << "\nNumber of objects leaked: " << A3Object::countInstances() << std::endl;
}
