
#include <iostream>
#include "Circle.h"
using namespace std;


int main() {

    //pointers = heap-based objects

    //heap based object
    cout << "Heap-Based" << endl;
    Circle* heapObj1 = new Circle; // call the default constructor
    cout << "H-Obj1: " << heapObj1->getArea() << endl;

    Circle* heapObj2 = new Circle(1,0,0);
    cout << "H-Obj2: " << heapObj2->getArea() << endl;

    heapObj2 = heapObj1;  // 1 pointers - they both point to the same block of memory
    heapObj1->setRadius(99);
    cout << "H-Obj1 [After =]: " << heapObj1->getArea() << endl;           //A
    cout << "H-Obj2 [After =]: " << heapObj2->getArea() << endl << endl;   //B
    // A = B


    //**************************************************************
    cout << "Stack-Based" << endl ;
    //stack-based object
    //int x;
    //int y;
    Circle stackObj1; // my object is not a pointer  - no () for the default constructor
    cout << "S-Obj1: " << stackObj1.getArea() << endl;

    Circle stackObj2(1,0,0);
    cout << "S-Obj2: " << stackObj2.getArea() << endl;

    int x, y;
    x = y;

    stackObj2 = stackObj1; // 2? copy and past
    stackObj1.setRadius(99);

    cout << "S-Obj1 [After =]: " << stackObj1.getArea() << endl; // A
    cout << "S-Obj2 [After =]: " << stackObj2.getArea() << endl;  // B
    //A = B not



    return 0;

}







































































//
//int number = 88;
//int*pNumber;
//
//pNumber = &number;
//printf("%p \n", pNumber); //A
//printf("%p \n", &number); //B
//printf("%d \n", *pNumber); //C
//printf("%d \n", number); //D
//
//*pNumber = 99;
//printf("%p \n", pNumber); //A2
//printf("%p \n", &number); //B2
//printf("%d \n", *pNumber); //C2
//printf("%d \n", number);  //D2