import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class RestaurantManager {
    static int staticOrderId = 0;
    List<Order> orderList = new ArrayList<>();
    RecipeStack recipeStack;

    public RestaurantManager(RecipeStack recipeStack) {
        this.recipeStack = recipeStack;
    }

//    public int createOrder(Order order) throws RestaurantException{
    public int createOrder(int tableNumber, int mealId, int amount, LocalDateTime orderedTime) throws RestaurantException{
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
                order.setFulfilmentTime(LocalDateTime.now());
            }
        } else throw new RestaurantException("Order with Id " + id + " not found");
    }

    public Order getOrderById(int id) throws RestaurantException{
        for( Order order : orderList){
            if(order.getOrderId() == id){
                return order;
            }
        }
        throw new RestaurantException("Order with Id " + id + " not found");
    }

    public void cancelOrder(int id) throws RestaurantException{
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

    public int getHighestOrderId(){
        int id = 0;
        for( Order order : orderList){
            if(order.getOrderId() > id){
                id = order.getOrderId();
            }
        }
        return  id;
    }

    public void exportOrderListToFile(String file) throws RestaurantException{
        String delimiter = GlobalVariables.getDelimiter();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
            for(Order order : orderList){
                String line = order.getOrderId() + delimiter + order.getTableNumber() + delimiter + order.getMealId() + delimiter +
                        order.getAmount() + delimiter + order.getStatus() + delimiter + order.getOrderedTime() + delimiter +
                        order.getFulfilmentTime() + "\n";
                bw.write(line);
            }
            bw.flush();
        } catch (IOException e) {
            throw new RestaurantException(e.getMessage());
        }
    }

    public void importOrderListFromFile(String file) throws RestaurantException{
        String delimiter = GlobalVariables.getDelimiter();
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            Scanner scanner = new Scanner(br);
            Order order;
            int orderId = 0, tableNumber = 0, mealId = 0, amount = 0;
            OrderStatus status;
            LocalDateTime orderedTime, fulfilmentTime;
            String line;

            while(scanner.hasNextLine()) {
                line = scanner.nextLine();
                String parts[] = line.split(delimiter);
                orderId = Integer.parseInt(parts[0]);
                tableNumber = Integer.parseInt(parts[1]);
                mealId = Integer.parseInt(parts[2]);
                amount = Integer.parseInt(parts[3]);
                status = OrderStatus.valueOf(parts[4]);
                orderedTime = LocalDateTime.parse(parts[5]);
                if(parts[6].equals("null")){
                    fulfilmentTime = null;
                } else {
                    fulfilmentTime = LocalDateTime.parse(parts[6]);
                }
                order = new Order(orderId, tableNumber, mealId, amount, status, orderedTime, fulfilmentTime);
                orderList.add(order);
            }
            staticOrderId = getHighestOrderId();
        } catch (IOException e){
            throw new RestaurantException(e.getMessage());
        }
    }
    public void clearOrderList(){
        this.orderList.clear();
    }

    public int getTotalNumberOfOrders(){
        return this.orderList.size();
    }

    public int getStaticOrderId(){
        return staticOrderId;
    }

    private List<Order> getOrdersSortedOrderTime(){
        List<Order> sortedOrderList = new ArrayList<>();
        sortedOrderList.addAll(this.orderList);
        sortedOrderList.sort(Comparator.comparing(Order::getOrderedTime));
        return sortedOrderList;
    }

    public void printSortedOrderList(){
        String delimiter = GlobalVariables.getDelimiter();
        List<Order> sortedOrderList = getOrdersSortedOrderTime();
        for(Order order : sortedOrderList){
            System.out.println(order.getOrderId() + delimiter + order.getOrderedTime());
        }
    }

    public long getAverageFulfilmentTime(){
        long totalMinutes = 0;
        long numberOfOrders = 0;
        if (orderList.size() == 0) {
            return 0;
        }

        for(Order order : orderList){
            long processTime = order.getFulfilmentPeriod();
            if (processTime != -1){ //we do not count orders which are still being processed
                totalMinutes += processTime;
                numberOfOrders++;
            }
        }
        return totalMinutes/numberOfOrders;
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
        for(Order order : orderList){
            if(order.getTableNumber() == tableId){
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
        }
        output += "******";

        return output;
    }

}

