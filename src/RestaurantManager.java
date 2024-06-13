import java.util.*;

public class RestaurantManager {
    List<Order> orderList = new ArrayList<>();
    RecipeStack recipeStack;

    public RestaurantManager(RecipeStack recipeStack) {
        this.recipeStack = recipeStack;
    }

    public int createOrder(Order order) throws RestaurantException{
        if(recipeStack.isMealAvailable(order.getMealId())){
            orderList.add(order);
        } else {
            throw new RestaurantException("Meal not available, order not created");
        }
        return order.getId();
    }
}

