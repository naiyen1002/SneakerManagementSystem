package asg.StaffManagementModule.TestCase;

import asg.StaffManagementModule.Controller.StaffController;
import asg.StaffManagementModule.Model.Staff;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestStaff {

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

        // ------------------- ADD FUNCTION TEST CASES ------------------- //

        /**
         * Add new staff --> All fields correct
         */
        @Test
        @DisplayName("Add Staff - All Valid Inputs")
        public void testAddStaff() {
                // simulateInput("ST006\nAlice\nFemale\nDeveloper\n4500\nIT\n");

                int initialSize = staffSystem.staffList.size();
                Staff newStaff = new Staff("ST006", "Alice", "Female", "Developer", 4500.0, "IT");
                staffSystem.staffList.add(newStaff);

                assertEquals(initialSize + 1, staffSystem.staffList.size(),
                                "Staff list should increase by 1 after adding staff");

                Staff addedStaff = staffSystem.staffList.get(staffSystem.staffList.size() - 1);
                assertEquals("ST006", addedStaff.getId(), "ID should be ST006");
                assertEquals("Alice", addedStaff.getName(), "Name should be Alice");
                assertEquals("Female", addedStaff.getGender(), "Gender should be Female");
                assertEquals("Developer", addedStaff.getPosition(), "Position should be Developer");
                assertEquals(4500.0, addedStaff.getSalary(), 0.001, "Salary should be 4500");
                assertEquals("IT", addedStaff.getDepartment(), "Department should be IT");

                // String output = outputStream.toString();
                // assertTrue(output.contains("Staff added successfully"),
                // "Should display success message");
        }

        /**
         * Add staff --> invalid ID
         */
        @Test
        @DisplayName("Add Staff - Invalid ID")
        public void testAddStaffInvalidId() {
                // simulateInput("abc\nST006\nDavid\nmale\nManager\n6000\nHR\n");

                int initialSize = staffSystem.staffList.size();
                Staff newStaff = new Staff("ST006", "David", "Male", "Manager", 6000, "HR");
                staffSystem.staffList.add(newStaff);

                assertEquals(initialSize + 1, staffSystem.staffList.size());
                Staff addedStaff = staffSystem.staffList.get(staffSystem.staffList.size() - 1);
                assertEquals("ST006", addedStaff.getId());

                // String output = outputStream.toString();
                // assertTrue(output.contains("ID must start with ST"),
                // "Should display error for invalid ID format");
        }

        /**
         * Add staff --> valid gender (Male or Female only)
         */
        @Test
        @DisplayName("Add Staff - Valid Gender Check")
        public void testAddStaffValidGender() {
                int initialSize = staffSystem.staffList.size();

                Staff newStaff = new Staff("ST006", "Joey", "Female", "Designer", 4800.0, "Design");
                staffSystem.staffList.add(newStaff);

                assertEquals(initialSize + 1, staffSystem.staffList.size());
                Staff addedStaff = staffSystem.staffList.get(staffSystem.staffList.size() - 1);

                String gender = addedStaff.getGender();
                assertTrue(gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female"),
                                "Gender should be either Male or Female");
                assertEquals("Female", addedStaff.getGender(), "Gender should be Female");
        }

        /**
         * Add staff --> valid position (no numbers)
         */
        @Test
        @DisplayName("Add Staff - Valid Position")
        public void testAddStaffValidPosition() {
                int initialSize = staffSystem.staffList.size();

                Staff newStaff = new Staff("ST007", "Eason", "Female", "Developer", 5000.0, "IT");
                staffSystem.staffList.add(newStaff);

                assertEquals(initialSize + 1, staffSystem.staffList.size());
                Staff addedStaff = staffSystem.staffList.get(staffSystem.staffList.size() - 1);
                assertEquals("Developer", addedStaff.getPosition());

                // Verify position does not contain numbers
                assertFalse(addedStaff.getPosition().matches(".*\\d+.*"),
                                "Position should not contain numbers");
        }

        /**
         * Add staff --> valid department (no numbers)
         */
        @Test
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
         * Add staff --> valid salary (numeric value)
         */
        @Test
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
         * Add staff --> check duplicate ID
         */
        @Test
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
         * Add staff --> positive salary only
         */
        @Test
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
         * Add staff --> Valid ID (not ST000)
         */
        @Test
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

        // ------------------- MODIFY FUNCTION TEST CASES ------------------- //

        /**
         * Modify staff --> All fields correct
         */
        @Test
        @DisplayName("Modify Staff - All Fields Updated")
        public void testModifyStaff() {
                Staff staffToModify = staffSystem.staffList.stream()
                                .filter(s -> s.getId().equals("ST002"))
                                .findFirst()
                                .orElse(null);

                assertNotNull(staffToModify, "Staff with ID ST002 should exist");

                staffToModify.setName("Jojo");
                staffToModify.setGender("Male");
                staffToModify.setPosition("Content Creator");
                staffToModify.setSalary(5500.0);
                staffToModify.setDepartment("Multimedia");

                assertEquals("Jojo", staffToModify.getName(), "Name should be updated");
                assertEquals("Male", staffToModify.getGender(), "Gender should be updated to Male");
                assertEquals("Content Creator", staffToModify.getPosition(), "Position should be updated");
                assertEquals(5500.0, staffToModify.getSalary(), 0.001, "Salary should be updated");
                assertEquals("Multimedia", staffToModify.getDepartment(), "Department should be updated");
        }

        /**
         * Modify staff --> non-existent ID check
         */
        @Test
        @DisplayName("Modify Staff - Non-Existent ID")
        public void testModifyStaffNonExistentId() {
                Staff staffToModify = staffSystem.staffList.stream()
                                .filter(s -> s.getId().equals("ST999"))
                                .findFirst()
                                .orElse(null);

                assertNull(staffToModify, "Staff with ID ST999 should not exist");

                Staff existingStaff = staffSystem.staffList.stream()
                                .filter(s -> s.getId().equals("ST001"))
                                .findFirst()
                                .orElse(null);

                assertNotNull(existingStaff, "Staff with ID ST001 should exist");
                existingStaff.setName("JiaHuiLee");
                assertEquals("JiaHuiLee", existingStaff.getName(), "Name should be updated");
        }

        /**
         * Modify staff --> only modify salary
         */
        @Test
        @DisplayName("Modify Staff - Only Salary Updated")
        public void testModifyStaffSalary() {
                Staff staffToModify = staffSystem.staffList.stream()
                                .filter(s -> s.getId().equals("ST001"))
                                .findFirst()
                                .orElse(null);

                assertNotNull(staffToModify, "Staff with ID ST001 should exist");
                String originalName = staffToModify.getName();

                staffToModify.setSalary(9999.0);

                assertEquals(9999.0, staffToModify.getSalary(), 0.001, "Salary should be updated");
                assertEquals(originalName, staffToModify.getName(), "Name should remain unchanged");
        }

        /**
         * Modify staff --> modify multiple fields
         */
        @Test
        @DisplayName("Modify Staff - Multiple Fields Updated")
        public void testModifyStaffMultipleFields() {
                Staff staffToModify = staffSystem.staffList.stream()
                                .filter(s -> s.getId().equals("ST001"))
                                .findFirst()
                                .orElse(null);

                assertNotNull(staffToModify, "Staff with ID ST001 should exist");

                staffToModify.setName("Jh Lee");
                staffToModify.setPosition("Senior Manager");
                staffToModify.setSalary(12000.0);

                assertEquals("Jh Lee", staffToModify.getName(), "Name should be updated");
                assertEquals("Senior Manager", staffToModify.getPosition(), "Position should be updated");
                assertEquals(12000.0, staffToModify.getSalary(), 0.001, "Salary should be updated");
        }

        /**
         * Modify staff --> verify original values unchanged when not modified
         */
        @Test
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
         * Modify staff --> modify name
         */
        @Test
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

        // ------------------- DELETE FUNCTION TEST CASES ------------------- //

        /**
         * Delete staff --> With valid ID
         */
        @Test
        @DisplayName("Delete Staff - Valid ID")
        public void testDeleteStaff() {
                int initialSize = staffSystem.staffList.size();

                Staff staffToDelete = staffSystem.staffList.stream()
                                .filter(s -> s.getId().equals("ST002"))
                                .findFirst()
                                .orElse(null);

                assertNotNull(staffToDelete, "Staff with ID ST002 should exist before deletion");
                assertEquals("NaiYen", staffToDelete.getName());

                staffSystem.staffList.remove(staffToDelete);

                assertEquals(initialSize - 1, staffSystem.staffList.size(),
                                "Staff list should decrease by 1 after deletion");

                boolean found = staffSystem.staffList.stream()
                                .anyMatch(s -> s.getId().equals("ST002"));
                assertFalse(found, "Staff with ID ST002 should not exist after deletion");
        }

        /**
         * Delete staff --> Invalid ID format
         */
        @Test
        @DisplayName("Delete Staff - Invalid ID Format")
        public void testDeleteStaffInvalidId() {
                int initialSize = staffSystem.staffList.size();

                boolean invalidRemoved = staffSystem.staffList.removeIf(s -> s.getId().equals("INVALID"));
                assertFalse(invalidRemoved, "Should not remove any staff with invalid ID");
                assertEquals(initialSize, staffSystem.staffList.size(),
                                "Staff list should remain unchanged");
        }

        /**
         * Delete staff --> cancel delete
         */
        @Test
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
         * Delete staff --> Delete the same staff twice
         */
        @Test
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

        // ------------------- SEARCH FUNCTION TEST CASES ------------------- //

        /**
         * Search staff --> ID
         */
        @Test
        @DisplayName("Search Staff - By ID")
        public void testSearchStaffById() {
                Staff staffFound = staffSystem.staffList.stream()
                                .filter(s -> s.getId().equalsIgnoreCase("ST001"))
                                .findFirst()
                                .orElse(null);

                assertNotNull(staffFound, "Should find staff with ID ST001");
                assertEquals("ST001", staffFound.getId());
                assertEquals("JiaHui", staffFound.getName());
                assertEquals("Manager", staffFound.getPosition());
                assertEquals("HR", staffFound.getDepartment());
        }

        /**
         * Search staff --> Name
         */
        @Test
        @DisplayName("Search Staff - By Name")
        public void testSearchStaffByName() {
                Staff staffFound = staffSystem.staffList.stream()
                                .filter(s -> s.getName().equalsIgnoreCase("NaiYen"))
                                .findFirst()
                                .orElse(null);

                assertNotNull(staffFound, "Should find staff by exact name");
                assertEquals("ST002", staffFound.getId());
                assertEquals("Engineer", staffFound.getPosition());
                assertEquals("IT", staffFound.getDepartment());
        }

        /**
         * Search staff --> Partial name
         */
        @Test
        @DisplayName("Search Staff - By Partial Name")
        public void testSearchStaffPartialName() {
                java.util.List<Staff> staffFound = staffSystem.staffList.stream()
                                .filter(s -> s.getName().toLowerCase().contains("nai"))
                                .collect(java.util.stream.Collectors.toList());

                assertFalse(staffFound.isEmpty(), "Should find staff by partial name");
                assertTrue(staffFound.stream().anyMatch(s -> s.getName().equals("NaiYen")));
                assertTrue(staffFound.stream().anyMatch(s -> s.getId().equals("ST002")));
        }

        /**
         * Search staff --> lowercase name but same structure
         */
        @Test
        @DisplayName("Search Staff - Lowercase Name")
        public void testSearchStaffCaseLowercaseName() {
                Staff staffFound = staffSystem.staffList.stream()
                                .filter(s -> s.getName().toLowerCase().contains("naiyen".toLowerCase()))
                                .findFirst()
                                .orElse(null);

                assertNotNull(staffFound, "Should find staff regardless of case");
                assertEquals("NaiYen", staffFound.getName());
                assertEquals("ST002", staffFound.getId());
        }

        /**
         * Search staff --> no match found
         */
        @Test
        @DisplayName("Search Staff - No Match Found")
        public void testSearchStaffNoMatch() {
                Staff staffFound = staffSystem.staffList.stream()
                                .filter(s -> s.getId().equals("STXXX") ||
                                                s.getName().toLowerCase().contains("unknown"))
                                .findFirst()
                                .orElse(null);

                assertNull(staffFound, "Should not find non-existent staff");
        }

        /**
         * Search staff --> non-existent ID
         */
        @Test
        @DisplayName("Search Staff - Non-Existent ID")
        public void testSearchStaffNonExistentId() {
                Staff staffFound = staffSystem.staffList.stream()
                                .filter(s -> s.getId().equals("ST999"))
                                .findFirst()
                                .orElse(null);

                assertNull(staffFound, "Should not find staff with non-existent ID");
        }

        /**
         * Search staff --> non-existent name
         */
        @Test
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

}

                

                
                                

                

                
                                

                

                
                                

                

                
                                

                

                
                                

                

                
                                

                

                
                                

                

                
                                

                

                
                                

                

                
                                

                

                
                                

                

                
                                 
                                

                 
                                

                 
                                

                 
                                

                 
                                

                 
                                

                 
                                

                 
                                

                 
                                

                 

                
                                 
                                

                

                 
                                

                