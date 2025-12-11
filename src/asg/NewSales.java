// package asg;

// import java.util.ArrayList;
// import java.util.Scanner;

// public class NewSales {
//     private static ArrayList<SalesItem> salesDetails = new ArrayList<>();
//     static {
//         salesDetails.add(new SalesItem("S001", "M101", "I001", "NIKE", "First Running Shoes", "Black", 79.98, 1));
//         salesDetails.add(new SalesItem("S002", "M102", "I002", "PUMA", "Athletic Sneakers", "White", 89.99, 9));
//         salesDetails.add(new SalesItem("S003", "M103", "I003", "ADIDAS", "Casual Sneakers", "Grey", 99.97, 3));
//         salesDetails.add(new SalesItem("S004", "M104", "I004", "PUMA", "Athletic Sneakers", "White", 89.49, 7));
//         salesDetails.add(new SalesItem("S005", "M105", "I005", "PUMA", "Athletic Sneakers", "White", 89.79, 5));
//     }
    
//     public static void main() {
//         menuSales();
//     }

//     public static void menuSales() {
//         Scanner scanner = new Scanner(System.in);
//         char menu;

//         do {
//             clearScreen();
//             System.out.println("\n\n\n                                  ======================================");
//             System.out.println("                                           SALES MANAGEMENT MENU      ");
//             System.out.println("                                  ======================================");
//             System.out.println("                                      1 -> Display Sales Information");
//             System.out.println("                                      2 -> Add New Sales Information");
//             System.out.println("                                      3 -> Delete Sales Information");
//             System.out.println("                                      4 -> Modify Sales Information");
//             System.out.println("                                      5 -> Search Sales Information");
//             System.out.println("                                      6 -> Sales Report");
//             System.out.println("                                      0 -> Exit");
//             System.out.println("                                  ======================================");
//             System.out.print("                                  Enter your choice: ");

//             menu = scanner.next().charAt(0);
//             clearScreen();
//             switch (menu) {
//                 case '1' -> displaySales();
//                 case '2' -> addSales();
//                 case '3' -> deleteSales();
//                 case '4' -> modifySales();
//                 case '5' -> searchSales();
//                 case '6' -> reportSales();
//                 case '0' -> {
//                     System.out.println("\nExiting Management Menu...\n");
//                 }
//                 default -> {
//                     System.out.println("Invalid input. Please enter a number between 0 to 6 only.\n");
//                     System.out.print("Press Enter to continue...");
//                     scanner.nextLine();
//                     scanner.nextLine();
//                 }
//             }
//         } while (menu != '0');
//     }

//     public static void displaySales() {
//         Scanner scanner = new Scanner(System.in);

//         System.out.println("+========+=========+=========+=========+=============================+==========+==============+=========+");
//         System.out.println("|SALES ID|MEMBER ID|ITEM CODE|  BRAND  |       ITEM DESCRIPTION      |  COLOUR  |ITEM PRICE(RM)|QTY SALES|");
//         System.out.println("+========+=========+=========+=========+=============================+==========+==============+=========+");

//         int count = 0;
//         for (SalesItem item : salesDetails) {
//             System.out.printf("|  %-6s|  %-7s|  %-7s|%-9s|%-29s|%-10s|%14.2f|%9d|%n", item.getSalesId(), item.getMemberId(), item.getItemCode(), item.getBrand(), item.getItemDescription(), item.getColour(), item.getItemPrice(), item.getQuantitySales());
//             System.out.println("+--------+---------+---------+---------+-----------------------------+----------+--------------+---------+");
//             count++;
//         }

//         System.out.println("\n\n"+count + " records have been display.\n");
//         System.out.print("Press Enter to continue...");
//         scanner.nextLine();
//     }

//     public static void addSales() {
//         Scanner scanner = new Scanner(System.in);
//         char yesNo;
//         int count = 0;

//         do {
//             String itemCode;
//             String itemBrand;
//             String itemColor;
//             double itemPrice;
//             String itemDescription;
//             boolean isDuplicate;
//             int quantitySales;
//             String salesId;
//             String memberId;



//             do {
//                 isDuplicate = false;
//                 System.out.print("\nEnter Sales ID (FOUR characters): ");
//                 salesId = scanner.next();

//                 if (salesId.length() != 4) {
//                     System.out.println("Invalid sales id length. Please enter exactly 4 characters.");
//                     isDuplicate = true;
//                 } else {
//                     for (SalesItem item : salesDetails) {
//                         if (item.getSalesId().equals(salesId)) {
//                             System.out.println("Sales id already exists. Please enter a unique sales id.");
//                             isDuplicate = true;
//                             break;
//                         }
//                     }
//                 }
//             } while (isDuplicate);

//             do {
//                 isDuplicate = false;
//                 System.out.print("\nEnter Member ID (FOUR characters): ");
//                 memberId = scanner.next();

//                 if (memberId.length() != 4) {
//                     System.out.println("Invalid member id length. Please enter exactly 4 characters.");
//                     isDuplicate = true;
//                 } else {
//                     for (SalesItem item : salesDetails) {
//                         if (item.getMemberId().equals(memberId)) {
//                             System.out.println("Member id already exists. Please enter a unique member id.");
//                             isDuplicate = true;
//                             break;
//                         }
//                     }
//                 }
//             } while (isDuplicate);

//             do {
//                 isDuplicate = false;
//                 System.out.print("\nEnter Item Code (FOUR characters): ");
//                 itemCode = scanner.next();

//                 if (itemCode.length() != 4) {
//                     System.out.println("Invalid item code length. Please enter exactly 4 characters.");
//                     isDuplicate = true;
//                 } else {
//                     // Check if the item code already exists
//                     for (SalesItem item : salesDetails) {
//                         if (item.getItemCode().equals(itemCode)) {
//                             System.out.println("Item code already exists. Please enter a unique item code.");
//                             isDuplicate = true;
//                             break;
//                         }
//                     }
//                 }
//             } while (isDuplicate);

//             do {
//                 isDuplicate = false;
//                 System.out.println("\nEnter Brand: ");
//                 System.out.println("1. NIKE");
//                 System.out.println("2. PUMA");
//                 System.out.println("3. ADIDAS");
//                 System.out.println("4. Other");
//                 System.out.print("Enter your choice (1-4): ");

//                 int brandChoice = scanner.nextInt();

//                 switch (brandChoice) {
//                     case 1 -> itemBrand = "NIKE";
//                     case 2 -> itemBrand = "PUMA";
//                     case 3 -> itemBrand = "ADIDAS";
//                     case 4 -> {
//                         scanner.nextLine(); // Consume newline
//                         System.out.print("Enter Other Brand: ");
//                         itemBrand = scanner.nextLine();
//                     }
//                     default -> {
//                         System.out.println("Invalid choice. Setting brand to 'Other'.");
//                         itemBrand = "Other";
//                     }
//                 }
//                 if (itemBrand.length() > 9) {
//                     System.out.println("Brand name is too long. Please enter below 9 characters.");
//                     isDuplicate = true;
//                 } else if (itemBrand.length() == 0) {
//                     System.out.println("Please enter brand name!!!");
//                     isDuplicate = true;
//                 }
//             } while (isDuplicate);


//             do {
//                 isDuplicate = false;
//                 scanner.nextLine();
//                 System.out.print("\nEnter Item Description: ");
//                 itemDescription = scanner.nextLine();
//                 if (itemDescription.length() > 29) {
//                     System.out.println("Item description is too long. Please enter below 30 characters.");
//                     isDuplicate = true;
//                 } else if (itemDescription.length() == 0) {
//                     System.out.println("Please enter item description!!!");
//                     isDuplicate = true;
//                 }
//             } while (isDuplicate);


//             do {
//                 isDuplicate = false;

//                 System.out.println("\nChoose Item Color:");
//                 System.out.println("1. Black");
//                 System.out.println("2. White");
//                 System.out.println("3. Grey");
//                 System.out.println("4. Red");
//                 System.out.println("5. Other");
//                 System.out.print("Enter your choice (1-5): ");


//                 int colorChoice = scanner.nextInt();

//                 switch (colorChoice) {
//                     case 1 -> itemColor = "Black";
//                     case 2 -> itemColor = "White";
//                     case 3 -> itemColor = "Grey";
//                     case 4 -> itemColor = "Red";
//                     case 5 -> {
//                         scanner.nextLine(); // Consume newline
//                         System.out.print("Enter Other Color: ");
//                         itemColor = scanner.nextLine();
//                     }
//                     default -> {
//                         System.out.println("Invalid choice. Setting color to 'Other'.");
//                         itemColor = "Other";
//                     }
//                 }
//                 if (itemColor.length() > 10) {
//                     System.out.println("Color name is too long. Please enter below 6 characters.");
//                     isDuplicate = true;
//                     //colorChoice=5;
//                 } else if (itemColor.length() == 0) {
//                     System.out.println("Please enter colour name!!!");
//                     isDuplicate = true;
//                 }
//             } while (isDuplicate);


//             do {
//                 System.out.print("\nEnter Item Price:RM ");
//                 itemPrice = scanner.nextDouble();
//                 if (itemPrice > 999999999999.99) {
//                     System.out.println("Item price exceeds the maximum allowed amount of RM999999999.99.");
//                 }
//             } while (itemPrice > 99999999999.99);



//             System.out.print("\nEnter Item Quantity Sales: ");
//             quantitySales = scanner.nextInt();


//             System.out.println("\n\nSALES ID        :" + salesId);
//             System.out.println("MEMBER ID       :" + memberId);
//             System.out.println("ITEM CODE       :" + itemCode);
//             System.out.println("BRAND           :" + itemBrand);
//             System.out.println("ITEM DESCRIPTION:" + itemDescription);
//             System.out.println("COLOUR          :" + itemColor);
//             System.out.println("ITEM PRICE      :RM" + itemPrice);
//             System.out.println("QUANTITY SALES  :" + quantitySales);

//             do {
//                 System.out.println("\n\nDo you suare want to add this new sales information? (Y/N): ");
//                 yesNo = scanner.next().charAt(0);
//                 if (yesNo != 'y' && yesNo != 'Y' && yesNo != 'n' && yesNo != 'N') {
//                     System.out.println("Enter y, Y, n, or N only!!!");
//                 } else if (yesNo == 'y' || yesNo == 'Y') {
//                     SalesItem item = new SalesItem(salesId, memberId, itemCode, itemBrand, itemDescription, itemColor, itemPrice, quantitySales);
//                     salesDetails.add(item);
//                     count++;
//                 }
//             } while (yesNo != 'y' && yesNo != 'Y' && yesNo != 'n' && yesNo != 'N');


//             do {
//                 System.out.print("\n\nDo you want to continue entering new items? (Y/N): ");
//                 yesNo = scanner.next().charAt(0);
//                 if (yesNo != 'y' && yesNo != 'Y' && yesNo != 'n' && yesNo != 'N') {
//                     System.out.println("Enter y, Y, n, or N only!!!");
//                 }
//             } while (yesNo != 'y' && yesNo != 'Y' && yesNo != 'n' && yesNo != 'N');


            

//         } while (yesNo == 'y' || yesNo == 'Y');
        
//         System.out.println("\n\n"+count + " records have been added.\n");
//         System.out.print("Press Enter to continue...");
//         scanner.nextLine();
//         scanner.nextLine();
//     }

//     public static void deleteSales() {
//         Scanner scanner = new Scanner(System.in);

//         int found = 0;
//         int count=0;
//         char yesNo;

                
//         do {            
//             System.out.print("\nEnter Sales ID to delete: ");
//             String SalesIdToDelete = scanner.next();
        
//             for (SalesItem item : salesDetails) {
//                 if (item.getSalesId().equals(SalesIdToDelete)) {
//                     System.out.println("\n\nItem Found:");
//                     System.out.println("\nSALES ID        :" + item.getSalesId());
//                     System.out.println("MEMBER ID       :" + item.getMemberId());
//                     System.out.println("ITEM CODE       :" + item.getItemCode());
//                     System.out.println("BRAND           :" + item.getBrand());
//                     System.out.println("ITEM DESCRIPTION:" + item.getItemDescription());
//                     System.out.println("COLOUR          :" + item.getColour());
//                     System.out.println("ITEM PRICE      :RM" + item.getItemPrice());
//                     System.out.println("QUANTITY SALES  :" + item.getQuantitySales());
                    
//                     found++;

//                     System.out.print("\nAre you sure you want to delete this record? (Y/N): ");
//                     char confirm = scanner.next().charAt(0);

//                     if (Character.toUpperCase(confirm) == 'Y') {
//                         salesDetails.remove(item);
//                         System.out.println("\nSales ID with " + SalesIdToDelete + " has been deleted.");
//                         count++;
//                     } else {
//                         System.out.println("\n\nDeletion canceled.");
                        
//                     }
//                     break;
//                 }
//             }

//             if (found == 0) {
//                 System.out.println("\nSales ID with " + SalesIdToDelete + " not found.");

//             }
//             found = 0;
//             do {
//                 System.out.print("\n\nDo you want to continue deleted items? (Y/N): ");
//                 yesNo = scanner.next().charAt(0);
//                 if (yesNo != 'y' && yesNo != 'Y' && yesNo != 'n' && yesNo != 'N') {
//                     System.out.println("Enter y, Y, n, or N only!!!");
//                 }
//             } while (yesNo != 'y' && yesNo != 'Y' && yesNo != 'n' && yesNo != 'N');

//         } while (yesNo == 'y' || yesNo == 'Y');
        
//         System.out.println("\n\n"+count + " records have been deleted.\n");
//         System.out.print("\nPress Enter to continue...");
//         scanner.nextLine();
//         scanner.nextLine();

//     }

//     public static void modifySales() {
//         Scanner scanner = new Scanner(System.in);

//         int count=0;
//         char yesNo;
//         int found = 0;
//         String newSalesId;
//         String newMemberId;
//         String newItemCode;
//         String newItemBrand;
//         String newItemDescription;
//         String newItemColor;
//         double newItemPrice;
//         int newQuantitySales;
//         String SalesIdToModify;

//         boolean isDuplicate;

//         do {            
//             System.out.print("\nEnter Sales ID to modify: ");
//             SalesIdToModify = scanner.next();
        
//             for (SalesItem item : salesDetails) {
//                 if (item.getSalesId().equals(SalesIdToModify)) {
//                     System.out.println("\n\nItem Found:");
//                     System.out.println("\nSALES ID        :" + item.getSalesId());
//                     System.out.println("MEMBER ID       :" + item.getMemberId());
//                     System.out.println("ITEM CODE       :" + item.getItemCode());
//                     System.out.println("BRAND           :" + item.getBrand());
//                     System.out.println("ITEM DESCRIPTION:" + item.getItemDescription());
//                     System.out.println("COLOUR          :" + item.getColour());
//                     System.out.println("ITEM PRICE      :RM" + item.getItemPrice());
//                     System.out.println("QUANTITY SALES  :" + item.getQuantitySales());
                    
//                     found++;

                    
                    
//                 }
//             }

//             if (found == 0) {
//                 System.out.println("\nSales ID with " + SalesIdToModify + " not found.");
//                 found = 0;
//             } else {
//                 do {
//                     isDuplicate = false;
//                     System.out.print("\nEnter new Sales ID (FOUR characters): ");
//                     newSalesId = scanner.next();

//                     if (newSalesId.length() != 4) {
//                         System.out.println("\nInvalid sales id length. Please enter exactly 4 characters.");
//                         isDuplicate = true;
//                     } else {
//                         for (SalesItem item : salesDetails) {
//                             if (item.getSalesId().equals(newSalesId)) {
//                                 System.out.println("\nSales id already exists. Please enter a unique sales id.");
//                                 isDuplicate = true;
//                                 break;
//                             }
//                         }
//                     }
//                 } while (isDuplicate);


//                 do {
//                     isDuplicate = false;
//                     System.out.print("\nEnter new Member ID (FOUR characters): ");
//                     newMemberId = scanner.next();

//                     if (newMemberId.length() != 4) {
//                         System.out.println("\nInvalid member id length. Please enter exactly 4 characters.");
//                         isDuplicate = true;
//                     } else {
//                         for (SalesItem item : salesDetails) {
//                             if (item.getMemberId().equals(newMemberId)) {
//                                 System.out.println("\nMember id already exists. Please enter a unique member id.");
//                                 isDuplicate = true;
//                                 break;
//                             }
//                         }
//                     }
//                 } while (isDuplicate);


//                 do {
//                     isDuplicate = false;
//                     System.out.print("\nEnter new Item Code (FOUR characters): ");
//                     newItemCode = scanner.next();

//                     if (newItemCode.length() != 4) {
//                         System.out.println("\nInvalid item code length. Please enter exactly 4 characters.");
//                         isDuplicate = true;
//                     } else {
//                         for (SalesItem item : salesDetails) {
//                             if (item.getItemCode().equals(newItemCode)) {
//                                 System.out.println("\nItem code already exists. Please enter a unique item code.");
//                                 isDuplicate = true;
//                                 break;
//                             }
//                         }
//                     }
//                 } while (isDuplicate);


//                 do {
//                     isDuplicate = false;
//                     System.out.println("\nEnter new Brand: ");
//                     System.out.println("1. NIKE");
//                     System.out.println("2. PUMA");
//                     System.out.println("3. ADIDAS");
//                     System.out.println("4. Other");
//                     System.out.print("Enter your choice (1-4): ");

//                     int brandChoice = scanner.nextInt();

//                     switch (brandChoice) {
//                         case 1 -> newItemBrand = "NIKE";
//                         case 2 -> newItemBrand = "PUMA";
//                         case 3 -> newItemBrand = "ADIDAS";
//                         case 4 -> {
//                             scanner.nextLine(); // Consume newline
//                             System.out.print("\nEnter Other New Brand: ");
//                             newItemBrand = scanner.nextLine();
//                         }
//                         default -> {
//                             System.out.println("\nInvalid choice. Setting new brand to 'Other'.");
//                             newItemBrand = "Other";
//                         }
//                     }
//                     if (newItemBrand.length() > 9) {
//                         System.out.println("\nNew brand name is too long. Please enter below 9 characters.");
//                         isDuplicate = true;
//                     } else if (newItemBrand.length() == 0) {
//                         System.out.println("\nPlease enter new brand name!!!");
//                         isDuplicate = true;
//                     }
//                 } while (isDuplicate);


//                 do {
//                     isDuplicate = false;
//                     scanner.nextLine();
//                     System.out.print("\nEnter New Item Description: ");
//                     newItemDescription = scanner.nextLine();
//                     if (newItemDescription.length() > 29) {
//                         System.out.println("New item description is too long. Please enter below 30 characters.");
//                         isDuplicate = true;
//                     } else if (newItemDescription.length() == 0) {
//                         System.out.println("Please enter new item description!!!");
//                         isDuplicate = true;
//                     }
//                 } while (isDuplicate);


//                 do {
//                     isDuplicate = false;

//                     System.out.println("\nChoose Item Color:");
//                     System.out.println("1. Black");
//                     System.out.println("2. White");
//                     System.out.println("3. Grey");
//                     System.out.println("4. Red");
//                     System.out.println("5. Other");
//                     System.out.print("Enter your choice (1-5): ");


//                     int colorChoice = scanner.nextInt();

//                     switch (colorChoice) {
//                         case 1 -> newItemColor = "Black";
//                         case 2 -> newItemColor = "White";
//                         case 3 -> newItemColor = "Grey";
//                         case 4 -> newItemColor = "Red";
//                         case 5 -> {
//                             scanner.nextLine(); // Consume newline
//                             System.out.print("\nEnter Other Color: ");
//                             newItemColor = scanner.nextLine();
//                         }
//                         default -> {
//                             System.out.println("\nInvalid choice. Setting new color to 'Other'.");
//                             newItemColor = "Other";
//                         }
//                     }
//                     if (newItemColor.length() > 10) {
//                         System.out.println("\nNew color name is too long. Please enter below 11 characters.");
//                         isDuplicate = true;
//                         //colorChoice=5;
//                     } else if (newItemColor.length() == 0) {
//                         System.out.println("\nPlease enter new colour name!!!");
//                         isDuplicate = true;
//                     }
//                 } while (isDuplicate);


//                 do {
//                     System.out.print("\nEnter New Item Price:RM ");
//                     newItemPrice = scanner.nextDouble();
//                     if (newItemPrice > 999999999999.99) {
//                         System.out.println("\nItem price exceeds the maximum allowed amount of RM999999999.99.");
//                     }
//                 } while (newItemPrice > 99999999999.99);



//                 System.out.print("\nEnter Item Quantity Sales: ");
//                 newQuantitySales = scanner.nextInt();


//                 System.out.println("\n\n\nSales Information After modify: ");
//                 System.out.println("\nSALES ID        :" + newSalesId);
//                 System.out.println("MEMBER ID       :" + newMemberId);
//                 System.out.println("ITEM CODE       :" + newItemCode);
//                 System.out.println("BRAND           :" + newItemBrand);
//                 System.out.println("ITEM DESCRIPTION:" + newItemDescription);
//                 System.out.println("COLOUR          :" + newItemColor);
//                 System.out.println("ITEM PRICE      :RM" + newItemPrice);
//                 System.out.println("QUANTITY SALES  :" + newQuantitySales);


//                 do {
//                     System.out.print("\nAre you sure you want to modify this record? (Y/N): ");
//                     yesNo = scanner.next().charAt(0);
//                     if (yesNo != 'y' && yesNo != 'Y' && yesNo != 'n' && yesNo != 'N') {
//                         System.out.println("Enter y, Y, n, or N only!!!");
//                     }
//                 } while (yesNo != 'y' && yesNo != 'Y' && yesNo != 'n' && yesNo != 'N');

//                 if (Character.toUpperCase(yesNo) == 'Y') {
//                     for (SalesItem item : salesDetails) {
//                         if (item.getSalesId().equals(SalesIdToModify)) {
//                             item.setSalesId(newSalesId);
//                             item.setMemberId(newMemberId);
//                             item.setItemCode(newItemCode);
//                             item.setBrand(newItemBrand);
//                             item.setItemDescription(newItemDescription);
//                             item.setColour(newItemColor);
//                             item.setItemPrice(newItemPrice);
//                             item.setQuantitySales(newQuantitySales);
//                         }
//                     }

//                     System.out.println("\nSales ID with " + SalesIdToModify + " has been modify.");
//                     count++;
//                 } else {
//                     System.out.println("\n\nModify canceled.");

//                 }
//                 //break;
//             }
            
//             do {
//                 System.out.print("\n\nDo you want to continue modify items? (Y/N): ");
//                 yesNo = scanner.next().charAt(0);
//                 if (yesNo != 'y' && yesNo != 'Y' && yesNo != 'n' && yesNo != 'N') {
//                     System.out.println("Enter y, Y, n, or N only!!!");
//                 }
//             } while (yesNo != 'y' && yesNo != 'Y' && yesNo != 'n' && yesNo != 'N');

//         } while (yesNo == 'y' || yesNo == 'Y');
        
//         System.out.println("\n\n"+count + " records have been modify.\n");
//         System.out.print("\nPress Enter to continue...");
//         scanner.nextLine();
//         scanner.nextLine();
//     }

//     public static void searchSales() {
//         Scanner scanner = new Scanner(System.in);
//         char menu;
//         char yesNo;

                
//         do {            
//             System.out.println("\n\n\n                                  --------------------------------------");
//             System.out.println("                                                SEARCH MENU             ");
//             System.out.println("                                  --------------------------------------");
//             System.out.println("                                      1 -> Search by SALES ID");
//             System.out.println("                                      2 -> Search by MEMBER ID");
//             System.out.println("                                      3 -> Search by ITEM CODE");
//             System.out.println("                                      4 -> Search by BRAND");
//             System.out.println("                                      5 -> Search by ITEM DESCRIPTION");
//             System.out.println("                                      6 -> Search by COLOUR");
//             System.out.println("                                      7 -> Search by ITEM PRICE");
//             System.out.println("                                      8 -> Search by QUANTITY SALES");            System.out.println("                                      0 -> Exit");
//             System.out.println("                                  --------------------------------------");
//             System.out.print("                                  Enter your choice: ");

//             menu = scanner.next().charAt(0);
//             clearScreen();
//             switch (menu) {
//                 case '1' -> searchSalesId();
//                 case '2' -> searchMemberId();
//                 case '3' -> searchItemCode();
//                 case '4' -> searchBrand();
//                 case '5' -> searchItemDescription();
//                 case '6' -> searchColour();
//                 case '7' -> searchItemPrice();
//                 case '8' -> searchQuantitySales();
//                 case '0' -> {
//                     System.out.println("\nExiting Search Menu...\n");
//                 }
//                 default -> System.out.println("Invalid input. Please enter a number between 0 to 8 only.\n");
//             }            
                        
//             do {
//                 System.out.print("\n\nDo you want to continue search items? (Y/N): ");
//                 yesNo = scanner.next().charAt(0);
//                 if (yesNo != 'y' && yesNo != 'Y' && yesNo != 'n' && yesNo != 'N') {
//                     System.out.println("Enter y, Y, n, or N only!!!");
//                 }
//             } while (yesNo != 'y' && yesNo != 'Y' && yesNo != 'n' && yesNo != 'N');
//         } while (yesNo == 'y' || yesNo == 'Y');
        
//         System.out.print("\nPress Enter to back sales management menu...");
//         scanner.nextLine();
//         scanner.nextLine();
        
//     }

//     public static void reportSales() {
//         Scanner scanner = new Scanner(System.in);
//         int saleItem = 0;
//         int quantity = 0;
//         double subTotal = 0.00;

//         System.out.println("+========+=========+=========+=========+=============================+==========+==============+=========+");
//         System.out.println("|SALES ID|MEMBER ID|ITEM CODE|  BRAND  |       ITEM DESCRIPTION      |  COLOUR  |ITEM PRICE(RM)|QTY SALES|");
//         System.out.println("+========+=========+=========+=========+=============================+==========+==============+=========+");

//         for (SalesItem  item : salesDetails) {
//             double total = item.getQuantitySales() * item.getItemPrice();
//             System.out.printf("|  %-6s|  %-7s|  %-7s|%-9s|%-29s|%-10s|%14.2f|%9d|%n", item.getSalesId(), item.getMemberId(), item.getItemCode(), item.getBrand(), item.getItemDescription(), item.getColour(), item.getItemPrice(), item.getQuantitySales());
//             System.out.println("+--------+---------+---------+---------+-----------------------------+----------+--------------+---------+");
//             saleItem++;
//             quantity += item.getQuantitySales();
//             subTotal += total;
//         }

//         System.out.println("\n\nList Sales Item      = " + saleItem);
//         System.out.println("Total Quantity Sales = " + quantity);
//         System.out.println("SubTotal Sales       = RM" + String.format("%.2f", subTotal) + "\n");
//         System.out.print("Press Enter to continue...");
//         scanner.nextLine();
//     }
    
//     public static void clearScreen() {
//         for (int i = 0; i < 50; i++) {
//             System.out.println(); // Print 50 newlines to clear the screen
//         }
//     }

    
//     private static void searchSalesId() {
//         Scanner scanner = new Scanner(System.in);
//         int found = 0;
//         System.out.print("Enter Sales ID to search: ");
//             String itemCodeToSearch = scanner.next();
//             for (SalesItem item : salesDetails) {
//                 if (item.getSalesId().equals(itemCodeToSearch)) {
//                     System.out.println("\n\nItem Found:");
//                     System.out.println("\nSALES ID        :" + item.getSalesId());
//                     System.out.println("MEMBER ID       :" + item.getMemberId());
//                     System.out.println("ITEM CODE       :" + item.getItemCode());
//                     System.out.println("BRAND           :" + item.getBrand());
//                     System.out.println("ITEM DESCRIPTION:" + item.getItemDescription());
//                     System.out.println("COLOUR          :" + item.getColour());
//                     System.out.println("ITEM PRICE      :RM" + item.getItemPrice());
//                     System.out.println("QUANTITY SALES  :" + item.getQuantitySales());
//                     found++;
//                 }
//             }
//             if (found == 0) {
//                 System.out.println("\nSales ID with " + itemCodeToSearch + " not found.");
//             }
//     }
    
//     private static void searchMemberId() {
//     Scanner scanner = new Scanner(System.in);
//     int found = 0;
//     System.out.print("Enter Member ID to search: ");
//         String memberIdToSearch = scanner.next();
//         for (SalesItem item : salesDetails) {
//             if (item.getMemberId().equals(memberIdToSearch)) {
//                 System.out.println("\n\nItem Found:");
//                 System.out.println("\nSALES ID        :" + item.getSalesId());
//                 System.out.println("MEMBER ID       :" + item.getMemberId());
//                 System.out.println("ITEM CODE       :" + item.getItemCode());
//                 System.out.println("BRAND           :" + item.getBrand());
//                 System.out.println("ITEM DESCRIPTION:" + item.getItemDescription());
//                 System.out.println("COLOUR          :" + item.getColour());
//                 System.out.println("ITEM PRICE      :RM" + item.getItemPrice());
//                 System.out.println("QUANTITY SALES  :" + item.getQuantitySales());
//                 found++;
//             }
//         }
//         if (found == 0) {
//             System.out.println("\nMember ID with " + memberIdToSearch + " not found.");
//         }
//     }

//     private static void searchItemCode() {
//         Scanner scanner = new Scanner(System.in);
//         int found = 0;
//         System.out.print("Enter Item Code to search: ");
//         String itemCodeToSearch = scanner.next();
//         for (SalesItem item : salesDetails) {
//             if (item.getItemCode().equals(itemCodeToSearch)) {
//                 System.out.println("\n\nItem Found:");
//                 System.out.println("\nSALES ID        :" + item.getSalesId());
//                 System.out.println("MEMBER ID       :" + item.getMemberId());
//                 System.out.println("ITEM CODE       :" + item.getItemCode());
//                 System.out.println("BRAND           :" + item.getBrand());
//                 System.out.println("ITEM DESCRIPTION:" + item.getItemDescription());
//                 System.out.println("COLOUR          :" + item.getColour());
//                 System.out.println("ITEM PRICE      :RM" + item.getItemPrice());
//                 System.out.println("QUANTITY SALES  :" + item.getQuantitySales());
//                 found++;
//             }
//         }
//         if (found == 0) {
//             System.out.println("\nItem Code with " + itemCodeToSearch + " not found.");
//         }
//     }
    
//     private static void searchBrand() {
//         Scanner scanner = new Scanner(System.in);
//         int found = 0;
//         System.out.print("Enter Brand to search: ");
//         String brandToSearch = scanner.nextLine();
//         for (SalesItem item : salesDetails) {
//             if (item.getBrand().equals(brandToSearch)) {
//                 found++;
//                 System.out.println("\n\nItem Found: "+found+")");
//                 System.out.println("\nSALES ID        :" + item.getSalesId());
//                 System.out.println("MEMBER ID       :" + item.getMemberId());
//                 System.out.println("ITEM CODE       :" + item.getItemCode());
//                 System.out.println("BRAND           :" + item.getBrand());
//                 System.out.println("ITEM DESCRIPTION:" + item.getItemDescription());
//                 System.out.println("COLOUR          :" + item.getColour());
//                 System.out.println("ITEM PRICE      :RM" + item.getItemPrice());
//                 System.out.println("QUANTITY SALES  :" + item.getQuantitySales());
//             }
//         }
//         if (found == 0) {
//             System.out.println("\nBrand with " + brandToSearch + " not found.");
//         }
//     }
    
//     private static void searchItemDescription() {
//         Scanner scanner = new Scanner(System.in);
//         int found = 0;
//         System.out.print("Enter Description to search: ");
//         String descriptionToSearch = scanner.nextLine();
//         for (SalesItem item : salesDetails) {
//             if (item.getItemDescription().equals(descriptionToSearch)) {
//                 found++;
//                 System.out.println("\n\nItem Found: "+found+")");
//                 System.out.println("\nSALES ID        :" + item.getSalesId());
//                 System.out.println("MEMBER ID       :" + item.getMemberId());
//                 System.out.println("ITEM CODE       :" + item.getItemCode());
//                 System.out.println("BRAND           :" + item.getBrand());
//                 System.out.println("ITEM DESCRIPTION:" + item.getItemDescription());
//                 System.out.println("COLOUR          :" + item.getColour());
//                 System.out.println("ITEM PRICE      :RM" + item.getItemPrice());
//                 System.out.println("QUANTITY SALES  :" + item.getQuantitySales());
//             }
//         }
//         if (found == 0) {
//             System.out.println("\nDescription with " + descriptionToSearch + " not found.");
//         }
//     }
    
//     private static void searchColour() {
//         Scanner scanner = new Scanner(System.in);
//         int found = 0;
//         System.out.print("Enter Colour to search: ");
//         String colourToSearch = scanner.nextLine();
//         for (SalesItem item : salesDetails) {
//             if (item.getColour().equals(colourToSearch)) {
//                 found++;
//                 System.out.println("\n\nItem Found: "+found+")");
//                 System.out.println("\nSALES ID        :" + item.getSalesId());
//                 System.out.println("MEMBER ID       :" + item.getMemberId());
//                 System.out.println("ITEM CODE       :" + item.getItemCode());
//                 System.out.println("BRAND           :" + item.getBrand());
//                 System.out.println("ITEM DESCRIPTION:" + item.getItemDescription());
//                 System.out.println("COLOUR          :" + item.getColour());
//                 System.out.println("ITEM PRICE      :RM" + item.getItemPrice());
//                 System.out.println("QUANTITY SALES  :" + item.getQuantitySales());
//             }
//         }
//         if (found == 0) {
//             System.out.println("\nColour with " + colourToSearch + " not found.");
//         }
//     }
    
//     private static void searchItemPrice() {
//         Scanner scanner = new Scanner(System.in);
//         int found = 0;
//         System.out.print("Enter Item Price to search:RM ");
//         double itemPriceToSearch = scanner.nextDouble();

//         for (SalesItem item : salesDetails) {
//             if (item.getItemPrice() == itemPriceToSearch) {
//                 found++;
//                 System.out.println("\nItem Found: " + found+")");
//                 System.out.println("\nSALES ID        :" + item.getSalesId());
//                 System.out.println("MEMBER ID       :" + item.getMemberId());
//                 System.out.println("ITEM CODE       :" + item.getItemCode());
//                 System.out.println("BRAND           :" + item.getBrand());
//                 System.out.println("ITEM DESCRIPTION:" + item.getItemDescription());
//                 System.out.println("COLOUR          :" + item.getColour());
//                 System.out.println("ITEM PRICE      :RM " + item.getItemPrice());
//                 System.out.println("QUANTITY SALES  :" + item.getQuantitySales());
//             }
//         }

//         if (found == 0) {
//             System.out.println("\nItem Price with RM" + itemPriceToSearch + " not found.");
//         }
//     }
    
//     private static void searchQuantitySales() {
//         Scanner scanner = new Scanner(System.in);
//         int found = 0;
//         System.out.print("Enter Quantity Sales to search: ");
//         int quantitySalesToSearch = scanner.nextInt();

//         for (SalesItem item : salesDetails) {
//             if (item.getQuantitySales() == quantitySalesToSearch) {
//                 found++;
//                 System.out.println("\nItem Found: " + found+")");
//                 System.out.println("\nSALES ID        :" + item.getSalesId());
//                 System.out.println("MEMBER ID       :" + item.getMemberId());
//                 System.out.println("ITEM CODE       :" + item.getItemCode());
//                 System.out.println("BRAND           :" + item.getBrand());
//                 System.out.println("ITEM DESCRIPTION:" + item.getItemDescription());
//                 System.out.println("COLOUR          :" + item.getColour());
//                 System.out.println("ITEM PRICE      :RM" + item.getItemPrice());
//                 System.out.println("QUANTITY SALES  :" + item.getQuantitySales());
//             }
//         }

//         if (found == 0) {
//             System.out.println("\nQuantity Sales with " + quantitySalesToSearch + " not found.");
//         }
//     }
// }