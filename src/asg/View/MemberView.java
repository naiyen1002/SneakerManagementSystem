package asg.View;

import asg.Model.Member;
import asg.Model.Gender;
import asg.Model.MembershipTier;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class MemberView {

    private static Scanner scanner = new Scanner(System.in);

    // ==================== MENU DISPLAY ====================

    public void displayMemberMainMenu() {
        System.out.println("\n\n--------------------------------");
        System.out.println("\tMember Main Menu\t\t");
        System.out.println("--------------------------------\n");
        System.out.println("1. Display All Members Details");
        System.out.println("2. Add New Records");
        System.out.println("3. Search For Member Records");
        System.out.println("4. Modify Member's Records");
        System.out.println("5. Delete Member's Records");
        System.out.println("6. Show All Type Of Member");
        System.out.println("7. Exit/Back To Menu\n");
    }

    public int getMenuChoice() {
        int choice = -1;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("Choose From (1-7): ");
                choice = scanner.nextInt();
                scanner.nextLine();

                if (choice >= 1 && choice <= 7) {
                    validInput = true;
                } else {
                    System.out.println("\nYou Can Only Choose From 1 - 7 Only.. .\n");
                }
            } catch (Exception e) {
                System.out.println("\nEnter Digit Only... Please enter a number between 1 and 7.\n");
                scanner.nextLine();
            }
        }
        return choice;
    }

    // ==================== DISPLAY MEMBERS ====================

    public void displayAllMembers(List<Member> memberList) {
        System.out.println("\n--------------------------------");
        System.out.println("     All Member Information\t\t");
        System.out.println("--------------------------------\n");
        System.out.printf("%-11s %-15s\t%s\t%s\t%s\t%s\n", "Member ID", "Name", "Gender", "IC Number",
                "Contact Number (+60)", "Date Become Member (dd/mm/yyyy)");
        System.out.println(
                "=========   ================\t======\t==============\t====================\t===============================");
        for (Member member : memberList) {
            System.out.printf("%-11s %-15s\t%s\t%s\t%d\t\t%d/%d/%d\n", member.getId(), member.getName(),
                    member.getGender().getDisplayName(), member.getIcNumber(), member.getContactNumber(),
                    member.getFormattedJoinDate());
        }
        System.out.println("\n\n<<< Total " + memberList.size() + " Member(s) >>>\n");
    }

    public void displayMemberDetails(Member member) {
        System.out.println("\n===============================================");
        System.out.printf("Member ID : %s", member.getId());
        System.out.printf("\nName : %s", member.getName());
        System.out.printf("\nGender : %s", member.getGender().getDisplayName());
        System.out.printf("\nIC Number : %s", member.getIcNumber());
        System.out.printf("\nContact Number (+60) : %s", member.getContactNumber());
        System.out.printf("\nDate Become Member : %s", member.getFormattedJoinDate());
        System.out.printf("\n===============================================\n");
    }

    // ==================== INPUT METHODS ====================

    public String inputMemberId() {
        System.out.print("\nMember ID:");
        return scanner.nextLine().trim();
    }

    public String inputName() {
        System.out.print("\nName : ");
        return scanner.nextLine().trim();
    }

    public Gender inputGender() {
        while (true) {
            System.out.print("\nGender (Male/Female/Other): ");
            String input = scanner.nextLine().trim();

            try {
                return Gender.fromString(input);
            } catch (IllegalArgumentException e) {
                System.out.println("\nInvalid Gender... Please Enter Male, Female, or Other");
            }
        }
    }

    public String inputIcNumber() {
        while (true) {
            System.out.print("\nIC Number (xxxxxx-xx-xxxx): ");
            String icNumber = scanner.nextLine().trim();

            if (icNumber.matches("\\d{6}-\\d{2}-\\d{4}")) {
                return icNumber;
            } else {
                System.out.println("\nInvalid format!  Please use format: xxxxxx-xx-xxxx");
            }
        }
    }

    public String inputContactNumber() {
        while (true) {
            System.out.print("\nContact Number (+60): ");
            String contact = scanner.nextLine().trim();

            if (contact.matches("\\d{9,11}")) {
                return contact;
            } else {
                System.out.println("\nInvalid! Contact number must be 9-11 digits");
            }
        }
    }

    public LocalDate inputJoinDate() {
        while (true) {
            System.out.print("\nDate Become A Member (dd/mm/yyyy): ");
            String dateStr = scanner.nextLine().trim();

            try {
                return Member.parseJoinDate(dateStr);
            } catch (DateTimeParseException e) {
                System.out.println("\nInvalid date format! Please enter format like: dd/mm/yyyy");
            }
        }
    }

    // ==================== SEARCH MENU ====================

    public int displaySearchMenu() {
        System.out.println("\n--------------------------------");
        System.out.println("   Search Member Information\t\t");
        System.out.println("--------------------------------\n");
        System.out.println("1. Member ID");
        System.out.println("2. Name");
        System.out.println("3. Gender");
        System.out.println("4. IC Number");
        System.out.println("5. Contact Number");
        System.out.println("6. Join Date");

        return getSearchChoice();
    }

    private int getSearchChoice() {
        int choice = -1;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("\nChoose From (1-7) to Search Member's Information : ");
                choice = scanner.nextInt();
                scanner.nextLine();

                if (choice >= 1 && choice <= 6) {
                    validInput = true;
                } else {
                    System.out.println("\nYou Can Only Choose From 1 - 6 Only...\n");
                }
            } catch (Exception e) {
                System.out.println("\nEnter Digit Only... Please enter a number between 1 and 6\n");
                scanner.nextLine();
            }
        }
        return choice;
    }

    // ==================== CONFIRMATION METHODS ====================

    public boolean confirmAction(String action) {
        while (true) {
            System.out.printf("\nAre You Sure Want To %s Record(s) (y=Yes/n=No) ?  : ", action);
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.isEmpty()) {
                System.out.println("\nNo input provided.. .");
                continue;
            }

            char response = input.charAt(0);
            if (response == 'Y') {
                return true;
            } else if (response == 'N') {
                return false;
            } else {
                System.out.println("\nINVALID RESPONSE! !! Please Enter y=Yes or n=No");
            }
        }
    }

    public boolean askContinue(String message) {
        while (true) {
            System.out.printf("\n%s (y=Yes/n=No) : ", message);
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.isEmpty()) {
                System.out.println("\nNo input provided...");
                continue;
            }

            char response = input.charAt(0);
            if (response == 'Y') {
                return true;
            } else if (response == 'N') {
                return false;
            } else {
                System.out.println("\nINVALID RESPONSE!!! Please Enter y=Yes or n=No");
            }
        }
    }

    // ==================== MESSAGE DISPLAY ====================

    public void showSuccessMessage(String message) {
        System.out.println("\n" + message + " SUCCESSFUL!! !\n");
    }

    public void showErrorMessage(String message) {
        System.out.println("\n" + message + "\n");
    }

    public void showCancelMessage(String action) {
        System.out.printf("\n%s CANCEL.. .\n", action.toUpperCase());
    }

    public void showNotFoundMessage() {
        System.out.println("\nMember's Information NOT FOUND!!!");
    }

    public void showMembershipReport(List<Member> members, int[] tierCounts) {
        System.out.println("\n\n--------------------------------------------");
        System.out.println("          Monthly Report Of Member");
        System.out.println("--------------------------------------------\n");
        System.out.printf("%-11s %-15s\t%s\t%-12s\n",
                "Member ID", "Name", "Type Of Member", "Total Consumption (RM)");
        System.out.println("=========   ================\t==============\t======================");

        for (Member member : members) {
            System.out.printf("%-11s %-15s\t%s\t-\n",
                    member.getId(),
                    member.getName(),
                    member.getMembershipTier().getDisplayName());
        }

        System.out.printf("\nTotal Golden Member ---> %d\tTotal Silver Member ---> %d\n",
                tierCounts[0], tierCounts[1]);
        System.out.printf("Total Bronze Member ---> %d\tTotal Basic Member ---> %d\n",
                tierCounts[2], tierCounts[3]);
    }

    public void displayExitMessage() {
        System.out.println("\nEXITING MENU...\n");
        System.out.println("END OF PROCESSING...\n");
    }

}
