import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Order {
    private int orderId;
    private int tableNumber;
    private int mealId;
    private int amount;
    private OrderStatus status;
    private LocalDateTime orderedTime;
    private LocalDateTime fulfilmentTime;

    public Order(int orderId, int tableNumber, int mealId, int amount, OrderStatus status, LocalDateTime orderedTime, LocalDateTime fulfilmentTime) {
        this.orderId = orderId;
        this.tableNumber = tableNumber;
        this.mealId = mealId;
        this.amount = amount;
        this.status = status;
        this.orderedTime = orderedTime;
        this.fulfilmentTime = fulfilmentTime;
    }

    public Order(int orderId, int tableNumber, int mealId, int amount, LocalDateTime orderedTime) {
//        id++;
        this.orderId = orderId;
        this.tableNumber = tableNumber;
        this.mealId = mealId;
        this.amount = amount;
        this.status = OrderStatus.ORDERED;
        this.orderedTime = orderedTime;

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

    public LocalDateTime getOrderedTime() {
        return orderedTime;
    }

    public void setOrderedTime(LocalDateTime orderedTime) {
        this.orderedTime = orderedTime;
    }

    public LocalDateTime getFulfilmentTime() {
        return fulfilmentTime;
    }

    public void setFulfilmentTime(LocalDateTime fulfilmentTime) {
        this.fulfilmentTime = fulfilmentTime;
    }

    public int getOrderId(){
        return this.orderId;
    }

    public long getFulfilmentPeriod() {
        if( this.fulfilmentTime != null ) {
            return this.orderedTime.until(this.fulfilmentTime, ChronoUnit.MINUTES);
        } else {
            return -1; //if the order is still being processed, return -1
        }
    }

}
