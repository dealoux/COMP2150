#pragma once

#include "a3comparable.h"

class Iterator {
public:
  virtual bool has_next() = 0;
  virtual Comparable *next() = 0;
  virtual Comparable *remove() = 0;
  virtual ~Iterator() = 0;
};
inline Iterator::~Iterator() = default;
