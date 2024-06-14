import java.io.*;
import java.time.LocalDateTime;
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

//    public LocalTime getAverageFulfilmentTime(){
//
//    }

//    private LocalTime getFulfilmentTime(Order order){
//        if( order.getFulfilmentTime() != null ) {
//            return 10;
//        } else {
//            return LocalTime.now().minusHours(1);
//        }
//    }
}

