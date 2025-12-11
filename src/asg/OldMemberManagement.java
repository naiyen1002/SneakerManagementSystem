
// package asg;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Scanner;

// import asg.Model.MemberDetails;

// public class OldMemberManagement {

//     private static ArrayList<MemberDetails> memberList = new ArrayList<>();
//     private static ArrayList<SalesItem> salesDetails = new ArrayList<>();
//     private static Scanner scanner = new Scanner(System.in);
//     public static Object main;

//     public static void main() {

//         dateJoin joinDate1 = new dateJoin(04, 8, 2012);
//         dateJoin joinDate2 = new dateJoin(03, 12, 2000);
//         dateJoin joinDate3 = new dateJoin(22, 12, 2010);
//         dateJoin joinDate4 = new dateJoin(17, 05, 2011);
//         dateJoin joinDate5 = new dateJoin(20, 07, 2013);
//         memberList.add(new MemberDetails("M101", "ginwei", "Male", "040909-34-1304", 1156988920, joinDate1));
//         memberList.add(new MemberDetails("M102", "jinsheng", "Male", "040322-34-1478", 182259156, joinDate2));
//         memberList.add(new MemberDetails("M103", "weihong", "Female", "046767-87-1100", 176871038, joinDate3));
//         memberList.add(new MemberDetails("M104", "edwin", "Male", "089867-88-1188", 102583900, joinDate4));
//         memberList.add(new MemberDetails("M105", "jinkhai", "Male", "097766-69-1001", 1158914822, joinDate5));

//         salesDetails.add(new SalesItem("S001", "M101", "I001", "NIKE", "First Running Shoes", "Black", 79.98, 1));
//         salesDetails.add(new SalesItem("S002", "M102", "I002", "PUMA", "Athletic Sneakers", "White", 89.99, 9));
//         salesDetails.add(new SalesItem("S003", "M103", "I003", "ADIDAS", "Casual Sneakers", "Grey", 99.97, 3));
//         salesDetails.add(new SalesItem("S004", "M104", "I004", "PUMA", "Athletic Sneakers", "White", 89.49, 7));
//         salesDetails.add(new SalesItem("S005", "M105", "I005", "PUMA", "Athletic Sneakers", "White", 89.79, 5));

//         int choice = 0;
//         boolean invalidChoice;
//         do {
//             System.out.println("\n\n--------------------------------");
//             System.out.println("\tMember Main Menu\t\t");
//             System.out.println("--------------------------------\n");
//             System.out.println("1. Display All Members Details");
//             System.out.println("2. Add New Records");
//             System.out.println("3. Search For Member Records");
//             System.out.println("4. Modify Member's Records");
//             System.out.println("5. Delete Member's Records");
//             System.out.println("6. Show All Type Of Member");
//             System.out.println("7. Exit/Back To Menu\n");

//             do {
//                 invalidChoice = false;
//                 try {
//                     if (!invalidChoice) {
//                         do {
//                             System.out.print("Choose From 1,2,3,4,5,6,7 : ");
//                             choice = scanner.nextInt();
//                             if (choice < 0 || choice > 7) {
//                                 System.out.println("\nYou Can Only Choose From 1 - 7 Only...\n");
//                             }
//                         } while (choice < 0 || choice > 7);
//                     }
//                 } catch (Exception exc) {
//                     System.out.println("\nEnter Digit Only Helloo...\n");
//                     invalidChoice = true;
//                     scanner.nextLine();
//                 }
//             } while (invalidChoice);

//             switch (choice) {
//                 case 1:
//                     DisplayMemberDetails();
//                     break;
//                 case 2:
//                     addRecord();
//                     break;
//                 case 3:
//                     searchMember();
//                     break;
//                 case 4:
//                     modify();
//                     break;
//                 case 5:
//                     deleteInfo();
//                     break;
//                 case 6:
//                     reportInfo();
//                     break;
//                 case 7:
//                     System.out.println("\nEXITING MENU...\n");
//                     break;
//                 default:
//                     System.out.println("INVALID CHOICE!!! Please Key In Again.\n");
//             }
//         } while (choice != 7);
//         System.out.println("END OF PROCESSING...\n");
//     }

//     // Display Member Details
//     private static void DisplayMemberDetails() {
//         System.out.println("\n--------------------------------");
//         System.out.println("     All Member Information\t\t");
//         System.out.println("--------------------------------\n");
//         System.out.printf("%-11s %-15s\t%s\t%s\t%s\t%s\n", "Member ID", "Name", "Gender", "IC Number",
//                 "Contact Number (+60)", "Date Become Member (dd/mm/yyyy)");
//         System.out.println(
//                 "=========   ================\t======\t==============\t====================\t===============================");
//         for (MemberDetails Member : memberList) {
//             System.out.printf("%-11s %-15s\t%s\t%s\t%d\t\t%d/%d/%d\n", Member.getId(), Member.getName(),
//                     Member.getGender(), Member.getIcNumber(), Member.getMemberContact(), Member.getDateJoin().getDay(),
//                     Member.getDateJoin().getMonth(), Member.getDateJoin().getYear());
//         }
//         System.out.println("\n\n<<< Total " + memberList.size() + " Member(s) >>>\n");
//     }

//     // Ask Sure To Add
//     //// V1
//     // private static boolean confirmAddRecords() {
//     // System.out.print("Are You Sure Want To Add Record(s) (y=Yes/n=No) ? : ");
//     // char response = scanner.nextLine().charAt(0);
//     // scanner.nextLine();
//     // while (Character.toUpperCase(response) != 'Y' &&
//     // Character.toUpperCase(response) != 'N') {
//     // System.out.print("\nINVALID RESPONSE!!! Please Enter y=Yes or n=No : ");
//     // response = scanner.nextLine().charAt(0);
//     // }
//     // return Character.toUpperCase(response) == 'Y';
//     // }
//     //// V2
//     private static boolean confirmAddRecords() {
//         System.out.print("\nAre You Sure Want To Add Record(s) (y=Yes/n=No) ? : ");
//         String input = scanner.nextLine();
//         if (input.isEmpty()) {
//             input = scanner.nextLine();
//         }

//         while (input.isEmpty()) {
//             System.out.println("\nNo input provided.");
//             System.out.print("\nAre You Sure Want To Add Record(s) (y=Yes/n=No) ? : ");
//             input = scanner.nextLine();
//         }

//         char response = input.charAt(0);

//         while (Character.toUpperCase(response) != 'Y' && Character.toUpperCase(response) != 'N') {
//             System.out.print("\nINVALID RESPONSE!!! Please Enter y=Yes or n=No : ");
//             input = scanner.nextLine();

//             while (input.isEmpty()) {
//                 System.out.println("\nNo input provided.");
//                 System.out.print("\nAre You Sure Want To Add Record(s) (y=Yes/n=No) ? : ");
//                 input = scanner.nextLine();
//             }

//             response = input.charAt(0);
//         }

//         return Character.toUpperCase(response) == 'Y';
//     }

//     private static void addRecord() {
//         char another, sure;
//         int i = 0;

//         if (confirmAddRecords()) {
//             do {
//                 System.out.println("\n--------------------------------");
//                 System.out.println("     Add New Member Records\t\t");
//                 System.out.println("--------------------------------");

//                 String memberid;
//                 boolean memberIdExists;

//                 do {
//                     memberIdExists = false;
//                     System.out.print("\nMember ID : ");
//                     memberid = scanner.nextLine();
//                     if (memberid.isEmpty()) {
//                         memberid = scanner.nextLine();
//                     }

//                     for (i = 0; i < memberList.size(); i++) {
//                         if (memberList.get(i).getId().equalsIgnoreCase(memberid)) {
//                             System.out.printf(
//                                     "\n%s This Member ID Is Already Exist... Please Enter Another Member ID\n",
//                                     memberid);
//                             memberIdExists = true;
//                             break;
//                         }
//                     }
//                 } while (memberIdExists);

//                 System.out.print("\nName : ");
//                 String memberName = scanner.nextLine();

//                 System.out.print("\nGender Male / Female / Others: ");
//                 String gender = scanner.nextLine();
//                 while (!gender.equalsIgnoreCase("Male") && !gender.equalsIgnoreCase("Female")
//                         && !gender.equalsIgnoreCase("Others")
//                         && !gender.equalsIgnoreCase("male") && !gender.equalsIgnoreCase("female")
//                         && !gender.equalsIgnoreCase("others")) {
//                     System.out.println("\nInvalid Gender... Please Enter Male, Female Or Others");
//                     System.out.print("\nGender Male / Female / Others: ");
//                     gender = scanner.nextLine();
//                 }

//                 System.out.print("\nIC Number (xxxxxx-xx-xxxx): ");
//                 String icNumber = scanner.nextLine();

//                 System.out.print("\nContact Number (+60): ");
//                 int memberContact = (Integer.parseInt(scanner.nextLine()));

//                 System.out.print("\nDate Become A Member (dd/mm/yyyy): ");
//                 String[] dateParts = scanner.nextLine().split("/");
//                 int day = Integer.parseInt(dateParts[0]);
//                 int month = Integer.parseInt(dateParts[1]);
//                 int year = Integer.parseInt(dateParts[2]);
//                 dateJoin dateJoin = new dateJoin(day, month, year);

//                 MemberDetails memberde = new MemberDetails(memberid, memberName, gender, icNumber, memberContact,
//                         dateJoin);

//                 System.out.print("\nAre You Sure You Want To Add This Record (y=Yes/n=No)? : ");
//                 sure = scanner.nextLine().charAt(0);
//                 while (Character.toUpperCase(sure) != 'Y' && Character.toUpperCase(sure) != 'N') {
//                     System.out.print("\nINVALID RESPONSE!!! Please Enter y = Yes or n = No : ");
//                     sure = scanner.nextLine().charAt(0);
//                 }

//                 if (Character.toUpperCase(sure) == 'Y') {
//                     memberList.add(memberde);
//                     System.out.print("\nADD SUCCESSFUL!!!\n");
//                 } else {
//                     System.out.printf("\nADD CANCEL...\n");
//                 }

//                 System.out.print("\nAdd Another Record (y=Yes/n=No)? : ");
//                 another = scanner.nextLine().charAt(0);
//                 while (Character.toUpperCase(another) != 'Y' && Character.toUpperCase(another) != 'N') {
//                     System.out.print("\nINVALID RESPONSE!!! Please Enter y = Yes or n = No : ");
//                     another = scanner.nextLine().charAt(0);
//                     System.out.println();
//                 }

//             } while (Character.toUpperCase(another) == 'Y');
//         }
//     }

//     private static boolean confirmSearch() {
//         System.out.print("\nAre You Sure Want To Search Record(s) (y=Yes/n=No) ? : ");
//         String input = scanner.nextLine();
//         if (input.isEmpty()) {
//             input = scanner.nextLine();
//         }

//         while (input.isEmpty()) {
//             System.out.println("\nNo Input Provided...");
//             System.out.print("\nAre You Sure Want To Search Record(s) (y=Yes/n=No) ? : ");
//             input = scanner.nextLine();
//         }

//         char response = input.charAt(0);

//         while (Character.toUpperCase(response) != 'Y' && Character.toUpperCase(response) != 'N') {
//             System.out.print("\nINVALID RESPONSE!!! Please Enter y=Yes or n=No : ");
//             input = scanner.nextLine();

//             while (input.isEmpty()) {
//                 System.out.println("\nNo Input Provided...");
//                 System.out.print("\nAre You Sure Want To Search Record(s) (y=Yes/n=No) ? : ");
//                 input = scanner.nextLine();
//             }

//             response = input.charAt(0);
//         }

//         return Character.toUpperCase(response) == 'Y';
//     }

//     private static void searchMember() {

//         String memberid;
//         String searchName;
//         String searchGender;
//         String searchIcnumber;
//         int contactNum = 0;
//         int day = 0, month = 0, year = 0;
//         String searchAnother;
//         char response = 'n';
//         boolean found = false;
//         int choice = 0;
//         boolean invalidSearchChoice;

//         int i;

//         if (confirmSearch()) {

//             do {
//                 System.out.println("\n--------------------------------");
//                 System.out.println("   Search Member Information\t\t");
//                 System.out.println("--------------------------------\n");
//                 System.out.println("1. Member ID");
//                 System.out.println("2. Name");
//                 System.out.println("3. Gender Male / Female / Others");
//                 System.out.println("4. IC Number (xxxxxx-xx-xxxx)");
//                 System.out.println("5. Contact Number (+60)");
//                 System.out.println("6. Date Become A Member (dd/mm/yyyy)");
//                 found = false;

//                 do {
//                     invalidSearchChoice = false;
//                     try {
//                         if (!invalidSearchChoice) {
//                             do {
//                                 System.out.printf("\nEnter (1 - 6) To Search Member's Informations : ");
//                                 choice = scanner.nextInt();
//                                 if (choice < 0 || choice > 6) {
//                                     System.out.println("\nYou Can Only Choose From 1 - 6 Only...\n");
//                                 }
//                             } while (choice < 0 || choice > 6);
//                         }
//                     } catch (Exception exc) {
//                         System.out.println("\nEnter Digit Only Helloo...");
//                         invalidSearchChoice = true;
//                         scanner.nextLine();
//                     }
//                 } while (invalidSearchChoice);

//                 switch (choice) {
//                     case 1:
//                         System.out.print("\n--> Member ID : ");
//                         scanner.nextLine();
//                         memberid = scanner.nextLine();
//                         if (memberid.isEmpty()) {
//                             System.out.printf("\nType Something If You Want to Search...\n");
//                         } else {
//                             for (i = 0; i < memberList.size(); i++) {
//                                 if (memberList.get(i).getId().equalsIgnoreCase(memberid)) {
//                                     found = true;
//                                     System.out.println("\n===============================================");
//                                     System.out.printf("Member ID : %s", memberList.get(i).getId());
//                                     System.out.printf("\nName : %s", memberList.get(i).getName());
//                                     System.out.printf("\nGender : %s", memberList.get(i).getGender());
//                                     System.out.printf("\nIC Number : %s", memberList.get(i).getIcNumber());
//                                     System.out.printf("\nContect Number (+60) : %d",
//                                             memberList.get(i).getMemberContact());
//                                     System.out.printf("\nDate Become Member : %d/%d/%d",
//                                             memberList.get(i).getDateJoin().getDay(),
//                                             memberList.get(i).getDateJoin().getMonth(),
//                                             memberList.get(i).getDateJoin().getYear());
//                                     System.out.printf("\n===============================================\n");
//                                 } else if (i < memberList.size() - 1) {
//                                     continue;
//                                 }
//                                 if (!found) {
//                                     System.out.printf("\nSearch Member's Information NOT MATCH!!!\n");
//                                     break;
//                                 }
//                             }
//                         }
//                         System.out.printf("\nWant Search Another Member Information ? (y=Yes/n=No) : ");
//                         searchAnother = scanner.nextLine();

//                         while (searchAnother.isEmpty()) {
//                             System.out.println("\nNo Input Provided...");
//                             System.out.printf("\nWant Search Another Member Information ? (y=Yes/n=No) : ");
//                             searchAnother = scanner.nextLine();
//                         }

//                         response = searchAnother.charAt(0);

//                         while (Character.toUpperCase(response) != 'Y' && Character.toUpperCase(response) != 'N') {
//                             System.out.print("\nINVALID RESPONSE!!! Please Enter y=Yes or n=No : ");
//                             searchAnother = scanner.nextLine();

//                             while (searchAnother.isEmpty()) {
//                                 System.out.println("\nNo Input Provided...");
//                                 System.out.printf("\nWant Search Another Member Information ? (y=Yes/n=No) : ");
//                                 searchAnother = scanner.nextLine();
//                             }

//                             response = searchAnother.charAt(0);
//                         }
//                         break;

//                     case 2:
//                         System.out.print("\n--> Name : ");
//                         scanner.nextLine();
//                         searchName = scanner.nextLine();
//                         if (searchName.isEmpty()) {
//                             System.out.printf("\nType Something If You Want to Search...\n");
//                         } else {
//                             for (i = 0; i < memberList.size(); i++) {
//                                 if (memberList.get(i).getName().equalsIgnoreCase(searchName)) {
//                                     found = true;
//                                     System.out.println("\n===============================================");
//                                     System.out.printf("Member ID : %s", memberList.get(i).getId());
//                                     System.out.printf("\nName : %s", memberList.get(i).getName());
//                                     System.out.printf("\nGender : %s", memberList.get(i).getGender());
//                                     System.out.printf("\nIC Number : %s", memberList.get(i).getIcNumber());
//                                     System.out.printf("\nContect Number (+60) : %d",
//                                             memberList.get(i).getMemberContact());
//                                     System.out.printf("\nDate Become Member : %d/%d/%d",
//                                             memberList.get(i).getDateJoin().getDay(),
//                                             memberList.get(i).getDateJoin().getMonth(),
//                                             memberList.get(i).getDateJoin().getYear());
//                                     System.out.printf("\n===============================================\n");
//                                 } else if (i < memberList.size() - 1) {
//                                     continue;
//                                 }
//                                 if (!found) {
//                                     System.out.printf("\nSearch Member's Information NOT MATCH!!!\n");
//                                     break;
//                                 }
//                             }
//                         }
//                         System.out.printf("\nWant Search Another Member Information ? (y=Yes/n=No) : ");
//                         searchAnother = scanner.nextLine();

//                         while (searchAnother.isEmpty()) {
//                             System.out.println("\nNo Input Provided...");
//                             System.out.printf("\nWant Search Another Member Information ? (y=Yes/n=No) : ");
//                             searchAnother = scanner.nextLine();
//                         }

//                         response = searchAnother.charAt(0);

//                         while (Character.toUpperCase(response) != 'Y' && Character.toUpperCase(response) != 'N') {
//                             System.out.print("\nINVALID RESPONSE!!! Please Enter y=Yes or n=No : ");
//                             searchAnother = scanner.nextLine();

//                             while (searchAnother.isEmpty()) {
//                                 System.out.println("\nNo Input Provided...");
//                                 System.out.printf("\nWant Search Another Member Information ? (y=Yes/n=No) : ");
//                                 searchAnother = scanner.nextLine();
//                             }

//                             response = searchAnother.charAt(0);
//                         }
//                         break;

//                     case 3:
//                         System.out.print("\n--> Gender : ");
//                         scanner.nextLine();
//                         searchGender = scanner.nextLine();
//                         if (searchGender.isEmpty()) {
//                             System.out.printf("\nType Something If You Want to Search...\n");
//                         } else {
//                             for (i = 0; i < memberList.size(); i++) {
//                                 if (memberList.get(i).getGender().equalsIgnoreCase(searchGender)) {
//                                     found = true;
//                                     System.out.println("\n===============================================");
//                                     System.out.printf("Member ID : %s", memberList.get(i).getId());
//                                     System.out.printf("\nName : %s", memberList.get(i).getName());
//                                     System.out.printf("\nGender : %s", memberList.get(i).getGender());
//                                     System.out.printf("\nIC Number : %s", memberList.get(i).getIcNumber());
//                                     System.out.printf("\nContect Number (+60) : %d",
//                                             memberList.get(i).getMemberContact());
//                                     System.out.printf("\nDate Become Member : %d/%d/%d",
//                                             memberList.get(i).getDateJoin().getDay(),
//                                             memberList.get(i).getDateJoin().getMonth(),
//                                             memberList.get(i).getDateJoin().getYear());
//                                     System.out.printf("\n===============================================\n");
//                                 } else if (i < memberList.size() - 1) {
//                                     continue;
//                                 }
//                                 if (!found) {
//                                     System.out.printf("\nSearch Member's Information NOT MATCH!!!\n");
//                                     break;
//                                 }
//                             }
//                         }
//                         System.out.printf("\nWant Search Another Member Information ? (y=Yes/n=No) : ");
//                         searchAnother = scanner.nextLine();

//                         while (searchAnother.isEmpty()) {
//                             System.out.println("\nNo Input Provided...");
//                             System.out.printf("\nWant Search Another Member Information ? (y=Yes/n=No) : ");
//                             searchAnother = scanner.nextLine();
//                         }

//                         response = searchAnother.charAt(0);

//                         while (Character.toUpperCase(response) != 'Y' && Character.toUpperCase(response) != 'N') {
//                             System.out.print("\nINVALID RESPONSE!!! Please Enter y=Yes or n=No : ");
//                             searchAnother = scanner.nextLine();

//                             while (searchAnother.isEmpty()) {
//                                 System.out.println("\nNo Input Provided...");
//                                 System.out.printf("\nWant Search Another Member Information ? (y=Yes/n=No) : ");
//                                 searchAnother = scanner.nextLine();
//                             }

//                             response = searchAnother.charAt(0);
//                         }
//                         break;

//                     case 4:
//                         System.out.print("\n--> IC Number (xxxxxx-xx-xxxx) : ");
//                         scanner.nextLine();
//                         searchIcnumber = scanner.nextLine();
//                         if (searchIcnumber.isEmpty()) {
//                             System.out.printf("\nType Something If You Want to Search...\n");
//                         } else {
//                             for (i = 0; i < memberList.size(); i++) {
//                                 if (memberList.get(i).getIcNumber().equalsIgnoreCase(searchIcnumber)) {
//                                     found = true;
//                                     System.out.println("\n===============================================");
//                                     System.out.printf("Member ID : %s", memberList.get(i).getId());
//                                     System.out.printf("\nName : %s", memberList.get(i).getName());
//                                     System.out.printf("\nGender : %s", memberList.get(i).getGender());
//                                     System.out.printf("\nIC Number : %s", memberList.get(i).getIcNumber());
//                                     System.out.printf("\nContect Number (+60) : %d",
//                                             memberList.get(i).getMemberContact());
//                                     System.out.printf("\nDate Become Member : %d/%d/%d",
//                                             memberList.get(i).getDateJoin().getDay(),
//                                             memberList.get(i).getDateJoin().getMonth(),
//                                             memberList.get(i).getDateJoin().getYear());
//                                     System.out.printf("\n===============================================\n");
//                                 } else if (i < memberList.size() - 1) {
//                                     continue;
//                                 }
//                                 if (!found) {
//                                     System.out.printf("\nSearch Member's Information NOT MATCH!!!\n");
//                                     break;
//                                 }
//                             }
//                         }
//                         System.out.printf("\nWant Search Another Member Information ? (y=Yes/n=No) : ");
//                         searchAnother = scanner.nextLine();

//                         while (searchAnother.isEmpty()) {
//                             System.out.println("\nNo Input Provided...");
//                             System.out.printf("\nWant Search Another Member Information ? (y=Yes/n=No) : ");
//                             searchAnother = scanner.nextLine();
//                         }

//                         response = searchAnother.charAt(0);

//                         while (Character.toUpperCase(response) != 'Y' && Character.toUpperCase(response) != 'N') {
//                             System.out.print("\nINVALID RESPONSE!!! Please Enter y=Yes or n=No : ");
//                             searchAnother = scanner.nextLine();

//                             while (searchAnother.isEmpty()) {
//                                 System.out.println("\nNo Input Provided...");
//                                 System.out.printf("\nWant Search Another Member Information ? (y=Yes/n=No) : ");
//                                 searchAnother = scanner.nextLine();
//                             }

//                             response = searchAnother.charAt(0);
//                         }
//                         break;

//                     case 5:
//                         System.out.print("\n--> Contact Number (+60) : ");
//                         scanner.nextLine();
//                         String contactNumStr = scanner.nextLine();
//                         if (contactNumStr.isEmpty()) {
//                             System.out.printf("\nType Something If You Want to Search...\n");
//                         } else {
//                             contactNum = Integer.parseInt(contactNumStr);
//                             for (i = 0; i < memberList.size(); i++) {
//                                 if (memberList.get(i).getMemberContact() == contactNum) {
//                                     found = true;
//                                     System.out.println("\n===============================================");
//                                     System.out.printf("Member ID : %s", memberList.get(i).getId());
//                                     System.out.printf("\nName : %s", memberList.get(i).getName());
//                                     System.out.printf("\nGender : %s", memberList.get(i).getGender());
//                                     System.out.printf("\nIC Number : %s", memberList.get(i).getIcNumber());
//                                     System.out.printf("\nContect Number (+60) : %d",
//                                             memberList.get(i).getMemberContact());
//                                     System.out.printf("\nDate Become Member : %d/%d/%d",
//                                             memberList.get(i).getDateJoin().getDay(),
//                                             memberList.get(i).getDateJoin().getMonth(),
//                                             memberList.get(i).getDateJoin().getYear());
//                                     System.out.printf("\n===============================================\n");
//                                 } else if (i < memberList.size() - 1) {
//                                     continue;
//                                 }
//                                 if (!found) {
//                                     System.out.printf("\nSearch Member's Information NOT MATCH!!!\n");
//                                     break;
//                                 }
//                             }
//                         }
//                         System.out.printf("\nWant Search Another Member Information ? (y=Yes/n=No) : ");
//                         searchAnother = scanner.nextLine();

//                         while (searchAnother.isEmpty()) {
//                             System.out.println("\nNo Input Provided...");
//                             System.out.printf("\nWant Search Another Member Information ? (y=Yes/n=No) : ");
//                             searchAnother = scanner.nextLine();
//                         }

//                         response = searchAnother.charAt(0);

//                         while (Character.toUpperCase(response) != 'Y' && Character.toUpperCase(response) != 'N') {
//                             System.out.print("\nINVALID RESPONSE!!! Please Enter y=Yes or n=No : ");
//                             searchAnother = scanner.nextLine();

//                             while (searchAnother.isEmpty()) {
//                                 System.out.println("\nNo Input Provided...");
//                                 System.out.printf("\nWant Search Another Member Information ? (y=Yes/n=No) : ");
//                                 searchAnother = scanner.nextLine();
//                             }

//                             response = searchAnother.charAt(0);
//                         }
//                         break;

//                     case 6:
//                         System.out.print("\n--> Which DATE Become A Member (dd/mm/yyyy) : ");
//                         scanner.nextLine();
//                         String dateInput = scanner.nextLine();
//                         if (dateInput.isEmpty()) {
//                             System.out.printf("\nType Something If You Want to Search...\n");
//                         } else {
//                             String[] dateParts = dateInput.split("/");
//                             if (dateParts.length != 3) {
//                                 System.out.println("\nInvalid Date Format. Please Enter dd/mm/yyyy.");
//                             } else {
//                                 try {
//                                     day = Integer.parseInt(dateParts[0]);
//                                     month = Integer.parseInt(dateParts[1]);
//                                     year = Integer.parseInt(dateParts[2]);
//                                     for (i = 0; i < memberList.size(); i++) {
//                                         if (memberList.get(i).getDateJoin().getDay() == day
//                                                 && memberList.get(i).getDateJoin().getMonth() == month
//                                                 && memberList.get(i).getDateJoin().getYear() == year) {
//                                             found = true;
//                                             System.out.println("\n===============================================");
//                                             System.out.printf("Member ID : %s", memberList.get(i).getId());
//                                             System.out.printf("\nName : %s", memberList.get(i).getName());
//                                             System.out.printf("\nGender : %s", memberList.get(i).getGender());
//                                             System.out.printf("\nIC Number : %s", memberList.get(i).getIcNumber());
//                                             System.out.printf("\nContect Number (+60) : %d",
//                                                     memberList.get(i).getMemberContact());
//                                             System.out.printf("\nDate Become Member : %d/%d/%d",
//                                                     memberList.get(i).getDateJoin().getDay(),
//                                                     memberList.get(i).getDateJoin().getMonth(),
//                                                     memberList.get(i).getDateJoin().getYear());
//                                             System.out.printf("\n===============================================\n");
//                                         } else if (i < memberList.size() - 1) {
//                                             continue;
//                                         }
//                                     }
//                                     if (!found) {
//                                         System.out.printf("\nSearch Member's Information NOT MATCH!!!\n");
//                                         break;
//                                     }
//                                 } catch (NumberFormatException ex) {
//                                     System.out.printf("\nInvalid Date Format. Please Enter dd/mm/yyyy.");
//                                 }
//                             }
//                         }
//                         System.out.printf("\nWant Search Another Member Information ? (y=Yes/n=No) : ");
//                         searchAnother = scanner.nextLine();

//                         while (searchAnother.isEmpty()) {
//                             System.out.println("\nNo Input Provided...");
//                             System.out.printf("\nWant Search Another Member Information ? (y=Yes/n=No) : ");
//                             searchAnother = scanner.nextLine();
//                         }

//                         response = searchAnother.charAt(0);

//                         while (Character.toUpperCase(response) != 'Y' && Character.toUpperCase(response) != 'N') {
//                             System.out.print("\nINVALID RESPONSE!!! Please Enter y=Yes or n=No : ");
//                             searchAnother = scanner.nextLine();

//                             while (searchAnother.isEmpty()) {
//                                 System.out.println("\nNo Input Provided...");
//                                 System.out.printf("\nWant Search Another Member Information ? (y=Yes/n=No) : ");
//                                 searchAnother = scanner.nextLine();
//                             }

//                             response = searchAnother.charAt(0);
//                         }
//                         break;
//                 }

//             } while (Character.toUpperCase(response) == 'Y');
//         }
//     }

//     private static boolean confirmModify() {
//         System.out.print("\nAre You Sure Want To Modify Record(s) (y=Yes/n=No) ? : ");
//         String input = scanner.nextLine();
//         if (input.isEmpty()) {
//             input = scanner.nextLine();
//         }

//         while (input.isEmpty()) {
//             System.out.println("\nNo Input Provided...");
//             System.out.print("\nAre You Sure Want To Modify Record(s) (y=Yes/n=No) ? : ");
//             input = scanner.nextLine();
//         }

//         char response = input.charAt(0);

//         while (Character.toUpperCase(response) != 'Y' && Character.toUpperCase(response) != 'N') {
//             System.out.print("\nINVALID RESPONSE!!! Please Enter y=Yes or n=No : ");
//             input = scanner.nextLine();

//             while (input.isEmpty()) {
//                 System.out.println("\nNo Input Provided...");
//                 System.out.print("\nAre You Sure Want To Modify Record(s) (y=Yes/n=No) ? : ");
//                 input = scanner.nextLine();
//             }

//             response = input.charAt(0);
//         }

//         return Character.toUpperCase(response) == 'Y';
//     }

//     private static void modify() {

//         String modifyID;
//         String modifyName;
//         int modifyNum;
//         String confirm;
//         char conLanFirm = 'n';
//         boolean match = false;
//         String modifyAnother;
//         char continueModify = 'n';

//         if (confirmModify()) {
//             do {
//                 match = false;
//                 System.out.printf("\nEnter Member ID To Modify : ");
//                 modifyID = scanner.nextLine();

//                 for (int i = 0; i < memberList.size(); i++) {
//                     if (memberList.get(i).getId().equalsIgnoreCase(modifyID)) {
//                         match = true;
//                         System.out.println("\n---------------------------------");
//                         System.out.println("   Member's Informations Found");
//                         System.out.println("---------------------------------");
//                         System.out.printf("Member ID : %s", memberList.get(i).getId());
//                         System.out.printf("\nName : %s", memberList.get(i).getName());
//                         System.out.printf("\nGender : %s", memberList.get(i).getGender());
//                         System.out.printf("\nIC Number : %s", memberList.get(i).getIcNumber());
//                         System.out.printf("\nContact Number (+60) : %d", memberList.get(i).getMemberContact());
//                         System.out.printf("\nDate Become Member : %d/%d/%d", memberList.get(i).getDateJoin().getDay(),
//                                 memberList.get(i).getDateJoin().getMonth(), memberList.get(i).getDateJoin().getYear());

//                         System.out.printf("\n\nEnter Modify Member's Name : ");
//                         modifyName = scanner.nextLine();

//                         System.out.printf("\nEnter New Contact Number (+60) : ");
//                         modifyNum = scanner.nextInt();

//                         System.out.printf(
//                                 "\nAre You Sure You Want To Modify This Member's Information ? (y=Yes/n=No) : ");
//                         confirm = scanner.nextLine();
//                         if (confirm.isEmpty()) {
//                             confirm = scanner.nextLine();
//                         }

//                         while (confirm.isEmpty()) {
//                             System.out.println("\nNo Input Provided...");
//                             System.out.printf(
//                                     "\nAre You Sure You Want To Modify This Member's Information ? (y=Yes/n=No) : ");
//                             confirm = scanner.nextLine();
//                         }

//                         conLanFirm = confirm.charAt(0);

//                         while (Character.toUpperCase(conLanFirm) != 'Y' && Character.toUpperCase(conLanFirm) != 'N') {
//                             System.out.print("\nINVALID RESPONSE!!! Please Enter y=Yes or n=No : ");
//                             confirm = scanner.nextLine();

//                             while (confirm.isEmpty()) {
//                                 System.out.println("\nNo Input Provided...");
//                                 System.out.printf(
//                                         "\nAre You Sure You Want To Modify This Member's Information ? (y=Yes/n=No) : ");
//                                 confirm = scanner.nextLine();
//                             }

//                             conLanFirm = confirm.charAt(0);
//                         }

//                         if (Character.toUpperCase(conLanFirm) == 'Y') {
//                             MemberDetails m = memberList.get(i);
//                             m.setName(modifyName);
//                             memberList.set(i, m);
//                             m.setMemberContact(modifyNum);
//                             memberList.set(i, m);
//                             System.out.printf("\nMODIFY SUCCESSFUL!!!\n");
//                             System.out.printf("\n------------------------------------");
//                             System.out.printf("\n    Member's Information Updated");
//                             System.out.printf("\n------------------------------------\n");
//                             System.out.printf("Member ID : %s", memberList.get(i).getId());
//                             System.out.printf("\nName : %s", memberList.get(i).getName());
//                             System.out.printf("\nGender : %s", memberList.get(i).getGender());
//                             System.out.printf("\nIC Number : %s", memberList.get(i).getIcNumber());
//                             System.out.printf("\nContact Number (+60) : %d", memberList.get(i).getMemberContact());
//                             System.out.printf("\nDate Become Member : %d/%d/%d",
//                                     memberList.get(i).getDateJoin().getDay(),
//                                     memberList.get(i).getDateJoin().getMonth(),
//                                     memberList.get(i).getDateJoin().getYear());
//                             break;
//                         } else {
//                             System.out.printf("\nMODIFY CANCEL...");
//                             break;
//                         }

//                     } else if (i + 1 != memberList.size()) {
//                         continue;
//                     } else {
//                         System.out.printf("\nMember's Information NOT FOUND!!!");
//                         break;
//                     }

//                 }

//                 System.out.printf("\n\nDo You Want To Continue Modify Member's Informations ? (y=Yes/n=No) : ");
//                 modifyAnother = scanner.nextLine();

//                 while (modifyAnother.isEmpty()) {
//                     System.out.println("\nNo Input Provided...");
//                     System.out.printf("\nDo You Want To Continue Modify Member's Informations ? (y=Yes/n=No) : ");
//                     modifyAnother = scanner.nextLine();
//                 }

//                 continueModify = modifyAnother.charAt(0);

//                 while (Character.toUpperCase(continueModify) != 'Y' && Character.toUpperCase(continueModify) != 'N') {
//                     System.out.print("\nINVALID RESPONSE!!! Please Enter y=Yes or n=No : ");
//                     modifyAnother = scanner.nextLine();

//                     while (modifyAnother.isEmpty()) {
//                         System.out.println("\nNo Input Provided...");
//                         System.out.printf("\nDo You Want To Continue Modify Member's Informations ? (y=Yes/n=No) : ");
//                         modifyAnother = scanner.nextLine();
//                     }

//                     continueModify = modifyAnother.charAt(0);
//                 }
//             } while (Character.toUpperCase(continueModify) == 'Y');

//         }
//     }

//     private static boolean confirmDelete() {
//         System.out.print("\nAre You Sure Want To Delete Record(s) (y=Yes/n=No) ? : ");
//         String input = scanner.nextLine();
//         if (input.isEmpty()) {
//             input = scanner.nextLine();
//         }

//         while (input.isEmpty()) {
//             System.out.println("\nNo Input Provided...");
//             System.out.print("\nAre You Sure Want To Delete Record(s) (y=Yes/n=No) ? : ");
//             input = scanner.nextLine();
//         }

//         char response = input.charAt(0);

//         while (Character.toUpperCase(response) != 'Y' && Character.toUpperCase(response) != 'N') {
//             System.out.print("\nINVALID RESPONSE!!! Please Enter y=Yes or n=No : ");
//             input = scanner.nextLine();

//             while (input.isEmpty()) {
//                 System.out.println("\nNo Input Provided...");
//                 System.out.print("\nAre You Sure Want To Delete Record(s) (y=Yes/n=No) ? : ");
//                 input = scanner.nextLine();
//             }

//             response = input.charAt(0);
//         }

//         return Character.toUpperCase(response) == 'Y';
//     }

//     private static void deleteInfo() {

//         String delId;
//         String confirmDel;
//         char conLanFirmDel = 'n';
//         String delAnother;
//         char conDelAnother = 'n';
//         int count = 0;
//         int countDel = 0;

//         if (confirmDelete()) {
//             do {
//                 count = 0;
//                 System.out.printf("\nEnter Member ID To Delete : ");
//                 delId = scanner.nextLine();

//                 for (int i = 0; i < memberList.size(); i++) {
//                     if (memberList.get(i).getId().equalsIgnoreCase(delId)) {
//                         count++;

//                         System.out.println("\n---------------------------------");
//                         System.out.println("   Delete Member's Information");
//                         System.out.println("---------------------------------\n");
//                         System.out.printf("Member ID : %s", memberList.get(i).getId());
//                         System.out.printf("\nName : %s", memberList.get(i).getName());
//                         System.out.printf("\nGender : %s", memberList.get(i).getGender());
//                         System.out.printf("\nIC Number : %s", memberList.get(i).getIcNumber());
//                         System.out.printf("\nContact Number (+60) : %d", memberList.get(i).getMemberContact());
//                         System.out.printf("\nDate Become Member : %d/%d/%d", memberList.get(i).getDateJoin().getDay(),
//                                 memberList.get(i).getDateJoin().getMonth(), memberList.get(i).getDateJoin().getYear());
//                     }
//                 }

//                 if (count == 0) {
//                     System.out.printf("\nNo Member's Information Found With This Member ID = %s", delId);
//                     System.out.printf("\n\nDelete Another Member ? (yes=y/no=n) : ");
//                     delAnother = scanner.nextLine();

//                     while (delAnother.isEmpty()) {
//                         System.out.println("\nNo Input Provided...");
//                         System.out.printf(
//                                 "\nDelete Another Member ? (yes=y/no=n) : ");
//                         delAnother = scanner.nextLine();
//                     }

//                     conDelAnother = delAnother.charAt(0);

//                     while (Character.toUpperCase(conDelAnother) != 'Y' && Character.toUpperCase(conDelAnother) != 'N') {
//                         System.out.print("\nINVALID RESPONSE!!! Please Enter y=Yes or n=No : ");
//                         delAnother = scanner.nextLine();

//                         while (delAnother.isEmpty()) {
//                             System.out.println("\nNo Input Provided...");
//                             System.out.printf("\nDelete Another Member ? (yes=y/no=n) : ");
//                             delAnother = scanner.nextLine();
//                         }

//                         conDelAnother = delAnother.charAt(0);
//                         break;
//                     }

//                 } else {
//                     System.out.printf("\n\nConfirm To Delete ? (y=Yes/n=No) : ");
//                     confirmDel = scanner.nextLine();

//                     while (confirmDel.isEmpty()) {
//                         System.out.println("\nNo Input Provided...");
//                         System.out.printf(
//                                 "\nConfirm To Delete ? (y=Yes/n=No) : ");
//                         confirmDel = scanner.nextLine();
//                     }

//                     conLanFirmDel = confirmDel.charAt(0);

//                     while (Character.toUpperCase(conLanFirmDel) != 'Y'
//                             && Character.toUpperCase(conLanFirmDel) != 'N') {
//                         System.out.print("\nINVALID RESPONSE!!! Please Enter y=Yes or n=No : ");
//                         confirmDel = scanner.nextLine();

//                         while (confirmDel.isEmpty()) {
//                             System.out.println("\nNo Input Provided...");
//                             System.out.printf("\nConfirm To Delete ? (y=Yes/n=No) : ");
//                             confirmDel = scanner.nextLine();
//                         }

//                         conLanFirmDel = confirmDel.charAt(0);
//                     }

//                     if (Character.toUpperCase(conLanFirmDel) == 'Y') {
//                         for (int i = 0; i < memberList.size(); i++) {
//                             if (memberList.get(i).getId().equalsIgnoreCase(delId)) {
//                                 memberList.remove(i);
//                                 countDel++;
//                             }
//                         }
//                         System.out.printf("\n%s This Member's Information Has Been Delete SUCCESSFUL!!!", delId);
//                         System.out.printf("\n\n%d Record(s) Deleted. ", countDel);
//                     } else {
//                         System.out.printf("\nDELETION CANCEL...");
//                     }

//                     System.out.printf("\n\nDelete Another Member ? (yes=y/no=n) : ");
//                     delAnother = scanner.nextLine();

//                     while (delAnother.isEmpty()) {
//                         System.out.println("\nNo Input Provided...");
//                         System.out.printf(
//                                 "\nDelete Another Member ? (yes=y/no=n) : ");
//                         delAnother = scanner.nextLine();
//                     }

//                     conDelAnother = delAnother.charAt(0);

//                     while (Character.toUpperCase(conDelAnother) != 'Y'
//                             && Character.toUpperCase(conDelAnother) != 'N') {
//                         System.out.print("\nINVALID RESPONSE!!! Please Enter y=Yes or n=No : ");
//                         delAnother = scanner.nextLine();

//                         while (delAnother.isEmpty()) {
//                             System.out.println("\nNo Input Provided...");
//                             System.out.printf("\nDelete Another Member ? (yes=y/no=n) : ");
//                             delAnother = scanner.nextLine();
//                         }

//                         conDelAnother = delAnother.charAt(0);
//                     }
//                 }

//             } while (Character.toUpperCase(conDelAnother) == 'Y');

//         }
//     }

//     public static void calcTypeOfMember(int quantity, double price, List<String> t, int[] count) {
//         if (quantity * price >= 3000) {
//             t.add("Golden Member");
//             count[0]++;
//         } else if (quantity * price >= 2000) {
//             t.add("Silver Member");
//             count[1]++;
//         } else if (quantity * price >= 1000) {
//             t.add("Bronze Member");
//             count[2]++;
//         } else {
//             t.add("Basic Member");
//             count[3]++;
//         }
//     }

//     private static void reportInfo() {

//         int i;
//         double totalConsumption = 0;
//         int[] count = new int[4];
//         List<String> typeOfMember = new ArrayList<>();

//         System.out.printf("\n\n--------------------------------------------\n");
//         System.out.printf("          Monthly Report Of Member\n");
//         System.out.printf("--------------------------------------------\n\n");
//         System.out.printf("%-11s %-15s\t%s\t%-12s\n", "Member ID", "Name", "Type Of Member", "Total Consumption (RM)");
//         System.out.printf("=========   ================\t==============\t======================\n");

//         for (MemberDetails typeMem : memberList) {
//             for (i = 0; i < salesDetails.size(); i++) {
//                 if (typeMem.getId().equalsIgnoreCase(salesDetails.get(i).getMemberId())) {

//                     calcTypeOfMember(salesDetails.get(i).getQuantitySales(), salesDetails.get(i).getItemPrice(),
//                             typeOfMember, count);
//                     totalConsumption = salesDetails.get(i).getQuantitySales() * salesDetails.get(i).getItemPrice();
//                     System.out.printf("%-11s %-15s\t%s\tRM %.2f\n", memberList.get(i).getId(),
//                             memberList.get(i).getName(), typeOfMember.get(i), totalConsumption);
//                 }
//             }
//         }

//         System.out.printf(
//                 "\nTotal Golden Member ---> %d\tTotal Silver Member ---> %d\nTotal Bronze Member ---> %d\tTotal Basic Member ---> %d\n",
//                 count[0], count[1], count[2], count[3]);
//     }

// }
