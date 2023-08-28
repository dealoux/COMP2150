#pragma once
#include "Child.h"


class GrandChild : public Child{
public:
    //we don't need to use virtual keyword in the child or grandchild classes to have a polymorphic method (we need it in the parent class)
     void polymorphicMethod();

    void methodGrandChild();
};
