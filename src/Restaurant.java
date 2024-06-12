import java.math.BigDecimal;

public class Restaurant {
    public static void main(String[] args) {
        RecipeStack recipeStack = new RecipeStack();

        try {
            recipeStack.addMeal(1111, new Recipe("Vídeňský řízek v trojobalu s bramborem", BigDecimal.valueOf(120), 15));
        } catch (RestaurantException e){
            System.err.println(e.getMessage());
        }

        try {
            recipeStack.addMeal(2222, new Recipe("Boloňské špagety", BigDecimal.valueOf(110), 8, "bolonske-spagety-01"));
        } catch (RestaurantException e){
            System.err.println(e.getMessage());
        }

        try {
            recipeStack.addMeal(3333, new Recipe("Bramborák", BigDecimal.valueOf(50), 7, "bramborak-01"));
        } catch (RestaurantException e){
            System.err.println(e.getMessage());
        }

        try {
            recipeStack.addMeal(4444, new Recipe("T-bone steak z argentinského býka", BigDecimal.valueOf(210), 20, "T-bone-steak"));
        } catch (RestaurantException e){
            System.err.println(e.getMessage());
        }

        try {
            recipeStack.addMeal(33, new Recipe("Kola-Lokova limonáda", BigDecimal.valueOf(25), 0, "kola-loka-01"));
        } catch (RestaurantException e){
            System.err.println(e.getMessage());
        }

        try {
            recipeStack.addMeal(11, new Recipe("Pivo 10", BigDecimal.valueOf(35), 2, "pivo"));
        } catch (RestaurantException e){
            System.err.println(e.getMessage());
        }

        try {
            recipeStack.addMeal(22, new Recipe("Birel", BigDecimal.valueOf(30), 0, "birel"));
        } catch (RestaurantException e){
            System.err.println(e.getMessage());
        }

        try{
            recipeStack.exportToFile(GlobalVariables.getRecipeStackFilename());
        } catch (RestaurantException e){
            System.err.println(e.getMessage());
        }

        recipeStack.clear();

        try {
            recipeStack = RecipeStack.importFromFile(GlobalVariables.getRecipeStackFilename());
        } catch (RestaurantException e){
            System.err.println(e.getMessage());
        }

        System.out.println(recipeStack.getNumberOfMeals());

        System.out.println("Hotovo");
    }
}