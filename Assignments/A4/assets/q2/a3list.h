#pragma once

#include "a3collection.h"
#include "a3comparable.h"
#include "a3comparator.h"
#include "a3iterator.h"

class A3Node;

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
