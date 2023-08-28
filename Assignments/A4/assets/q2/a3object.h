#pragma once

class A3Object {
private:
  static int instances;
public:
  A3Object();
  virtual ~A3Object();
  static int countInstances();
};
