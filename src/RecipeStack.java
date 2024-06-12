import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class RecipeStack {
    private Map<Integer, Recipe> recipeStack = new HashMap<>();

    public void addMeal(int codeNumber, Recipe recipe){
        this.recipeStack.put(codeNumber, recipe);
    }

    public Recipe getMeal(int codeNumber){
        return this.recipeStack.get(codeNumber);
    }

    public int getNumberOfMeals(){
        return this.recipeStack.size();
    }

    public void removeMeal(int codeNumber){
        this.recipeStack.remove(codeNumber);
    }

    public void clear(){
        this.recipeStack.clear();
    }

    public void exportToFile() throws RestaurantException{
        String delimiter = GlobalVariables.getDelimiter();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("recipeStack.txt"))){
            recipeStack.forEach((k, v) -> {
                try {
                    bw.write(k + delimiter + v.getTitle() + "\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            throw new RestaurantException(e.getMessage());
        }

        recipeStack.values().forEach(v -> {
            try {
                v.exportToFile();
            } catch (RestaurantException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static RecipeStack importFromFile(String path) throws RestaurantException{
        String delimiter = GlobalVariables.getDelimiter();
        RecipeStack recipeStack1 = new RecipeStack();
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            Scanner scanner = new Scanner(br);
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String parts[] = line.split(delimiter);
                Recipe recipe = Recipe.importFromFile(parts[1] + ".txt");
                recipeStack1.addMeal(Integer.parseInt(parts[0]), recipe);
            }
            return recipeStack1;
        } catch (IOException e){
            throw new RestaurantException(e.getMessage());
        }
    }
}