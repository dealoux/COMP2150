#pragma once

#include "a3object.h"

class Comparable : public A3Object {
public:
  virtual int compareTo(Comparable *other) = 0;
  virtual ~Comparable() = 0;
};
inline Comparable::~Comparable() = default;
