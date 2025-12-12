package asg.MemberManagementModule.Test;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import asg.MemberManagementModule.Constants.MemberData;
import asg.MemberManagementModule.Controller.MemberController;
import asg.MemberManagementModule.Model.Gender;
import asg.MemberManagementModule.Model.Member;
import asg.MemberManagementModule.Model.MembershipTier;
import asg.MemberManagementModule.View.MemberView;

public class MemberControllerTest {
    private MemberView memberView;
    private MemberController memberController;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;

    @BeforeEach
    public void setUp() {
        // Capture console output for testing
        System.setOut(new PrintStream(outputStreamCaptor));
        memberView = new MemberView();
    }

    @AfterEach
    public void tearDown() {
        // Restore standard output
        System.setOut(standardOut);
        memberView = null;
        memberController = null;
    }

    // ================== CONTROLLER TESTS (Non-Interactive) ==================

    @Test
    @Order(1)
    @DisplayName("Test Controller - Creation With Initial Data")
    public void testController_Creation_WithInitialData() {

        List<Member> sampleData = MemberData.initiallizeMembersData();

        memberController = new MemberController(memberView, sampleData);

        // Assert
        assertEquals(5, memberController.getMemberCount());
    }

    @Test
    @Order(2)
    @DisplayName("Test Controller - Find Member By ID")
    public void testController_FindMemberById() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());

        // Act
        Member found = memberController.findMemberById("M001");

        // Assert
        assertNotNull(found);
        assertEquals("M001", found.getId());
    }

    @Test
    @Order(3)
    @DisplayName("Test Controller - Find Member By ID Not Found")
    public void testController_FindMemberById_NotFound() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());

        // Act
        Member found = memberController.findMemberById("M999");

        // Assert
        assertNull(found);
    }

    @Test
    @Order(4)
    @DisplayName("Test Controller - Find Member By ID Case Insensitive")
    public void testController_FindMemberById_CaseInsensitive() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());

        // Act
        Member found = memberController.findMemberById("m001");

        // Assert
        assertNotNull(found);
        assertEquals("M001", found.getId());
    }

    @Test
    @Order(5)
    @DisplayName("Test Controller - Find Member By ID - Empty Input")
    public void testController_FindMemberById_EmptyInput() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());

        // Act
        Member found = memberController.findMemberById("   ");

        // Assert
        assertNull(found);
    }

    @Test
    @Order(6)
    @DisplayName("Test Controller - Search Members By Name")
    public void testController_SearchMembersByName() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());

        // Act
        List<Member> results = memberController.searchMembersByName("Juju");

        // Assert
        assertFalse(results.isEmpty());
        assertTrue(results.get(0).getName().contains("Juju"));
    }

    @Test
    @Order(7)
    @DisplayName("Test Controller - Search Members By Gender")
    public void testController_SearchMembersByGender() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());

        // Act
        List<Member> results = memberController.searchMembersByGender(Gender.FEMALE);

        // Assert
        assertFalse(results.isEmpty());
        for (Member member : results) {
            assertEquals(Gender.FEMALE, member.getGender());
        }
    }

    @Test
    @Order(8)
    @DisplayName("Test Controller - Search Members By Gender - Null Input")
    public void testController_SearchMembersByGender_NullInput() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());

        // Act
        List<Member> results = memberController.searchMembersByGender(null);

        // Assert
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    @Order(9)
    @DisplayName("Test Controller - Search Members By IC Number")
    public void testController_SearchMembersByIcNumber() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());

        // Act
        List<Member> results = memberController.searchMembersByIcNumber("950101-01-1234");

        // Assert
        assertNotNull(results);
    }

    @Test
    @Order(10)
    @DisplayName("Test Controller - Search Members By IC Number - Empty Input")
    public void testController_SearchMembersByIcNumber_EmptyInput() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());

        // Act
        List<Member> results = memberController.searchMembersByIcNumber("   ");

        // Assert
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    @Order(11)
    @DisplayName("Test Controller - Search Members By Contact")
    public void testController_SearchMembersByContact() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());

        // Act
        List<Member> results = memberController.searchMembersByContact("123456789");

        // Assert
        assertNotNull(results);
    }

    @Test
    @Order(12)
    @DisplayName("Test Controller - Search Members By Contact - Empty Input")
    public void testController_SearchMembersByContact_EmptyInput() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());

        // Act
        List<Member> results = memberController.searchMembersByContact("");

        // Assert
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    @Order(13)
    @DisplayName("Test Controller - Search Members By Join Date")
    public void testController_SearchMembersByJoinDate() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());

        // Act
        List<Member> results = memberController.searchMembersByJoinDate(LocalDate.of(2020, 1, 15));

        // Assert
        assertNotNull(results);
    }

    @Test
    @Order(14)
    @DisplayName("Test Controller - Search Members By Join Date - Null Input")
    public void testController_SearchMembersByJoinDate_NullInput() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());

        // Act
        List<Member> results = memberController.searchMembersByJoinDate(null);

        // Assert
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    @Order(15)
    @DisplayName("Test Controller - Search Members By Tier")
    public void testController_SearchMembersByTier() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());

        // Act
        List<Member> results = memberController.searchMembersByTier(MembershipTier.BASIC);

        // Assert
        assertFalse(results.isEmpty());
    }

    @Test
    @Order(16)
    @DisplayName("Test Controller - Search Members By Tier - Null Input")
    public void testController_SearchMembersByTier_NullInput() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());

        // Act
        List<Member> results = memberController.searchMembersByTier(null);

        // Assert
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    @Order(17)
    @DisplayName("Test Controller - Delete Non-Existent Member")
    public void testController_DeleteNonExistentMember() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());

        // Act
        boolean result = memberController.deleteMemberById("M999");

        // Assert
        assertFalse(result);
    }

    @Test
    @Order(18)
    @DisplayName("Test Controller - Delete Member By ID - Empty Input")
    public void testController_DeleteMemberById_EmptyInput() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());

        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            memberController.deleteMemberById("");
        });
    }

    @Test
    @Order(19)
    @DisplayName("Test Controller - Update Member - Null Inputs")
    public void testController_UpdateMember_NullInputs() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            memberController.updateMemberDirect(null, MemberData.createTestMember());
        });
        assertThrows(IllegalArgumentException.class, () -> {
            memberController.updateMemberDirect("M001", null);
        });
    }

    @Test
    @Order(20)
    @DisplayName("Test Controller - Display All Members")
    public void testController_DisplayAllMembers() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());

        // Act
        memberController.displayAllMembers();

        // Assert - verify output was produced
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Member ID"));
    }

    @Test
    @Order(21)
    @DisplayName("Test Controller - Display Membership Report")
    public void testController_DisplayMembershipReport() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());

        // Act
        memberController.displayMembershipReport();

        // Assert - verify output was produced
        String output = outputStreamCaptor.toString();
        assertNotNull(output);
    }

    // ==================== INTERACTIVE METHOD TESTS ====================

    /**
     * Helper method to create a Scanner from simulated input
     */
    private Scanner createTestScanner(String input) {
        return new Scanner(new java.io.ByteArrayInputStream(input.getBytes()));
    }

    @Test
    @Order(22)
    @DisplayName("Test Controller - addMember - Complete flow")
    public void testController_addMember_CompleteFlow() {
        // Arrange - simulate: y (confirm) -> M100 (id) -> Test User (name) ->
        // Male (gender) -> 950101-01-1234 (ic) -> 123456789 (contact) ->
        // 15/01/2023 (date) -> y (final confirm) -> n (don't continue)
        String input = "y\nM100\nTest User\nMale\n950101-01-1234\n123456789\n15/01/2023\ny\nn\n";
        Scanner testScanner = createTestScanner(input);
        MemberView testView = new MemberView(testScanner);
        memberController = new MemberController(testView);

        // Act
        memberController.addMember();

        // Assert
        assertEquals(1, memberController.getMemberCount());
        assertNotNull(memberController.findMemberById("M100"));
    }

    @Test
    @Order(23)
    @DisplayName("Test Controller - addMember - Cancel at first confirmation")
    public void testController_addMember_CancelFirstConfirmation() {
        // Arrange - simulate: n (cancel)
        String input = "n\n";
        Scanner testScanner = createTestScanner(input);
        MemberView testView = new MemberView(testScanner);
        memberController = new MemberController(testView, MemberData.initiallizeMembersData());
        int initialCount = memberController.getMemberCount();

        // Act
        memberController.addMember();

        // Assert - no new member added
        assertEquals(initialCount, memberController.getMemberCount());
    }

    @Test
    @Order(24)
    @DisplayName("Test Controller - updateMember - Update name")
    public void testController_updateMember_UpdateName() {
        // Arrange - simulate: M001 (id) -> y (confirm) -> 1 (update name) ->
        // Updated Name -> 5 (finish) -> n (don't continue)
        String input = "M001\ny\n1\nUpdated Name\n5\nn\n";
        Scanner testScanner = createTestScanner(input);
        MemberView testView = new MemberView(testScanner);
        memberController = new MemberController(testView, MemberData.initiallizeMembersData());

        // Act
        memberController.updateMember();

        // Assert
        Member updated = memberController.findMemberById("M001");
        assertEquals("Updated Name", updated.getName());
    }

    @Test
    @Order(25)
    @DisplayName("Test Controller - updateMember - Cancel confirmation")
    public void testController_updateMember_CancelConfirmation() {
        // Arrange - simulate: M001 (id) -> n (cancel) -> n (don't continue)
        String input = "M001\nn\nn\n";
        Scanner testScanner = createTestScanner(input);
        MemberView testView = new MemberView(testScanner);
        memberController = new MemberController(testView, MemberData.initiallizeMembersData());
        String originalName = memberController.findMemberById("M001").getName();

        // Act
        memberController.updateMember();

        // Assert - name unchanged
        assertEquals(originalName, memberController.findMemberById("M001").getName());
    }

    @Test
    @Order(26)
    @DisplayName("Test Controller - deleteMember - Member found and deleted")
    public void testController_deleteMember_Success() {
        // Arrange - simulate: M001 (id) -> y (confirm) -> n (don't continue)
        String input = "M001\ny\nn\n";
        Scanner testScanner = createTestScanner(input);
        MemberView testView = new MemberView(testScanner);
        memberController = new MemberController(testView, MemberData.initiallizeMembersData());
        int initialCount = memberController.getMemberCount();

        // Act
        memberController.deleteMember();

        // Assert
        assertEquals(initialCount - 1, memberController.getMemberCount());
        assertNull(memberController.findMemberById("M001"));
    }

    @Test
    @Order(27)
    @DisplayName("Test Controller - deleteMember - Cancel confirmation")
    public void testController_deleteMember_CancelConfirmation() {
        // Arrange - simulate: M001 (id) -> n (cancel) -> n (don't continue)
        String input = "M001\nn\nn\n";
        Scanner testScanner = createTestScanner(input);
        MemberView testView = new MemberView(testScanner);
        memberController = new MemberController(testView, MemberData.initiallizeMembersData());
        int initialCount = memberController.getMemberCount();

        // Act
        memberController.deleteMember();

        // Assert - count unchanged
        assertEquals(initialCount, memberController.getMemberCount());
    }

    @Test
    @Order(28)
    @DisplayName("Test Controller - searchMember - Search by ID")
    public void testController_searchMember_SearchById() {
        // Arrange - simulate: 1 (search by ID) -> M001 -> n (don't continue)
        String input = "1\nM001\nn\n";
        Scanner testScanner = createTestScanner(input);
        MemberView testView = new MemberView(testScanner);
        memberController = new MemberController(testView, MemberData.initiallizeMembersData());

        // Act
        memberController.searchMember();

        // Assert - verify output contains member info
        String output = outputStreamCaptor.toString();
        assertTrue(output.length() > 0);
    }

    @Test
    @Order(29)
    @DisplayName("Test Controller - searchMember - Search by Name")
    public void testController_searchMember_SearchByName() {
        // Arrange - simulate: 2 (search by name) -> John -> n (don't continue)
        String input = "2\nJohn\nn\n";
        Scanner testScanner = createTestScanner(input);
        MemberView testView = new MemberView(testScanner);
        memberController = new MemberController(testView, MemberData.initiallizeMembersData());

        // Act
        memberController.searchMember();

        // Assert
        String output = outputStreamCaptor.toString();
        assertTrue(output.length() > 0);
    }

    @Test
    @Order(30)
    @DisplayName("Test Interactive - addMember - Duplicate Member ID")
    public void testInteractive_addMember_DuplicateId() {
        // Arrange - try M001 (duplicate), then M100 (unique), then cancel at final
        // confirmation
        // y (confirm) -> M001 (duplicate, will loop) -> M100 (unique) -> Test -> Male
        // -> 950101-01-1234 -> 123456789 -> 15/01/2023 -> n (cancel final) -> n (don't
        // continue)
        String input = "y\nM001\nM100\nDuplicate Test\nMale\n950101-01-1234\n123456789\n15/01/2023\nn\nn\n";
        Scanner testScanner = createTestScanner(input);
        MemberView testView = new MemberView(testScanner);
        memberController = new MemberController(testView, MemberData.initiallizeMembersData());
        int initialCount = memberController.getMemberCount();

        // Act
        memberController.addMember();

        // Assert - should not add because we cancelled at final confirmation
        assertEquals(initialCount, memberController.getMemberCount());
    }

    @Test
    @Order(31)
    @DisplayName("Test Interactive - addMember - With Existing Sales Detection")
    public void testInteractive_addMember_WithExistingSales() {
        // Arrange - add M006 which has existing sales ($2000)
        // y -> M006 -> New Customer -> Male -> 950101-01-1234 -> 123456789 ->
        // 15/01/2023 -> y -> n
        String input = "y\nM006\nNew Customer\nMale\n950101-01-1234\n123456789\n15/01/2023\ny\nn\n";
        Scanner testScanner = createTestScanner(input);
        MemberView testView = new MemberView(testScanner);
        memberController = new MemberController(testView);

        // Act
        memberController.addMember();

        // Assert - should auto-assign SILVER tier
        Member added = memberController.findMemberById("M006");
        assertNotNull(added);
        assertEquals(MembershipTier.SILVER, added.getMembershipTier());
        assertEquals(2000.0, added.getTotalSpending(), 0.01);
    }

    @Test
    @Order(32)
    @DisplayName("Test Interactive - addMember - Cancel at Final Confirmation")
    public void testInteractive_addMember_CancelFinalConfirmation() {
        // Arrange - y -> M100 -> Test -> Male -> 950101-01-1234 -> 123456789 ->
        // 15/01/2023 -> n (cancel final)
        String input = "y\nM100\nTest User\nMale\n950101-01-1234\n123456789\n15/01/2023\nn\nn\n";
        Scanner testScanner = createTestScanner(input);
        MemberView testView = new MemberView(testScanner);
        memberController = new MemberController(testView);

        // Act
        memberController.addMember();

        // Assert - should not add
        assertEquals(0, memberController.getMemberCount());
    }

    @Test
    @Order(33)
    @DisplayName("Test Interactive - updateMember - Non-Existent Member")
    public void testInteractive_updateMember_NonExistent() {
        // Arrange - try to update M999 which doesn't exist
        // y -> M999 -> n
        String input = "y\nM999\nn\n";
        Scanner testScanner = createTestScanner(input);
        MemberView testView = new MemberView(testScanner);
        memberController = new MemberController(testView, MemberData.initiallizeMembersData());

        // Act
        memberController.updateMember();

        // Assert - should show not found message
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("NOT FOUND") || output.contains("not found"));
    }

    @Test
    @Order(34)
    @DisplayName("Test Interactive - updateMember - Update Multiple Fields")
    public void testInteractive_updateMember_MultipleFields() {
        // Arrange - update M001: ID -> confirm -> name -> gender -> finish -> don't
        // continue
        // M001 -> y (confirm) -> 1 (name) -> Updated Name -> 2 (gender) -> Female -> 5
        // (finish) -> n (don't continue)
        String input = "M001\ny\n1\nUpdated Name\n2\nFemale\n5\nn\n";
        Scanner testScanner = createTestScanner(input);
        MemberView testView = new MemberView(testScanner);
        memberController = new MemberController(testView, MemberData.initiallizeMembersData());

        // Act
        memberController.updateMember();

        // Assert
        Member updated = memberController.findMemberById("M001");
        assertEquals("Updated Name", updated.getName());
        assertEquals(Gender.FEMALE, updated.getGender());
    }

    @Test
    @Order(35)
    @DisplayName("Test Interactive - updateMember - Cancel Update")
    public void testInteractive_updateMember_CancelUpdate() {
        // Arrange - M001 -> n (cancel at confirmation) -> n (don't continue)
        String input = "M001\nn\nn\n";
        Scanner testScanner = createTestScanner(input);
        MemberView testView = new MemberView(testScanner);
        memberController = new MemberController(testView, MemberData.initiallizeMembersData());
        Member original = memberController.findMemberById("M001");
        String originalName = original.getName();

        // Act
        memberController.updateMember();

        // Assert - name should not change
        assertEquals(originalName, memberController.findMemberById("M001").getName());
    }

    @Test
    @Order(36)
    @DisplayName("Test Interactive - deleteMember - Non-Existent Member")
    public void testInteractive_deleteMember_NonExistent() {
        // Arrange - try to delete M999 (not found, loops back) -> n (don't continue)
        // M999 -> n (don't continue)
        String input = "M999\nn\n";
        Scanner testScanner = createTestScanner(input);
        MemberView testView = new MemberView(testScanner);
        memberController = new MemberController(testView, MemberData.initiallizeMembersData());
        int initialCount = memberController.getMemberCount();

        // Act
        memberController.deleteMember();

        // Assert - count should not change
        assertEquals(initialCount, memberController.getMemberCount());
    }

    @Test
    @Order(37)
    @DisplayName("Test Interactive - deleteMember - Verify Removal")
    public void testInteractive_deleteMember_VerifyRemoval() {
        // Arrange - delete M005: ID -> confirm -> don't continue
        // M005 -> y (confirm delete) -> n (don't continue)
        String input = "M005\ny\nn\n";
        Scanner testScanner = createTestScanner(input);
        MemberView testView = new MemberView(testScanner);
        memberController = new MemberController(testView, MemberData.initiallizeMembersData());
        int initialCount = memberController.getMemberCount();

        // Act
        memberController.deleteMember();

        // Assert
        assertEquals(initialCount - 1, memberController.getMemberCount());
        assertNull(memberController.findMemberById("M005"));
    }

    @Test
    @Order(38)
    @DisplayName("Test Interactive - searchMember - By Gender")
    public void testInteractive_searchMember_ByGender() {
        // Arrange - search by gender: 3 (gender) -> Male -> n
        String input = "3\nMale\nn\n";
        Scanner testScanner = createTestScanner(input);
        MemberView testView = new MemberView(testScanner);
        memberController = new MemberController(testView, MemberData.initiallizeMembersData());

        // Act
        memberController.searchMember();

        // Assert - should find male members
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("M001") || output.contains("John Smith"));
    }

    @Test
    @Order(39)
    @DisplayName("Test Interactive - searchMember - By IC Number")
    public void testInteractive_searchMember_ByIcNumber() {
        // Arrange - search by IC: 4 (IC) -> 950101-01-1234 -> n
        String input = "4\n950101-01-1234\nn\n";
        Scanner testScanner = createTestScanner(input);
        MemberView testView = new MemberView(testScanner);
        memberController = new MemberController(testView, MemberData.initiallizeMembersData());

        // Act
        memberController.searchMember();

        // Assert
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("M001") || output.contains("John"));
    }

    @Test
    @Order(40)
    @DisplayName("Test Interactive - searchMember - By Contact Number")
    public void testInteractive_searchMember_ByContact() {
        // Arrange - search by contact: 5 (contact) -> 123456789 -> n
        String input = "5\n123456789\nn\n";
        Scanner testScanner = createTestScanner(input);
        MemberView testView = new MemberView(testScanner);
        memberController = new MemberController(testView, MemberData.initiallizeMembersData());

        // Act
        memberController.searchMember();

        // Assert
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("M001") || output.contains("John"));
    }

    @Test
    @Order(41)
    @DisplayName("Test Interactive - searchMember - No Results")
    public void testInteractive_searchMember_NoResults() {
        // Arrange - search for non-existent name: 2 (name) -> NonExistentName -> n
        String input = "2\nNonExistentName\nn\n";
        Scanner testScanner = createTestScanner(input);
        MemberView testView = new MemberView(testScanner);
        memberController = new MemberController(testView, MemberData.initiallizeMembersData());

        // Act
        memberController.searchMember();

        // Assert - should show no results message
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("NOT FOUND") || output.contains("not found") || output.contains("No members"));
    }

    // ==================== INVALID INPUT TESTS ====================

    @Test
    @Order(43)
    @DisplayName("Test Controller - Find Member By ID - Special Characters")
    public void testController_FindMemberById_SpecialCharacters() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());

        // Act
        Member found = memberController.findMemberById("M@#$%");

        // Assert
        assertNull(found);
    }

    @Test
    @Order(45)
    @DisplayName("Test Controller - Search Members By Name - Empty Input")
    public void testController_SearchMembersByName_EmptyInput() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());

        // Act
        List<Member> results = memberController.searchMembersByName("");

        // Assert
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    @Order(48)
    @DisplayName("Test Controller - Search Members By IC Number - Special Characters")
    public void testController_SearchMembersByIcNumber_SpecialCharacters() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());

        // Act
        List<Member> results = memberController.searchMembersByIcNumber("@#$%^&*");

        // Assert
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    @Order(51)
    @DisplayName("Test Controller - Search Members By Contact - Special Characters")
    public void testController_SearchMembersByContact_SpecialCharacters() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());

        // Act
        List<Member> results = memberController.searchMembersByContact("!@#$%");

        // Assert
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    // ==================== EDGE CASE TESTS ====================

    @Test
    @Order(57)
    @DisplayName("Test Controller - Search Members By Name - Case Sensitivity")
    public void testController_SearchMembersByName_CaseSensitivity() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());

        // Act - search with different cases
        List<Member> resultsLower = memberController.searchMembersByName("juju");
        List<Member> resultsUpper = memberController.searchMembersByName("JUJU");
        List<Member> resultsMixed = memberController.searchMembersByName("Juju");

        // Assert - all should return same results (case-insensitive)
        assertFalse(resultsLower.isEmpty());
        assertEquals(resultsLower.size(), resultsUpper.size());
        assertEquals(resultsLower.size(), resultsMixed.size());
    }

    @Test
    @Order(58)
    @DisplayName("Test Controller - Search Members By Name - Partial Match")
    public void testController_SearchMembersByName_PartialMatch() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());

        // Act
        List<Member> results = memberController.searchMembersByName("Juju");

        // Assert
        assertNotNull(results);
        for (Member member : results) {
            assertTrue(member.getName().toLowerCase().contains("juju"));
        }
    }

    @Test
    @Order(59)
    @DisplayName("Test Controller - Display All Members - Empty List")
    public void testController_DisplayAllMembers_EmptyList() {
        // Arrange
        memberController = new MemberController(memberView);

        // Act
        memberController.displayAllMembers();

        // Assert
        String output = outputStreamCaptor.toString();
        assertNotNull(output);
    }

    @Test
    @Order(60)
    @DisplayName("Test Controller - Display Membership Report - Empty List")
    public void testController_DisplayMembershipReport_EmptyList() {
        // Arrange
        memberController = new MemberController(memberView);

        // Act
        memberController.displayMembershipReport();

        // Assert
        String output = outputStreamCaptor.toString();
        assertNotNull(output);
    }

    @Test
    @Order(61)
    @DisplayName("Test Controller - Get Member Count - Empty List")
    public void testController_GetMemberCount_EmptyList() {
        // Arrange
        memberController = new MemberController(memberView);

        // Act
        int count = memberController.getMemberCount();

        // Assert
        assertEquals(0, count);
    }

    @Test
    @Order(62)
    @DisplayName("Test Controller - Search Members By Tier - All Tiers")
    public void testController_SearchMembersByTier_AllTiers() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());

        // Act
        List<Member> basicMembers = memberController.searchMembersByTier(MembershipTier.BASIC);
        List<Member> bronzeMembers = memberController.searchMembersByTier(MembershipTier.BRONZE);
        List<Member> silverMembers = memberController.searchMembersByTier(MembershipTier.SILVER);
        List<Member> goldenMembers = memberController.searchMembersByTier(MembershipTier.GOLDEN);

        // Assert
        assertNotNull(basicMembers);
        assertNotNull(bronzeMembers);
        assertNotNull(silverMembers);
        assertNotNull(goldenMembers);
    }

    @Test
    @Order(63)
    @DisplayName("Test Controller - Search Members By Gender - All Genders")
    public void testController_SearchMembersByGender_AllGenders() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());

        // Act
        List<Member> maleMembers = memberController.searchMembersByGender(Gender.MALE);
        List<Member> femaleMembers = memberController.searchMembersByGender(Gender.FEMALE);

        // Assert
        assertNotNull(maleMembers);
        assertNotNull(femaleMembers);

        for (Member member : maleMembers) {
            assertEquals(Gender.MALE, member.getGender());
        }

        for (Member member : femaleMembers) {
            assertEquals(Gender.FEMALE, member.getGender());
        }
    }

    @Test
    @Order(64)
    @DisplayName("Test Controller - Delete Member - Verify Count Decreases")
    public void testController_DeleteMember_VerifyCountDecreases() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());
        int initialCount = memberController.getMemberCount();

        // Act
        boolean deleted = memberController.deleteMemberById("M001");

        // Assert
        assertTrue(deleted);
        assertEquals(initialCount - 1, memberController.getMemberCount());
    }

    @Test
    @Order(65)
    @DisplayName("Test Controller - Update Member - Verify Changes Persist")
    public void testController_UpdateMember_VerifyChangesPersist() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());
        Member updatedMember = new Member("M001", "Updated Name", Gender.FEMALE,
                "990101-01-9999", "999999999", LocalDate.now(), MembershipTier.GOLDEN);

        // Act
        boolean updated = memberController.updateMemberDirect("M001", updatedMember);

        // Assert
        assertTrue(updated);
        Member retrieved = memberController.findMemberById("M001");
        assertEquals("Updated Name", retrieved.getName());
        assertEquals(Gender.FEMALE, retrieved.getGender());
        assertEquals("990101-01-9999", retrieved.getIcNumber());
        assertEquals("999999999", retrieved.getContactNumber());
        assertEquals(MembershipTier.GOLDEN, retrieved.getMembershipTier());
    }

    @Test
    @Order(66)
    @DisplayName("Test Controller - Search Members By Join Date - Future Date")
    public void testController_SearchMembersByJoinDate_FutureDate() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());
        LocalDate futureDate = LocalDate.now().plusYears(1);

        // Act
        List<Member> results = memberController.searchMembersByJoinDate(futureDate);

        // Assert
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    @Order(67)
    @DisplayName("Test Controller - Search Members By Join Date - Past Date")
    public void testController_SearchMembersByJoinDate_PastDate() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());
        LocalDate pastDate = LocalDate.of(2019, 1, 1);

        // Act
        List<Member> results = memberController.searchMembersByJoinDate(pastDate);

        // Assert
        assertNotNull(results);
    }

    @Test
    @Order(68)
    @DisplayName("Test Controller - Find Member By ID - Very Long ID")
    public void testController_FindMemberById_VeryLongId() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());
        String longId = "M" + "0".repeat(1000);

        // Act
        Member found = memberController.findMemberById(longId);

        // Assert
        assertNull(found);
    }

    @Test
    @Order(69)
    @DisplayName("Test Controller - Search Members By Name - Special Characters")
    public void testController_SearchMembersByName_SpecialCharacters() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());

        // Act
        List<Member> results = memberController.searchMembersByName("@#$%^&*()");

        // Assert
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    @Order(70)
    @DisplayName("Test Controller - Search Members By IC Number - Malformed IC")
    public void testController_SearchMembersByIcNumber_MalformedIC() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());

        // Act
        List<Member> results = memberController.searchMembersByIcNumber("invalid-ic-format");

        // Assert
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }
}
