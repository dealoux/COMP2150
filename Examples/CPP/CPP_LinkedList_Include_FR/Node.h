
#pragma once
//what includes do we need here?
//#include "IntItem.h"
class IntItem;  //FR


class Node {
private:
    IntItem* item; //item has fields / item has method
    Node* next;
public:
    Node();
    Node(IntItem*);
    //item.methodX()
    //item.fieldA
    //Node(RandomClass*)
    void print();
    Node* getNext();
    void setNext(Node*);
    IntItem* getData();
};
