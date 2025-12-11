package asg.MemberManagementModule.View;

import asg.MemberManagementModule.Model.Member;
import asg.MemberManagementModule.Constants.MemberConstants;
import asg.MemberManagementModule.Model.Gender;
import asg.MemberManagementModule.Model.MembershipTier;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MemberView {

    private static Scanner scanner = new Scanner(System.in);

    // ==================== MENU DISPLAY ====================

    /**
     * Display the main member management menu
     */
    public void displayMemberMainMenu() {
        System.out.println(MemberConstants.HEADER_MAIN_MENU);
        System.out.println(MemberConstants.MENU_OPTION_1);
        System.out.println(MemberConstants.MENU_OPTION_2);
        System.out.println(MemberConstants.MENU_OPTION_3);
        System.out.println(MemberConstants.MENU_OPTION_4);
        System.out.println(MemberConstants.MENU_OPTION_5);
        System.out.println(MemberConstants.MENU_OPTION_6);
        System.out.println(MemberConstants.MENU_OPTION_7);
    }

    /**
     * Get and validate main menu choice
     * 
     * @return Valid menu choice (1-7)
     */
    public int getMenuChoice() {
        return getValidatedIntegerInput(MemberConstants.PROMPT_MENU_CHOICE, 1, 7);
    }

    // ==================== DISPLAY MEMBERS ====================

    /**
     * Display all members in a formatted table
     * 
     * @param memberList is a List of members to display
     */
    public void displayAllMembers(List<Member> memberList) {
        if (memberList == null || memberList.isEmpty()) {
            System.out.println(MemberConstants.NO_MEMBERS_FOUND);
            return;
        }

        System.out.println(MemberConstants.HEADER_DISPLAY_ALL);
        System.out.printf(MemberConstants.FORMAT_TABLE_HEADER,
                "Member ID", "Name", "Gender", "IC Number", "Contact Number (+60)",
                "Join Date (dd/mm/yyyy)", "Membership Tier");
        System.out.println(MemberConstants.FORMAT_TABLE_SEPARATOR);

        for (Member member : memberList) {
            System.out.printf(MemberConstants.FORMAT_TABLE_ROW,
                    member.getId(),
                    member.getName(),
                    member.getGender().getDisplayName(),
                    member.getIcNumber(),
                    member.getContactNumber(),
                    member.getFormattedJoinDate(),
                    member.getMembershipTier().getDisplayName());
        }

        System.out.printf(MemberConstants.TOTAL_MEMBERS_FORMAT, memberList.size());
    }

    /**
     * Display detailed information for a single member
     * 
     * @param member Member to display
     */
    public void displayMemberDetails(Member member) {
        if (member == null) {
            showErrorMessage(MemberConstants.MSG_NOT_FOUND);
            return;
        }

        System.out.println(MemberConstants.DETAIL_SEPARATOR);
        System.out.printf(MemberConstants.DETAIL_MEMBER_ID, member.getId());
        System.out.printf(MemberConstants.DETAIL_NAME, member.getName());
        System.out.printf(MemberConstants.DETAIL_GENDER, member.getGender().getDisplayName());
        System.out.printf(MemberConstants.DETAIL_IC_NUMBER, member.getIcNumber());
        System.out.printf(MemberConstants.DETAIL_CONTACT, member.getContactNumber());
        System.out.printf(MemberConstants.DETAIL_JOIN_DATE, member.getFormattedJoinDate());
        System.out.printf(MemberConstants.DETAIL_MEMBERSHIP_TIER, member.getMembershipTier().getDisplayName());
        System.out.printf(MemberConstants.DETAIL_TOTAL_SPENDING, member.getTotalSpending());
        System.out.println(MemberConstants.DETAIL_SEPARATOR + "\n");
    }

    /**
     * Display membership report with tier
     * 
     * @param members    List of all members
     * @param tierCounts Map of tier counts [Golden, Silver, Bronze, Basic]
     */
    public void displayMembershipReport(List<Member> members, Map<MembershipTier, Integer> tierCounts) {
        if (members == null || members.isEmpty()) {
            System.out.println(MemberConstants.NO_MEMBERS_FOUND);
            return;
        }

        System.out.println(MemberConstants.HEADER_MEMBERSHIP_REPORT);
        System.out.printf(MemberConstants.REPORT_TABLE_HEADER,
                MemberConstants.REPORT_HEADER_MEMBER_ID,
                MemberConstants.REPORT_HEADER_NAME,
                MemberConstants.REPORT_HEADER_TYPE,
                MemberConstants.REPORT_HEADER_SPENDING);
        System.out.println(MemberConstants.REPORT_TABLE_SEPARATOR);

        for (Member member : members) {
            System.out.printf(MemberConstants.REPORT_TABLE_ROW,
                    member.getId(),
                    member.getName(),
                    member.getMembershipTier().getDisplayName(),
                    member.getTotalSpending());
        }

        System.out.println(MemberConstants.REPORT_TIER_SEPARATOR);
        System.out.printf(MemberConstants.REPORT_TOTAL_GOLDEN,
                tierCounts.getOrDefault(MembershipTier.GOLDEN, 0));
        System.out.printf(MemberConstants.REPORT_TOTAL_SILVER,
                tierCounts.getOrDefault(MembershipTier.SILVER, 0));
        System.out.printf(MemberConstants.REPORT_TOTAL_BRONZE,
                tierCounts.getOrDefault(MembershipTier.BRONZE, 0));
        System.out.printf(MemberConstants.REPORT_TOTAL_BASIC,
                tierCounts.getOrDefault(MembershipTier.BASIC, 0));
        System.out.println(MemberConstants.REPORT_TIER_SEPARATOR_END);
    }

    // ==================== INPUT METHODS ====================

    /**
     * Input member ID with prompt
     * 
     * @return Trimmed member ID input
     */
    public String inputMemberId() {
        System.out.print(MemberConstants.PROMPT_MEMBER_ID);
        return scanner.nextLine().trim().toUpperCase();
    }

    /**
     * Input member name with validation
     * 
     * @return Valid name (not empty, at least 2 characters)
     */
    public String inputName() {
        while (true) {
            System.out.print(MemberConstants.PROMPT_NAME);
            String name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                showErrorMessage(MemberConstants.ERROR_EMPTY_NAME);
                continue;
            }

            if (name.length() < 2) {
                showErrorMessage(MemberConstants.ERROR_NAME_TOO_SHORT);
                continue;
            }

            return name;
        }
    }

    /**
     * Input gender with validation
     * 
     * @return Valid Gender enum value
     */
    public Gender inputGender() {
        while (true) {
            System.out.print(MemberConstants.PROMPT_GENDER);
            String input = scanner.nextLine().trim();

            try {
                return Gender.fromString(input);
            } catch (IllegalArgumentException e) {
                showErrorMessage(MemberConstants.ERROR_INVALID_GENDER);
            }
        }
    }

    /**
     * Input IC number with format validation
     * 
     * @return Valid IC number (xxxxxx-xx-xxxx)
     */
    public String inputIcNumber() {
        while (true) {
            System.out.print(MemberConstants.PROMPT_IC_NUMBER);
            String icNumber = scanner.nextLine().trim();

            if (icNumber.matches(MemberConstants.PATTERN_IC_NUMBER)) {
                return icNumber;
            } else {
                showErrorMessage(MemberConstants.ERROR_INVALID_IC);
            }
        }
    }

    /**
     * Input contact number with validation
     * 
     * @return Valid contact number (9-11 digits)
     */
    public String inputContactNumber() {
        while (true) {
            System.out.print(MemberConstants.PROMPT_CONTACT);
            String contact = scanner.nextLine().trim();

            if (contact.matches(MemberConstants.PATTERN_CONTACT)) {
                return contact;
            } else {
                showErrorMessage(MemberConstants.ERROR_INVALID_CONTACT);
            }
        }
    }

    /**
     * Input join date with validation
     * 
     * @return Valid LocalDate (not in future)
     */
    public LocalDate inputJoinDate() {
        while (true) {
            System.out.print(MemberConstants.PROMPT_JOIN_DATE);
            String dateStr = scanner.nextLine().trim();

            try {
                LocalDate date = Member.parseJoinDate(dateStr);

                if (date.isAfter(LocalDate.now())) {
                    showErrorMessage(MemberConstants.ERROR_FUTURE_DATE);
                    continue;
                }

                return date;
            } catch (DateTimeParseException e) {
                showErrorMessage(MemberConstants.ERROR_INVALID_DATE_FORMAT);
            } catch (IllegalArgumentException e) {
                showErrorMessage(e.getMessage());
            }
        }
    }

    // ==================== SEARCH MENU ====================

    /**
     * Display search menu and get search criteria choice
     * 
     * @return Search choice (1-6)
     */
    public int displaySearchMenu() {
        System.out.println(MemberConstants.HEADER_SEARCH_MEMBER);
        System.out.println(MemberConstants.SEARCH_BY_ID);
        System.out.println(MemberConstants.SEARCH_BY_NAME);
        System.out.println(MemberConstants.SEARCH_BY_GENDER);
        System.out.println(MemberConstants.SEARCH_BY_IC);
        System.out.println(MemberConstants.SEARCH_BY_CONTACT);
        System.out.println(MemberConstants.SEARCH_BY_DATE);

        return getValidatedIntegerInput(MemberConstants.PROMPT_SEARCH_CHOICE, 1, 6);
    }

    // ==================== UPDATE MENU ====================

    /**
     * Display update menu and get field choice
     * 
     * @return Update choice (1-5)
     */
    public int displayUpdateMenu() {
        System.out.println(MemberConstants.HEADER_UPDATE_MEMBER);
        System.out.println(MemberConstants.UPDATE_NAME);
        System.out.println(MemberConstants.UPDATE_GENDER);
        System.out.println(MemberConstants.UPDATE_IC);
        System.out.println(MemberConstants.UPDATE_CONTACT);
        System.out.println(MemberConstants.UPDATE_FINISH);

        return getValidatedIntegerInput(MemberConstants.PROMPT_UPDATE_CHOICE, 1, 5);
    }

    // ==================== CONFIRMATION METHODS ====================

    /**
     * Confirm action with user (y/n)
     * 
     * @param action Action to confirm (Exp: "Add", "Delete")
     * @return true if user confirms (y), false otherwise (n)
     */
    public boolean confirmAction(String action) {
        while (true) {
            System.out.printf(MemberConstants.CONFIRM_ACTION_FORMAT, action);
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.isEmpty()) {
                showErrorMessage(MemberConstants.NO_INPUT_PROVIDED);
                continue;
            }

            char response = input.charAt(0);
            if (response == MemberConstants.PROMPT_Y_STRING.charAt(0)) {
                return true;
            } else if (response == MemberConstants.PROMPT_N_STRING.charAt(0)) {
                return false;
            } else {
                showErrorMessage(MemberConstants.INVALID_RESPONSE);
            }
        }
    }

    /**
     * Ask if user wants to continue operation
     * 
     * @param message Message to display (e.g., "Add Another Record")
     * @return true if user wants to continue (y), false otherwise (n)
     */
    public boolean askContinue(String message) {
        while (true) {
            System.out.printf(MemberConstants.ASK_CONTINUE_FORMAT, message);
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.isEmpty()) {
                showErrorMessage(MemberConstants.NO_INPUT_PROVIDED);
                continue;
            }

            char response = input.charAt(0);
            if (response == MemberConstants.PROMPT_Y_STRING.charAt(0)) {
                return true;
            } else if (response == MemberConstants.PROMPT_N_STRING.charAt(0)) {
                return false;
            } else {
                showErrorMessage(MemberConstants.INVALID_RESPONSE);
            }
        }
    }

    // ==================== MESSAGE DISPLAY ====================

    /**
     * Display success message
     * 
     * @param message Success message to display
     */
    public void showSuccessMessage(String message) {
        System.out.printf(MemberConstants.MSG_FORMAT_SUCCESS, message);
    }

    /**
     * Display error message
     * 
     * @param message Error message to display
     */
    public void showErrorMessage(String message) {
        System.out.printf(MemberConstants.MSG_FORMAT_ERROR, message);
    }

    /**
     * Display cancellation message
     * 
     * @param action Action that was cancelled
     */
    public void showCancelMessage(String action) {
        System.out.printf(MemberConstants.MSG_FORMAT_CANCEL, action);
    }

    /**
     * Display "not found" message
     */
    public void showNotFoundMessage() {
        showErrorMessage(MemberConstants.MSG_NOT_FOUND);
    }

    /**
     * Display exit message
     */
    public void displayExitMessage() {
        System.out.println(MemberConstants.EXIT_MESSAGE);
        System.out.println(MemberConstants.END_PROCESSING);
    }

    // ==================== INTEGER INPUT VALIDATION ====================

    /**
     * Get validated integer input within a range
     * 
     * @param prompt Prompt message to display
     * @param min    Minimum valid value (inclusive)
     * @param max    Maximum valid value (inclusive)
     * @return Valid integer input
     */
    private int getValidatedIntegerInput(String prompt, int min, int max) {
        int choice = -1;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                choice = Integer.parseInt(input);

                if (choice >= min && choice <= max) {
                    validInput = true;
                } else {
                    System.out.printf(MemberConstants.INVALID_MENU_RANGE, min, max);
                }
            } catch (NumberFormatException e) {
                System.out.printf(MemberConstants.INVALID_INPUT_DIGIT, min, max);
            }
        }
        return choice;
    }

}
