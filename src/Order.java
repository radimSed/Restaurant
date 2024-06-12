import java.time.LocalTime;

public class Order {
    private static int id = 0;
    private int orderId;
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
}
