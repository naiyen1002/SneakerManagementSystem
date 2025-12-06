package asg.StaffManagementModule.View;

import java.util.List;

import asg.StaffManagementModule.Constants.StaffConstants;
import asg.StaffManagementModule.Model.Staff;

public class StaffView {

    /**
     * Staff module menu display
     */
    public void displayMenu() {
        System.out.print(StaffConstants.STAFF_MENU_HEADER);
        System.out.print(StaffConstants.STAFF_MENU_TITLE);
        System.out.print(StaffConstants.STAFF_MENU_HEADER);
        System.out.print(StaffConstants.STAFF_MENU_OPTION_1);
        System.out.print(StaffConstants.STAFF_MENU_OPTION_2);
        System.out.print(StaffConstants.STAFF_MENU_OPTION_3);
        System.out.print(StaffConstants.STAFF_MENU_OPTION_4);
        System.out.print(StaffConstants.STAFF_MENU_OPTION_5);
        System.out.print(StaffConstants.STAFF_MENU_OPTION_6);
        System.out.print(StaffConstants.STAFF_MENU_OPTION_0);
        System.out.print(StaffConstants.STAFF_MENU_ENDER);
    }

    /**
     * Display single staff details
     * 
     * @param staff
     */
    public void displayStaffDetails(Staff staff) {
        System.out.println(StaffConstants.LABEL_ID + staff.getId());
        System.out.println(StaffConstants.LABEL_NAME + staff.getName());
        System.out.println(StaffConstants.LABEL_GENDER + staff.getGender());
        System.out.println(StaffConstants.LABEL_POSITION + staff.getPosition());
        System.out.println(StaffConstants.LABEL_SALARY + staff.getSalary());
        System.out.println(StaffConstants.LABEL_DEPARTMENT + staff.getDepartment());
    }

    /**
     * Display all staff members
     * 
     * @param staffList
     */
    public void displayAllStaff(List<Staff> staffList) {
        if (staffList.isEmpty()) {
            System.out.println(StaffConstants.ERROR_NO_STAFF_AVAILABLE);
        } else {
            System.out.println(StaffConstants.LABEL_STAFF_DETAILS);
            for (Staff staff : staffList) {
                displayStaffDetails(staff);
                System.out.println(StaffConstants.LABEL_SEPARATOR);
            }
        }
    }

    /**
     * Display search results header
     */
    public void displaySearchResultsHeader() {
        System.out.println(StaffConstants.LABEL_SEARCH_RESULTS);
    }

    /**
     * Display current staff header
     */
    public void displayCurrentStaffHeader() {
        System.out.println(StaffConstants.LABEL_CURRENT_STAFF);
    }

    /**
     * Display department report
     * 
     * @param department
     * @param staffInDepartment
     * @param totalSalary
     */
    public void displayDepartmentReport(String department, List<Staff> staffInDepartment, double totalSalary) {
        System.out.println(String.format(StaffConstants.LABEL_DEPARTMENT_REPORT, department));

        if (staffInDepartment.isEmpty()) {
            System.out.println(String.format(StaffConstants.ERROR_NO_STAFF_IN_DEPARTMENT, department));
        } else {
            for (Staff staff : staffInDepartment) {
                displayStaffDetails(staff);
                System.out.println(StaffConstants.LABEL_SEPARATOR);
            }
            System.out.println(String.format(StaffConstants.LABEL_TOTAL_SALARY, department, totalSalary));
        }
    }

    /**
     * Display success message
     */
    public void displaySuccessMessage(String message) {
        System.out.println(message);
    }

    /**
     * Display error message
     * 
     * @param message
     */
    public void displayErrorMessage(String message) {
        System.out.println(message);
    }

    /**
     * Display exit message
     */
    public void displayExitMessage() {
        System.out.println(StaffConstants.MESSAGE_EXIT_STAFF_MODULE);
    }

    /**
     * Display prompt message
     * 
     * @param prompt
     */
    public void displayPrompt(String prompt) {
        System.out.print(prompt);
    }

    /**
     * Display separator line
     */
    public void displaySeparator() {
        System.out.println(StaffConstants.LABEL_SEPARATOR);
    }

}
