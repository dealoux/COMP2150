#include <iostream>
using namespace std;

// hierarchy 1
class A {
public:
    virtual void talk() { cout << "I'm an A!" << endl; }
};

class B : public A {
public:
    virtual void talk() { cout << "I'm a B!" << endl; }
};

// hierarchy 2
class Parent {
public:
    virtual A* build() { return new A(); }

    virtual void methodA(B* myvar) { /* do some stuff */ }
};

class Child: public Parent {
public:
    B* build() { return new B(); } // [covariant] of return type?

    virtual void methodA(A* myvar) { /* do some stuff */ } // [contravariance] of input
};

int main() {


    Parent* p = new Child(); // compile-time type = Parent       run-time type = Child

    A* obj = p->build(); // no error === ParentObject = ChildObject

    //B* obj = p->build(); // [error] child = parent ==> error
    //this is a compile-rime error. p->build returns A (for the compiler)


    obj->talk();
    // the talk method of class B will be called at RUN_TIME (what is the dynamic type of obj at run time?) -> B

    return 0;
}
