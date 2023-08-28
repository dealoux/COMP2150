#include "a3list.h"
#include "a3object.h"

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

class LLIterator : public Iterator {
private:
  A3List *list;
  A3Node *curr;
public:
  LLIterator(A3List *list, A3Node *curr) {
    this->list = list;
    this->curr = curr;
  }
  bool has_next() {
    return curr != nullptr;
  }
  Comparable *next() {
    Comparable *data = curr->get_data();
    curr = curr->get_next();
    return data;
  }
  Comparable *remove() {
    Comparable *data = next();
    list->remove(data);
    return data;
  }
  ~LLIterator() { }
};

A3List::A3List() {
  head = nullptr;
}

Iterator *A3List::start() {
  return new LLIterator(this, this->head);
}

void A3List::finish(Iterator *i) {
  delete i;
}

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
