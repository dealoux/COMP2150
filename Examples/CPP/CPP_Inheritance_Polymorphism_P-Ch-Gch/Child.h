#pragma once
#include "Parent.h"

class Child : public Parent{
public:
    void polymorphicMethod();

    void methodChild();

    //if you change the name of the method to food you will get error because of using override keyword
    void foo() override;

    //we use this syntax, so we can call methodA() of the parent class directly (check foo method of child.cpp)
    //using Parent::methodA;
    void methodA(int x);
};
