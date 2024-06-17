import java.time.format.DateTimeFormatter;

public class GlobalVariables {
    private static final String DELIMMITER = "\t";
    private static final String RECIPESTACKFILENAME = "recipeStack.txt";
    private static final String ORDERSTACKFILENAME = "orderStack.txt";
    private static DateTimeFormatter MYTIMEFORMAT = DateTimeFormatter.ofPattern("HH:mm");


    public static String getDelimiter(){
        return DELIMMITER;
    }

    public static String getRecipeStackFilename(){
        return RECIPESTACKFILENAME;
    }

    public static String getOrderstackfilename(){
        return ORDERSTACKFILENAME;
    }

    public static DateTimeFormatter getMYTIMEFORMAT(){
        return MYTIMEFORMAT;
    }
}