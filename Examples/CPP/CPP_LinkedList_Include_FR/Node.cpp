
//what includes do we need here?
#include "Node.h"
//include/FR - A+
#include "IntItem.h"
//we cannot use FR - check line 25



Node* Node::getNext() {
    return next;
}

void Node::setNext(Node* next) {
    //this->next = nullptr;
    this->next = next;
}

IntItem* Node::getData() {
    return item;
}

void Node::print() {
    //item is an object of IntItem
    item->print();
}

Node::Node(IntItem* item) {
    this->item = item;
    this->next = nullptr;
}

Node::Node() {
    this->item = 0;
    this->next = nullptr;
}
