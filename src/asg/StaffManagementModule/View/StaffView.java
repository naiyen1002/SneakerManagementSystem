package asg.StaffManagementModule.View;

import java.util.List;

import asg.StaffManagementModule.Constants.StaffConstants;
import asg.StaffManagementModule.Model.Staff;

public class StaffView {

    /**
     * Display menu header
     */
    public void displayMenuHeader() {
        System.out.print(StaffConstants.STAFF_MENU_HEADER);
        System.out.print(StaffConstants.STAFF_MENU_TITLE);
        System.out.println(StaffConstants.STAFF_MENU_HEADER);
    }

    /**
     * Display menu ender
     */
    public void displayMenuEnder() {
        System.out.print(StaffConstants.STAFF_MENU_ENDER);
    }

    /**
     * Display modify menu header
     */
    public void displayModifyMenuHeader() {
        System.out.print(StaffConstants.MODIFY_MENU_HEADER);
        System.out.println(StaffConstants.MODIFY_MENU_TITLE);
        System.out.println(StaffConstants.MODIFY_MENU_HEADER);
    }

    /**
     * Display modify menu ender
     */
    public void displayModifyMenuEnder() {
        System.out.print(StaffConstants.MODIFY_MENU_ENDER);
    }

    /**
     * Display single staff details
     * 
     * @param staff
     */
    public void displayStaffDetails(Staff staff) {
        System.out.println("\n" + StaffConstants.STAFF_DETAILS_SEPARATOR);
        System.out.println(StaffConstants.STAFF_DETAILS_TITLE);
        System.out.println(StaffConstants.STAFF_DETAILS_SEPARATOR);
        System.out.println("  " + StaffConstants.LABEL_ID + staff.getId());
        System.out.println("  " + StaffConstants.LABEL_NAME + staff.getName());
        System.out.println("  " + StaffConstants.LABEL_GENDER + staff.getGender());
        System.out.println("  " + StaffConstants.LABEL_POSITION + staff.getPosition());
        System.out.printf("  " + StaffConstants.LABEL_SALARY + "%.2f%n", staff.getSalary());
        System.out.println("  " + StaffConstants.LABEL_DEPARTMENT + staff.getDepartment());
        System.out.println(StaffConstants.STAFF_DETAILS_SEPARATOR);
    }

    /**
     * Display all staff members in table format
     * 
     * @param staffList
     */
    public void displayAllStaff(List<Staff> staffList) {
        if (staffList.isEmpty()) {
            System.out.println(StaffConstants.ERROR_NO_STAFF_AVAILABLE);
        } else {

            System.out.println("\n" + StaffConstants.TABLE_SEPARATOR);
            System.out.println(StaffConstants.TABLE_STAFF_TITLE);
            System.out.println(StaffConstants.TABLE_SEPARATOR);
            System.out.printf(StaffConstants.TABLE_STAFF_HEADER_FORMAT + "%n",
                    "No", "Staff ID", "Name", "Gender", "Position", "Salary (RM)", "Department");
            System.out.println(StaffConstants.TABLE_SEPARATOR);

            for (int i = 0; i < staffList.size(); i++) {
                Staff staff = staffList.get(i);
                System.out.printf(StaffConstants.TABLE_STAFF_ROW_FORMAT + "%n",
                        i + 1,
                        staff.getId(),
                        staff.getName(),
                        staff.getGender(),
                        staff.getPosition(),
                        staff.getSalary(),
                        staff.getDepartment());
            }
            System.out.println(StaffConstants.TABLE_SEPARATOR + "\n");
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
     * Display existing departments in the system
     * 
     * @param departments
     */
    public void displayExistingDepartments(List<String> departments) {
        System.out.println(StaffConstants.LABEL_EXISTING_DEPARTMENTS);
        if (departments.isEmpty()) {
            System.out.println("No departments found.");
        } else {
            for (String dept : departments) {
                System.out.println("--> " + dept);
            }
        }
        System.out.println();
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
