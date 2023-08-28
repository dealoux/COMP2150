"user strickt";



function main()
{
    let obj = new Parent();


    obj.myFunction();                 //A
    obj.myFunction(10,20);      //B
    obj.myFunction(13);            //C


    console.log("NEW ***** NEW ")

    let p = new Parent();
    let c = new Child();
    p.myFunction();     //A Parent
    c.myFunction();     //B Child

    console.log("*** p = c ***");
    // p = c;
    // c.myFunction();   //A Child
    // p.myFunction();   //B Child

    // p.methodChild();  // 1 - no error
    // p.methodParent(); // 2 - no error
    // c.methodChild();  // 3 - no error
    // c.methodParent(); // 4 - no error

    console.log("*** c = p ***");
    c = p;
    // c.myFunction();   //A  Parent
    // p.myFunction();   //B  Parent

    //p.methodChild();   // 1 error
    //p.methodParent();  // 2 no error
    //c.methodChild();   // 3 error
    //c.methodParent();  // 4 no error

    c = new TestClass(); // totally fine in JS
    // c.myFunction();  // Function in the testClass

}



class Parent{
    constructor()
    {
        this.x = 10;
    }

    myFunction()
    {
        if(arguments.length == 0)
        {
            console.log("Parent: " );
        }
        else if(arguments.length == 2) {
            console.log("Parent - Overload: " );
        }
        else if(arguments.length == 1) {
            console.log("Parent - Overload: " );
        }
    }

    // myFunction()
    // {
    //     console.log("Parent: ");
    // }
    //
    // myFunction(t)
    // {
    //     console.log("Parent - TTT  " );
    // }

    methodParent()
    {
        console.log("Only Parent: " );
    }
}


class Child extends Parent {
    constructor()
    {
        // this.y = 20; // ok? - error - we need to call the constructor of the parent class first.
        super();  // You have to call the parent's constructor using the super keyword and it should be the first line
        this.y = 20;
    }

    myFunction()
    {
        console.log("Child: " );
    }

    methodChild()
    {
        console.log("Only Child: " );
    }
}




class TestClass
{
    myFunction()
    {
        console.log("Test" );
    }
}









main();