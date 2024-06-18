import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
            throw new RestaurantException("Jídlo č. " + mealId + " není dostupné, objednávka nebyla vytvořena.");
        }
    }

    public void changeOrder(int id, OrderStatus status, LocalDateTime time) throws RestaurantException{
        this.orderList.changeOrder(id, status, time);
    }

    public int getNumberOfUnfinishedOrders(){
        return this.orderList.getNumberOfUnfinishedOrders();
    }

    public int getNumberOfMeals(){
        return recipeStack.getNumberOfMeals();
    }

    public void exportDataToFiles(String recipeStackFile, String orderStackFile) throws RestaurantException{
        this.recipeStack.exportToFile(recipeStackFile);
        this.orderList.exportOrderListToFile(orderStackFile);
    }

    public void importDataFromFiles(String recipeStackFile, String orderStackFile) throws RestaurantException{
        try {
            this.recipeStack.importFromFile(recipeStackFile);
        } catch (RestaurantException e) {
            String errorMessage = e.getMessage();
            errorMessage += "\nChyba čtení dat ze souboru " + recipeStackFile + ".";
            throw new RestaurantException(errorMessage);
        }
        this.orderList.importOrderListFromFile(orderStackFile);

    }

    public void clearSystem(){
        this.recipeStack.clear();
        this.orderList.clearOrderList();
    }

    public int getTotalNumberOfOrders(){
        return this.orderList.getTotalNumberOfOrders();
    }

    public void printSortedOrderList(){
        String delimiter = GlobalVariables.getDelimiter();
        List<Order> sortedOrderList = this.orderList.getOrdersSortedOrderedTime();
        for(Order order : sortedOrderList){
            System.out.println(order.getOrderId() + delimiter + order.getOrderedTime().format(GlobalVariables.getMYTIMEFORMAT()));
        }
    }

    public long getAverageFulfilmentTime(){
        return this.orderList.getAverageFulfilmentTime();
    }

    public String exportOrdersPerTable(int tableId) throws RestaurantException {
        if (tableId < 0) {
            throw new RestaurantException("Záporné číslo pro identifikaci stolu není povoleno. Zadal jsi " + tableId);
        }
        String output, text;
        int itemNumber = 0;

        if(tableId < 10) {
            output = "** Objednávky pro stůl č.  " + tableId + " **\n****\n";
        } else {
            output = " Objednávky pro stůl č. " + tableId + " **\n****\n";
        }

        for(Order order : this.orderList.getOrdersPerTable(tableId)){
            itemNumber++;
            text = itemNumber + ". " + printOrderInfo(order);
            output += text;
        }
        output += "******";

        return output;
    }

    private String printOrderInfo(Order order){
        DateTimeFormatter myTimeFormat = GlobalVariables.getMYTIMEFORMAT();
        String mealTitle = recipeStack.getMeal(order.getMealId()).getTitle();
        String amount = order.getAmount() + "x";
        String price = recipeStack.getMeal(order.getMealId()).getPrice().multiply(BigDecimal.valueOf(order.getAmount())).toString();
        String orderedTime = order.getOrderedTime().format(myTimeFormat);
        String fulfilmentTime;
        if(order.getFulfilmentTime() == null){
            fulfilmentTime = "Neservírováno";
        } else {
            fulfilmentTime = order.getFulfilmentTime().format(myTimeFormat);
        }
        String times = orderedTime + "-" + fulfilmentTime;
        String isPaid;
        if (order.getStatus() == OrderStatus.PAID) {
            isPaid = "zaplacena";
        } else {
            isPaid = "";
        }
        return mealTitle + " " + amount +" (" + price + " Kč):\t" + times + "\t" + isPaid + "\n";

    }

    public void printMealsOrderedToday(){
        Set<Integer> todaysMealsOrdered = this.orderList.getTodaysMealsIds();

        for(int mealId:todaysMealsOrdered){
            System.out.println(mealId + GlobalVariables.getDelimiter() + recipeStack.getMeal(mealId).getTitle());
        }
    }

}

