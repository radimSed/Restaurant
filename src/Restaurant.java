import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Restaurant {


    public static void main(String[] args) {
        int orderId1 = 0, orderId2 = 0, orderId3 = 0, orderId4 = 0;

        RestaurantManager restaurantManager = new RestaurantManager();

        addMealsToRecipeStack(restaurantManager);


        try {
            orderId1 = restaurantManager.createOrder(2, 2222, 2, LocalDateTime.now().minusMinutes(10));
        } catch (RestaurantException e) {
            System.err.println(e.getMessage());
        }

        try{
            restaurantManager.addMealToRecipeStack(6666, new Recipe("Svíčková s knedlíkem", BigDecimal.valueOf(125), 15));
        } catch (RestaurantException e) {
            System.err.println(e.getMessage());
        }

        try {
            orderId2 = restaurantManager.createOrder(1, 6666, 3,LocalDateTime.now().minusMinutes(5));
        } catch (RestaurantException e) {
            System.err.println(e.getMessage());
        }

        try {
            orderId3 = restaurantManager.createOrder(1, 22, 3,LocalDateTime.now().minusMinutes(12));
        } catch (RestaurantException e) {
            System.err.println(e.getMessage());
        }

        try{
            restaurantManager.cancelOrder(orderId2);
        } catch (RestaurantException e) {
            System.err.println(e.getMessage());
        }

        try{
            restaurantManager.cancelOrder(orderId2); //to try cancel one order twice
        } catch (RestaurantException e) {
            System.err.println(e.getMessage());
        }

        try{
            restaurantManager.changeOrder(orderId1, OrderStatus.SERVED);
        } catch (RestaurantException e) {
            System.err.println(e.getMessage());
        }

        try{
            restaurantManager.changeOrder(orderId3, OrderStatus.PAID);
        } catch (RestaurantException e) {
            System.err.println(e.getMessage());
        }

        System.out.println(restaurantManager.getNumberOfUnfinishedOrders());

        restaurantManager.removeMealfromStack(11);

        try {
            orderId4 = restaurantManager.createOrder(3, 11, 4,LocalDateTime.now());
        } catch (RestaurantException e) {
            System.err.println(e.getMessage());
        }

        try{
            restaurantManager.exportDataToFiles(GlobalVariables.getRecipeStackFilename(), GlobalVariables.getOrderstackfilename());
        } catch (RestaurantException e){
            System.err.println(e.getMessage());
        }

        restaurantManager.clearSystem();

        try{
            restaurantManager.importDataFromFiles(GlobalVariables.getRecipeStackFilename(), GlobalVariables.getOrderstackfilename());
        } catch (RestaurantException e){
            System.err.println(e.getMessage());
        }

        System.out.println(restaurantManager.getNumberOfMeals());
        System.out.println(restaurantManager.getTotalNumberOfOrders());
        System.out.println(restaurantManager.getStaticOrderId());
        System.out.println(restaurantManager.getNumberOfUnfinishedOrders());

        restaurantManager.printSortedOrderList();


        try{
            System.out.println(restaurantManager.getOrderById(orderId1).getFulfilmentPeriod());
        } catch (RestaurantException e){
            System.err.println(e.getMessage());
        }
        try{
            System.out.println(restaurantManager.getOrderById(orderId2).getFulfilmentPeriod());
        } catch (RestaurantException e){
            System.err.println(e.getMessage());
        }
        try{
            System.out.println(restaurantManager.getOrderById(orderId3).getFulfilmentPeriod());
        } catch (RestaurantException e){
            System.err.println(e.getMessage());
        }
        try{
            System.out.println(restaurantManager.getOrderById(orderId4).getFulfilmentPeriod());
        } catch (RestaurantException e){
            System.err.println(e.getMessage());
        }

        System.out.println(restaurantManager.getAverageFulfilmentTime());

        try{
            System.out.println(restaurantManager.exportOrdersPerTable(-2));
        } catch (RestaurantException e){
            System.err.println(e.getMessage());
        }

        try{
            System.out.println(restaurantManager.exportOrdersPerTable(2));
        } catch (RestaurantException e){
            System.err.println(e.getMessage());
        }

        try{
            System.out.println(restaurantManager.exportOrdersPerTable(1));
        } catch (RestaurantException e){
            System.err.println(e.getMessage());
        }

        System.out.println("Hotovo");
    }

    private static void addMealsToRecipeStack(RestaurantManager manager) {
        try {
            manager.addMealToRecipeStack(1111, new Recipe("Vídeňský řízek v trojobalu s bramborem", BigDecimal.valueOf(120), 15));
        } catch (RestaurantException e){
            System.err.println(e.getMessage());
        }

        try {
            manager.addMealToRecipeStack(2222, new Recipe("Boloňské špagety", BigDecimal.valueOf(110), 8, "bolonske-spagety-01"));
        } catch (RestaurantException e){
            System.err.println(e.getMessage());
        }

        try {
            manager.addMealToRecipeStack(3333, new Recipe("Bramborák", BigDecimal.valueOf(50), 7, "bramborak-01"));
        } catch (RestaurantException e){
            System.err.println(e.getMessage());
        }

        try {
            manager.addMealToRecipeStack(4444, new Recipe("T-bone steak z argentinského býka", BigDecimal.valueOf(210), 20, "T-bone-steak"));
        } catch (RestaurantException e){
            System.err.println(e.getMessage());
        }

        try {
            manager.addMealToRecipeStack(33, new Recipe("Kola-Lokova limonáda", BigDecimal.valueOf(25), 0, "kola-loka-01"));
        } catch (RestaurantException e){
            System.err.println(e.getMessage());
        }

        try {
            manager.addMealToRecipeStack(11, new Recipe("Pivo 10", BigDecimal.valueOf(35), 2, "pivo"));
        } catch (RestaurantException e){
            System.err.println(e.getMessage());
        }

        try {
            manager.addMealToRecipeStack(22, new Recipe("Birel", BigDecimal.valueOf(30), 0, "birel"));
        } catch (RestaurantException e){
            System.err.println(e.getMessage());
        }
    }
}