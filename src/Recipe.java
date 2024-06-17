import java.io.*;
import java.math.BigDecimal;
import java.util.Scanner;

public class Recipe {
    private  String title;
    private BigDecimal price;
    private int preparationTime;
    private String pictureName;
    GlobalVariables globalVars = new GlobalVariables();

    public Recipe(String title, BigDecimal price, int preparationTime, String pictureName) throws RestaurantException{
        this.title = title;
        this.price = price;
        setPreparationTime(preparationTime);
        this.pictureName = pictureName;
    }

    public Recipe(String title, BigDecimal price, int preparationTime) throws RestaurantException{
        this.title = title;
        this.price = price;
        setPreparationTime(preparationTime);
        this.pictureName = "blank";
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPreparationTime(int preparationTime) throws RestaurantException{
        if (preparationTime < 0){
            throw new RestaurantException("Preparation time must be larger than 0. Recipe was not created.");
        } else {
            this.preparationTime = preparationTime;
        }
    }

    public void exportToFile(String filename) throws RestaurantException{
        //filename = title of the meal + .txt
        String delimiter = GlobalVariables.getDelimiter();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))){
            bw.write(this.title + delimiter + this.price + delimiter + this.preparationTime + delimiter + this.pictureName);
        } catch (IOException e){
            throw new RestaurantException(e.getMessage());
        }
    }

    public static Recipe importFromFile(String path) throws RestaurantException{
        String delimiter = GlobalVariables.getDelimiter();
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            Scanner scanner = new Scanner(br);
            String line = scanner.nextLine();
            String parts[] = line.split(delimiter);
            return new Recipe(parts[0], new BigDecimal(parts[1]) , Integer.parseInt(parts[2]), parts[3]);
        } catch (IOException e){
            throw new RestaurantException(e.getMessage());
        }
    }
}