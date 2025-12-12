package asg.StaffManagementModule.Service;

import asg.StaffManagementModule.Constants.StaffConstants;
import asg.StaffManagementModule.Constants.StaffData;
import asg.StaffManagementModule.Model.Staff;
import asg.StaffManagementModule.View.StaffView;

import java.util.ArrayList;
import java.util.Scanner;

public class StaffService {

    private final StaffView view;
    private final Scanner scanner;
    private final ArrayList<Staff> staffList;

    public StaffService(StaffView view, Scanner scanner, ArrayList<Staff> staffList) {
        this.view = view;
        this.scanner = scanner;
        this.staffList = staffList;
    }

    /**
     * Validation for staff id
     * 
     * @return id
     */
    public String getValidStaffId() {
        String id;
        // Show the available ID
        String nextId = getNextAvailableId();
        view.displaySuggestedId(nextId);

        while (true) {
            view.displayPrompt(StaffConstants.ENTER_ID);
            id = scanner.nextLine();
            if (!id.matches(StaffConstants.REGEX_STAFFID)) {
                view.displayErrorMessage(StaffConstants.ERROR_ID_NUMERIC);
                continue;
            }
            // Check if ID is ST000
            if (id.toUpperCase().equals("ST000")) {
                view.displayErrorMessage(StaffConstants.ERROR_ID_ZERO);
                continue;
            }
            // Check if ID already exists
            if (isStaffIdExists(id)) {
                view.displayErrorMessage(String.format(StaffConstants.ERROR_ID_EXISTS, id));
                continue;
            }
            break;
        }
        return id.toUpperCase();
    }

    /**
     * Get the next available staff ID
     * 
     * @return next ID
     */
    public String getNextAvailableId() {
        int maxNum = 0;
        for (Staff staff : staffList) {
            String id = staff.getId();
            if (id.startsWith("ST")) {
                try {
                    int num = Integer.parseInt(id.substring(2));
                    if (num > maxNum) {
                        maxNum = num;
                    }
                } catch (NumberFormatException e) {

                }
            }
        }
        return String.format("ST%03d", maxNum + 1);
    }

    /**
     * Check if staff ID already exists in the system
     * 
     * @param id
     * @return true if exists, false otherwise
     */
    public boolean isStaffIdExists(String id) {
        for (Staff staff : staffList) {
            if (staff.getId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validate staff name
     * 
     * @param isModify
     * @return name
     */
    public String getValidName(boolean isModify) {
        String name;
        while (true) {
            view.displayPrompt(isModify ? StaffConstants.NEW_NAME : StaffConstants.ENTER_NAME);
            name = scanner.nextLine();

            if (name.trim().isEmpty()) {
                view.displayErrorMessage(StaffConstants.ERROR_NAME_BLANK);
                continue;
            }

            if (name.matches(StaffConstants.REGEX_POSITION_DEPT)) {
                view.displayErrorMessage(StaffConstants.ERROR_NAME_CONTAINS_NUMBERS);
                continue;
            }

            return toTitleCase(name.trim());
        }
    }

    /**
     * Validate gender
     * 
     * @param isModify
     * @return gender
     */
    public String getValidGender(boolean isModify) {
        String gender;
        while (true) {
            view.displayPrompt(isModify ? StaffConstants.NEW_GENDER : StaffConstants.ENTER_GENDER);
            gender = scanner.nextLine().trim();
            if (gender.equalsIgnoreCase(StaffConstants.GENDER_MALE) ||
                    gender.equalsIgnoreCase(StaffConstants.GENDER_FEMALE)) {
                // Return with proper case (Male or Female)
                return gender.substring(0, 1).toUpperCase() + gender.substring(1).toLowerCase();
            } else {
                view.displayErrorMessage(StaffConstants.ERROR_GENDER_INVALID);
            }
        }
    }

    /**
     * Validate position
     * 
     * @param isModify
     * @return position
     */
    public String getValidPosition(boolean isModify) {
        String position;
        while (true) {
            view.displayPrompt(isModify ? StaffConstants.NEW_POSITION : StaffConstants.ENTER_POSITION);
            position = scanner.nextLine();

            if (position.trim().isEmpty()) {
                view.displayErrorMessage(StaffConstants.ERROR_POSITION_BLANK);
                continue;
            }

            if (position.matches(StaffConstants.REGEX_POSITION_DEPT)) {
                view.displayErrorMessage(StaffConstants.ERROR_POSITION_CONTAINS_NUMBERS);
                continue;
            }

            return toTitleCase(position);
        }
    }

    /**
     * Validate salary
     * 
     * @param isModify
     * @return salary
     */
    public Double getValidSalary(boolean isModify) {
        while (true) {
            view.displayPrompt(isModify ? StaffConstants.NEW_SALARY : StaffConstants.ENTER_SALARY);
            String input = scanner.nextLine();

            if (input.trim().isEmpty()) {
                view.displayErrorMessage(StaffConstants.ERROR_SALARY_BLANK);
                continue;
            }

            try {
                double salary = Double.parseDouble(input);
                if (salary >= StaffData.MIN_SALARY) {
                    return salary;
                } else {
                    view.displayErrorMessage(StaffConstants.ERROR_SALARY_ZERO);
                }
            } catch (NumberFormatException e) {
                view.displayErrorMessage(StaffConstants.ERROR_SALARY_NUMERIC);
            }
        }
    }

    /**
     * Validate department
     * 
     * @param isModify
     * @return department
     */
    public String getValidDepartment(boolean isModify) {
        String department;
        while (true) {
            view.displayPrompt(isModify ? StaffConstants.NEW_DEPARTMENT : StaffConstants.ENTER_DEPARTMENT);
            department = scanner.nextLine();

            if (department.trim().isEmpty()) {
                view.displayErrorMessage(StaffConstants.ERROR_DEPARTMENT_BLANK);
                continue;
            }

            if (department.matches(StaffConstants.REGEX_POSITION_DEPT)) {
                view.displayErrorMessage(StaffConstants.ERROR_DEPARTMENT_CONTAINS_NUMBERS);
                continue;
            }

            return findExistingDepartment(department);
        }
    }

    /**
     * Convert string (add, search, delete) to Title Case
     * 
     * @param input
     * @return title case string
     */
    public String toTitleCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        StringBuilder result = new StringBuilder();
        String[] words = input.trim().split("\\s+");
        for (int i = 0; i < words.length; i++) {
            if (i > 0) {
                result.append(" ");
            }
            String word = words[i];
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)));
                if (word.length() > 1) {
                    result.append(word.substring(1).toLowerCase());
                }
            }
        }
        return result.toString();
    }

    /**
     * Get confirmation from user
     * 
     * @param prompt
     * @return true if yes, false if no
     */
    public boolean getConfirmation(String prompt) {
        while (true) {
            view.displayPrompt(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("yes")) {
                return true;
            } else if (input.equals("no")) {
                return false;
            } else {
                view.displayErrorMessage(StaffConstants.ERROR_YES_NO_ONLY);
            }
        }
    }

    /**
     * Find existing department
     * 
     * @param inputDept
     * @return existing department or new department in title case
     */
    public String findExistingDepartment(String inputDept) {
        for (Staff staff : staffList) {
            if (staff.getDepartment().equalsIgnoreCase(inputDept)) {
                // Return the existing one
                return staff.getDepartment();
            }
        }
        // Return new department
        return toTitleCase(inputDept);
    }
}
