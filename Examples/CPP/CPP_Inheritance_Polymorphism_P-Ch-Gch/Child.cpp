#include "Child.h"
#include <iostream>
using namespace std;

void Child::polymorphicMethod() {
    cout<<"Child Class" << endl;
}

void Child::methodChild() {
    cout<<"Method Child"<<endl;
}

void Child::foo(){
    //Parent::methodParent(); //this is one of the ways to call a method of a specific class (e.g., parent class) //when this is useful?
    methodParent();

    //calling methodA of the parent class with no input parameter (not child)
    //methodA();
    //Parent::methodA();


    cout<<"foo? Child"<<endl;
}

void Child::methodA(int x){
    cout<<"methodA Child"<<endl;
}

