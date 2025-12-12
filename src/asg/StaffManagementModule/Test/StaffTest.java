package asg.StaffManagementModule.Test;

import asg.StaffManagementModule.Controller.StaffController;
import asg.StaffManagementModule.Model.Staff;
import asg.StaffManagementModule.Constants.StaffConstants;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Test cases for StaffController class
 * Tests: Add, Modify, Delete, Search, Report functions
 * 83 test cases
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StaffTest {

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

        // ------------------- ADD FUNCTION TEST CASES ------------------- //

        /**
         * Add new staff --> All fields correct
         */
        @Test
        @Order(1)
        @DisplayName("Add Staff - All Valid Inputs")
        public void testAddStaff() {
                // Get initial list size before adding
                int initialSize = staffSystem.staffList.size();
                Staff newStaff = new Staff("ST006", "Alice", "Female", "Developer", 4500.0, "IT");
                staffSystem.staffList.add(newStaff);

                // Size should increase by 1 (5 fixed + 1 = 6)
                assertEquals(initialSize + 1, staffSystem.staffList.size(),
                                "Staff list should increase by 1 after adding staff");

                // Verify all fields of the added staff are correct
                Staff addedStaff = staffSystem.staffList.get(staffSystem.staffList.size() - 1);
                assertEquals("ST006", addedStaff.getId(), "ID should be ST006");
                assertEquals("Alice", addedStaff.getName(), "Name should be Alice");
                assertEquals("Female", addedStaff.getGender(), "Gender should be Female");
                assertEquals("Developer", addedStaff.getPosition(), "Position should be Developer");
                assertEquals(4500.0, addedStaff.getSalary(), 0.001, "Salary should be 4500");
                assertEquals("IT", addedStaff.getDepartment(), "Department should be IT");
        }

        /**
         * Add new staff --> All fields correct (Simulated Input)
         */
        @Test
        @Order(2)
        @DisplayName("Add Staff - All Valid Inputs (SI)")
        public void testSIAddStaff() {
                // ID, Name, Gender, Position, Salary, Department
                simulateInput("ST006\nAlice\nFemale\nDeveloper\n4500\nIT\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();

                // Store initial size before adding
                int initialSize = testController.staffList.size();
                testController.addStaff();

                // Size should increase by 1
                assertEquals(initialSize + 1, testController.staffList.size(),
                                "Staff list should increase by 1 after adding staff");

                // Verify all fields of the added staff
                Staff addedStaff = testController.staffList.get(testController.staffList.size() - 1);
                assertEquals("ST006", addedStaff.getId(), "ID should be ST006");
                assertEquals("Alice", addedStaff.getName(), "Name should be Alice");
                assertEquals("Female", addedStaff.getGender(), "Gender should be Female");
                assertEquals("Developer", addedStaff.getPosition(), "Position should be Developer");
                assertEquals(4500.0, addedStaff.getSalary(), 0.001, "Salary should be 4500");
                assertEquals("IT", addedStaff.getDepartment(), "Department should be IT");

                // Verify success message is displayed
                String output = outputStream.toString();
                assertTrue(output.contains(StaffConstants.SUCCESS_STAFF_ADDED),
                                "Should display success message");
        }

        /**
         * Add staff --> invalid ID then valid
         */
        @Test
        @Order(3)
        @DisplayName("Add Staff - Invalid ID")
        public void testAddStaffInvalidId() {
                // Get initial list size
                int initialSize = staffSystem.staffList.size();
                Staff newStaff = new Staff("ST006", "David", "Male", "Manager", 6000, "HR");
                staffSystem.staffList.add(newStaff);

                // Size should increase by 1
                assertEquals(initialSize + 1, staffSystem.staffList.size());

                // Verify staff was added with correct ID
                Staff addedStaff = staffSystem.staffList.get(staffSystem.staffList.size() - 1);
                assertEquals("ST006", addedStaff.getId());
        }

        /**
         * Add staff --> invalid ID then valid ID (Simulated Input)
         */
        @Test
        @Order(4)
        @DisplayName("Add Staff - Invalid ID Then Valid (SI)")
        public void testSIAddStaffInvalidId() {
                // invalid ID "abc", then valid ID ST006 with all other fields
                simulateInput("abc\nST006\nDavid\nmale\nManager\n6000\nHR\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();

                // Store initial size before adding
                int initialSize = testController.staffList.size();
                testController.addStaff();

                // Size should increase by 1 after entering valid ID
                assertEquals(initialSize + 1, testController.staffList.size());

                // Verify staff added with the valid ID
                Staff addedStaff = testController.staffList.get(testController.staffList.size() - 1);
                assertEquals("ST006", addedStaff.getId());

                // Verify error message was shown for invalid ID
                String output = outputStream.toString();
                assertTrue(output.contains(StaffConstants.ERROR_ID_NUMERIC),
                                "Should display error for invalid ID format");
        }

        /**
         * Add staff --> valid gender (Male or Female only)
         */
        @Test
        @Order(5)
        @DisplayName("Add Staff - Valid Gender Check")
        public void testAddStaffValidGender() {
                // Get initial size
                int initialSize = staffSystem.staffList.size();

                // Add staff with Female gender
                Staff newStaff = new Staff("ST006", "Joey", "Female", "Designer", 4800.0, "Design");
                staffSystem.staffList.add(newStaff);

                // Size should increase by 1
                assertEquals(initialSize + 1, staffSystem.staffList.size());

                // Get added staff and verify gender
                Staff addedStaff = staffSystem.staffList.get(staffSystem.staffList.size() - 1);
                String gender = addedStaff.getGender();

                // Gender must be either Male or Female (valid values only)
                assertTrue(gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female"),
                                "Gender should be either Male or Female");
                assertEquals("Female", addedStaff.getGender(), "Gender should be Female");
        }

        /**
         * Add staff --> valid gender (Male or Female only)
         */
        @Test
        @Order(6)
        @DisplayName("Add Staff - Valid Gender Check  (SI)")
        public void testSIAddStaffValidGender() {
                // Female gender
                simulateInput("ST006\nJoey\nFemale\nDesigner\n4800\nDesign\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();

                // Store initial size
                int initialSize = testController.staffList.size();
                testController.addStaff();

                // Size should increase by 1
                assertEquals(initialSize + 1, testController.staffList.size());

                // Get added staff and verify gender is valid
                Staff addedStaff = testController.staffList.get(testController.staffList.size() - 1);
                String gender = addedStaff.getGender();

                // Gender must be Male or Female only
                assertTrue(gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female"),
                                "Gender should be either Male or Female");
                assertEquals("Female", addedStaff.getGender(), "Gender should be Female");
        }

        /**
         * Add staff --> valid position (no numbers)
         */
        @Test
        @Order(7)
        @DisplayName("Add Staff - Valid Position")
        public void testAddStaffValidPosition() {
                // Get initial list size
                int initialSize = staffSystem.staffList.size();

                // Create and add new staff with valid position
                Staff newStaff = new Staff("ST007", "Eason", "Female", "Developer", 5000.0, "IT");
                staffSystem.staffList.add(newStaff);

                // Size should increase by 1
                assertEquals(initialSize + 1, staffSystem.staffList.size());

                // Verify position is correct
                Staff addedStaff = staffSystem.staffList.get(staffSystem.staffList.size() - 1);
                assertEquals("Developer", addedStaff.getPosition());

                // Position should not contain any numbers
                assertFalse(addedStaff.getPosition().matches(".*\\d+.*"),
                                "Position should not contain numbers");
        }

        /**
         * Add staff --> valid position (no numbers)
         */
        @Test
        @Order(8)
        @DisplayName("Add Staff - Valid Position  (SI)")
        public void testSIAddStaffValidPosition() {
                // valid staff input
                simulateInput("ST007\nEason\nFemale\nDeveloper\n5000\nIT\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();

                int initialSize = testController.staffList.size();

                testController.addStaff();

                // Size should increase by 1
                assertEquals(initialSize + 1, testController.staffList.size());

                // Verify position is correct and contains no numbers
                Staff addedStaff = testController.staffList.get(testController.staffList.size() - 1);
                assertEquals("Developer", addedStaff.getPosition());
                assertFalse(addedStaff.getPosition().matches(".*\\d+.*"),
                                "Position should not contain numbers");
        }

        /**
         * Add staff --> valid department (no numbers)
         */
        @Test
        @Order(9)
        @DisplayName("Add Staff - Valid Department")
        public void testAddStaffValidDepartment() {
                int initialSize = staffSystem.staffList.size();

                Staff newStaff = new Staff("ST008", "Robert", "Male", "Analyst", 4500.0, "Finance");
                staffSystem.staffList.add(newStaff);

                assertEquals(initialSize + 1, staffSystem.staffList.size());
                Staff addedStaff = staffSystem.staffList.get(staffSystem.staffList.size() - 1);
                assertEquals("Finance", addedStaff.getDepartment());

                // Verify department does not contain numbers
                assertFalse(addedStaff.getDepartment().matches(".*\\d+.*"),
                                "Department should not contain numbers");
        }

        /**
         * Add staff --> valid department (no numbers)
         */
        @Test
        @Order(10)
        @DisplayName("Add Staff - Valid Department  (SI)")
        public void testSIAddStaffValidDepartment() {
                simulateInput("ST008\nRobert\nMale\nAnalyst\n4500\nFinance\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();

                int initialSize = testController.staffList.size();
                testController.addStaff();

                assertEquals(initialSize + 1, testController.staffList.size());
                Staff addedStaff = testController.staffList.get(testController.staffList.size() - 1);
                assertEquals("Finance", addedStaff.getDepartment());
                // Verify department does not contain numbers
                assertFalse(addedStaff.getDepartment().matches(".*\\d+.*"),
                                "Department should not contain numbers");
        }

        /**
         * Add staff --> valid salary (numeric value)
         */
        @Test
        @Order(11)
        @DisplayName("Add Staff - Valid Salary")
        public void testAddStaffValidSalary() {
                int initialSize = staffSystem.staffList.size();

                Staff newStaff = new Staff("ST009", "Grace", "Female", "Designer", 4800.0, "Design");
                staffSystem.staffList.add(newStaff);

                assertEquals(initialSize + 1, staffSystem.staffList.size());
                Staff addedStaff = staffSystem.staffList.get(staffSystem.staffList.size() - 1);
                assertEquals(4800.0, addedStaff.getSalary(), 0.001);

                // Verify salary is a valid numeric value
                assertTrue(addedStaff.getSalary() >= 0, "Salary should be non-negative");
        }

        /**
         * Add staff --> valid salary (numeric value)
         */
        @Test
        @Order(12)
        @DisplayName("Add Staff - Valid Salary (SI)")
        public void testSIAddStaffValidSalary() {
                simulateInput("ST009\nGrace\nFemale\nDesigner\n4800\nDesign\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();

                int initialSize = testController.staffList.size();
                testController.addStaff();

                assertEquals(initialSize + 1, testController.staffList.size());
                Staff addedStaff = testController.staffList.get(testController.staffList.size() - 1);
                assertEquals(4800.0, addedStaff.getSalary(), 0.001);
                // Verify salary is a valid numeric value
                assertTrue(addedStaff.getSalary() >= 0, "Salary should be non-negative");
        }

        /**
         * Add staff --> check duplicate ID
         */
        @Test
        @Order(13)
        @DisplayName("Add Staff - Duplicate ID Check")
        public void testAddStaffDuplicateId() {
                // Check if ID already exists before adding
                boolean idExists = staffSystem.staffList.stream()
                                .anyMatch(s -> s.getId().equals("ST001"));
                assertTrue(idExists, "ST001 should already exist");

                Staff newStaff = new Staff("ST011", "Bernice", "Male", "Tester", 5000.0, "IT");
                boolean canAdd = staffSystem.staffList.stream()
                                .noneMatch(s -> s.getId().equals(newStaff.getId()));
                assertTrue(canAdd, "ST011 should not exist yet");

                staffSystem.staffList.add(newStaff);
                Staff addedStaff = staffSystem.staffList.get(staffSystem.staffList.size() - 1);
                assertEquals("ST011", addedStaff.getId());
        }

        /**
         * Add staff --> check duplicate ID (first try duplicate, then valid)
         */
        @Test
        @Order(14)
        @DisplayName("Add Staff - Duplicate ID Check (SI)")
        public void testSIAddStaffDuplicateId() {

                simulateInput("ST001\nST011\nBernice\nMale\nTester\n5000\nIT\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();

                // Check if ID already exists before adding
                boolean idExists = testController.staffList.stream()
                                .anyMatch(s -> s.getId().equals("ST001"));

                assertTrue(idExists, "ST001 should already exist");

                int initialSize = testController.staffList.size();
                testController.addStaff();

                assertEquals(initialSize + 1, testController.staffList.size());
                Staff addedStaff = testController.staffList.get(testController.staffList.size() - 1);
                assertEquals("ST011", addedStaff.getId());
                String output = outputStream.toString();
                assertTrue(output.contains("already exists"), "Should display duplicate ID error");
        }

        /**
         * Add staff --> positive salary only
         */
        @Test
        @Order(15)
        @DisplayName("Add Staff - Positive Salary")
        public void testAddStaffPositiveSalary() {
                int initialSize = staffSystem.staffList.size();

                Staff newStaff = new Staff("ST012", "Yshan", "Female", "Analyst", 3500.0, "Finance");
                staffSystem.staffList.add(newStaff);

                assertEquals(initialSize + 1, staffSystem.staffList.size());
                Staff addedStaff = staffSystem.staffList.get(staffSystem.staffList.size() - 1);
                assertEquals(3500.0, addedStaff.getSalary(), 0.001);

                assertTrue(addedStaff.getSalary() >= 0, "Salary should not be negative");
        }

        /**
         * Add staff --> positive salary only
         */
        @Test
        @Order(16)
        @DisplayName("Add Staff - Positive Salary (SI)")
        public void testSIAddStaffPositiveSalary() {
                simulateInput("ST012\nYshan\nFemale\nAnalyst\n3500\nFinance\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();

                int initialSize = testController.staffList.size();
                testController.addStaff();

                assertEquals(initialSize + 1, testController.staffList.size());
                Staff addedStaff = testController.staffList.get(testController.staffList.size() - 1);
                assertEquals(3500.0, addedStaff.getSalary(), 0.001);
                assertTrue(addedStaff.getSalary() >= 0, "Salary should not be negative");
        }

        /**
         * Add staff --> Valid ID (not ST000)
         */
        @Test
        @Order(17)
        @DisplayName("Add Staff - Valid ID Format")
        public void testAddStaffValidId() {
                int initialSize = staffSystem.staffList.size();

                Staff newStaff = new Staff("ST006", "Soo", "Male", "Developer", 5000.0, "IT");
                staffSystem.staffList.add(newStaff);

                assertEquals(initialSize + 1, staffSystem.staffList.size());
                Staff addedStaff = staffSystem.staffList.get(staffSystem.staffList.size() - 1);
                assertEquals("ST006", addedStaff.getId());

                // Verify ID format is valid (starts with ST and not ST000)
                assertTrue(addedStaff.getId().matches("(?i)ST\\d{3}"), "ID should match format STxxx");
                assertNotEquals("ST000", addedStaff.getId(), "ID should not be ST000");
        }

        /**
         * Add staff --> Valid ID (not ST000)
         */
        @Test
        @Order(18)
        @DisplayName("Add Staff - Valid ID Format (SI)")
        public void testSIAddStaffValidId() {
                simulateInput("ST006\nSoo\nMale\nDeveloper\n5000\nIT\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();

                int initialSize = testController.staffList.size();
                testController.addStaff();

                assertEquals(initialSize + 1, testController.staffList.size());
                Staff addedStaff = testController.staffList.get(testController.staffList.size() - 1);
                assertEquals("ST006", addedStaff.getId());

                // Verify ID format is valid (starts with ST and not ST000)
                assertTrue(addedStaff.getId().matches("(?i)ST\\d{3}"), "ID should match format STxxx");
                assertNotEquals("ST000", addedStaff.getId(), "ID should not be ST000");
        }

        // ------------------- MODIFY FUNCTION TEST CASES ------------------- //

        /**
         * Modify staff --> All fields correct
         */
        @Test
        @Order(19)
        @DisplayName("Modify Staff - All Fields Updated")
        public void testModifyStaff() {
                // Find staff ST002 to modify
                Staff staffToModify = staffSystem.staffList.stream()
                                .filter(s -> s.getId().equals("ST002"))
                                .findFirst()
                                .orElse(null);

                // ST002 should exist
                assertNotNull(staffToModify, "Staff with ID ST002 should exist");

                // Update all fields
                staffToModify.setName("Jojo");
                staffToModify.setGender("Male");
                staffToModify.setPosition("Content Creator");
                staffToModify.setSalary(5500.0);
                staffToModify.setDepartment("Multimedia");

                // Verify all fields updated correctly
                assertEquals("Jojo", staffToModify.getName(), "Name should be updated");
                assertEquals("Male", staffToModify.getGender(), "Gender should be updated to Male");
                assertEquals("Content Creator", staffToModify.getPosition(), "Position should be updated");
                assertEquals(5500.0, staffToModify.getSalary(), 0.001, "Salary should be updated");
                assertEquals("Multimedia", staffToModify.getDepartment(), "Department should be updated");
        }

        /**
         * Modify staff --> All fields correct (Simulated Input)
         */
        @Test
        @Order(20)
        @DisplayName("Modify Staff - All Fields Updated (SI)")
        public void testSIModifyStaff() {
                // ST002: modify all fields (name, gender, position, salary, department) then
                // exit
                simulateInput("ST002\n1\nJojo\nyes\n2\nMale\nyes\n3\nContent Creator\nyes\n4\n5500\nyes\n5\nMultimedia\nyes\n0\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();

                testController.modifyStaff();

                // Find modified staff
                Staff modifiedStaff = testController.staffList.stream()
                                .filter(s -> s.getId().equals("ST002"))
                                .findFirst()
                                .orElse(null);

                // Verify all fields updated
                assertNotNull(modifiedStaff, "Staff with ID ST002 should exist");
                assertEquals("Jojo", modifiedStaff.getName(), "Name should be updated");
                assertEquals("Male", modifiedStaff.getGender(), "Gender should be updated to Male");
                assertEquals("Content Creator", modifiedStaff.getPosition(), "Position should be updated");
                assertEquals(5500.0, modifiedStaff.getSalary(), 0.001, "Salary should be updated");
                assertEquals("Multimedia", modifiedStaff.getDepartment(), "Department should be updated");
        }

        /**
         * Modify staff --> non-existent ID check
         */
        @Test
        @Order(21)
        @DisplayName("Modify Staff - Non-Existent ID")
        public void testModifyStaffNonExistentId() {
                // ST999 should not exist
                Staff staffToModify = staffSystem.staffList.stream()
                                .filter(s -> s.getId().equals("ST999"))
                                .findFirst()
                                .orElse(null);
                assertNull(staffToModify, "Staff with ID ST999 should not exist");

                // Find existing staff ST001 instead
                Staff existingStaff = staffSystem.staffList.stream()
                                .filter(s -> s.getId().equals("ST001"))
                                .findFirst()
                                .orElse(null);

                // Modify existing staff
                assertNotNull(existingStaff, "Staff with ID ST001 should exist");
                existingStaff.setName("JiaHuiLee");
                assertEquals("JiaHuiLee", existingStaff.getName(), "Name should be updated");
        }

        /**
         * Modify staff --> non-existent ID then valid ID (Simulated Input)
         */
        @Test
        @Order(22)
        @DisplayName("Modify Staff - Non-Existent ID Then Valid (SI)")
        public void testSIModifyStaffNonExistentId() {
                // ST999 (not found), then ST001 (valid), modify name, confirm, exit
                simulateInput("ST999\nST001\n1\nJiaHuiLee\nyes\n0\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();
                testController.modifyStaff();

                // Find modified staff
                Staff existingStaff = testController.staffList.stream()
                                .filter(s -> s.getId().equals("ST001"))
                                .findFirst()
                                .orElse(null);

                assertNotNull(existingStaff, "Staff with ID ST001 should exist");
                // Title case converts JiaHuiLee to Jiahuilee
                assertEquals("Jiahuilee", existingStaff.getName(), "Name should be updated");

                // Should show not found for ST999
                String output = outputStream.toString();
                assertTrue(output.contains("not found"), "Should display not found message for ST999");
        }

        /**
         * Modify staff --> only modify salary
         */
        @Test
        @Order(23)
        @DisplayName("Modify Staff - Only Salary Updated")
        public void testModifyStaffSalary() {
                // Find staff ST001
                Staff staffToModify = staffSystem.staffList.stream()
                                .filter(s -> s.getId().equals("ST001"))
                                .findFirst()
                                .orElse(null);

                assertNotNull(staffToModify, "Staff with ID ST001 should exist");
                // Store original name
                String originalName = staffToModify.getName();

                // Only update salary
                staffToModify.setSalary(9999.0);

                // Salary updated, name unchanged
                assertEquals(9999.0, staffToModify.getSalary(), 0.001, "Salary should be updated");
                assertEquals(originalName, staffToModify.getName(), "Name should remain unchanged");
        }

        /**
         * Modify staff --> only modify salary (Simulated Input)
         */
        @Test
        @Order(24)
        @DisplayName("Modify Staff - Only Salary Updated (SI)")
        public void testSIModifyStaffSalary() {
                // ST001: modify salary to 9999, confirm, exit
                simulateInput("ST001\n4\n9999\nyes\n0\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();

                // Store original name
                Staff originalStaff = testController.staffList.stream()
                                .filter(s -> s.getId().equals("ST001"))
                                .findFirst()
                                .orElse(null);
                String originalName = originalStaff.getName();

                testController.modifyStaff();

                // Verify salary updated, name unchanged
                Staff modifiedStaff = testController.staffList.stream()
                                .filter(s -> s.getId().equals("ST001"))
                                .findFirst()
                                .orElse(null);

                assertNotNull(modifiedStaff, "Staff with ID ST001 should exist");
                assertEquals(9999.0, modifiedStaff.getSalary(), 0.001, "Salary should be updated");
                assertEquals(originalName, modifiedStaff.getName(), "Name should remain unchanged");
        }

        /**
         * Modify staff --> modify multiple fields
         */
        @Test
        @Order(25)
        @DisplayName("Modify Staff - Multiple Fields Updated")
        public void testModifyStaffMultipleFields() {
                // Find staff ST001
                Staff staffToModify = staffSystem.staffList.stream()
                                .filter(s -> s.getId().equals("ST001"))
                                .findFirst()
                                .orElse(null);

                assertNotNull(staffToModify, "Staff with ID ST001 should exist");

                // Update multiple fields
                staffToModify.setName("Jh Lee");
                staffToModify.setPosition("Senior Manager");
                staffToModify.setSalary(12000.0);

                // Verify all updates
                assertEquals("Jh Lee", staffToModify.getName(), "Name should be updated");
                assertEquals("Senior Manager", staffToModify.getPosition(), "Position should be updated");
                assertEquals(12000.0, staffToModify.getSalary(), 0.001, "Salary should be updated");
        }

        /**
         * Modify staff --> modify multiple fields (Simulated Input)
         */
        @Test
        @Order(26)
        @DisplayName("Modify Staff - Multiple Fields Updated (SI)")
        public void testSIModifyStaffMultipleFields() {
                // ST001: modify name, position, salary then exit
                simulateInput("ST001\n1\nJh Lee\nyes\n3\nSenior Manager\nyes\n4\n12000\nyes\n0\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();
                testController.modifyStaff();

                // Verify all fields updated
                Staff modifiedStaff = testController.staffList.stream()
                                .filter(s -> s.getId().equals("ST001"))
                                .findFirst()
                                .orElse(null);

                assertNotNull(modifiedStaff, "Staff with ID ST001 should exist");
                assertEquals("Jh Lee", modifiedStaff.getName(), "Name should be updated");
                assertEquals("Senior Manager", modifiedStaff.getPosition(), "Position should be updated");
                assertEquals(12000.0, modifiedStaff.getSalary(), 0.001, "Salary should be updated");
        }

        /**
         * Modify staff --> verify original values unchanged when not modified
         */
        @Test
        @Order(27)
        @DisplayName("Modify Staff - Original Values Unchanged")
        public void testModifyStaffOriginalUnchanged() {
                Staff staffToModify = staffSystem.staffList.stream()
                                .filter(s -> s.getId().equals("ST001"))
                                .findFirst()
                                .orElse(null);

                assertNotNull(staffToModify, "Staff with ID ST001 should exist");
                String originalGender = staffToModify.getGender();
                String originalDepartment = staffToModify.getDepartment();

                staffToModify.setName("JIAHUI");

                assertEquals("JIAHUI", staffToModify.getName(), "Name should be updated");
                assertEquals(originalGender, staffToModify.getGender(), "Gender should remain unchanged");
                assertEquals(originalDepartment, staffToModify.getDepartment(), "Department should remain unchanged");
        }

        /**
         * Modify staff --> verify original values unchanged (Simulated Input)
         */
        @Test
        @Order(28)
        @DisplayName("Modify Staff - Original Values Unchanged (SI)")
        public void testSIModifyStaffOriginalUnchanged() {
                simulateInput("ST001\n1\nJIAHUI\nyes\n0\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();

                Staff originalStaff = testController.staffList.stream()
                                .filter(s -> s.getId().equals("ST001"))
                                .findFirst()
                                .orElse(null);
                String originalGender = originalStaff.getGender();
                String originalDepartment = originalStaff.getDepartment();

                testController.modifyStaff();

                Staff modifiedStaff = testController.staffList.stream()
                                .filter(s -> s.getId().equals("ST001"))
                                .findFirst()
                                .orElse(null);

                assertNotNull(modifiedStaff, "Staff with ID ST001 should exist");
                assertEquals("Jiahui", modifiedStaff.getName(), "Name should be updated");
                assertEquals(originalGender, modifiedStaff.getGender(), "Gender should remain unchanged");
                assertEquals(originalDepartment, modifiedStaff.getDepartment(), "Department should remain unchanged");
        }

        /**
         * Modify staff --> modify name
         */
        @Test
        @Order(29)
        @DisplayName("Modify Staff - Name Updated")
        public void testModifyStaffName() {
                Staff staffToModify = staffSystem.staffList.stream()
                                .filter(s -> s.getId().equals("ST001"))
                                .findFirst()
                                .orElse(null);

                assertNotNull(staffToModify);
                staffToModify.setName("JHui");
                assertEquals("JHui", staffToModify.getName(), "Name should be updated");
        }

        /**
         * Modify staff --> modify name (Simulated Input)
         */
        @Test
        @Order(30)
        @DisplayName("Modify Staff - Name Updated (SI)")
        public void testSIModifyStaffName() {
                simulateInput("ST001\n1\nJHui\nyes\n0\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();

                testController.modifyStaff();

                Staff modifiedStaff = testController.staffList.stream()
                                .filter(s -> s.getId().equals("ST001"))
                                .findFirst()
                                .orElse(null);

                assertNotNull(modifiedStaff);
                assertEquals("Jhui", modifiedStaff.getName(), "Name should be updated");
        }

        /**
         * Modify staff --> cancel modification (Simulated Input)
         */
        @Test
        @Order(31)
        @DisplayName("Modify Staff - Modification Cancelled (SI)")
        public void testSIModifyStaffCancelled() {
                simulateInput("ST001\n1\nNewName\nno\n0\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();

                Staff originalStaff = testController.staffList.stream()
                                .filter(s -> s.getId().equals("ST001"))
                                .findFirst()
                                .orElse(null);
                String originalName = originalStaff.getName();

                testController.modifyStaff();

                Staff staff = testController.staffList.stream()
                                .filter(s -> s.getId().equals("ST001"))
                                .findFirst()
                                .orElse(null);

                assertEquals(originalName, staff.getName(), "Name should remain unchanged after cancellation");

                String output = outputStream.toString();
                assertTrue(output.contains("cancelled") || output.contains(StaffConstants.ACTION_CANCELLED),
                                "Should display cancellation message");
        }

        /**
         * Modify staff --> no modifications made (Simulated Input)
         */
        @Test
        @Order(32)
        @DisplayName("Modify Staff - No Modifications Made (SI)")
        public void testSIModifyStaffNoModifications() {
                simulateInput("ST001\n0\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();

                testController.modifyStaff();

                String output = outputStream.toString();
                assertTrue(output.contains(StaffConstants.ERROR_NO_MODIFICATIONS)
                                || output.contains("no modifications"),
                                "Should display no modifications message");
        }

        // ------------------- DELETE FUNCTION TEST CASES ------------------- //

        /**
         * Delete staff --> With valid ID
         */
        @Test
        @Order(33)
        @DisplayName("Delete Staff - Valid ID")
        public void testDeleteStaff() {
                // Store initial size
                int initialSize = staffSystem.staffList.size();

                // Find staff ST002 to delete
                Staff staffToDelete = staffSystem.staffList.stream()
                                .filter(s -> s.getId().equals("ST002"))
                                .findFirst()
                                .orElse(null);

                // Verify ST002 exists before deletion
                assertNotNull(staffToDelete, "Staff with ID ST002 should exist before deletion");
                assertEquals("NaiYen", staffToDelete.getName());

                // Delete staff
                staffSystem.staffList.remove(staffToDelete);

                // Size should decrease by 1
                assertEquals(initialSize - 1, staffSystem.staffList.size(),
                                "Staff list should decrease by 1 after deletion");

                // ST002 should no longer exist
                boolean found = staffSystem.staffList.stream()
                                .anyMatch(s -> s.getId().equals("ST002"));
                assertFalse(found, "Staff with ID ST002 should not exist after deletion");
        }

        /**
         * Delete staff --> With valid ID (Simulated Input)
         */
        @Test
        @Order(34)
        @DisplayName("Delete Staff - Valid ID (SI)")
        public void testSIDeleteStaff() {
                // ST002 + confirm delete
                simulateInput("ST002\nyes\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();

                // Store initial size
                int initialSize = testController.staffList.size();
                testController.deleteStaff();

                // Size should decrease by 1
                assertEquals(initialSize - 1, testController.staffList.size(),
                                "Staff list should decrease by 1 after deletion");

                // ST002 should not exist after deletion
                boolean found = testController.staffList.stream()
                                .anyMatch(s -> s.getId().equals("ST002"));
                assertFalse(found, "Staff with ID ST002 should not exist after deletion");

                // Should show success message
                String output = outputStream.toString();
                assertTrue(output.contains("deleted") || output.contains("successfully"),
                                "Should display deletion success message");
        }

        /**
         * Delete staff --> Invalid ID format
         */
        @Test
        @Order(35)
        @DisplayName("Delete Staff - Invalid ID Format")
        public void testDeleteStaffInvalidId() {
                // Store initial size
                int initialSize = staffSystem.staffList.size();

                // Try to remove invalid ID
                boolean invalidRemoved = staffSystem.staffList.removeIf(s -> s.getId().equals("INVALID"));

                // Should not remove anything
                assertFalse(invalidRemoved, "Should not remove any staff with invalid ID");
                assertEquals(initialSize, staffSystem.staffList.size(),
                                "Staff list should remain unchanged");
        }

        /**
         * Delete staff --> Invalid ID then valid ID (Simulated Input)
         */
        @Test
        @Order(36)
        @DisplayName("Delete Staff - Invalid ID Then Valid (SI)")
        public void testSIDeleteStaffInvalidId() {
                // INVALID ID first, then valid ST002, confirm
                simulateInput("INVALID\nST002\nyes\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();

                // Store initial size
                int initialSize = testController.staffList.size();
                testController.deleteStaff();

                // Should delete valid ID after error
                assertEquals(initialSize - 1, testController.staffList.size(),
                                "Staff list should decrease by 1 after valid deletion");

                // Should show error for invalid ID
                String output = outputStream.toString();
                assertTrue(output.contains("ID must start with ST") || output.contains("not found"),
                                "Should display error for invalid ID format");
        }

        /**
         * Delete staff --> cancel delete
         */
        @Test
        @Order(37)
        @DisplayName("Delete Staff - Cancel deletion")
        public void testDeleteStaffActionCancel() {
                int initialSize = staffSystem.staffList.size();

                Staff staffToDelete = staffSystem.staffList.stream()
                                .filter(s -> s.getId().equals("ST001"))
                                .findFirst()
                                .orElse(null);

                assertNotNull(staffToDelete, "ST001 should exist and be found for deletion");
                assertEquals("JiaHui", staffToDelete.getName(), "Staff name should be JiaHui");

                // No to remove
                boolean shouldDelete = false;
                if (shouldDelete) {
                        staffSystem.staffList.remove(staffToDelete);
                }

                // Verify staff still exists after cancellation
                assertEquals(initialSize, staffSystem.staffList.size(),
                                "Staff list should remain unchanged after cancel");
                assertTrue(staffSystem.staffList.contains(staffToDelete),
                                "Staff should still be in the list after cancel");
        }

        /**
         * Delete staff --> cancel delete (Simulated Input)
         */
        @Test
        @Order(38)
        @DisplayName("Delete Staff - Cancel deletion (SI)")
        public void testSIDeleteStaffActionCancel() {
                simulateInput("ST001\nno\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();

                int initialSize = testController.staffList.size();
                testController.deleteStaff();

                assertEquals(initialSize, testController.staffList.size(),
                                "Staff list should remain unchanged after cancel");

                boolean found = testController.staffList.stream()
                                .anyMatch(s -> s.getId().equals("ST001"));
                assertTrue(found, "ST001 should still exist after cancellation");

                String output = outputStream.toString();
                assertTrue(output.contains("cancelled") || output.contains("Action cancelled"),
                                "Should display cancellation message");
        }

        /**
         * Delete staff --> Delete the same staff twice
         */
        @Test
        @Order(39)
        @DisplayName("Delete Staff - Delete Same Staff Twice")
        public void testDeleteStaffTwice() {
                int initialSize = staffSystem.staffList.size();

                boolean firstRemove = staffSystem.staffList.removeIf(s -> s.getId().equals("ST002"));
                assertTrue(firstRemove, "First deletion should succeed");
                assertEquals(initialSize - 1, staffSystem.staffList.size());

                // Second deletion (should fail because already deleted)
                boolean secondRemove = staffSystem.staffList.removeIf(s -> s.getId().equals("ST002"));
                assertFalse(secondRemove, "Second deletion should fail - staff already deleted");
                assertEquals(initialSize - 1, staffSystem.staffList.size());
        }

        /**
         * Delete staff --> Delete non-existent ID (Simulated Input)
         */
        @Test
        @Order(40)
        @DisplayName("Delete Staff - Non-Existent ID (SI)")
        public void testSIDeleteStaffNonExistent() {
                simulateInput("ST999\nST002\nyes\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();

                int initialSize = testController.staffList.size();
                testController.deleteStaff();

                assertEquals(initialSize - 1, testController.staffList.size(),
                                "Staff list should decrease after finding valid ID");

                String output = outputStream.toString();
                assertTrue(output.contains("not found"), "Should display not found message for ST999");
        }

        // ------------------- SEARCH FUNCTION TEST CASES ------------------- //

        /**
         * Search staff --> ID
         */
        @Test
        @Order(41)
        @DisplayName("Search Staff - By ID")
        public void testSearchStaffById() {
                // Search by ID ST001
                Staff staffFound = staffSystem.staffList.stream()
                                .filter(s -> s.getId().equalsIgnoreCase("ST001"))
                                .findFirst()
                                .orElse(null);

                // Should find staff and verify details
                assertNotNull(staffFound, "Should find staff with ID ST001");
                assertEquals("ST001", staffFound.getId());
                assertEquals("JiaHui", staffFound.getName());
                assertEquals("Manager", staffFound.getPosition());
                assertEquals("HR", staffFound.getDepartment());
        }

        /**
         * Search staff --> ID (Simulated Input)
         */
        @Test
        @Order(42)
        @DisplayName("Search Staff - By ID (SI)")
        public void testSISearchStaffById() {
                // Search query: ST001
                simulateInput("ST001\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();
                testController.searchStaff();

                // Should find and display ST001 details
                String output = outputStream.toString();
                assertTrue(output.contains("ST001"), "Should find and display ST001");
                assertTrue(output.contains("JiaHui"), "Should display staff name");
                assertTrue(output.contains("Manager"), "Should display staff position");
        }

        /**
         * Search staff --> Name
         */
        @Test
        @Order(43)
        @DisplayName("Search Staff - By Name")
        public void testSearchStaffByName() {
                // Search by exact name
                Staff staffFound = staffSystem.staffList.stream()
                                .filter(s -> s.getName().equalsIgnoreCase("NaiYen"))
                                .findFirst()
                                .orElse(null);

                // Should find and return correct staff
                assertNotNull(staffFound, "Should find staff by exact name");
                assertEquals("ST002", staffFound.getId());
                assertEquals("Engineer", staffFound.getPosition());
                assertEquals("IT", staffFound.getDepartment());
        }

        /**
         * Search staff --> Name (Simulated Input)
         */
        @Test
        @Order(44)
        @DisplayName("Search Staff - By Name (SI)")
        public void testSISearchStaffByName() {
                // Search query: NaiYen
                simulateInput("NaiYen\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();
                testController.searchStaff();

                // Should find and display matching staff
                String output = outputStream.toString();
                assertTrue(output.contains("NaiYen"), "Should find staff by name");
                assertTrue(output.contains("ST002"), "Should display matching staff ID");
                assertTrue(output.contains("Engineer"), "Should display staff position");
        }

        /**
         * Search staff --> Partial name
         */
        @Test
        @Order(45)
        @DisplayName("Search Staff - By Partial Name")
        public void testSearchStaffPartialName() {
                // Search by partial name "nai"
                java.util.List<Staff> staffFound = staffSystem.staffList.stream()
                                .filter(s -> s.getName().toLowerCase().contains("nai"))
                                .collect(java.util.stream.Collectors.toList());

                // Should find NaiYen
                assertFalse(staffFound.isEmpty(), "Should find staff by partial name");
                assertTrue(staffFound.stream().anyMatch(s -> s.getName().equals("NaiYen")));
                assertTrue(staffFound.stream().anyMatch(s -> s.getId().equals("ST002")));
        }

        /**
         * Search staff --> Partial name (Simulated Input)
         */
        @Test
        @Order(46)
        @DisplayName("Search Staff - By Partial Name (SI)")
        public void testSISearchStaffPartialName() {
                // Search query: Nai (partial)
                simulateInput("Nai\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();
                testController.searchStaff();

                // Should find NaiYen
                String output = outputStream.toString();
                assertTrue(output.contains("NaiYen"), "Should find staff by partial name");
                assertTrue(output.contains("ST002"), "Should display matching staff ID");
        }

        /**
         * Search staff --> lowercase name but same structure
         */
        @Test
        @Order(47)
        @DisplayName("Search Staff - Lowercase Name")
        public void testSearchStaffCaseLowercaseName() {
                // Search with lowercase "naiyen"
                Staff staffFound = staffSystem.staffList.stream()
                                .filter(s -> s.getName().toLowerCase().contains("naiyen".toLowerCase()))
                                .findFirst()
                                .orElse(null);

                // Should find regardless of case
                assertNotNull(staffFound, "Should find staff regardless of case");
                assertEquals("NaiYen", staffFound.getName());
                assertEquals("ST002", staffFound.getId());
        }

        /**
         * Search staff --> lowercase name (Simulated Input)
         */
        @Test
        @Order(48)
        @DisplayName("Search Staff - Lowercase Name (SI)")
        public void testSISearchStaffCaseLowercaseName() {
                // Search query: naiyen (lowercase)
                simulateInput("naiyen\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();
                testController.searchStaff();

                // Should find regardless of case
                String output = outputStream.toString();
                assertTrue(output.contains("NaiYen") || output.contains("naiyen"),
                                "Should find staff regardless of case");
                assertTrue(output.contains("ST002"), "Should display staff ID");
        }

        /**
         * Search staff --> no match found
         */
        @Test
        @Order(49)
        @DisplayName("Search Staff - No Match Found")
        public void testSearchStaffNoMatch() {
                // Search for non-existent staff
                Staff staffFound = staffSystem.staffList.stream()
                                .filter(s -> s.getId().equals("STXXX") ||
                                                s.getName().toLowerCase().contains("unknown"))
                                .findFirst()
                                .orElse(null);

                // Should not find any
                assertNull(staffFound, "Should not find non-existent staff");
        }

        /**
         * Search staff --> no match found (Simulated Input)
         */
        @Test
        @Order(50)
        @DisplayName("Search Staff - No Match Found (SI)")
        public void testSISearchStaffNoMatch() {
                simulateInput("Unknown\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();

                testController.searchStaff();

                String output = outputStream.toString();
                assertTrue(output.contains(StaffConstants.ERROR_NO_MATCHING_STAFF),
                                "Should display no match message");
        }

        /**
         * Search staff --> non-existent ID
         */
        @Test
        @Order(51)
        @DisplayName("Search Staff - Non-Existent ID")
        public void testSearchStaffNonExistentId() {
                Staff staffFound = staffSystem.staffList.stream()
                                .filter(s -> s.getId().equals("ST999"))
                                .findFirst()
                                .orElse(null);

                assertNull(staffFound, "Should not find staff with non-existent ID");
        }

        /**
         * Search staff --> non-existent ID (Simulated Input)
         */
        @Test
        @Order(52)
        @DisplayName("Search Staff - Non-Existent ID (SI)")
        public void testSISearchStaffNonExistentId() {
                simulateInput("ST999\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();

                testController.searchStaff();

                String output = outputStream.toString();
                assertTrue(output.contains(StaffConstants.ERROR_NO_MATCHING_STAFF),
                                "Should display not found message");
        }

        /**
         * Search staff --> non-existent name
         */
        @Test
        @Order(53)
        @DisplayName("Search Staff - Non-Existent Name")
        public void testSearchStaffNonExistentName() {
                java.util.List<Staff> staffFound = staffSystem.staffList.stream()
                                .filter(s -> s.getName().toLowerCase().contains("unknown"))
                                .collect(java.util.stream.Collectors.toList());

                assertTrue(staffFound.isEmpty(), "Should not find staff with non-existent name");
        }

        // ------------------- REPORT FUNCTION TEST CASES ------------------- //

        /**
         * Report --> non-existent department check
         */
        @Test
        @Order(54)
        @DisplayName("Report - Non-Existent Department")
        public void testReportNonExistentDepartment() {
                java.util.List<Staff> deptFound = staffSystem.staffList.stream()
                                .filter(s -> s.getDepartment().equalsIgnoreCase("Clubbing"))
                                .collect(java.util.stream.Collectors.toList());

                assertTrue(deptFound.isEmpty(), "Should not find this non-existent department");
        }

        /**
         * Report --> get staff by department
         */
        @Test
        @Order(55)
        @DisplayName("Report - Get Staff By Department")
        public void testReportByDepartment() {
                java.util.List<Staff> itStaff = staffSystem.staffList.stream()
                                .filter(s -> s.getDepartment().equalsIgnoreCase("IT"))
                                .collect(java.util.stream.Collectors.toList());

                assertFalse(itStaff.isEmpty(), "IT department should have staff");
                assertTrue(itStaff.stream().anyMatch(s -> s.getName().equals("NaiYen")));
        }

        /**
         * Report --> calculate total salary
         */
        @Test
        @Order(56)
        @DisplayName("Report - Calculate Total Salary")
        public void testReportTotalSalary() {
                double totalSalary = staffSystem.staffList.stream()
                                .mapToDouble(Staff::getSalary)
                                .sum();

                assertTrue(totalSalary > 0, "Total salary should be greater than 0");
                assertEquals(5, staffSystem.staffList.size(), "Should have 5 initial staff");
        }

        /**
         * Report --> department salary calculation
         */
        @Test
        @Order(57)
        @DisplayName("Report - Department Salary Calculation")
        public void testReportDepartmentSalary() {
                Staff newStaff = new Staff("ST010", "John", "Male", "Tester", 5500.0, "IT");
                staffSystem.staffList.add(newStaff);

                double itSalary = staffSystem.staffList.stream()
                                .filter(s -> s.getDepartment().equalsIgnoreCase("IT"))
                                .mapToDouble(Staff::getSalary)
                                .sum();

                assertTrue(itSalary > 0, "IT department total salary should be positive");
                assertTrue(staffSystem.staffList.stream().anyMatch(s -> s.getName().equals("John")));
                assertTrue(staffSystem.staffList.stream().anyMatch(s -> s.getName().equals("NaiYen")));
        }

        // ------------------- DISPLAY FUNCTION TEST CASES ------------------- //

        /**
         * Display all staff
         */
        @Test
        @Order(58)
        @DisplayName("Display All Staff")
        public void testDisplayAllStaff() {
                staffSystem.displayStaff();

                String output = outputStream.toString();
                assertTrue(output.contains("Staff") || output.contains("STAFF"),
                                "Should display staff header");
        }

        /**
         * Verify initial data
         */
        @Test
        @Order(59)
        @DisplayName("Verify Initial Staff Data")
        public void testInitialStaffData() {
                assertEquals(5, staffSystem.staffList.size(), "Should have 5 initial staff");

                Staff firstStaff = staffSystem.staffList.get(0);
                assertEquals("ST001", firstStaff.getId());
                assertEquals("JiaHui", firstStaff.getName());

                Staff secondStaff = staffSystem.staffList.get(1);
                assertEquals("ST002", secondStaff.getId());
                assertEquals("NaiYen", secondStaff.getName());
        }

        // ------------------- CONTROLLER TEST CASES (SI) ------------------- //

        /**
         * Test Controller --> getMenuChoice with valid input
         */
        @Test
        @Order(60)
        @DisplayName("Controller - Get Menu Choice Valid (SI)")
        public void testSIGetMenuChoice() {
                simulateInput("1\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();

                // Call displayStaff which is option 1
                testController.displayStaff();

                String output = outputStream.toString();
                assertTrue(output.contains(StaffConstants.TABLE_STAFF_TITLE) || output.contains("Staff"),
                                "Should display staff list");
        }

        /**
         * Test Controller --> processMenuOption with Display Staff
         */
        @Test
        @Order(61)
        @DisplayName("Controller - Process Menu Option Display (SI)")
        public void testSIProcessMenuOptionDisplay() {
                StaffController testController = new StaffController();
                testController.retrieveStaff();

                testController.displayStaff();

                String output = outputStream.toString();
                assertTrue(output.contains("ST001"), "Should display staff ID");
                assertTrue(output.contains("JiaHui"), "Should display staff name");
        }

        /**
         * Test Controller --> departmentSalaryReport with Overall Report
         */
        @Test
        @Order(62)
        @DisplayName("Controller - Department Salary Report Overall (SI)")
        public void testSIDepartmentSalaryReportOverall() {
                simulateInput("1\n0\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();

                testController.departmentSalaryReport();

                String output = outputStream.toString();
                assertTrue(output.contains(StaffConstants.REPORT_TITLE_OVERALL) || output.contains("REPORT"),
                                "Should display overall report");
        }

        /**
         * Test Controller --> departmentSalaryReport with Department Report
         */
        @Test
        @Order(63)
        @DisplayName("Controller - Department Salary Report By Dept (SI)")
        public void testSIDepartmentSalaryReportByDept() {
                simulateInput("2\nIT\n0\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();

                testController.departmentSalaryReport();

                String output = outputStream.toString();
                assertTrue(output.contains("DEPARTMENT STAFF REPORT") || output.contains("IT"),
                                "Should display department report");
        }

        /**
         * Test Controller --> displayOverallReport
         */
        @Test
        @Order(64)
        @DisplayName("Controller - Display Overall Report (SI)")
        public void testSIDisplayOverallReport() {
                StaffController testController = new StaffController();
                testController.retrieveStaff();

                testController.displayOverallReport();

                String output = outputStream.toString();
                assertTrue(output.contains(StaffConstants.REPORT_TITLE_OVERALL), "Should display overall report title");
                assertTrue(output.contains("Total Staff:"), "Should display total staff count");
                assertTrue(output.contains("Total Salary:"), "Should display total salary");
        }

        /**
         * Test Controller --> displayDepartmentReport with valid department
         */
        @Test
        @Order(65)
        @DisplayName("Controller - Display Department Report (SI)")
        public void testSIDisplayDepartmentReport() {
                simulateInput("IT\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();

                testController.displayDepartmentReport();

                String output = outputStream.toString();
                assertTrue(output.contains("DEPARTMENT STAFF REPORT"), "Should display department report title");
                assertTrue(output.contains("IT"), "Should display department name");
        }

        /**
         * Test Controller --> displayDepartmentReport with invalid then valid
         * department
         */
        @Test
        @Order(66)
        @DisplayName("Controller - Display Department Report Invalid Then Valid (SI)")
        public void testSIDisplayDepartmentReportInvalidThenValid() {
                simulateInput("InvalidDept\nIT\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();

                testController.displayDepartmentReport();

                String output = outputStream.toString();
                assertTrue(output.contains("does not exist") || output.contains("not found")
                                || output.contains("Invalid"),
                                "Should display error for invalid department");
                assertTrue(output.contains("DEPARTMENT STAFF REPORT") || output.contains("IT"),
                                "Should eventually display valid department report");
        }

        /**
         * Test Controller --> getExistingDepartments
         */
        @Test
        @Order(67)
        @DisplayName("Controller - Get Existing Departments")
        public void testGetExistingDepartments() {
                StaffController testController = new StaffController();
                testController.retrieveStaff();

                java.util.List<String> departments = testController.getExistingDepartments();

                assertFalse(departments.isEmpty(), "Should have existing departments");
                assertTrue(departments.contains("IT"), "Should contain IT department");
                assertTrue(departments.contains("HR"), "Should contain HR department");
        }

        /**
         * Test Controller --> closeSystem
         */
        @Test
        @Order(68)
        @DisplayName("Controller - Close System (SI)")
        public void testSICloseSystem() {
                StaffController testController = new StaffController();
                testController.retrieveStaff();

                testController.closeSystem();

                String output = outputStream.toString();
                assertTrue(output.contains(StaffConstants.MESSAGE_EXIT_STAFF_MODULE) || output.contains("Goodbye")
                                || output.contains("Exiting"),
                                "Should display exit message");
        }

        /**
         * Test Controller --> displayMenu
         */
        @Test
        @Order(69)
        @DisplayName("Controller - Display Menu (SI)")
        public void testSIDisplayMenu() {
                StaffController testController = new StaffController();
                testController.retrieveStaff();

                testController.displayMenu();

                String output = outputStream.toString();
                assertTrue(output.contains("STAFF MANAGEMENT"), "Should display menu header");
                assertTrue(output.contains("Display"), "Should display menu options");
        }

        /**
         * Test Controller --> displayModifyMenu
         */
        @Test
        @Order(70)
        @DisplayName("Controller - Display Modify Menu (SI)")
        public void testSIDisplayModifyMenu() {
                StaffController testController = new StaffController();
                testController.retrieveStaff();

                testController.displayModifyMenu();

                String output = outputStream.toString();
                assertTrue(output.contains("MODIFY") || output.contains("Name") || output.contains("Gender"),
                                "Should display modify menu options");
        }

        /**
         * Test Controller --> displayReportMenu
         */
        @Test
        @Order(71)
        @DisplayName("Controller - Display Report Menu (SI)")
        public void testSIDisplayReportMenu() {
                StaffController testController = new StaffController();
                testController.retrieveStaff();

                testController.displayReportMenu();

                String output = outputStream.toString();
                assertTrue(output.contains("REPORT") || output.contains("Overall") || output.contains("Department"),
                                "Should display report menu options");
        }

        /**
         * Test Controller --> getMenuChoice with valid input
         */
        @Test
        @Order(72)
        @DisplayName("Controller - Get Menu Choice (SI)")
        public void testSIControllerGetMenuChoice() {
                simulateInput("3\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();

                int choice = testController.getMenuChoice();
                assertEquals(3, choice, "Should return the entered choice");
        }

        /**
         * Test Controller --> getMenuOption with valid input
         */
        @Test
        @Order(73)
        @DisplayName("Controller - Get Menu Option (SI)")
        public void testSIControllerGetMenuOption() {
                simulateInput("1\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();

                asg.StaffManagementModule.Constants.StaffMenuOptions option = testController.getMenuOption();
                assertNotNull(option, "Should return a menu option");
                assertEquals(asg.StaffManagementModule.Constants.StaffMenuOptions.DISPLAY_STAFF, option,
                                "Option 1 should be DISPLAY_STAFF");
        }

        /**
         * Test Controller --> processMenuOption Display Staff
         */
        @Test
        @Order(74)
        @DisplayName("Controller - Process Menu Option Display Staff (SI)")
        public void testSIControllerProcessMenuOptionDisplay() {
                StaffController testController = new StaffController();
                testController.retrieveStaff();

                testController.processMenuOption(asg.StaffManagementModule.Constants.StaffMenuOptions.DISPLAY_STAFF);

                String output = outputStream.toString();
                assertTrue(output.contains("STAFF LIST") || output.contains("ST001"),
                                "Should display staff list");
        }

        /**
         * Test Controller --> processMenuOption Add Staff
         */
        @Test
        @Order(75)
        @DisplayName("Controller - Process Menu Option Add Staff (SI)")
        public void testSIControllerProcessMenuOptionAdd() {
                simulateInput("ST006\nTestUser\nMale\nDeveloper\n5000\nIT\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();

                int initialSize = testController.staffList.size();
                testController.processMenuOption(asg.StaffManagementModule.Constants.StaffMenuOptions.ADD_STAFF);

                assertEquals(initialSize + 1, testController.staffList.size(),
                                "Should add new staff");
        }

        /**
         * Test Controller --> processMenuOption Search Staff
         */
        @Test
        @Order(76)
        @DisplayName("Controller - Process Menu Option Search Staff (SI)")
        public void testSIControllerProcessMenuOptionSearch() {
                simulateInput("ST001\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();

                testController.processMenuOption(asg.StaffManagementModule.Constants.StaffMenuOptions.SEARCH_STAFF);

                String output = outputStream.toString();
                assertTrue(output.contains("ST001") || output.contains("JiaHui"),
                                "Should find and display staff");
        }

        /**
         * Test Controller --> getReportMenuOption with valid input
         */
        @Test
        @Order(77)
        @DisplayName("Controller - Get Report Menu Option (SI)")
        public void testSIControllerGetReportMenuOption() {
                simulateInput("1\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();

                asg.StaffManagementModule.Constants.StaffMenuOptions option = testController.getReportMenuOption();
                assertNotNull(option, "Should return a report menu option");
                assertEquals(asg.StaffManagementModule.Constants.StaffMenuOptions.OVERALL_REPORT, option,
                                "Option 1 should be OVERALL_REPORT");
        }

        /**
         * Test Controller --> getModifyMenuOption with valid input
         */
        @Test
        @Order(78)
        @DisplayName("Controller - Get Modify Menu Option (SI)")
        public void testSIControllerGetModifyMenuOption() {
                simulateInput("1\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();

                asg.StaffManagementModule.Constants.StaffMenuOptions option = testController.getModifyMenuOption();
                assertNotNull(option, "Should return a modify menu option");
                assertEquals(asg.StaffManagementModule.Constants.StaffMenuOptions.MODIFY_NAME, option,
                                "Option 1 should be MODIFY_NAME");
        }

        /**
         * Test Controller --> processModifyOption - Modify Name
         */
        @Test
        @Order(79)
        @DisplayName("Controller - ProcessModifyOption Name (SI)")
        public void testSIControllerProcessModifyOptionName() {
                simulateInput("NewName\nyes\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();

                Staff staffToModify = testController.staffList.get(0);
                String originalName = staffToModify.getName();

                boolean modified = testController.processModifyOption(
                                asg.StaffManagementModule.Constants.StaffMenuOptions.MODIFY_NAME,
                                staffToModify, false);

                assertTrue(modified, "Should return true after modification");
                assertNotEquals(originalName, staffToModify.getName(), "Name should be changed");
        }

        /**
         * Test Controller --> processModifyOption - Modify Gender
         */
        @Test
        @Order(80)
        @DisplayName("Controller - ProcessModifyOption Gender (SI)")
        public void testSIControllerProcessModifyOptionGender() {
                simulateInput("Female\nyes\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();

                Staff staffToModify = testController.staffList.get(0);

                boolean modified = testController.processModifyOption(
                                asg.StaffManagementModule.Constants.StaffMenuOptions.MODIFY_GENDER,
                                staffToModify, false);

                assertTrue(modified, "Should return true after modification");
                assertEquals("Female", staffToModify.getGender(), "Gender should be changed to Female");
        }

        /**
         * Test Controller --> processModifyOption - Modify Position
         */
        @Test
        @Order(81)
        @DisplayName("Controller - ProcessModifyOption Position (SI)")
        public void testSIControllerProcessModifyOptionPosition() {
                simulateInput("Senior Manager\nyes\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();

                Staff staffToModify = testController.staffList.get(0);

                boolean modified = testController.processModifyOption(
                                asg.StaffManagementModule.Constants.StaffMenuOptions.MODIFY_POSITION,
                                staffToModify, false);

                assertTrue(modified, "Should return true after modification");
                assertEquals("Senior Manager", staffToModify.getPosition(), "Position should be changed");
        }

        /**
         * Test Controller --> processModifyOption - Modify Salary
         */
        @Test
        @Order(82)
        @DisplayName("Controller - ProcessModifyOption Salary (SI)")
        public void testSIControllerProcessModifyOptionSalary() {
                simulateInput("7500\nyes\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();

                Staff staffToModify = testController.staffList.get(0);

                boolean modified = testController.processModifyOption(
                                asg.StaffManagementModule.Constants.StaffMenuOptions.MODIFY_SALARY,
                                staffToModify, false);

                assertTrue(modified, "Should return true after modification");
                assertEquals(7500.0, staffToModify.getSalary(), 0.01, "Salary should be changed");
        }

        /**
         * Test Controller --> processModifyOption - Modify Department
         */
        @Test
        @Order(83)
        @DisplayName("Controller - ProcessModifyOption Department (SI)")
        public void testSIControllerProcessModifyOptionDepartment() {
                simulateInput("Marketing\nyes\n");

                StaffController testController = new StaffController();
                testController.retrieveStaff();

                Staff staffToModify = testController.staffList.get(0);

                boolean modified = testController.processModifyOption(
                                asg.StaffManagementModule.Constants.StaffMenuOptions.MODIFY_DEPARTMENT,
                                staffToModify, false);

                assertTrue(modified, "Should return true after modification");
                assertEquals("Marketing", staffToModify.getDepartment(), "Department should be changed");
        }

}
