import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class RestaurantManager {
        RecipeStack recipeStack  = new RecipeStack();
        OrderList orderList = new OrderList();


    public void addMealToRecipeStack(int code, Recipe recipe){
        recipeStack.addMeal(code, recipe);
    }

    public int createOrder(int tableNumber, int mealId, int amount, LocalDateTime orderedTime) throws RestaurantException{
        if(recipeStack.isMealAvailable(mealId)){
            return orderList.createOrder(tableNumber, mealId, amount, orderedTime);
        } else {
            throw new RestaurantException("Meal id " + mealId + " not available, order not created");
        }
    }

    public void removeMealfromStack(int codeNumber) {
        this.recipeStack.removeMeal(codeNumber);
    }


    public void changeOrder(int id, OrderStatus status) throws RestaurantException{
        this.orderList.changeOrder(id, status);
    }

    public Order getOrderById(int id) throws RestaurantException{
        return this.orderList.getOrderById(id);
    }

    public void cancelOrder(int id) throws RestaurantException{
        this.orderList.cancelOrder(id);
    }

    public int getNumberOfUnfinishedOrders(){
        return this.orderList.getNumberOfUnfinishedOrders();
    }

    public int getHighestOrderId(){
        return this.orderList.getHighestOrderId();
    }

    public int getNumberOfMeals(){
        return recipeStack.getNumberOfMeals();
    }

    public void exportDataToFiles() throws RestaurantException{
        this.recipeStack.exportToFile(GlobalVariables.getRecipeStackFilename());
        this.orderList.exportOrderListToFile(GlobalVariables.getOrderstackfilename());
    }

    public void importDataFromFiles() throws RestaurantException{
        this.recipeStack.importFromFile(GlobalVariables.getRecipeStackFilename());
        this.orderList.importOrderListFromFile(GlobalVariables.getOrderstackfilename());

    }

    public void clearSystem(){
        this.recipeStack.clear();
        this.orderList.clearOrderList();
    }

    public int getTotalNumberOfOrders(){
        return this.orderList.getTotalNumberOfOrders();
    }

    public int getStaticOrderId(){
        return this.orderList.getStaticOrderId();
    }

    public void printSortedOrderList(){
        String delimiter = GlobalVariables.getDelimiter();
        List<Order> sortedOrderList = this.orderList.getOrdersSortedOrderTime();
        for(Order order : sortedOrderList){
            System.out.println(order.getOrderId() + delimiter + order.getOrderedTime());
        }
    }

    public long getAverageFulfilmentTime(){
        return this.orderList.getAverageFulfilmentTime();
    }

    public String exportOrdersPerTable(int tableId) throws RestaurantException{
        if(tableId < 0){
            throw new RestaurantException("Negative numbering for table not allowed. You entered " + tableId);
        }

        DateTimeFormatter myTimeFormat = DateTimeFormatter.ofPattern("HH:mm");
        String output, text;
        int itemNumber = 0;
        if(tableId < 10) {
            output = "** Orders for table nr.  " + tableId + " **\n****\n";
        } else {
            output = "** Orders for table nr. " + tableId + " **\n****\n";
        }
        for(Order order : this.orderList.getOrdersPerTable(tableId)){
            itemNumber++;
            String mealTitle = recipeStack.getMeal(order.getMealId()).getTitle();
            String amount = order.getAmount() + "x";
            String price = recipeStack.getMeal(order.getMealId()).getPrice().multiply(BigDecimal.valueOf(order.getAmount())).toString();
            String times = order.getOrderedTime().format(myTimeFormat) + "-" + order.getFulfilmentTime().format(myTimeFormat);
            String isPaid;
            if (order.getStatus() == OrderStatus.PAID) {
                isPaid = "paid";
            } else {
                isPaid = "";
            }
            text = itemNumber + ". " + mealTitle + " " + amount +" (" + price + " Kč):\t" + times + "\t" + isPaid + "\n";
            output += text;
        }
        output += "******";

        return output;
    }

}

