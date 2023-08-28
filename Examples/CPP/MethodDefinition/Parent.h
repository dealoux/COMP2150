
//this is a bad example in terms of implementing your classes
//you are not supposed to .......


class Parent {
public:

    //1
    // A: [shadowing] - B: overriding - C: redefinition - D:refinement
    //1-same signiture in the child and parent class
    //2-no virtual / abstract => no polymorphism
    //we only care about the static type of the object
    //java? -> no shadowing - there is no way to turn off polymorphism
    void methodX() {
        //some stuff  Parent
    }

    //2
    //A: shadowing - B: [overriding] - C: redefinition - D:refinement
    virtual void methodY() {
        //some stuff Parent
    }

    //3
    //A: shadowing - B: [overriding] - C: redefinition - D:refinement
    virtual void methodZ() = 0;

    //4
    //A: shadowing - B: overriding - C: [redefinition] - D:refinement
    void methodR() {
        //do some stuff Parent
    }


    //5
    //A: shadowing - B: overriding - C: redefinition - D:[refinement]
    Parent() {
        //initializing the value of fields of the parent class
    }

    //6
    //A: shadowing - B: overriding - C: redefinition - D:[refinement]
    void printMethod() {
        //print Some Parent
    }
};






//****************
class Child : public Parent
{
public:
    void methodX(){
        //some stuff  Child
    }

    void methodY(){
        //some stuff Child
    }

    void methodZ(){
        //some stuff Child
    }

    void methodR(int x, int d){
        //some stuff Child
    }


    Child():Parent(){
        //initializing the value of fields of the CHILD class
    }


    void printMethod(){
        Parent::printMethod();
        //print Some extra stuff
    }
};



