#pragma once

//what includes do we need here
class Node;
class IntItem;


class List {
private:
    Node* head;
public:
    List();
    void print();
    void insert(IntItem*);
};

