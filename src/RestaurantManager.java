import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class RestaurantManager {
    RecipeStack recipeStack;
    OrderList orderList;

    public RestaurantManager(RecipeStack recipeStack, OrderList orderlist) {
        this.recipeStack = recipeStack;
        this.orderList = orderlist;
    }

//    public int createOrder(Order order) throws RestaurantException{
    public int createOrder(int tableNumber, int mealId, int amount, LocalDateTime orderedTime) throws RestaurantException{
        if(recipeStack.isMealAvailable(mealId)){
            return orderList.createOrder(tableNumber, mealId, amount, orderedTime);
        } else {
            throw new RestaurantException("Meal id " + mealId + " not available, order not created");
        }
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

//    public void exportOrderListToFile(String file) throws RestaurantException{
//        String delimiter = GlobalVariables.getDelimiter();
//        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
//            for(Order order : orderList){
//                String line = order.getOrderId() + delimiter + order.getTableNumber() + delimiter + order.getMealId() + delimiter +
//                        order.getAmount() + delimiter + order.getStatus() + delimiter + order.getOrderedTime() + delimiter +
//                        order.getFulfilmentTime() + "\n";
//                bw.write(line);
//            }
//            bw.flush();
//        } catch (IOException e) {
//            throw new RestaurantException(e.getMessage());
//        }
//    }

    public void exportDataToFiles() throws RestaurantException{
        this.recipeStack.exportToFile(GlobalVariables.getRecipeStackFilename());
        this.orderList.exportOrderListToFile(GlobalVariables.getOrderstackfilename());
    }

//    public void importOrderListFromFile(String file) throws RestaurantException{
//        String delimiter = GlobalVariables.getDelimiter();
//        try(BufferedReader br = new BufferedReader(new FileReader(file))){
//            Scanner scanner = new Scanner(br);
//            Order order;
//            int orderId = 0, tableNumber = 0, mealId = 0, amount = 0;
//            OrderStatus status;
//            LocalDateTime orderedTime, fulfilmentTime;
//            String line;
//
//            while(scanner.hasNextLine()) {
//                line = scanner.nextLine();
//                String parts[] = line.split(delimiter);
//                orderId = Integer.parseInt(parts[0]);
//                tableNumber = Integer.parseInt(parts[1]);
//                mealId = Integer.parseInt(parts[2]);
//                amount = Integer.parseInt(parts[3]);
//                status = OrderStatus.valueOf(parts[4]);
//                orderedTime = LocalDateTime.parse(parts[5]);
//                if(parts[6].equals("null")){
//                    fulfilmentTime = null;
//                } else {
//                    fulfilmentTime = LocalDateTime.parse(parts[6]);
//                }
//                order = new Order(orderId, tableNumber, mealId, amount, status, orderedTime, fulfilmentTime);
//                orderList.add(order);
//            }
//            staticOrderId = getHighestOrderId();
//        } catch (IOException e){
//            throw new RestaurantException(e.getMessage());
//        }
//    }
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

