package asg.TestCases.TestStaffMgmt;

import asg.StaffManagementModule.Controller.StaffManagementSystem;
import asg.StaffManagementModule.Model.Staff;

import java.io.PrintStream;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestStaff {

    private StaffManagementSystem staffSystem;
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        staffSystem = new StaffManagementSystem();
        staffSystem.retrieveStaff(); // Load initial staff data
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
        staffSystem = null;
    }

    /**
     * Add new staff --> all fields correct
     */
    @Test
    public void testAddStaff() {
        // The new added will be with ID 6 since current contains 5 staffs
        Staff newStaff = new Staff("6", "Alice", "female", "Developer", 4500.0, "IT");
        staffSystem.staffList.add(newStaff);

        assertEquals(6, staffSystem.staffList.size(), "Staff list should have 6 member after adding this staff");
        assertEquals("Alice", staffSystem.staffList.get(5).getName(),
                "The name of the newly added staff should be Alice (Last staff name)");
    }

    /**
     * Delete staff
     */
    @Test
    public void testDeleteStaff() {
        // Delete staff with ID "2"
        staffSystem.staffList.removeIf(staff -> staff.getId().equals("2"));

        // Verify staff with ID "2" is gone
        boolean found = staffSystem.staffList.stream()
                .anyMatch(staff -> staff.getId().equals("2"));

        assertFalse(found, "Staff with ID 2 should not exist anymore after deletion.");
    }

    /**
     * Delete non-exist staff
     */
    @Test
    public void testDeleteNotExistStaff() {
        int initialSize = staffSystem.staffList.size();

        // Try to delete staff with non-existent ID "999"
        staffSystem.staffList.removeIf(staff -> staff.getId().equals("10"));

        assertEquals(initialSize, staffSystem.staffList.size(), "Staff list size should remain the same");
    }

    /**
     * Modify non-exist staff information
     */
    @Test
    public void testModifyNotExistStaff() {
        Staff staff = staffSystem.staffList.stream()
                .filter(s -> s.getId().equals("10"))
                .findFirst()
                .orElse(null);

        assertNull(staff, "Staff with ID 10 should not exist");
    }

    /**
     * Search staff by ID
     */
    @Test
    public void testSearchStaffById() {
        Staff staff = staffSystem.staffList.stream()
                .filter(s -> s.getId().equals("1"))
                .findFirst()
                .orElse(null);

        assertNotNull(staff, "Staff with ID 1 should exist");
        assertEquals("JiaHui", staff.getName());
    }

    /**
     * Search staff by Name
     */
    @Test
    public void testSearchStaff_ByName() {
        Staff staff = staffSystem.staffList.stream()
                .filter(s -> s.getName().contains("NaiYen"))
                .findFirst()
                .orElse(null);

        assertNotNull(staff, "Staff with name NaiYen should exist");
        assertEquals("2", staff.getId());
    }

    // Ideas
    // Add new staff --> invalid input
    // Modify staff
    // Modify staff --> invalid input
    // Delete staff --> with name
    // Modify staf --> change department
    // Report --> total up salary

}
