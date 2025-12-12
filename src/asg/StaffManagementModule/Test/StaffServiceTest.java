package asg.StaffManagementModule.Test;

import asg.StaffManagementModule.Controller.StaffController;
import asg.StaffManagementModule.Model.Staff;
import asg.StaffManagementModule.View.StaffView;
import asg.StaffManagementModule.Service.StaffService;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Test cases for StaffService class
 * Total 16 test cases
 */
public class StaffServiceTest {

        private StaffController staffSystem;
        private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        private final PrintStream originalOut = System.out;

        @BeforeEach
        public void setUp() {
                System.setOut(new PrintStream(outputStream));
                staffSystem = new StaffController();
                staffSystem.retrieveStaff();
        }

        @AfterEach
        public void tearDown() {
                System.setOut(originalOut);
        }

        // For simulating user input
        private void simulateInput(String input) {
                java.io.ByteArrayInputStream inputStream = new java.io.ByteArrayInputStream(input.getBytes());
                System.setIn(inputStream);
        }

        // ------------------- SERVICE FUNCTION TEST CASES ------------------- //

        /**
         * Test StaffService --> getNextAvailableId
         */
        @Test
        @Order(1)
        @DisplayName("StaffService - Get Next Available ID")
        public void testStaffServiceGetNextAvailableId() {
                StaffView view = new StaffView();
                Scanner scanner = new Scanner(System.in);
                ArrayList<Staff> list = new ArrayList<>(staffSystem.staffList);

                StaffService service = new StaffService(view, scanner, list);

                // Get next available ID - should return STxxx format
                String nextId = service.getNextAvailableId();
                assertNotNull(nextId, "Next available ID should not be null");
                assertTrue(nextId.matches("(?i)ST\\d{3}"), "ID should match format STxxx");
                assertNotEquals("ST000", nextId, "ID should not be ST000");
        }

        /**
         * Test StaffService --> toTitleCase single word
         */
        @Test
        @Order(2)
        @DisplayName("StaffService - ToTitleCase Single Word")
        public void testStaffServiceToTitleCaseSingleWord() {
                StaffView view = new StaffView();
                Scanner scanner = new Scanner(System.in);
                ArrayList<Staff> list = new ArrayList<>();

                StaffService service = new StaffService(view, scanner, list);

                // Convert single word to title case
                String result = service.toTitleCase("hello");
                assertEquals("Hello", result, "Should capitalize first letter");
        }

        /**
         * Test StaffService --> toTitleCase multiple words
         */
        @Test
        @Order(3)
        @DisplayName("StaffService - ToTitleCase Multiple Words")
        public void testStaffServiceToTitleCaseMultipleWords() {
                StaffView view = new StaffView();
                Scanner scanner = new Scanner(System.in);
                ArrayList<Staff> list = new ArrayList<>();

                StaffService service = new StaffService(view, scanner, list);

                // Convert multiple words to title case
                String result = service.toTitleCase("john doe");
                assertEquals("John Doe", result, "Should capitalize each word");
        }

        /**
         * Test StaffService --> toTitleCase all caps
         */
        @Test
        @Order(4)
        @DisplayName("StaffService - ToTitleCase All Caps")
        public void testStaffServiceToTitleCaseAllCaps() {
                StaffView view = new StaffView();
                Scanner scanner = new Scanner(System.in);
                ArrayList<Staff> list = new ArrayList<>();

                StaffService service = new StaffService(view, scanner, list);

                // Convert all caps to title case
                String result = service.toTitleCase("JOHN DOE");
                assertEquals("John Doe", result, "Should convert all caps to title case");
        }

        /**
         * Test StaffService --> isStaffIdExists - existing ID
         */
        @Test
        @Order(5)
        @DisplayName("StaffService - IsStaffIdExists - Existing ID")
        public void testStaffServiceIsStaffIdExistsTrue() {
                StaffView view = new StaffView();
                Scanner scanner = new Scanner(System.in);
                ArrayList<Staff> list = new ArrayList<>(staffSystem.staffList);

                StaffService service = new StaffService(view, scanner, list);

                // Check if ST001 exists - should return true
                boolean exists = service.isStaffIdExists("ST001");
                assertTrue(exists, "ST001 should exist in the list");
        }

        /**
         * Test StaffService --> isStaffIdExists - non-existent ID
         */
        @Test
        @Order(6)
        @DisplayName("StaffService - IsStaffIdExists - Non-Existent ID")
        public void testStaffServiceIsStaffIdExistsFalse() {
                StaffView view = new StaffView();
                Scanner scanner = new Scanner(System.in);
                ArrayList<Staff> list = new ArrayList<>(staffSystem.staffList);

                StaffService service = new StaffService(view, scanner, list);

                // Check if ST999 exists - should return false
                boolean exists = service.isStaffIdExists("ST999");
                assertFalse(exists, "ST999 should not exist in the list");
        }

        /**
         * Test StaffService --> getConfirmation - yes
         */
        @Test
        @Order(7)
        @DisplayName("StaffService - GetConfirmation - Yes (SI)")
        public void testStaffServiceGetConfirmationYes() {
                // yes
                simulateInput("yes\n");

                StaffView view = new StaffView();
                Scanner scanner = new Scanner(System.in);
                ArrayList<Staff> list = new ArrayList<>();

                StaffService service = new StaffService(view, scanner, list);

                // Get confirmation - should return true for 'yes'
                boolean result = service.getConfirmation("Confirm?");
                assertTrue(result, "Should return true for 'yes' input");
        }

        /**
         * Test StaffService --> getConfirmation - no
         */
        @Test
        @Order(8)
        @DisplayName("StaffService - GetConfirmation - No (SI)")
        public void testStaffServiceGetConfirmationNo() {
                //  no
                simulateInput("no\n");

                StaffView view = new StaffView();
                Scanner scanner = new Scanner(System.in);
                ArrayList<Staff> list = new ArrayList<>();

                StaffService service = new StaffService(view, scanner, list);

                // Get confirmation - should return false for 'no'
                boolean result = service.getConfirmation("Confirm?");
                assertFalse(result, "Should return false for 'no' input");
        }

        /**
         * Test StaffService --> findExistingDepartment - existing department
         */
        @Test
        @Order(9)
        @DisplayName("StaffService - FindExistingDepartment - Existing")
        public void testStaffServiceFindExistingDepartmentExists() {
                StaffView view = new StaffView();
                Scanner scanner = new Scanner(System.in);
                ArrayList<Staff> list = new ArrayList<>(staffSystem.staffList);

                StaffService service = new StaffService(view, scanner, list);

                // Find existing IT department (case insensitive)
                String dept = service.findExistingDepartment("it");
                assertEquals("IT", dept, "Should find existing IT department with correct case");
        }

        /**
         * Test StaffService --> findExistingDepartment - new department
         */
        @Test
        @Order(10)
        @DisplayName("StaffService - FindExistingDepartment - New Dept")
        public void testStaffServiceFindExistingDepartmentNew() {
                StaffView view = new StaffView();
                Scanner scanner = new Scanner(System.in);
                ArrayList<Staff> list = new ArrayList<>(staffSystem.staffList);

                StaffService service = new StaffService(view, scanner, list);

                // Find new department - should return title case
                String dept = service.findExistingDepartment("marketing");
                assertEquals("Marketing", dept, "Should convert new department to title case");
        }

        /**
         * Test StaffService --> getValidStaffId (SI)
         */
        @Test
        @Order(11)
        @DisplayName("StaffService - GetValidStaffId (SI)")
        public void testStaffServiceGetValidStaffId() {
                //  ST006
                simulateInput("ST006\n");

                StaffView view = new StaffView();
                Scanner scanner = new Scanner(System.in);
                ArrayList<Staff> list = new ArrayList<>(staffSystem.staffList);

                StaffService service = new StaffService(view, scanner, list);

                // Get valid staff ID - should return entered ID
                String id = service.getValidStaffId();
                assertEquals("ST006", id.toUpperCase(), "Should return valid staff ID");
        }

        /**
         * Test StaffService --> getValidName (SI)
         */
        @Test
        @Order(12)
        @DisplayName("StaffService - GetValidName (SI)")
        public void testStaffServiceGetValidName() {
                //  John Smith
                simulateInput("John Smith\n");

                StaffView view = new StaffView();
                Scanner scanner = new Scanner(System.in);
                ArrayList<Staff> list = new ArrayList<>();

                StaffService service = new StaffService(view, scanner, list);

                // Get valid name - should return title-cased name
                String name = service.getValidName(false);
                assertEquals("John Smith", name, "Should return title-cased name");
        }

        /**
         * Test StaffService --> getValidGender (SI)
         */
        @Test
        @Order(13)
        @DisplayName("StaffService - GetValidGender (SI)")
        public void testStaffServiceGetValidGender() {
                //  male
                simulateInput("male\n");

                StaffView view = new StaffView();
                Scanner scanner = new Scanner(System.in);
                ArrayList<Staff> list = new ArrayList<>();

                StaffService service = new StaffService(view, scanner, list);

                // Get valid gender - should normalize to Male
                String gender = service.getValidGender(false);
                assertEquals("Male", gender, "Should return normalized gender");
        }

        /**
         * Test StaffService --> getValidPosition (SI)
         */
        @Test
        @Order(14)
        @DisplayName("StaffService - GetValidPosition (SI)")
        public void testStaffServiceGetValidPosition() {
                //  Senior Developer
                simulateInput("Senior Developer\n");

                StaffView view = new StaffView();
                Scanner scanner = new Scanner(System.in);
                ArrayList<Staff> list = new ArrayList<>();

                StaffService service = new StaffService(view, scanner, list);

                // Get valid position - should return title-cased position
                String position = service.getValidPosition(false);
                assertEquals("Senior Developer", position, "Should return title-cased position");
        }

        /**
         * Test StaffService --> getValidSalary (SI)
         */
        @Test
        @Order(15)
        @DisplayName("StaffService - GetValidSalary (SI)")
        public void testStaffServiceGetValidSalary() {
                //  5500
                simulateInput("5500\n");

                StaffView view = new StaffView();
                Scanner scanner = new Scanner(System.in);
                ArrayList<Staff> list = new ArrayList<>();

                StaffService service = new StaffService(view, scanner, list);

                // Get valid salary - should return entered value
                double salary = service.getValidSalary(false);
                assertEquals(5500.0, salary, 0.01, "Should return valid salary");
        }

        /**
         * Test StaffService --> getValidDepartment (SI)
         */
        @Test
        @Order(16)
        @DisplayName("StaffService - GetValidDepartment (SI)")
        public void testStaffServiceGetValidDepartment() {
                //  Finance
                simulateInput("Finance\n");

                StaffView view = new StaffView();
                Scanner scanner = new Scanner(System.in);
                ArrayList<Staff> list = new ArrayList<>();

                StaffService service = new StaffService(view, scanner, list);

                // Get valid department - should return title-cased department
                String dept = service.getValidDepartment(false);
                assertEquals("Finance", dept, "Should return title-cased department");
        }

}
