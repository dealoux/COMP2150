//Interface
public interface ShapeCalculationInterface {
    public double calculateArea();
    public double calculatePerimeter();
    //
    //
    //
}

interface PrintableRadius{
    public void PrintRadius();
}

//Class Circle
class Circle implements ShapeCalculationInterface, PrintableRadius{

    int radius = 2;
    //declared in the interface
    public double calculateArea(){
        return (Math.PI*radius*radius);
    }

    //declared in the interface
    public double calculatePerimeter(){
        return (Math.PI*2*radius);
    }

    public void PrintRadius() {
        System.out.println("Radius:" + radius);
    }
}

//Class Square
class Square implements ShapeCalculationInterface {

    int a = 2;
    //declared in the interface
    public double calculateArea(){
        return (a * a);
    }


    //declared in the interface
    public double calculatePerimeter(){
        return (4 * a);
    }
}












//   IPlayer interface ( specific behaviours - methods )
//    |             |
//    |             |
//  HumanClass     Computer Class