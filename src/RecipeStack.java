import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class RecipeStack {
    private Map<Integer, Recipe> recipeStack = new HashMap<>();

    public void addMeal(int codeNumber, Recipe recipe) {
        this.recipeStack.put(codeNumber, recipe);
    }

    public Recipe getMeal(int codeNumber) {
        return this.recipeStack.get(codeNumber);
    }

    public int getNumberOfMeals() {
        return this.recipeStack.size();
    }

    public void removeMeal(int codeNumber) {
        this.recipeStack.remove(codeNumber);
    }

    public void clear() {
        this.recipeStack.clear();
    }

    public void exportToFile(String path) throws RestaurantException {
        String delimiter = GlobalVariables.getDelimiter();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (int key : recipeStack.keySet()) {
                bw.write("" + key + "\n");
                recipeStack.get(key).exportToFile(key + ".txt"); //filename for meal export file = key + .txt
            }
            bw.flush();
        } catch (IOException e) {
            throw new RestaurantException(e.getMessage());
        }
    }

    public static RecipeStack importFromFile(String path) throws RestaurantException {
        String delimiter = GlobalVariables.getDelimiter();
        RecipeStack recipeStack1 = new RecipeStack();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            Scanner scanner = new Scanner(br);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
//                String parts[] = line.split(delimiter);
                Recipe recipe = Recipe.importFromFile(line + ".txt");
                recipeStack1.addMeal(Integer.parseInt(line), recipe);
            }
            return recipeStack1;
        } catch (IOException e) {
            throw new RestaurantException(e.getMessage());
        }
    }

    public boolean isMealAvailable(int mealId) {
        for (int meal : recipeStack.keySet()) {
            if (meal == mealId) {
                return true;
            }
        }
        return false;
    }
}
