#include <iostream>
using namespace std;
#include "List.h"
#include "Node.h" //  we cannot use FR

//#include "IntItem.h"
//class IntItem;





List::List() {
    head = nullptr;
}

void List::print() {
    Node* currNode = head;
    cout << "The list contains: ";
    while (currNode != nullptr ) {
        currNode->print();
        cout << " ";
        currNode = currNode->getNext();
    }
    cout << endl;
}

void List::insert(IntItem* item) {
    Node* newNode = new Node(item);
    newNode->setNext(head);
    head = newNode;
}

