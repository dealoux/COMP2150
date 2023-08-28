#pragma once

#include "a3comparable.h"

class Comparator {
public:
  virtual int compare(Comparable *a, Comparable *b) = 0;
  virtual ~Comparator() = 0;
};
inline Comparator::~Comparator() = default;
