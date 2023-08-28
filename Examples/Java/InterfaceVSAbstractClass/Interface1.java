public interface Interface1{
    public int methodFromInterface1(int x);
}

 interface Interface2{
    public int methodFromInterface2(int x);
}

 interface Interface3 extends Interface2, Interface1{
    public int methodFromInterface3(int x);
}

class myClasse extends AbstractClass implements Interface3 {

    public int methodFromInterface3(int y)
    {
        return y+2;
    }
    public int methodFromInterface1(int y)
    {
        return y+2;
    }

    public int methodFromInterface2(int y)
    {
        return y+2;
    }



    public int methodFromAbstractClass(int k){
        //do some stuff
        return k++;
    }
}