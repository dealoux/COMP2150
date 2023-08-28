#include <iostream>
using namespace std;

class Parent {
public:
    int x;
    Parent(){ x = 0; y = 0; z=0; }
protected:
    int y;
private:
    int z;
};

//               $
class Child : private Parent {

public:
    void TestMethod() {
        cout << x;  // A?   no error
        cout << y;  // B?   no error
        cout << z;  // C?   error
    }
};

//important ************
class GrandChild : public Child {

public:
    void TestMethod2() {
        cout << x;  //A'?  [error]
        cout << y;  //B'?  [error]
        cout << z;  //C'?  [error]
    }
};

class Child2 : protected Parent {

public:
    void TestMethod() {
        cout << x;  // A?   no error
        cout << y;  // B?   no error
        cout << z;  // C?   [error]
    }
};

class GrandChild2 : public Child2 {

public:
    void TestMethod2() {
        cout << x;  //A'?  [error]
        cout << y;  //B'?  [error]
        cout << z;  //C'?  [error]
    }
};



//Main Function - Outside of the hierarchy of our classes
int main() {

    Parent *p = new Parent();            // x:public, y:protected, z:private
    Child *c = new Child();              //child : [[[private]]] parent
    GrandChild *gc = new GrandChild();   //grandchild : public child
    Child2 *c2 = new Child2();           //child2 : protected parent

    cout << "Parent:\t" <<  p->x << endl;      //A?  no error
    cout << "Parent:\t" <<  p->y << endl;      //B?  error
    cout << "Parent:\t" <<  p->z << endl;      //C?  error
//
//************* important
    cout << "Child:\t" << c->x << endl;      //A?  error
    cout << "Child:\t" << c->y << endl;      //B?  error
    cout << "Child:\t" << c->z << endl;      //C?  error
//
//
    cout << "GrandChild:\t" << gc->x << endl;  //A?  error
    cout << "GrandChild:\t" << gc->y << endl;  //B?  error
    cout << "GrandChild:\t" << gc->z << endl;  //C?  error
//
//************* important
    cout << "Child2:\t" << c2->x << endl;      //A?  [error]
    cout << "Child2:\t" << c2->y << endl;      //B?  [error]
    cout << "Child2:\t" << c2->z << endl;      //C?  [error]


    return 0;
}














//cout << x;  A?
//cout << y;  B?
//cout << z;  C?