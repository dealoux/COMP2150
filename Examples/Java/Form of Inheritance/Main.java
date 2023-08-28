public class Main {

    public static void main(String[] args) {

        //{Specialization} LSP always hold
        AnimalSpecialization AnimalObjectSpecialization = new BirdSpecialization();
        AnimalObjectSpecialization.breath();
        //AnimalObjectSpecialization.randomMethopd();


        //{Specification} LSP always hold
        ListItemSpecification ListItemObject = new IntItemSpecification();
        ListItemObject.print();



        //{construction} LSP doesn't hold -> add method has different behaviour in List and Set classes
        ListConstruction lsConstructionObject = new ListConstruction();
        lsConstructionObject.add(2);
        lsConstructionObject.add(2);
        lsConstructionObject.add(2);
        //adds 2 three times to the list

        lsConstructionObject = new SetConstruction();
        lsConstructionObject.add(13);
        lsConstructionObject.add(13);
        lsConstructionObject.add(13);
        //adds 13 to the set only once



        //{Extension} LSP holds
        SetExtension setObject = new StringSetExtension();
        setObject.add("Test"); // calling the add method of StringSetExtension -> this add method has similar behaviour to add method of SetExtension class




        //{Limitation} LSP doesn't hold
        PrintableIntItemLimitation printableObject = new PrintableIntItemLimitation();
        printableObject.print(); // output 1000

        printableObject = new UnprintableIntitemLimitation();
        printableObject.print(); //output = ?  => no output -> this means different behaviour


    }
}
