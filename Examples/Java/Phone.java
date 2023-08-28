public class Phone {

    // fields - instance variables
    // OS String
    // phone model String
    // phone number int

    //super important (private fields - information hiding)
    private String os;
    private String model;
    private int phoneNumber;
    private int password;


    //
    private Person owner;

    //how can you set the value of these fields?
    //constructor
    //setter

    //setter
    public void osSetter(String value)
    {
        //more controle
        //check the user name
        os = value;
    }

    //setter
    public void phoneNumberSetter(int value)
    {
        if(value < 0)
        {
            System.out.println("This is error");
            //THROW EXCEPTIONS
            //RETURN
        }

        //other conditions
    }

    public String osGetter()
    {
        return os;
    }

    //constructor
    public Phone(String osValue, String modelValue, int phoneNValue)
    {
        os = osValue;
        model = modelValue;
        phoneNumber = phoneNValue;
    }

    //default constructor
    public Phone()
    {
        os = "";
        model = "";
        phoneNumber = 0;
    }
}

//is-a relationship - inheritance - Iphone is-a Phone
class Iphone extends Phone{

}

//has-a relationship with Phone class
class Person{

}
