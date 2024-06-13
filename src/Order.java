import java.time.LocalTime;

public class Order {
    private static int id = 0;
    private int orderId;  //should be unigue in whole system
    private int tableNumber;
    private int mealId;
    private int amount;
    private OrderStatus status;
    private LocalTime orderedTime;
    private LocalTime fulfilmentTime;

    public Order(int tableNumber, int mealId, int amount) {
        id++;
        this.orderId = id;
        this.tableNumber = tableNumber;
        this.mealId = mealId;
        this.amount = amount;
        this.status = OrderStatus.ORDERED;
        this.orderedTime = LocalTime.now();

    }

    public int getId(){
        return this.orderId;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalTime getOrderedTime() {
        return orderedTime;
    }

    public void setOrderedTime(LocalTime orderedTime) {
        this.orderedTime = orderedTime;
    }

    public LocalTime getFulfilmentTime() {
        return fulfilmentTime;
    }

    public void setFulfilmentTime(LocalTime fulfilmentTime) {
        this.fulfilmentTime = fulfilmentTime;
    }

    public int getOrderId(){
        return this.orderId;
    }
}
