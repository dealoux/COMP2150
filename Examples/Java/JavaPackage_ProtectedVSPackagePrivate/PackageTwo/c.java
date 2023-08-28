package PackageTwo;
import PackageOne.*;


public class c{

    public void myMethod() {
//                System.out.println(protectedFieldA); //A: error
//                System.out.println(myField);         //B: error
    }
}

class D extends c {
    public void myMethod() {
//                System.out.println(protectedFieldA); //A: error
//                System.out.println(myField);         //B: error
    }

}

class E extends A {
    public void myMethod() {
                System.out.println(protectedFieldA); //A: no-error
                System.out.println(myField);         //B: error
    }

}
