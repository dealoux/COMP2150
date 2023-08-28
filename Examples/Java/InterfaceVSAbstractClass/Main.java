import java.awt.*;

public class Main {

    public static void main(String[] args) {

        //variable of type interface but it's pointing to actual subclasses
        ShapeCalculationInterface obj = new Circle();
        ShapeCalculationInterface obj2 = new Square();

        //similar to abstract classes
        //you can't create object of your abstract class but it can be a placeholder for subclasses
        AbstractClass objAbstract = new myClass2();

        //error you can't do that
        //ShapeCalculationInterface obj = new ShapeCalculationInterface();

        myMethod(obj); // obj -> circle
        myMethod(obj2); // obj2  -> square

        System.out.println("    ");
        myMethod2(obj); // obj -> circle
        myMethod2(obj2); // obj2  -> square
    }

    public static void myMethod(ShapeCalculationInterface myVar) {
        System.out.println(myVar.calculateArea());
    }

    public static void myMethod2(ShapeCalculationInterface myVar) {

        if(myVar instanceof Circle)
        {
            ((Circle) myVar).PrintRadius();
        }

    }




}





