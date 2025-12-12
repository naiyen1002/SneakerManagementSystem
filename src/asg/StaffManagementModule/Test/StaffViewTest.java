package asg.StaffManagementModule.Test;

import asg.StaffManagementModule.Controller.StaffController;
import asg.StaffManagementModule.Model.Staff;
import asg.StaffManagementModule.View.StaffView;
import asg.StaffManagementModule.Constants.StaffConstants;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Test cases for StaffView class
 * Total 18 test cases
 */
public class StaffViewTest {

    private StaffController staffSystem;
    private StaffView view;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
        staffSystem = new StaffController();
        staffSystem.retrieveStaff();
        view = new StaffView();
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    // ------------------- VIEW UI TEST CASES ------------------- //

    /**
     * Test View --> Display staff details output
     */
    @Test
    @Order(1)
    @DisplayName("View - Display Staff Details")
    public void testViewDisplayStaffDetails() {
        // Get first staff and display details
        Staff staff = staffSystem.staffList.get(0);
        staffSystem.displayStaffDetails(staff);

        // Should show staff details header and info
        String output = outputStream.toString();
        assertTrue(output.contains(StaffConstants.STAFF_DETAILS_TITLE), "Should display staff details header");
        assertTrue(output.contains("ST001"), "Should display staff ID");
        assertTrue(output.contains("JiaHui"), "Should display staff name");
        assertTrue(output.contains("Manager"), "Should display staff position");
    }

    /**
     * Test View --> Display all staff table format
     */
    @Test
    @Order(2)
    @DisplayName("View - Display All Staff Table")
    public void testViewDisplayAllStaffTable() {
        // Display all staff in table format
        staffSystem.displayStaff();

        // Should show staff list with table header and staff data
        String output = outputStream.toString();
        assertTrue(output.contains(StaffConstants.TABLE_STAFF_TITLE), "Should display staff list title");
        assertTrue(output.contains("Staff ID"), "Should display table header");
        assertTrue(output.contains("ST001"), "Should display first staff ID");
        assertTrue(output.contains("ST002"), "Should display second staff ID");
    }

    /**
     * Test View --> Display empty staff list
     */
    @Test
    @Order(3)
    @DisplayName("View - Display Empty Staff List")
    public void testViewDisplayEmptyStaffList() {
        // Clear staff list and display
        staffSystem.staffList.clear();
        staffSystem.displayStaff();

        // Should show no staff message
        String output = outputStream.toString();
        assertTrue(output.contains(StaffConstants.ERROR_NO_STAFF_AVAILABLE), "Should display no staff message");
    }

    /**
     * Test View --> Overall report display
     */
    @Test
    @Order(4)
    @DisplayName("View - Overall Report Display")
    public void testViewOverallReportDisplay() {
        // Calculate total salary
        double totalSalary = 0;
        for (Staff staff : staffSystem.staffList) {
            totalSalary += staff.getSalary();
        }

        // Display overall report
        view.displayOverallReport(staffSystem.staffList, totalSalary);

        // Should show overall report with title and totals
        String output = outputStream.toString();
        assertTrue(output.contains(StaffConstants.REPORT_TITLE_OVERALL), "Should display overall report title");
        assertTrue(output.contains("Total Staff:"), "Should display total staff count");
        assertTrue(output.contains("Total Salary:"), "Should display total salary");
    }

    /**
     * Test View --> Department report table display
     */
    @Test
    @Order(5)
    @DisplayName("View - Department Report Table Display")
    public void testViewDepartmentReportTableDisplay() {
        // Filter IT department staff
        List<Staff> itStaff = staffSystem.staffList.stream()
                .filter(s -> s.getDepartment().equalsIgnoreCase("IT"))
                .collect(Collectors.toList());

        double totalSalary = itStaff.stream().mapToDouble(Staff::getSalary).sum();

        // Display department report table
        view.displayDepartmentReportTable("IT", itStaff, totalSalary);

        // Should show department report with title and department info
        String output = outputStream.toString();
        assertTrue(output.contains("DEPARTMENT STAFF REPORT"), "Should display department report title");
        assertTrue(output.contains("IT"), "Should display department name");
        assertTrue(output.contains("Total Staff in IT"), "Should display department total");
    }

    /**
     * Test View --> Report menu header display
     */
    @Test
    @Order(6)
    @DisplayName("View - Report Menu Header Display")
    public void testViewReportMenuHeaderDisplay() {
        // Display report menu header
        view.displayReportMenuHeader();

        // Should show report menu title
        String output = outputStream.toString();
        assertTrue(output.contains(StaffConstants.REPORT_MENU_TITLE), "Should display report menu title");
    }

    /**
     * Test View --> Main menu options display
     */
    @Test
    @Order(7)
    @DisplayName("View - Main Menu Options Display")
    public void testViewMainMenuOptionsDisplay() {
        // Display main menu options
        view.displayMainMenuOptions(asg.StaffManagementModule.Constants.StaffMenuOptions.getMainMenuOptions());

        // Should show all menu options
        String output = outputStream.toString();
        assertTrue(output.contains("Display Staff"), "Should display first menu option");
        assertTrue(output.contains("Add New Staff"), "Should display add option");
        assertTrue(output.contains("Exit"), "Should display exit option");
    }

    /**
     * Test View --> Modify menu options display
     */
    @Test
    @Order(8)
    @DisplayName("View - Modify Menu Options Display")
    public void testViewModifyMenuOptionsDisplay() {
        // Display modify menu options
        view.displayModifyMenuOptions(
                asg.StaffManagementModule.Constants.StaffMenuOptions.getModifyMenuOptions());

        // Should show modification options
        String output = outputStream.toString();
        assertTrue(output.contains("Modify Name"), "Should display modify name option");
        assertTrue(output.contains("Modify Salary"), "Should display modify salary option");
        assertTrue(output.contains("Finish Modification"), "Should display finish option");
    }

    /**
     * Test View --> Report menu options display
     */
    @Test
    @Order(9)
    @DisplayName("View - Report Menu Options Display")
    public void testViewReportMenuOptionsDisplay() {
        // Display report menu options
        view.displayReportMenuOptions(
                asg.StaffManagementModule.Constants.StaffMenuOptions.getReportMenuOptions());

        // Should show report options
        String output = outputStream.toString();
        assertTrue(output.contains("Overall Report"), "Should display overall report option");
        assertTrue(output.contains("Department Report"), "Should display department report option");
        assertTrue(output.contains("Back to Staff Menu"), "Should display back option");
    }

    /**
     * Test View --> Suggested ID display
     */
    @Test
    @Order(10)
    @DisplayName("View - Suggested ID Display")
    public void testViewSuggestedIdDisplay() {
        // Display suggested ID
        view.displaySuggestedId("ST006");

        // Should show suggested ID message
        String output = outputStream.toString();
        assertTrue(output.contains("Suggested next available ID"), "Should display suggested ID label");
        assertTrue(output.contains("ST006"), "Should display the suggested ID");
    }

    /**
     * Test View --> Existing departments display
     */
    @Test
    @Order(11)
    @DisplayName("View - Existing Departments Display")
    public void testViewExistingDepartmentsDisplay() {
        // Display existing departments
        List<String> departments = Arrays.asList("IT", "HR", "Finance");
        view.displayExistingDepartments(departments);

        // Should show departments header and list
        String output = outputStream.toString();
        assertTrue(output.contains("Existing Departments"), "Should display departments header");
        assertTrue(output.contains("IT"), "Should display IT department");
        assertTrue(output.contains("HR"), "Should display HR department");
    }

    /**
     * Test View --> Success message display
     */
    @Test
    @Order(12)
    @DisplayName("View - Success Message Display")
    public void testViewSuccessMessageDisplay() {
        // Display success message
        view.displaySuccessMessage(StaffConstants.SUCCESS_STAFF_ADDED);

        // Should show success message
        String output = outputStream.toString();
        assertTrue(output.contains(StaffConstants.SUCCESS_STAFF_ADDED), "Should display success message");
    }

    /**
     * Test View --> Error message display
     */
    @Test
    @Order(13)
    @DisplayName("View - Error Message Display")
    public void testViewErrorMessageDisplay() {
        // Display error message
        view.displayErrorMessage("Invalid input.");

        // Should show error message
        String output = outputStream.toString();
        assertTrue(output.contains("Invalid input"), "Should display error message");
    }

    /**
     * Test View --> Menu header display
     */
    @Test
    @Order(14)
    @DisplayName("View - Menu Header Display")
    public void testViewMenuHeaderDisplay() {
        // Display menu header
        view.displayMenuHeader();

        // Should show menu title
        String output = outputStream.toString();
        assertTrue(output.contains(StaffConstants.STAFF_MENU_TITLE), "Should display menu title");
    }

    /**
     * Test View --> Modify menu header display
     */
    @Test
    @Order(15)
    @DisplayName("View - Modify Menu Header Display")
    public void testViewModifyMenuHeaderDisplay() {
        // Display modify menu header
        view.displayModifyMenuHeader();

        // Should show modify menu title
        String output = outputStream.toString();
        assertTrue(output.contains(StaffConstants.MODIFY_MENU_TITLE), "Should display modify menu title");
    }

    /**
     * Test View --> Search results header display
     */
    @Test
    @Order(16)
    @DisplayName("View - Search Results Header Display")
    public void testViewSearchResultsHeaderDisplay() {
        // Display search results header
        view.displaySearchResultsHeader();

        // Should show search results header
        String output = outputStream.toString();
        assertTrue(output.contains(StaffConstants.LABEL_SEARCH_RESULTS), "Should display search results header");
    }

    /**
     * Test View --> Exit message display
     */
    @Test
    @Order(17)
    @DisplayName("View - Exit Message Display")
    public void testViewExitMessageDisplay() {
        // Display exit message
        view.displayExitMessage();

        // Should show exit/goodbye message
        String output = outputStream.toString();
        assertTrue(output.contains(StaffConstants.MESSAGE_EXIT_STAFF_MODULE) || output.contains("Goodbye"),
                "Should display exit message");
    }

    /**
     * Test View --> Department report display
     */
    @Test
    @Order(18)
    @DisplayName("View - Department Report Display")
    public void testViewDepartmentReportDisplay() {
        // Filter HR department staff
        List<Staff> hrStaff = staffSystem.staffList.stream()
                .filter(s -> s.getDepartment().equalsIgnoreCase("HR"))
                .collect(Collectors.toList());

        double totalSalary = hrStaff.stream().mapToDouble(Staff::getSalary).sum();

        // Display department report
        view.displayDepartmentReport("HR", hrStaff, totalSalary);

        // Should show department name and total salary label
        String output = outputStream.toString();
        assertTrue(output.contains("HR"), "Should display department name");
        assertTrue(output.contains("Total Salary for Department"),
                "Should display total salary label");
    }

}
