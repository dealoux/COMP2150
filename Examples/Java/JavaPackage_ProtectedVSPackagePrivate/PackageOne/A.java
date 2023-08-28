package PackageOne;

public class A {
    protected int protectedFieldA = 10;
    int myField = 20; // package private

    //public methdos
}

class B extends A {
    public void myMethod()
    {
        System.out.println(protectedFieldA); //A: no error
        System.out.println(myField);         //B: no error
    }
}

class C {

    public void myMethod()
    {
        //do we have access to protectedFieldA or myField ===> yes

        System.out.println(protectedFieldA); //A: error
        System.out.println(myField);         //B: error

                 A tt = new A();
        System.out.println(tt.protectedFieldA);   //A: no-error
        System.out.println(tt.myField);           //B: no-error
    }
}
