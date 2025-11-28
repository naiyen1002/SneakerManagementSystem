package asg;

import java.util.ArrayList;
import java.util.Scanner;

public class MakeOrder {

    private static ArrayList<OrderDetails> orderItems = new ArrayList<>();
    private static ArrayList<StockItem> stockItems = new ArrayList<>();

    private static Scanner scanner = new Scanner(System.in);

    public static void main() {
        MakeOrder stock = new MakeOrder();
        stock.initializeStock();

        int choice = 0;
        boolean invalidChoice;

        do {
            System.out.println("\n\n--------------------------------");
            System.out.println("         Make Order Menu          ");
            System.out.println("--------------------------------\n");
            System.out.println("1. Display All Product Details");
            System.out.println("2. Make New Order");
            System.out.println("3. Search Product Details");
            System.out.println("4. Delete Order");
            System.out.println("5. Check Out/Make Payment");
            System.out.println("6. Exit/Back To Menu\n");

            do {
                invalidChoice = false;
                try {
                    if (!invalidChoice) {
                        do {
                            System.out.print("Choose From 1,2,3,4,5,6 : ");
                            choice = scanner.nextInt();
                            if (choice < 0 || choice > 6) {
                                System.out.println("\nYou Can Only Choose From 1 - 6 Only...\n");
                            }
                        } while (choice < 0 || choice > 6);
                    }
                } catch (Exception exc) {
                    System.out.println("\nEnter Digit Only Helloo...\n");
                    invalidChoice = true;
                }
                scanner.nextLine();
            } while (invalidChoice);

            switch (choice) {
                case 1:
                    displayProductDetails();
                    break;
                case 2:
                    makeOrder();
                    break;
                case 3:
                    searchProduct();
                    break;
                case 4:
                    deleteOrder();
                    break;
                case 5:
                    makePayment();
                    break;
                case 6:
                    System.out.println("\nEXITING MENU...\n");
                    break;
                default:
                    System.out.println("INVALID CHOICE!!! Please Key In Again.\n");
            }
        } while (choice != 6);
    }

    // ALL PRODUCT DETAILS
    public void initializeStock() {
        StockItem item1 = new StockItem("I001", "NIKE", "Air Max 90", "Black/White/Grey", 480.00, 50);
        StockItem item2 = new StockItem("I002", "NIKE", "Revolution 5", "Blue/White", 220.00, 30);
        StockItem item3 = new StockItem("I003", "NIKE", "Jordan 1 Mid", "Red/Black/White", 570.00, 25);
        StockItem item4 = new StockItem("I004", "NIKE", "Roshe One", "Green/Black", 340.00, 40);
        StockItem item5 = new StockItem("I005", "NIKE", "Flex Experience RN 10", "Grey/Blue", 280.00, 35);
        StockItem item6 = new StockItem("I006", "NIKE", "Air Max 270", "Pink/White", 600.00, 45);
        StockItem item7 = new StockItem("I007", "NIKE", "Tanjun", "Purple/Grey", 220.00, 20);
        StockItem item8 = new StockItem("I008", "NIKE", "React Element 55", "Teal/Black", 500.00, 28);
        StockItem item9 = new StockItem("I009", "NIKE", "Free RN 5.0", "Coral/White", 260.00, 22);
        StockItem item10 = new StockItem("I010", "NIKE", "Air Force 1", "Lavender/White", 330.00, 38);
        StockItem item11 = new StockItem("I011", "PUMA", "Suede Classic", "Black/White", 290.00, 33);
        StockItem item12 = new StockItem("I012", "PUMA", "Future Rider", "Blue/Red/White", 320.00, 15);
        StockItem item13 = new StockItem("I013", "PUMA", "Cell Venom", "Grey/Yellow/Black", 440.00, 27);
        StockItem item14 = new StockItem("I014", "PUMA", "Carson 2", "Navy/White", 230.00, 22);
        StockItem item15 = new StockItem("I015", "PUMA", "RS-X³", "Black/Green", 370.00, 28);
        StockItem item16 = new StockItem("I016", "PUMA", "Cali", "White/Pink", 360.00, 25);
        StockItem item17 = new StockItem("I017", "PUMA", "Nova", "Peach/Beige", 380.00, 30);
        StockItem item18 = new StockItem("I018", "PUMA", "Basket Heart", "Pastel Blue", 290.00, 40);
        StockItem item19 = new StockItem("I019", "PUMA", "Muse 2", "Lavender/Grey", 400.00, 20);
        StockItem item20 = new StockItem("I020", "PUMA", "Vikky", "Black/Rose Gold", 180.00, 15);
        StockItem item21 = new StockItem("I021", "ADIDAS", "Originals Superstar", "White/Black", 330.00, 32);
        StockItem item22 = new StockItem("I022", "ADIDAS", "Ultraboost 21", "Black/White", 800.00, 18);
        StockItem item23 = new StockItem("I023", "ADIDAS", "NMD R1", "Grey/Red/Blue", 550.00, 25);
        StockItem item24 = new StockItem("I024", "ADIDAS", "Cloudfoam Ultimate", "Navy/White", 280.00, 28);
        StockItem item25 = new StockItem("I025", "ADIDAS", "Gazelle", "Black/Gold", 300.00, 22);
        StockItem item26 = new StockItem("I026", "ADIDAS", "Stan Smith", "White/Pink", 310.00, 30);
        StockItem item27 = new StockItem("I027", "ADIDAS", "Sleek", "Pastel Blue", 360.00, 25);
        StockItem item28 = new StockItem("I028", "ADIDAS", "Swift Run", "Coral/White", 380.00, 20);
        StockItem item29 = new StockItem("I029", "ADIDAS", "Continental 80", "Pink/White", 320.00, 28);
        StockItem item30 = new StockItem("I030", "ADIDAS", "Falcon", "Black/Rose Gold", 380.00, 15);

        stockItems.add(item1);
        stockItems.add(item2);
        stockItems.add(item3);
        stockItems.add(item4);
        stockItems.add(item5);
        stockItems.add(item6);
        stockItems.add(item7);
        stockItems.add(item8);
        stockItems.add(item9);
        stockItems.add(item10);
        stockItems.add(item11);
        stockItems.add(item12);
        stockItems.add(item13);
        stockItems.add(item14);
        stockItems.add(item15);
        stockItems.add(item16);
        stockItems.add(item17);
        stockItems.add(item18);
        stockItems.add(item19);
        stockItems.add(item20);
        stockItems.add(item21);
        stockItems.add(item22);
        stockItems.add(item23);
        stockItems.add(item24);
        stockItems.add(item25);
        stockItems.add(item26);
        stockItems.add(item27);
        stockItems.add(item28);
        stockItems.add(item29);
        stockItems.add(item30);
    }

    private static void displayProductDetails() {
        System.out.printf("\n--------------------------------\n");
        System.out.printf("     All Product Information\n");
        System.out.printf("--------------------------------\n");
        System.out.printf("\n%-12s %-12s %-25s %-20s %-12s %-18s", "Item Code", "Brands", "Item Description", "Color",
                "Price/Unit", "Stock Available");
        System.out.printf("\n%-12s %-12s %-25s %-20s %-12s %-18s\n", "---------", "------", "--------------------",
                "-----------------", "----------", "---------------");
        for (StockItem item : stockItems) {
            String itemCode = item.getItemCode();
            String itemBrand = item.getBrand();
            String itemDescription = item.getItemDescription();
            String itemColor = item.getColour();
            double itemPrice = item.getItemPrice();
            int quantityInStock = item.getQuantityInStock();

            System.out.printf("%-12s %-12s %-25s %-20s RM %-12.2f %-18d\n", itemCode, itemBrand, itemDescription,
                    itemColor, itemPrice,
                    quantityInStock);
        }
    }

    private static void makeOrder() {

        int i;
        String orderCodeID;
        boolean match = false;
        String addToBasket;
        char confirmAdd = 'n';
        String addAnotherToCart;
        char conAddAnotherToCart = 'n';

        double currentTotal = 0;

        do {
            System.out.println("\n--------------------------------");
            System.out.println("        MAKE ORDER        ");
            System.out.println("--------------------------------");
            match = false;

            System.out.printf("\nEnter Product Item Code That Need To Order --> ");
            orderCodeID = scanner.nextLine();
            if (orderCodeID.isEmpty()) {
                System.out.printf("\nType Something If You Want to Order...\n");
            } else {
                for (i = 0; i < stockItems.size(); i++) {
                    if (stockItems.get(i).getItemCode().equalsIgnoreCase(orderCodeID)) {
                        match = true;
                        System.out.println("\n===============================================");
                        System.out.printf("Item Code : %s", stockItems.get(i).getItemCode());
                        System.out.printf("\nBrands : %s", stockItems.get(i).getBrand());
                        System.out.printf("\nItem Description : %s", stockItems.get(i).getItemDescription());
                        System.out.printf("\nColor : %s", stockItems.get(i).getColour());
                        System.out.printf("\nPrice/Unit : RM %.2f", stockItems.get(i).getItemPrice());
                        System.out.printf("\nStock Available : %d", stockItems.get(i).getQuantityInStock());
                        System.out.printf("\n===============================================\n");

                        System.out.printf("\nAre You Want To Add This Item To Basket ? (y=Yes/n=No) : ");
                        addToBasket = scanner.nextLine();

                        while (addToBasket.isEmpty()) {
                            System.out.println("\nNo Input Provided...");
                            System.out.printf("\nAre You Want To Add This Item To Basket (y=Yes/n=No) : ");
                            addToBasket = scanner.nextLine();
                        }

                        confirmAdd = addToBasket.charAt(0);

                        while (Character.toUpperCase(confirmAdd) != 'Y' && Character.toUpperCase(confirmAdd) != 'N') {
                            System.out.print("\nINVALID RESPONSE!!! Please Enter y=Yes or n=No : ");
                            addToBasket = scanner.nextLine();

                            while (addToBasket.isEmpty()) {
                                System.out.println("\nNo Input Provided...");
                                System.out.printf("\nAre You Want To Add This Item To Basket (y=Yes/n=No) : ");
                                addToBasket = scanner.nextLine();
                            }

                            confirmAdd = addToBasket.charAt(0);
                        }

                        String orderCode = stockItems.get(i).getItemCode();
                        String orderBrand = stockItems.get(i).getBrand();
                        String orderDescription = stockItems.get(i).getItemDescription();
                        String orderColor = stockItems.get(i).getColour();
                        double orderPrice = stockItems.get(i).getItemPrice();

                        OrderDetails addTocart = new OrderDetails(orderCode, orderBrand, orderDescription, orderColor,
                                orderPrice);

                        if (Character.toUpperCase(confirmAdd) == 'Y') {
                            orderItems.add(addTocart);
                            System.out.printf("\nADD TO BASKET SUCCESSFUL!!!\n");

                            currentTotal = currentTotal + orderPrice;

                            System.out.printf("\nCurrent Sub-total = RM %.2f\n", currentTotal);

                        } else {
                            System.out.printf("\nADD CANCEL...\n");
                        }
                    } else if (i + 1 != stockItems.size()) {
                        continue;
                    }
                    if (!match) {
                        System.out.printf("\nItem Code's NOT MATCH!!!\n");
                        break;
                    }
                }
            }

            System.out.printf("\nWant To Add Another Item(s) To Basket ? (y=Yes/n=No) : ");
            addAnotherToCart = scanner.nextLine();

            while (addAnotherToCart.isEmpty()) {
                System.out.println("\nNo Input Provided...");
                System.out.printf("\nAre You Want To Add This Item To Basket (y=Yes/n=No) : ");
                addToBasket = scanner.nextLine();
            }

            conAddAnotherToCart = addAnotherToCart.charAt(0);

            while (Character.toUpperCase(conAddAnotherToCart) != 'Y'
                    && Character.toUpperCase(conAddAnotherToCart) != 'N') {
                System.out.print("\nINVALID RESPONSE!!! Please Enter y=Yes or n=No : ");
                addAnotherToCart = scanner.nextLine();

                while (addAnotherToCart.isEmpty()) {
                    System.out.println("\nNo Input Provided...");
                    System.out.printf("\nAre You Want To Add This Item To Basket (y=Yes/n=No) : ");
                    addAnotherToCart = scanner.nextLine();
                }

                conAddAnotherToCart = addAnotherToCart.charAt(0);
            }

        } while (Character.toUpperCase(conAddAnotherToCart) == 'Y');
    }

    private static void searchProduct() {

        boolean invalidSearchChoice;
        int choice = 0;
        boolean match = false;
        String searchCode;
        String addToBasket;
        char confirmAdd = 'n';
        double currentTotal = 0;
        String searchAnotherOrder;
        char conSearchAnotherOrder = 'n';

        int i;
        String searchBrand;

        do {
            System.out.println("\n--------------------------------");
            System.out.println("   Search Item(s) Information\t\t");
            System.out.println("--------------------------------\n");
            System.out.println("1. Item Code");
            System.out.println("2. Brands");
            match = false;

            do {
                invalidSearchChoice = false;
                try {
                    if (!invalidSearchChoice) {
                        do {
                            System.out.printf("\nEnter 1/2 To Search Item(s) Informations : ");
                            choice = scanner.nextInt();
                            if (choice < 0 || choice > 2) {
                                System.out.println("\nYou Can Only Choose 1 Or 2 Only...\n");
                            }
                        } while (choice < 0 || choice > 2);
                    }
                } catch (Exception exc) {
                    System.out.println("\nEnter Digit Only Helloo...");
                    invalidSearchChoice = true;
                }
                scanner.nextLine();
            } while (invalidSearchChoice);

            switch (choice) {
                case 1:
                    System.out.printf("\nItem Code --> ");
                    searchCode = scanner.nextLine();

                    if (searchCode.isEmpty()) {
                        System.out.printf("\nType Something If You Want to Search...\n");
                    } else {
                        for (i = 0; i < stockItems.size(); i++) {
                            if (stockItems.get(i).getItemCode().equalsIgnoreCase(searchCode)) {
                                match = true;
                                System.out.println("\n===============================================");
                                System.out.printf("Item Code : %s", stockItems.get(i).getItemCode());
                                System.out.printf("\nBrands : %s", stockItems.get(i).getBrand());
                                System.out.printf("\nItem Description : %s", stockItems.get(i).getItemDescription());
                                System.out.printf("\nColor : %s", stockItems.get(i).getColour());
                                System.out.printf("\nPrice/Unit : RM %.2f", stockItems.get(i).getItemPrice());
                                System.out.printf("\nStock Available : %d", stockItems.get(i).getQuantityInStock());
                                System.out.printf("\n===============================================\n");

                                System.out.printf("\nAre You Want To Add This Item To Basket ? (y=Yes/n=No) : ");
                                addToBasket = scanner.nextLine();

                                while (addToBasket.isEmpty()) {
                                    System.out.println("\nNo Input Provided...");
                                    System.out.printf("\nAre You Want To Add This Item To Basket (y=Yes/n=No) : ");
                                    addToBasket = scanner.nextLine();
                                }

                                confirmAdd = addToBasket.charAt(0);

                                while (Character.toUpperCase(confirmAdd) != 'Y'
                                        && Character.toUpperCase(confirmAdd) != 'N') {
                                    System.out.print("\nINVALID RESPONSE!!! Please Enter y=Yes or n=No : ");
                                    addToBasket = scanner.nextLine();

                                    while (addToBasket.isEmpty()) {
                                        System.out.println("\nNo Input Provided...");
                                        System.out.printf("\nAre You Want To Add This Item To Basket (y=Yes/n=No) : ");
                                        addToBasket = scanner.nextLine();
                                    }

                                    confirmAdd = addToBasket.charAt(0);
                                }

                                String orderCode = stockItems.get(i).getItemCode();
                                String orderBrand = stockItems.get(i).getBrand();
                                String orderDescription = stockItems.get(i).getItemDescription();
                                String orderColor = stockItems.get(i).getColour();
                                double orderPrice = stockItems.get(i).getItemPrice();

                                OrderDetails addTocart = new OrderDetails(orderCode, orderBrand, orderDescription,
                                        orderColor,
                                        orderPrice);

                                if (Character.toUpperCase(confirmAdd) == 'Y') {
                                    orderItems.add(addTocart);
                                    System.out.printf("\nADD TO BASKET SUCCESSFUL!!!\n");

                                    currentTotal = currentTotal + orderPrice;

                                    System.out.printf("\nCurrent Sub-total = RM %.2f\n", currentTotal);

                                } else {
                                    System.out.printf("\nADD CANCEL...\n");
                                }
                            } else if (i < stockItems.size() - 1) {
                                continue;
                            }
                            if (!match) {
                                System.out.printf("\nSearch Item's Code NOT MATCH!!!\n");
                                break;
                            }
                        }
                    }

                    System.out.printf("\nWant Search Another Item(s) Information ? (y=Yes/n=No) : ");
                    searchAnotherOrder = scanner.nextLine();

                    while (searchAnotherOrder.isEmpty()) {
                        System.out.println("\nNo Input Provided...");
                        System.out.printf("\nWant Search Another Item(s) Information ? (y=Yes/n=No) : ");
                        searchAnotherOrder = scanner.nextLine();
                    }

                    conSearchAnotherOrder = searchAnotherOrder.charAt(0);

                    while (Character.toUpperCase(conSearchAnotherOrder) != 'Y'
                            && Character.toUpperCase(conSearchAnotherOrder) != 'N') {
                        System.out.print("\nINVALID RESPONSE!!! Please Enter y=Yes or n=No : ");
                        searchAnotherOrder = scanner.nextLine();

                        while (searchAnotherOrder.isEmpty()) {
                            System.out.println("\nNo Input Provided...");
                            System.out.printf("\nWant Search Another Item(s) Information ? (y=Yes/n=No) : ");
                            searchAnotherOrder = scanner.nextLine();
                        }

                        conSearchAnotherOrder = searchAnotherOrder.charAt(0);
                    }
                    break;

                case 2:
                    System.out.printf("\nEnter Brand (NIKE/PUMA/ADIDAS) --> ");
                    searchBrand = scanner.nextLine();

                    if (searchBrand.isEmpty()) {
                        System.out.printf("\nType Something If You Want to Search...\n");
                    } else {
                        for (i = 0; i < stockItems.size(); i++) {
                            if (stockItems.get(i).getBrand().equalsIgnoreCase(searchBrand)) {
                                match = true;
                                System.out.println("\n===============================================");
                                System.out.printf("Item Code : %s", stockItems.get(i).getItemCode());
                                System.out.printf("\nBrands : %s", stockItems.get(i).getBrand());
                                System.out.printf("\nItem Description : %s", stockItems.get(i).getItemDescription());
                                System.out.printf("\nColor : %s", stockItems.get(i).getColour());
                                System.out.printf("\nPrice/Unit : RM %.2f", stockItems.get(i).getItemPrice());
                                System.out.printf("\nStock Available : %d", stockItems.get(i).getQuantityInStock());
                                System.out.printf("\n===============================================\n");
                            } else if (i < stockItems.size() - 1) {
                                continue;
                            }
                            if (!match) {
                                System.out.printf(
                                        "\nSearch Item's Brand NOT MATCH!!! Please Type NIKE/PUMA/ADIDAS Only.\n");
                                break;
                            }
                        }
                    }

                    System.out.printf("\nWant Search Another Item(s) Information ? (y=Yes/n=No) : ");
                    searchAnotherOrder = scanner.nextLine();

                    while (searchAnotherOrder.isEmpty()) {
                        System.out.println("\nNo Input Provided...");
                        System.out.printf("\nWant Search Another Item(s) Information ? (y=Yes/n=No) : ");
                        searchAnotherOrder = scanner.nextLine();
                    }

                    conSearchAnotherOrder = searchAnotherOrder.charAt(0);

                    while (Character.toUpperCase(conSearchAnotherOrder) != 'Y'
                            && Character.toUpperCase(conSearchAnotherOrder) != 'N') {
                        System.out.print("\nINVALID RESPONSE!!! Please Enter y=Yes or n=No : ");
                        searchAnotherOrder = scanner.nextLine();

                        while (searchAnotherOrder.isEmpty()) {
                            System.out.println("\nNo Input Provided...");
                            System.out.printf("\nWant Search Another Item(s) Information ? (y=Yes/n=No) : ");
                            searchAnotherOrder = scanner.nextLine();
                        }

                        conSearchAnotherOrder = searchAnotherOrder.charAt(0);
                    }
                    break;

            }

        } while (Character.toUpperCase(conSearchAnotherOrder) == 'Y');
    }

    private static void deleteOrder() {

        int found = 0;
        String itemOrderToDel;
        int i;
        String delAnotherOrder;
        char conDelAnotherOrder = 'n';
        String confirmDelOrder;
        char conLanFirmDelOrder = 'n';

        do {
            System.out.println("\n--------------------------------");
            System.out.println("        DELETE ORDER        ");
            System.out.println("--------------------------------");

            for (OrderDetails delOrder : orderItems) {
                System.out.println("\n===============================================");
                System.out.printf("Item Code : %s", delOrder.getOrderCode());
                System.out.printf("\nBrands : %s", delOrder.getOrderBrand());
                System.out.printf("\nItem Description : %s", delOrder.getOrderDescription());
                System.out.printf("\nColor : %s", delOrder.getOrderColor());
                System.out.printf("\nPrice/Unit : RM %.2f", delOrder.getOrderPrice());
                System.out.printf("\n===============================================\n");
            }

            System.out.printf("\nEnter The Item Code To Delete The Order : ");
            itemOrderToDel = scanner.nextLine();
            for (i = 0; i < orderItems.size(); i++) {
                if (orderItems.get(i).getOrderCode().equalsIgnoreCase(itemOrderToDel)) {
                    found++;
                }
            }

            if (found == 0) {
                System.out.printf("\nNo Item Code Found With This Member ID = %s", itemOrderToDel);
                System.out.printf("\n\nDelete Another Item Code ? (yes=y/no=n) : ");
                delAnotherOrder = scanner.nextLine();

                while (delAnotherOrder.isEmpty()) {
                    System.out.println("\nNo Input Provided...");
                    System.out.printf(
                            "\nDelete Another Item Code ? (yes=y/no=n) : ");
                    delAnotherOrder = scanner.nextLine();
                }

                conDelAnotherOrder = delAnotherOrder.charAt(0);

                while (Character.toUpperCase(conDelAnotherOrder) != 'Y'
                        && Character.toUpperCase(conDelAnotherOrder) != 'N') {
                    System.out.print("\nINVALID RESPONSE!!! Please Enter y=Yes or n=No : ");
                    delAnotherOrder = scanner.nextLine();

                    while (delAnotherOrder.isEmpty()) {
                        System.out.println("\nNo Input Provided...");
                        System.out.printf("\nDelete Another Item Code ? (yes=y/no=n) : ");
                        delAnotherOrder = scanner.nextLine();
                    }

                    conDelAnotherOrder = delAnotherOrder.charAt(0);
                    break;
                }

            } else {
                System.out.printf("\nConfirm To Delete The Item ? (y=Yes/n=No) : ");
                confirmDelOrder = scanner.nextLine();

                while (confirmDelOrder.isEmpty()) {
                    System.out.println("\nNo Input Provided...");
                    System.out.printf(
                            "\nConfirm To Delete The Item ? (y=Yes/n=No) : ");
                    confirmDelOrder = scanner.nextLine();
                }

                conLanFirmDelOrder = confirmDelOrder.charAt(0);

                while (Character.toUpperCase(conLanFirmDelOrder) != 'Y'
                        && Character.toUpperCase(conLanFirmDelOrder) != 'N') {
                    System.out.print("\nINVALID RESPONSE!!! Please Enter y=Yes or n=No : ");
                    confirmDelOrder = scanner.nextLine();

                    while (confirmDelOrder.isEmpty()) {
                        System.out.println("\nNo Input Provided...");
                        System.out.printf("\nConfirm To Delete The Item ? (y=Yes/n=No) : ");
                        confirmDelOrder = scanner.nextLine();
                    }

                    conLanFirmDelOrder = confirmDelOrder.charAt(0);
                }

                if (Character.toUpperCase(conLanFirmDelOrder) == 'Y') {
                    for (i = 0; i < orderItems.size(); i++) {
                        if (orderItems.get(i).getOrderCode().equalsIgnoreCase(itemOrderToDel)) {
                            orderItems.remove(i);
                        }
                    }
                    System.out.printf("\n%s This Order Has Been Delete SUCCESSFUL!!!", itemOrderToDel);
                } else {
                    System.out.printf("\nDELETION CANCEL...");
                }

                System.out.printf("\n\nDelete Another Item Code ? (yes=y/no=n) : ");
                delAnotherOrder = scanner.nextLine();

                while (delAnotherOrder.isEmpty()) {
                    System.out.println("\nNo Input Provided...");
                    System.out.printf(
                            "\nDelete Another Item Code ? (yes=y/no=n) : ");
                    delAnotherOrder = scanner.nextLine();
                }

                conDelAnotherOrder = delAnotherOrder.charAt(0);

                while (Character.toUpperCase(conDelAnotherOrder) != 'Y'
                        && Character.toUpperCase(conDelAnotherOrder) != 'N') {
                    System.out.print("\nINVALID RESPONSE!!! Please Enter y=Yes or n=No : ");
                    delAnotherOrder = scanner.nextLine();

                    while (delAnotherOrder.isEmpty()) {
                        System.out.println("\nNo Input Provided...");
                        System.out.printf("\nDelete Another Item Code ? (yes=y/no=n) : ");
                        delAnotherOrder = scanner.nextLine();
                    }

                    conDelAnotherOrder = delAnotherOrder.charAt(0);
                }
            }

        } while (Character.toUpperCase(conDelAnotherOrder) == 'Y');
    }

    private static void makePayment() {

        double subTotal = 0;
        int qtyOrder = 0;

        System.out.printf("\n=========================$ $ $=========================");
        System.out.printf("\n                        RECEIPT              ");
        System.out.printf("\n=========================$ $ $=========================\n");
        System.out.printf("\n%-8s %-23s %-20s %-12s", "Brands", "Item Description", "Color", "Price/Unit");
        System.out.printf("\n%-8s %-23s %-20s %-12s\n", "------", "--------------------", "-----------------",
                "----------");
        for (OrderDetails order : orderItems) {
            System.out.printf("%-8s %-23s %-20s RM %-12.2f\n", order.getOrderBrand(), order.getOrderDescription(),
                    order.getOrderColor(), order.getOrderPrice());
            subTotal += order.getOrderPrice();
            qtyOrder++;
        }
        System.out.printf("\n\nTOTAL QUANTITY ITEM(S) --> %d", qtyOrder);
        System.out.printf("\nSUB TOTAL --> RM %.2f", subTotal);

    }
}
