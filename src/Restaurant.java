import java.io.IOException;
import java.math.BigDecimal;

public class Restaurant {
    public static void main(String[] args) {
        RecipeStack recipeStack = new RecipeStack();

        try {
            recipeStack.addMeal(1, new Recipe("Vídeňský řízek v trojobalu s bramborem", BigDecimal.valueOf(120), 10));
        } catch (RestaurantException e){
            System.err.println(e.getMessage());
        }

        try {
            recipeStack.addMeal(2, new Recipe("Boloňské špagety", BigDecimal.valueOf(110), 8, "bolonske-spagety-01"));
        } catch (RestaurantException e){
            System.err.println(e.getMessage());
        }

        try {
            recipeStack.addMeal(3, new Recipe("Kola-Lokova limonáda", BigDecimal.valueOf(15), 0, "kola-loka-01"));
        } catch (RestaurantException e){
            System.err.println(e.getMessage());
        }

        try{
            recipeStack.exportToFile();
        } catch (RestaurantException e){
            System.err.println(e.getMessage());
        }

        recipeStack.clear();

        try {
            recipeStack = RecipeStack.importFromFile("recipeStack.txt");
        } catch (RestaurantException e){
            System.err.println(e.getMessage());
        }

        System.out.println(recipeStack.getNumberOfMeals());

        System.out.println("Hotovo");
    }
}