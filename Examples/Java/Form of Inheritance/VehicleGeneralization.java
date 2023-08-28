public class VehicleGeneralization extends BikeGeneralization{
    //we may have more fields but these fields are more general
    String engineType;
    int numberOfSeat;
    int CargoArea;

    public void move()
    {
        //this move method and it's more general compared to the move method in the parent class (Bike)
    }
}
