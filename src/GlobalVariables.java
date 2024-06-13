public class GlobalVariables {
    private static final String DELIMMITER = "\t";
    private static final String RECIPESTACKFILENAME = "recipeStack.txt";
    private static final String ORDERSTACKFILENAME = "orderStack.txt";

    public static String getDelimiter(){
        return DELIMMITER;
    }

    public static String getRecipeStackFilename(){
        return RECIPESTACKFILENAME;
    }

    public static String getOrderstackfilename(){
        return ORDERSTACKFILENAME;
    }
}