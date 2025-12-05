package asg.Controller;

import asg.Model.Staff;
import asg.Constants.StaffConstants;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class StaffManagementSystem {

    public static void main() {
        StaffManagementSystem staffSystem = new StaffManagementSystem();
        staffSystem.initializeStaff();
        staffSystem.staffMenu();
    }

    public final ArrayList<Staff> staffList;
    public final Scanner scanner;

    public StaffManagementSystem() {
        this.staffList = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void staffMenu() {
        int option;

        do {
            /**
             * Use the fixed variables from StaffConstants to display the staff menu
             */
            // Staff Menu Header
            System.out.print(StaffConstants.STAFF_MENU_HEADER);
            System.out.print(StaffConstants.STAFF_MENU_TITLE);
            System.out.print(StaffConstants.STAFF_MENU_HEADER);
            // Staff Menu Options
            System.out.print(StaffConstants.STAFF_MENU_OPTION_1);
            System.out.print(StaffConstants.STAFF_MENU_OPTION_2);
            System.out.print(StaffConstants.STAFF_MENU_OPTION_3);
            System.out.print(StaffConstants.STAFF_MENU_OPTION_4);
            System.out.print(StaffConstants.STAFF_MENU_OPTION_5);
            System.out.print(StaffConstants.STAFF_MENU_OPTION_6);
            System.out.print(StaffConstants.STAFF_MENU_OPTION_0);
            // Staff Menu Ender
            System.out.print(StaffConstants.STAFF_MENU_ENDER);

            while (true) {
                System.out.print("Enter your choice: ");
                try {
                    option = scanner.nextInt();
                    scanner.nextLine();
                    break;
                } catch (InputMismatchException e) {
                    scanner.nextLine();
                    System.out.println("Invalid choice. Please enter again...\n");
                }
            }

            switch (option) {
                case 1:
                    displayStaff();
                    break;
                case 2:
                    addStaff();
                    break;
                case 3:
                    modifyStaff();
                    break;
                case 4:
                    deleteStaff();
                    break;
                case 5:
                    searchStaff();
                    break;
                case 6:
                    reportDepartment();
                    break;
                case 0:
                    closeSystem();
                    break;
                default:
                    System.out.println("\nInvalid choice...\n");
            }
        } while (option != 0);

        // scanner.close();
    }

    public void displayStaff() {
        if (staffList.isEmpty()) {
            System.out.println("No staff information available.");
        } else {
            System.out.println("Staff Details:");
            for (Staff staff : staffList) {

                System.out.println("ID: " + staff.getId());
                System.out.println("Name: " + staff.getName());
                System.out.println("Gender: " + staff.getGender());
                System.out.println("Position: " + staff.getPosition());
                System.out.println("Salary: " + staff.getSalary());
                System.out.println("Department: " + staff.getDepartment());
                System.out.println("-----------------------------");
            }
        }
    }

    public void addStaff() {
        String id;
        while (true) {
            System.out.print("Enter ID: ");
            id = scanner.nextLine();
            if (id.matches("\\d+")) {
                break;
            } else {
                System.out.println("ID must contain numbers only. Please try again.");
            }
        }

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        String gender;
        while (true) {
            System.out.print("Enter Gender (male/female): ");
            gender = scanner.nextLine().toLowerCase();
            if (gender.equals("male") || gender.equals("female")) {
                break;
            } else {
                System.out.println("Gender must be 'male' or 'female'. Please try again.");
            }
        }

        System.out.print("Enter Position: ");
        String position = scanner.nextLine();

        if (position.matches(".*\\d+.*")) {
            System.out.println("Position cannot contain numbers. Please try again.");
            return;
        }

        double salary;
        while (true) {
            System.out.print("Enter Salary: ");
            try {
                salary = scanner.nextDouble();
                if (salary > 0) {
                    break;
                } else {
                    System.out.println("Salary cannot less than zero. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }
        scanner.nextLine();

        System.out.print("Enter Department: ");
        String department = scanner.nextLine();

        if (department.matches(".*\\d+.*")) {
            System.out.println("Department cannot contain numbers. Please try again.");
            return;
        }

        if (id.isEmpty() || name.isEmpty() || position.isEmpty() || department.isEmpty()) {
            System.out.println("ID, Name, Position, and Department are mandatory fields and cannot be empty.");
            return;
        }

        Staff newStaff = new Staff(id, name, gender, position, salary, department);
        staffList.add(newStaff);
        System.out.println("Staff added successfully.");
    }

    public void modifyStaff() {
        System.out.print("Enter ID of staff to modify: ");
        String staffIdToModify = scanner.nextLine();
        boolean found = false;

        for (Staff staff : staffList) {
            if (staff.id.equals(staffIdToModify)) {
                System.out.println("Current Staff Details:");
                displayStaffDetails(staff);

                System.out.print("Enter new Name: ");
                String name = scanner.nextLine();

                String gender;
                while (true) {
                    System.out.print("Enter new Gender (male/female): ");
                    gender = scanner.nextLine().toLowerCase();
                    if (gender.equals("male") || gender.equals("female")) {
                        break;
                    } else {
                        System.out.println("Gender must be 'male' or 'female'. Please try again.");
                    }
                }

                String position;
                while (true) {
                    System.out.print("Enter new Position: ");
                    position = scanner.nextLine();
                    if (!position.matches(".*\\d+.*")) {
                        break;
                    } else {
                        System.out.println("Position cannot contain numbers. Please try again.");
                    }
                }

                double salary;
                while (true) {
                    System.out.print("Enter new Salary: ");
                    try {
                        salary = scanner.nextDouble();
                        if (salary >= 0) {
                            break;
                        } else {
                            System.out.println("Salary cannot be negative. Please try again.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                        scanner.nextLine();
                    }
                }
                scanner.nextLine();

                String department;
                while (true) {
                    System.out.print("Enter new Department: ");
                    department = scanner.nextLine();
                    if (!department.matches(".*\\d+.*")) {
                        break;
                    } else {
                        System.out.println("Department cannot contain numbers. Please try again.");
                    }
                }

                if (name.isEmpty() || position.isEmpty() || department.isEmpty()) {
                    System.out.println("Name, Position, and Department are mandatory fields and cannot be empty.");
                    return;
                }

                staff.name = name;
                staff.gender = gender;
                staff.position = position;
                staff.salary = salary;
                staff.department = department;

                System.out.println("Staff modified successfully.");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Staff with ID " + staffIdToModify + " not found.");
        }
    }

    public void deleteStaff() {
        System.out.print("Enter ID of staff to delete: ");
        String staffIdToDelete = scanner.nextLine();
        boolean found = false;

        for (Staff staff : staffList) {
            if (staff.id.equals(staffIdToDelete)) {
                staffList.remove(staff);
                System.out.println("Staff with ID " + staffIdToDelete + " deleted successfully.");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Staff with ID " + staffIdToDelete + " not found.");
        }
    }

    public void searchStaff() {
        System.out.print("Enter ID or Name of staff to search: ");
        String searchQuery = scanner.nextLine();
        boolean found = false;

        System.out.println("Search Results:");
        for (Staff staff : staffList) {
            if (staff.id.equalsIgnoreCase(searchQuery) || staff.name.contains(searchQuery)) {
                displayStaffDetails(staff);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching staff found.");
        }
    }

    public void reportDepartment() {
        System.out.print("Enter Department to report: ");
        String departmentToReport = scanner.nextLine();
        double totalSalary = 0;

        System.out.println("Staff Report for Department: " + departmentToReport);

        for (Staff staff : staffList) {
            if (staff.department.equalsIgnoreCase(departmentToReport)) {
                displayStaffDetails(staff);
                totalSalary += staff.salary;
                System.out.println("-----------------------------");
            }
        }

        if (totalSalary > 0) {
            System.out.println("Total Salary for Department " + departmentToReport + ": " + totalSalary);
        } else {
            System.out.println("No staff found in Department " + departmentToReport);
        }
    }

    public void displayStaffDetails(Staff staff) {
        System.out.println("ID: " + staff.id);
        System.out.println("Name: " + staff.name);
        System.out.println("Gender: " + staff.gender);
        System.out.println("Position: " + staff.position);
        System.out.println("Salary: " + staff.salary);
        System.out.println("Department: " + staff.department);
    }

    public void initializeStaff() {
        Staff staff1 = new Staff("1", "js", "male", "Manager", 5000.0, "HR");
        Staff staff2 = new Staff("2", "edwin", "female", "Engineer", 4000.0, "IT");
        Staff staff3 = new Staff("3", "gw", "male", "Accountant", 4500.0, "Finance");
        Staff staff4 = new Staff("4", "wh", "male", "Accountant", 4000.0, "Finance");

        staffList.add(staff1);
        staffList.add(staff2);
        staffList.add(staff3);
        staffList.add(staff4);
    }

    public void closeSystem() {
        System.out.println("\nThank you for using the staff management system. Goodbye!\n");
    }
}
