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
     * Add new staff --> All fields correct
     */
    @Test
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
     * Modify staff --> All fields correct
     */
    @Test
    public void testModifyStaff() {
        simulateInput("ST002\nUpdatedName\nMale\nContent Creator\n5500\nMultimedia\n");

        staffSystem.modifyStaff();

        // Find the modified staff
        Staff modifiedStaff = staffSystem.staffList.stream()
                .filter(s -> s.getId().equals("ST002"))
                .findFirst()
                .orElse(null);

        assertNotNull(modifiedStaff, "Staff with ID ST002 should exist");
        assertEquals("UpdatedName", modifiedStaff.getName(), "Name should be updated");
        assertEquals("Male", modifiedStaff.getGender(), "Gender should be updated to Male");
        assertEquals("Content Creator", modifiedStaff.getPosition(), "Position should be updated");
        assertEquals(5500.0, modifiedStaff.getSalary(), 0.001, "Salary should be updated");
        assertEquals("Multimedia", modifiedStaff.getDepartment(), "Department should be updated");

        String output = outputStream.toString();
        assertTrue(output.contains("Staff modified successfully"),
                "Should display success message");
    }

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

    /**
     * Delete staff --> With valid ID and exist
     */
    @Test
    public void testDeleteStaff() {
        simulateInput("ST002\n");

        int initialSize = staffSystem.staffList.size();

        // Verify staff with ID "2" exists before deletion
        Staff staffToDelete = staffSystem.staffList.stream()
                .filter(s -> s.getId().equals("ST002"))
                .findFirst()
                .orElse(null);
        assertNotNull(staffToDelete, "Staff with ID ST002 should exist before deletion");
        assertEquals("NaiYen", staffToDelete.getName());

        staffSystem.deleteStaff();

        // Verify staff list size decreased by 1
        assertEquals(initialSize - 1, staffSystem.staffList.size(),
                "Staff list should decrease by 1 after deletion");

        // Verify staff with ID "2" no longer exists
        boolean found = staffSystem.staffList.stream()
                .anyMatch(s -> s.getId().equals("2"));
        assertFalse(found, "Staff with ID ST002 should not exist after deletion");

        String output = outputStream.toString();
        assertTrue(output.contains("deleted successfully"),
                "Should display success message");
    }

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

    /**
     * Search staff by ID
     */
    @Test
    public void testSearchStaffById() {
        simulateInput("ST001\n");

        staffSystem.searchStaff();

        String output = outputStream.toString();
        assertTrue(output.contains("Search Results"),
                "Should display search results header");
        assertTrue(output.contains("JiaHui"),
                "Should find staff with ID ST001");
        assertTrue(output.contains("ID: ST001") || output.contains("ST001"),
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

}
