package asg.TestCases.TestStaffMgmt;

import asg.StaffManagementModule.Controller.StaffManagementSystem;
import asg.StaffManagementModule.Model.Staff;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestStaff {

    private StaffManagementSystem staffSystem;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        // staffSystem = new StaffManagementSystem();
        // staffSystem.retrieveStaff(); // Load initial staff data
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
        staffSystem = new StaffManagementSystem();
        staffSystem.retrieveStaff();
    }

    /**
     * Add new staff --> all fields correct
     */
    @Test
    public void testAddStaff() {
        simulateInput("6\nAlice\nfemale\nDeveloper\n4500\nIT\n");

        int initialSize = staffSystem.staffList.size();
        staffSystem.addStaff();

        assertEquals(initialSize + 1, staffSystem.staffList.size(),
                "Staff list should increase by 1 after adding staff");

        Staff addedStaff = staffSystem.staffList.get(staffSystem.staffList.size() - 1);
        assertEquals("6", addedStaff.getId(), "ID should be 6");
        assertEquals("Alice", addedStaff.getName(), "Name should be Alice");
        assertEquals("female", addedStaff.getGender(), "Gender should be female");
        assertEquals("Developer", addedStaff.getPosition(), "Position should be Developer");
        assertEquals(4500.0, addedStaff.getSalary(), 0.001, "Salary should be 4500");
        assertEquals("IT", addedStaff.getDepartment(), "Department should be IT");

        String output = outputStream.toString();
        assertTrue(output.contains("Staff added successfully"),
                "Should display success message");
    }

    // /**
    // * Delete staff
    // */
    // @Test
    // public void testDeleteStaff() {
    // // Delete staff with ID "2"
    // staffSystem.staffList.removeIf(staff -> staff.getId().equals("2"));

    // // Verify staff with ID "2" is gone
    // boolean found = staffSystem.staffList.stream()
    // .anyMatch(staff -> staff.getId().equals("2"));

    // assertFalse(found, "Staff with ID 2 should not exist anymore after
    // deletion.");
    // }

    // /**
    // * Delete non-exist staff
    // */
    // @Test
    // public void testDeleteNotExistStaff() {
    // int initialSize = staffSystem.staffList.size();

    // // Try to delete staff with non-existent ID "10"
    // staffSystem.staffList.removeIf(staff -> staff.getId().equals("10"));

    // assertEquals(initialSize, staffSystem.staffList.size(), "Staff list size
    // should remain the same");
    // }

    // /**
    // * Modify non-exist staff information
    // */
    // @Test
    // public void testModifyNotExistStaff() {
    // Staff staff = staffSystem.staffList.stream()
    // .filter(s -> s.getId().equals("10"))
    // .findFirst()
    // .orElse(null);

    // assertNull(staff, "Staff with ID 10 should not exist");
    // }

    // /**
    // * Search staff by ID
    // */
    // @Test
    // public void testSearchStaffById() {
    // Staff staff = staffSystem.staffList.stream()
    // .filter(s -> s.getId().equals("1"))
    // .findFirst()
    // .orElse(null);

    // assertNotNull(staff, "Staff with ID 1 should exist");
    // assertEquals("JiaHui", staff.getName());
    // }

    // /**
    // * Search staff by Name
    // */
    // @Test
    // public void testSearchStaff_ByName() {
    // Staff staff = staffSystem.staffList.stream()
    // .filter(s -> s.getName().contains("NaiYen"))
    // .findFirst()
    // .orElse(null);

    // assertNotNull(staff, "Staff with name NaiYen should exist");
    // assertEquals("2", staff.getId());
    // }

    // Ideas
    // Add new staff --> invalid input
    // Modify staff
    // Modify staff --> invalid input
    // Delete staff --> with name
    // Modify staf --> change department
    // Report --> total up salary

}
