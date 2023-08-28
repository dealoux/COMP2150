#include "a3object.h"

int A3Object::instances = 0;

A3Object::A3Object() {
  instances++;
}

A3Object::~A3Object() {
  instances--;
}

int A3Object::countInstances() {
  return instances;
}
