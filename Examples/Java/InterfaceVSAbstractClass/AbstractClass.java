public abstract class AbstractClass {
    int x;

    int conceretMethod(int t) {
        //do some stuff
        return t++;
    }

    abstract int methodFromAbstractClass(int k);
}

 abstract class AbstractClass2 {
     int x;

     int conceretMethod2(int t) {
         //do some stuff
         return t++;
     }

     abstract int methodFromAbstractClass2(int k);

 }


class myClass2 extends AbstractClass{
    public int methodFromAbstractClass(int k){
        //do some stuff
        return k++;
    }
}