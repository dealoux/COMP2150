#include "Parent.h"
#include "Child.h"
#include "GrandChild.h"
#include <iostream>
using namespace std;

int main() {

    //heap based object - pointing to a parent object
    Parent *p = new Parent;


    //calling the parent method
    p->polymorphicMethod(); //A

    //pointing to a child object
    p = new Child;
    //calling the child method (polymorphism)
    p->polymorphicMethod(); //B

    //pointing to a grandchild object
    p = new GrandChild;
    p->polymorphicMethod(); //C


    Child *ch = new GrandChild; //s:Child D:GrandChild
    ch->polymorphicMethod();  //child




    //if remove virtual = A = B = C = Parent




    Parent *p2 = new Child;
//    p2->methodChild(); // error - compile time error







    //downcasting in C++
    Child *ch1 = dynamic_cast<Child *>(p2);

    //if (dynamic_cast<Child *>(p2) != nullptr) // this is also possible
    if (ch1 != nullptr) {
        cout << "Casting Block" << endl;
        ch1->methodChild();
    }


    //in java
//    if(p2 instanceof Child)
//    {
//        //casting
//    }



//stack-based objects
    Parent ps;
    Child chS;
    ps = chS;
    ps.polymorphicMethod(); // parent


    return 0;
}
