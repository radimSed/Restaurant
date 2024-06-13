import java.time.LocalTime;
import java.util.*;

public class RestaurantManager {
    static int staticOrderId = 0;
    List<Order> orderList = new ArrayList<>();
    RecipeStack recipeStack;

    public RestaurantManager(RecipeStack recipeStack) {
        this.recipeStack = recipeStack;
    }

//    public int createOrder(Order order) throws RestaurantException{
    public int createOrder(int tableNumber, int mealId, int amount, LocalTime orderedTime) throws RestaurantException{
        if(recipeStack.isMealAvailable(mealId)){
            staticOrderId++;
            this.orderList.add(new Order(staticOrderId, tableNumber, mealId, amount,orderedTime));
            return staticOrderId;
        } else {
            throw new RestaurantException("Meal id " + mealId + " not available, order not created");
        }
    }

    public void changeOrder(int id, OrderStatus status) throws RestaurantException{
        Order order = getOrderById(id);
        if( order != null){
            order.setStatus(status);
            if (status == OrderStatus.SERVED || status == OrderStatus.PAID){
                order.setFulfilmentTime(LocalTime.now());
            }
        } else throw new RestaurantException("Order with Id " + id + "not found");
    }

    public Order getOrderById(int id){
        for( Order order : orderList){
            if(order.getOrderId() == id){
                return order;
            }
        }
        return null;
    }

    public void cancelOrder(int id){
        Order order = getOrderById(id);
        orderList.remove(order);
    }

    public int getNumberOfUnfinishedOrders(){
        int unfinishedOrder = 0;
        for( Order order : orderList) {
            if(order.getStatus() != OrderStatus.PAID){
                unfinishedOrder++;
            }
        }
        return unfinishedOrder;
    }

    public void exportOrderListToFile(String file){

    }
}

