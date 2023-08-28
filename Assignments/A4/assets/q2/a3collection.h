#pragma once

#include "a3object.h"
#include "a3comparable.h"
#include "a3iterator.h"

class Collection : public A3Object {
public:
  virtual void insert(Comparable *item) = 0;
  virtual Iterator *start() = 0;
  virtual void finish(Iterator *i) = 0;
  virtual ~Collection() = 0;
};
inline Collection::~Collection() = default;
