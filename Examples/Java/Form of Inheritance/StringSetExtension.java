public class StringSetExtension extends SetExtension{
    //{{{{{****** same behaviour ******}}}}}}  of add method in the parent class
    //could be considered as specialization too
    public void add(String x)
    {
        //add x to the stringSet
        //more details - specialized
    }

    //lsp? [same behaviour in child and parent]

    //extension
    public void StringContains(String x)
    {
        //returns all strings that contain x as a subString
    }


}
