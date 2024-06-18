import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class Restaurant {
    private static List<Integer> ordersIds = new ArrayList<>();

    public static void main(String[] args) {
        RestaurantManager restaurantManager = new RestaurantManager();

        try{
            addMealsToRecipeStack(restaurantManager);
            makeOrders(restaurantManager);
            changeOrders(restaurantManager);
            printInfo(restaurantManager);

            //Data saving to disk
            restaurantManager.exportDataToFiles(GlobalVariables.getRecipeStackFilename(), GlobalVariables.getOrderstackfilename());
            System.out.println("Data uložena na disk");

            //        system clearance
            restaurantManager.clearSystem();
            System.out.println("==Data smazána ze systému===============================================================");

            printInfo(restaurantManager);

            //Data loading from disk
            restaurantManager.importDataFromFiles(GlobalVariables.getRecipeStackFilename(), GlobalVariables.getOrderstackfilename());
            System.out.println("==Data načtena z disku do  systému======================================================");

            printInfo(restaurantManager);

        } catch (RestaurantException e){
            System.err.println(e.getMessage());
        }
        System.out.println("**************");
        System.out.println("**  Hotovo  **");
        System.out.println("**************");

    }

    private static void printInfo(RestaurantManager restaurantManager) throws RestaurantException{
        System.out.println();
        System.out.println(restaurantManager.exportOrdersPerTable(15));

        System.out.println();
        System.out.println(restaurantManager.exportOrdersPerTable(2));

        System.out.println();
        System.out.println("Počet jídel: " + restaurantManager.getNumberOfMeals());
        System.out.println("Celkový počet objednávek: " + restaurantManager.getTotalNumberOfOrders());
        System.out.println("Počet nevyřízených objednávek: " + restaurantManager.getNumberOfUnfinishedOrders());

        System.out.println();
        System.out.println("Objednávky setříděné podle času:");
        restaurantManager.printSortedOrderList();
        System.out.println("Průměrný čas vyřízení objednávky: " + restaurantManager.getAverageFulfilmentTime());

        System.out.println();
        System.out.println("Jídla objednaná dnes: ");
        restaurantManager.printMealsOrderedToday();
        System.out.println();
    }

    private static void changeOrders(RestaurantManager restaurantManager) throws RestaurantException{
        //Change of status - order "Kofola" for table 15 to Served
            //index 2 = orderId 3
            restaurantManager.changeOrder(ordersIds.get(2), OrderStatus.SERVED, LocalDateTime.now().minusMinutes(6));
        //Change of status - order for table 2 to Paid
            //index 5 = orderId 6
            restaurantManager.changeOrder(ordersIds.get(5), OrderStatus.PAID, LocalDateTime.now().minusMinutes(10));
            //index 3 = orderId 4
            restaurantManager.changeOrder(ordersIds.get(3), OrderStatus.PAID, LocalDateTime.now());
            //index 4 = orderId 5
            restaurantManager.changeOrder(ordersIds.get(4), OrderStatus.PAID, LocalDateTime.now());
    }

    private static void makeOrders(RestaurantManager manager) throws RestaurantException {
        //order for table 15(according to task)
            //index 0, id 1
            ordersIds.add(manager.createOrder(15, 1111, 2, LocalDateTime.now().minusMinutes(11)));
            //index 1, id 2
            ordersIds.add(manager.createOrder(15, 2222, 2,LocalDateTime.now().minusMinutes(11)));
            //index 2, id 3
            ordersIds.add(manager.createOrder(15, 33, 2,LocalDateTime.now().minusMinutes(11)));

        //order for table 2
            //index 3 = orderId 4
            ordersIds.add(manager.createOrder(2, 3333, 3, LocalDateTime.now().minusMinutes(15)));
            //index 4 = orderId 5
            ordersIds.add(manager.createOrder(2, 2222, 3,LocalDateTime.now().minusMinutes(15)));
            //index 5 = orderId 6
            ordersIds.add(manager.createOrder(2, 33, 3,LocalDateTime.now().minusMinutes(13)));
    }
    private static void addMealsToRecipeStack(RestaurantManager manager) throws RestaurantException {
           manager.addMealToRecipeStack(1111, new Recipe("Kuřecí řízek obalovaný 150g", BigDecimal.valueOf(120), 15));
           manager.addMealToRecipeStack(2222, new Recipe("Hranolky 150g", BigDecimal.valueOf(40), 10, "Hranolky01"));
           manager.addMealToRecipeStack(3333, new Recipe("Pstruh na víně", BigDecimal.valueOf(135), 15, "pstruh-01"));
           manager.addMealToRecipeStack(4444, new Recipe("T-bone steal z argentinského býka 200g", BigDecimal.valueOf(235), 25));
           manager.addMealToRecipeStack(5555, new Recipe("Bramborák 150g", BigDecimal.valueOf(55), 15));
           manager.addMealToRecipeStack(11, new Recipe("Pivo 10", BigDecimal.valueOf(30), 3, "Pivo"));
           manager.addMealToRecipeStack(12, new Recipe("Birel", BigDecimal.valueOf(30), 0, "Birel"));
           manager.addMealToRecipeStack(33, new Recipe("Kofola 0.5l", BigDecimal.valueOf(30), 3, "kofola-01"));
    }
}