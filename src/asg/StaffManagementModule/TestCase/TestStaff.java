package asg.StaffManagementModule.TestCase;

import asg.StaffManagementModule.Controller.StaffController;
import asg.StaffManagementModule.Model.Staff;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestStaff {

        private StaffController staffSystem;
        private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        private final PrintStream originalOut = System.out;

        @BeforeEach
        public void setUp() {
                System.setOut(new PrintStream(outputStream));
        }

        @AfterEach
        public void tearDown() {
                System.setOut(originalOut);
                System.setIn(System.in);
        }

        private void simulateInput(String data) {
                ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
                System.setIn(testIn);
                // Recreate staffSystem with new input stream
                staffSystem = new StaffController();
                staffSystem.retrieveStaff();
        }

        // ------------------- ADD FUNCTION TEST CASES ------------------- //

        /**
         * Add new staff --> All fields correct
         */
        @Test
        @DisplayName("Add Staff - All Valid Inputs")
        public void testAddStaff() {
                simulateInput("ST006\nAlice\nFemale\nDeveloper\n4500\nIT\n");

                int initialSize = staffSystem.staffList.size();
                staffSystem.addStaff();

                assertEquals(initialSize + 1, staffSystem.staffList.size(),
                                "Staff list should increase by 1 after adding staff");

                Staff addedStaff = staffSystem.staffList.get(staffSystem.staffList.size() - 1);
                assertEquals("ST006", addedStaff.getId(), "ID should be ST006");
                assertEquals("Alice", addedStaff.getName(), "Name should be Alice");
                assertEquals("Female", addedStaff.getGender(), "Gender should be Female");
                assertEquals("Developer", addedStaff.getPosition(), "Position should be Developer");
                assertEquals(4500.0, addedStaff.getSalary(), 0.001, "Salary should be 4500");
                assertEquals("IT", addedStaff.getDepartment(), "Department should be IT");

                String output = outputStream.toString();
                assertTrue(output.contains("Staff added successfully"),
                                "Should display success message");
        }

        /**
         * Add staff with invalid ID
         */
        @Test
        @DisplayName("Add Staff - Invalid ID Format")
        public void testAddStaffInvalidId() {
                simulateInput("abc\nST006\nDavid\nmale\nManager\n6000\nHR\n");

                int initialSize = staffSystem.staffList.size();
                staffSystem.addStaff();

                assertEquals(initialSize + 1, staffSystem.staffList.size());
                Staff addedStaff = staffSystem.staffList.get(staffSystem.staffList.size() - 1);
                assertEquals("ST006", addedStaff.getId());

                String output = outputStream.toString();
                assertTrue(output.contains("ID must start with ST"),
                                "Should display error for invalid ID format");
        }

        /**
         * Add staff with invalid gender
         */
        @Test
        @DisplayName("Add Staff - Invalid Gender")
        public void testAddStaffInvalidGender() {
                simulateInput("ST006\nCarol\nnon-binary\nfemale\nDesigner\n4800\nDesign\n");

                int initialSize = staffSystem.staffList.size();
                staffSystem.addStaff();

                assertEquals(initialSize + 1, staffSystem.staffList.size());
                Staff addedStaff = staffSystem.staffList.get(staffSystem.staffList.size() - 1);
                assertEquals("Female", addedStaff.getGender());

                String output = outputStream.toString();
                assertTrue(output.contains("Gender must be 'male' or 'female'"),
                                "Should display error for invalid gender");
        }

        /**
         * Add staff with invalid position (contains numbers)
         */
        @Test
        @DisplayName("Add Staff - Invalid Position")
        public void testAddStaffInvalidPosition() {
                simulateInput("ST007\nEason\nfemale\nDev123\nDeveloper\n5000\nIT\n");

                int initialSize = staffSystem.staffList.size();
                staffSystem.addStaff();

                assertEquals(initialSize + 1, staffSystem.staffList.size());
                Staff addedStaff = staffSystem.staffList.get(staffSystem.staffList.size() - 1);
                assertEquals("Developer", addedStaff.getPosition());

                String output = outputStream.toString();
                assertTrue(output.contains("Position cannot contain numbers"),
                                "Should display error for invalid position");
        }

        /**
         * Add staff with invalid department (contains numbers)
         */
        @Test
        @DisplayName("Add Staff - Invalid Department")
        public void testAddStaffInvalidDepartment() {
                simulateInput("ST008\nRobert\nmale\nAnalyst\n4500\nIT123\nFinance\n");

                int initialSize = staffSystem.staffList.size();
                staffSystem.addStaff();

                assertEquals(initialSize + 1, staffSystem.staffList.size());
                Staff addedStaff = staffSystem.staffList.get(staffSystem.staffList.size() - 1);
                assertEquals("Finance", addedStaff.getDepartment());

                String output = outputStream.toString();
                assertTrue(output.contains("Department cannot contain numbers"),
                                "Should display error for invalid department");
        }

        /**
         * Add staff with invalid salary (non-numeric)
         */
        @Test
        @DisplayName("Add Staff - Invalid Salary")
        public void testAddStaffInvalidSalary() {
                simulateInput("ST009\nGrace\nfemale\nDesigner\nabc\n4800\nDesign\n");

                int initialSize = staffSystem.staffList.size();
                staffSystem.addStaff();

                assertEquals(initialSize + 1, staffSystem.staffList.size());
                Staff addedStaff = staffSystem.staffList.get(staffSystem.staffList.size() - 1);
                assertEquals(4800.0, addedStaff.getSalary(), 0.001);

                String output = outputStream.toString();
                assertTrue(output.contains("Salary must be a numeric value"),
                                "Should display error for non-numeric salary");
        }

        /**
         * Add staff with duplicate ID
         */
        @Test
        @DisplayName("Add Staff - Duplicate ID")
        public void testAddStaffDuplicateId() {
                simulateInput("ST001\nST011\nBernice\nmale\nTester\n5000\nIT\n");

                int initialSize = staffSystem.staffList.size();
                staffSystem.addStaff();

                assertEquals(initialSize + 1, staffSystem.staffList.size());
                Staff addedStaff = staffSystem.staffList.get(staffSystem.staffList.size() - 1);
                assertEquals("ST011", addedStaff.getId());

                String output = outputStream.toString();
                assertTrue(output.contains("already exists"),
                                "Should display error for duplicate ID");
        }

        /**
         * Add staff with negative salary
         */
        @Test
        @DisplayName("Add Staff - Negative Salary")
        public void testAddStaffNegativeSalary() {
                simulateInput("ST012\nYshan\nfemale\nAnalyst\n-500\n3500\nFinance\n");

                int initialSize = staffSystem.staffList.size();
                staffSystem.addStaff();

                assertEquals(initialSize + 1, staffSystem.staffList.size());
                Staff addedStaff = staffSystem.staffList.get(staffSystem.staffList.size() - 1);
                assertEquals(3500.0, addedStaff.getSalary(), 0.001);

                String output = outputStream.toString();
                assertTrue(output.contains("cannot") && output.contains("zero")
                                || output.contains("less than"),
                                "Should display error for negative salary");
        }

        // ------------------- MODIFY FUNCTION TEST CASES ------------------- //

        /**
         * Modify staff --> All fields correct (with confirmation)
         */
        @Test
        @DisplayName("Modify Staff - All Valid Modifications")
        public void testModifyStaff() {
                simulateInput("ST002\n1\nJojo\nyes\n2\nmale\nyes\n3\nContent Creator\nyes\n4\n5500\nyes\n5\nMultimedia\nyes\n0\n");

                staffSystem.modifyStaff();

                Staff modifiedStaff = staffSystem.staffList.stream()
                                .filter(s -> s.getId().equals("ST002"))
                                .findFirst()
                                .orElse(null);

                assertNotNull(modifiedStaff, "Staff with ID ST002 should exist");
                assertEquals("Jojo", modifiedStaff.getName(), "Name should be updated");
                assertEquals("Male", modifiedStaff.getGender(), "Gender should be updated to Male");
                assertEquals("Content Creator", modifiedStaff.getPosition(), "Position should be updated");
                assertEquals(5500.0, modifiedStaff.getSalary(), 0.001, "Salary should be updated");
                assertEquals("Multimedia", modifiedStaff.getDepartment(), "Department should be updated");

                String output = outputStream.toString();
                assertTrue(output.contains("Staff modified successfully"),
                                "Should display success message");
        }

        /**
         * Modify staff with invalid ID
         */
        @Test
        @DisplayName("Modify Staff - Invalid ID")
        public void testModifyStaffInvalidId() {
                simulateInput("ST999\nST001\n1\nJiahuiLee\nyes\n0\n");

                staffSystem.modifyStaff();

                Staff modifiedStaff = staffSystem.staffList.stream()
                                .filter(s -> s.getId().equals("ST001"))
                                .findFirst()
                                .orElse(null);

                assertNotNull(modifiedStaff, "Staff with ID ST001 should exist");
                assertEquals("Jiahuilee", modifiedStaff.getName(), "Name should be updated (title case)");

                String output = outputStream.toString();
                assertTrue(output.contains("not found"),
                                "Should display not found error for invalid ID");
        }

        /**
         * Modify staff --> only modify one field (salary)
         */
        @Test
        @DisplayName("Modify Staff - Single Field (Salary)")
        public void testModifyStaffSalary() {
                simulateInput("ST001\n4\n9999\nyes\n0\n");

                staffSystem.modifyStaff();

                Staff modifiedStaff = staffSystem.staffList.stream()
                                .filter(s -> s.getId().equals("ST001"))
                                .findFirst()
                                .orElse(null);

                assertNotNull(modifiedStaff, "Staff with ID ST001 should exist");
                assertEquals(9999.0, modifiedStaff.getSalary(), 0.001, "Salary should be updated");

                String output = outputStream.toString();
                assertTrue(output.contains("Salary updated successfully"),
                                "Should display salary update success message");
        }

        /**
         * Modify staff --> modify multiple fields continuously
         */
        @Test
        @DisplayName("Modify Staff - Multiple Fields")
        public void testModifyStaffMultipleFields() {
                simulateInput("ST001\n1\nJh Lee\nyes\n3\nSenior Manager\nyes\n4\n12000\nyes\n0\n");

                staffSystem.modifyStaff();

                Staff modifiedStaff = staffSystem.staffList.stream()
                                .filter(s -> s.getId().equals("ST001"))
                                .findFirst()
                                .orElse(null);

                assertNotNull(modifiedStaff, "Staff with ID ST001 should exist");
                assertEquals("Jh Lee", modifiedStaff.getName(), "Name should be title case");
                assertEquals("Senior Manager", modifiedStaff.getPosition(), "Position should be updated");
                assertEquals(12000.0, modifiedStaff.getSalary(), 0.001, "Salary should be updated");

                String output = outputStream.toString();
                assertTrue(output.contains("Name updated successfully"),
                                "Should display name update success");
                assertTrue(output.contains("Position updated successfully"),
                                "Should display position update success");
                assertTrue(output.contains("Salary updated successfully"),
                                "Should display salary update success");
        }

        /**
         * Modify staff --> cancel modification
         */
        @Test
        @DisplayName("Modify Staff - Cancel Modification")
        public void testModifyStaffCancelled() {
                simulateInput("ST001\n1\nJIAHUI\nno\n0\n");

                staffSystem.modifyStaff();

                String output = outputStream.toString();
                assertTrue(output.contains("cancelled") || output.contains("Action cancelled"),
                                "Should display cancelled message");
        }

        // ------------------- DELETE FUNCTION TEST CASES ------------------- //

        /**
         * Delete staff --> With valid ID and confirmation
         */
        @Test
        @DisplayName("Delete Staff - Valid ID with Confirmation")
        public void testDeleteStaff() {
                simulateInput("ST002\nyes\n");

                int initialSize = staffSystem.staffList.size();

                Staff staffToDelete = staffSystem.staffList.stream()
                                .filter(s -> s.getId().equals("ST002"))
                                .findFirst()
                                .orElse(null);
                assertNotNull(staffToDelete, "Staff with ID ST002 should exist before deletion");
                assertEquals("NaiYen", staffToDelete.getName());

                staffSystem.deleteStaff();

                assertEquals(initialSize - 1, staffSystem.staffList.size(),
                                "Staff list should decrease by 1 after deletion");

                boolean found = staffSystem.staffList.stream()
                                .anyMatch(s -> s.getId().equals("ST002"));
                assertFalse(found, "Staff with ID ST002 should not exist after deletion");

                String output = outputStream.toString();
                assertTrue(output.contains("deleted successfully"),
                                "Should display success message");
        }

        /**
         * Delete staff with blank ID input first, then valid ID with confirmation
         */
        @Test
        @DisplayName("Delete Staff - Blank ID then Valid ID")
        public void testDeleteStaffBlankId() {
                simulateInput("\nST003\nyes\n");

                int initialSize = staffSystem.staffList.size();
                staffSystem.deleteStaff();

                assertEquals(initialSize - 1, staffSystem.staffList.size(),
                                "Staff list should decrease after deletion");

                String output = outputStream.toString();
                assertTrue(output.contains("cannot be blank") || output.contains("blank"),
                                "Should display error for blank ID");
                assertTrue(output.contains("deleted successfully"),
                                "Should display success message after valid deletion");
        }

        /**
         * Delete staff with non-existent ID
         */
        @Test
        @DisplayName("Delete Staff - Non-Existent ID")
        public void testDeleteStaffNotExist() {
                simulateInput("ST999\nST001\nno\n");

                int initialSize = staffSystem.staffList.size();
                staffSystem.deleteStaff();

                assertEquals(initialSize, staffSystem.staffList.size(),
                                "Staff list should remain unchanged when cancelled");

                String output = outputStream.toString();
                assertTrue(output.contains("not found"),
                                "Should display not found error");
        }

        /**
         * Delete staff --> cancel
         */
        @Test
        @DisplayName("Delete Staff - Cancel Deletion")
        public void testDeleteStaffCancelled() {
                simulateInput("ST001\nno\n");

                int initialSize = staffSystem.staffList.size();
                staffSystem.deleteStaff();

                assertEquals(initialSize, staffSystem.staffList.size(),
                                "Staff list should remain unchanged when cancelled");

                String output = outputStream.toString();
                assertTrue(output.contains("cancelled") || output.contains("Action cancelled"),
                                "Should display cancelled message");
        }

        // ------------------- SEARCH FUNCTION TEST CASES ------------------- //

        /**
         * Search staff by ID
         */
        @Test
        @DisplayName("Search Staff - By ID")
        public void testSearchStaffById() {
                simulateInput("ST001\n");

                staffSystem.searchStaff();

                String output = outputStream.toString();
                assertTrue(output.contains("Search Results") || output.contains("STAFF DETAILS"),
                                "Should display search results header");
                assertTrue(output.contains("JiaHui"),
                                "Should find staff with ID ST001");
                assertTrue(output.contains("ST001"),
                                "Should display staff ID");
                assertTrue(output.contains("Manager"),
                                "Should display staff position");
                assertTrue(output.contains("HR"),
                                "Should display staff department");
        }

        /**
         * Search staff by Name
         */
        @Test
        @DisplayName("Search Staff - By Name")
        public void testSearchStaffByName() {
                simulateInput("NaiYen\n");

                staffSystem.searchStaff();

                String output = outputStream.toString();
                assertTrue(output.contains("NaiYen"),
                                "Should find staff by exact name");
                assertTrue(output.contains("ST002"),
                                "Should display correct ID");
                assertTrue(output.contains("Engineer"),
                                "Should display staff position");
                assertTrue(output.contains("IT"),
                                "Should display staff department");
        }

        /**
         * Search staff by a part of name
         */
        @Test
        @DisplayName("Search Staff - By Partial Name")
        public void testSearchStaffPartialName() {
                simulateInput("Nai\n");

                staffSystem.searchStaff();

                String output = outputStream.toString();
                assertTrue(output.contains("NaiYen"),
                                "Should find staff by partial name");
                assertTrue(output.contains("ST002"),
                                "Should display correct ID for partial match");
        }

        /**
         * Search staff with lowercase name but same structure
         */
        @Test
        @DisplayName("Search Staff - Lowercase Name")
        public void testSearchStaffLowercaseName() {
                simulateInput("naiyen\n");

                staffSystem.searchStaff();

                String output = outputStream.toString();
                assertTrue(output.contains("NaiYen"),
                                "Should find staff regardless of case");
                assertTrue(output.contains("ST002"),
                                "Should display correct ID");
        }

        // ------------------- REPORT FUNCTION TEST CASES ------------------- //

        /**
         * Report for non-existent department (specific department)
         */
        @Test
        @DisplayName("Report - Non-Existent Department")
        public void testReportNonExistentDepartment() {
                // 2 is specific report for department

                simulateInput("2\nClubbing\nIT\n");

                staffSystem.departmentSalaryReport();

                String output = outputStream.toString();
                assertTrue(output.contains("does not exist"),
                                "Should display error for non-existent department");
        }

        /**
         * Report for existing department
         */
        @Test
        @DisplayName("Report - Existing Department")
        public void testReportExistingDepartment() {
                // 2 is specific report for department
                simulateInput("2\nIT\n");

                staffSystem.departmentSalaryReport();

                String output = outputStream.toString();
                assertTrue(output.contains("DEPARTMENT STAFF REPORT") || output.contains("IT"),
                                "Should display department report header");
                assertTrue(output.contains("NaiYen"),
                                "Should display staff in IT department");
                assertTrue(output.contains("Total Salary"),
                                "Should display total salary");
        }

        /**
         * Overall report (all staff)
         */
        @Test
        @DisplayName("Report - Overall Staff Report")
        public void testOverallReport() {
                simulateInput("1\n");

                staffSystem.departmentSalaryReport();

                String output = outputStream.toString();
                assertTrue(output.contains("OVERALL STAFF REPORT"),
                                "Should display overall report header");
                assertTrue(output.contains("Total Staff"),
                                "Should display total staff count");
                assertTrue(output.contains("Total Salary"),
                                "Should display total salary");
        }

        /**
         * Back to menu from report
         */
        @Test
        @DisplayName("Report - Back to Menu")
        public void testReportBackToMenu() {
                simulateInput("0\n");

                staffSystem.departmentSalaryReport();

                String output = outputStream.toString();
                assertTrue(output.contains("SELECT REPORT TYPE"),
                                "Should display report menu");
        }

        /**
         * Add new staff in department and verify report includes them
         */
        @Test
        @DisplayName("Report - New Staff Included in Department Report")
        public void testReportWithNewStaff() {
                // Add staff first
                simulateInput("ST010\nJohn\nmale\nTester\n5500\nIT\n2\nIT\n");
                staffSystem.addStaff();

                outputStream.reset();

                staffSystem.departmentSalaryReport();

                String output = outputStream.toString();
                assertTrue(output.contains("DEPARTMENT STAFF REPORT") || output.contains("IT"),
                                "Should display department report header");
                assertTrue(output.contains("John"),
                                "Should include newly added staff in report");
                assertTrue(output.contains("NaiYen"),
                                "Should still include existing IT staff");
                assertTrue(output.contains("Total Salary"),
                                "Should display total salary");
        }

        // ------------------- DISPLAY FUNCTION TEST CASES ------------------- //

        /**
         * Display all staff
         */
        @Test
        @DisplayName("Display All Staff")
        public void testDisplayAllStaff() {
                simulateInput("");

                staffSystem.displayStaff();

                String output = outputStream.toString();
                assertTrue(output.contains("Staff") || output.contains("STAFF LIST"),
                                "Should display staff details header");
                assertTrue(output.contains("ST001"),
                                "Should display first staff");
                assertTrue(output.contains("ST002"),
                                "Should display second staff");
                assertTrue(output.contains("JiaHui"),
                                "Should display staff name");
                assertTrue(output.contains("NaiYen"),
                                "Should display another staff name");
        }

}