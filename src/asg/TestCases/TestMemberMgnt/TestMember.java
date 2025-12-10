package asg.TestCases.TestMemberMgnt;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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

public class TestMember {
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

    // ==================== MEMBER MODEL TESTS ====================
    @Test
    @Order(2)
    @DisplayName("Test Member Creation - Valid Data")
    public void testMemberCreation_ValidData() {
        // Arrange & Act
        Member member = new Member(
                "M001",
                "John Doe",
                Gender.MALE,
                "950101-01-1234",
                "123456789",
                LocalDate.of(2023, 1, 15),
                MembershipTier.BASIC);

        // Assert
        assertNotNull(member);
        assertEquals("M001", member.getId());
        assertEquals("John Doe", member.getName());
        assertEquals(Gender.MALE, member.getGender());
        assertEquals("950101-01-1234", member.getIcNumber());
        assertEquals("123456789", member.getContactNumber());
        assertEquals(LocalDate.of(2023, 1, 15), member.getJoinDate());
        assertEquals(MembershipTier.BASIC, member.getMembershipTier());
        assertEquals(0.0, member.getTotalSpending());
    }

    @Test
    @Order(3)
    @DisplayName("Test Member Creation - Invalid ID (Null)")
    public void testMemberCreation_InvalidId_Null() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Member(
                    null,
                    "Test User",
                    Gender.MALE,
                    "950101-01-1234",
                    "123456789",
                    LocalDate.now(),
                    MembershipTier.BASIC);
        });
    }

    @Test
    @Order(4)
    @DisplayName("Test Member Creation - Invalid ID (Empty)")
    public void testMemberCreation_InvalidId_Empty() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Member(
                    "   ",
                    "Test User",
                    Gender.MALE,
                    "950101-01-1234",
                    "123456789",
                    LocalDate.now(),
                    MembershipTier.BASIC);
        });
    }

    @Test
    @Order(5)
    @DisplayName("Test Member Creation - Invalid Name (Null)")
    public void testMemberCreation_InvalidName_Null() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Member(
                    "M001",
                    null,
                    Gender.MALE,
                    "950101-01-1234",
                    "123456789",
                    LocalDate.now(),
                    MembershipTier.BASIC);
        });
    }

    @Test
    @Order(6)
    @DisplayName("Test Member Creation - Invalid Name (Too Short)")
    public void testMemberCreation_InvalidName_TooShort() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Member(
                    "M001",
                    "A",
                    Gender.MALE,
                    "950101-01-1234",
                    "123456789",
                    LocalDate.now(),
                    MembershipTier.BASIC);
        });
    }

    @Test
    @Order(7)
    @DisplayName("Test Member Creation - Invalid Gender (Null)")
    public void testMemberCreation_InvalidGender_Null() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Member(
                    "M001",
                    "Test User",
                    null,
                    "950101-01-1234",
                    "123456789",
                    LocalDate.now(),
                    MembershipTier.BASIC);
        });
    }

    @Test
    @Order(8)
    @DisplayName("Test Member Creation - Invalid IC Number")
    public void testMemberCreation_InvalidIcNumber() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Member(
                    "M001",
                    "Test User",
                    Gender.MALE,
                    "invalid-ic",
                    "123456789",
                    LocalDate.now(),
                    MembershipTier.BASIC);
        });
    }

    @Test
    @Order(9)
    @DisplayName("Test Member Creation - Invalid Contact Number (Too Short)")
    public void testMemberCreation_InvalidContact_TooShort() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Member(
                    "M001",
                    "Test User",
                    Gender.MALE,
                    "950101-01-1234",
                    "12345",
                    LocalDate.now(),
                    MembershipTier.BASIC);
        });
    }

    @Test
    @Order(10)
    @DisplayName("Test Member Creation - Invalid Contact Number (Too Long)")
    public void testMemberCreation_InvalidContact_TooLong() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Member(
                    "M001",
                    "Test User",
                    Gender.MALE,
                    "950101-01-1234",
                    "123456789012",
                    LocalDate.now(),
                    MembershipTier.BASIC);
        });
    }

    @Test
    @Order(11)
    @DisplayName("Test Member Creation - Invalid Join Date (Future)")
    public void testMemberCreation_InvalidJoinDate_Future() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Member(
                    "M001",
                    "Test User",
                    Gender.MALE,
                    "950101-01-1234",
                    "123456789",
                    LocalDate.now().plusDays(1),
                    MembershipTier.BASIC);
        });
    }

    @Test
    @Order(12)
    @DisplayName("Test Member Creation - Invalid Join Date (Null)")
    public void testMemberCreation_InvalidJoinDate_Null() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Member(
                    "M001",
                    "Test User",
                    Gender.MALE,
                    "950101-01-1234",
                    "123456789",
                    null,
                    MembershipTier.BASIC);
        });
    }

    @Test
    @Order(13)
    @DisplayName("Test Member Setters - Valid Data")
    public void testMemberSetters_ValidData() {
        // Arrange
        Member member = MemberData.createTestMember();

        // Act
        member.setName("Updated Name");
        member.setGender(Gender.FEMALE);
        member.setIcNumber("990101-01-9999");
        member.setContactNumber("999888777");
        member.setMembershipTier(MembershipTier.GOLDEN);
        member.setTotalSpending(5000.0);

        // Assert
        assertEquals("Updated Name", member.getName());
        assertEquals(Gender.FEMALE, member.getGender());
        assertEquals("990101-01-9999", member.getIcNumber());
        assertEquals("999888777", member.getContactNumber());
        assertEquals(MembershipTier.GOLDEN, member.getMembershipTier());
        assertEquals(5000.0, member.getTotalSpending());
    }

    @Test
    @Order(14)
    @DisplayName("Test Member - Add Spending")
    public void testMember_AddSpending() {
        // Arrange
        Member member = MemberData.createTestMember();

        // Act
        member.addSpending(500.0);

        // Assert
        assertEquals(500.0, member.getTotalSpending());
        assertEquals(MembershipTier.BASIC, member.getMembershipTier());

        // Act - Add more to upgrade tier
        member.addSpending(600.0);

        // Assert
        assertEquals(1100.0, member.getTotalSpending());
        assertEquals(MembershipTier.BRONZE, member.getMembershipTier());
    }

    @Test
    @Order(15)
    @DisplayName("Test Member - Add Negative Spending")
    public void testMember_AddNegativeSpending() {
        // Arrange
        Member member = MemberData.createTestMember();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            member.addSpending(-100.0);
        });
    }

    @Test
    @Order(16)
    @DisplayName("Test Member - Calculate Discounted Price")
    public void testMember_CalculateDiscountedPrice() {
        // Arrange
        Member member = new Member(
                "M001",
                "Test User",
                Gender.MALE,
                "950101-01-1234",
                "123456789",
                LocalDate.now(),
                MembershipTier.GOLDEN);

        // Act
        double discountedPrice = member.calculateDiscountedPrice(1000.0);

        // Assert
        assertEquals(850.0, discountedPrice, 0.01); // 15% discount for Golden
    }

    @Test
    @Order(17)
    @DisplayName("Test Member - Format Join Date")
    public void testMember_FormatJoinDate() {
        // Arrange
        Member member = new Member(
                "M001",
                "Test User",
                Gender.MALE,
                "950101-01-1234",
                "123456789",
                LocalDate.of(2023, 1, 15),
                MembershipTier.BRONZE);

        // Act
        String formatted = member.getFormattedJoinDate();

        // Assert
        assertEquals("15/01/2023", formatted);
    }

    @Test
    @Order(19)
    @DisplayName("Test Member - Parse Invalid Date Format")
    public void testMember_ParseInvalidDateFormat() {
        // Act & Assert
        assertThrows(DateTimeParseException.class, () -> {
            Member.parseJoinDate("2023-01-15");
        });
    }

    // ==================== GENDER ENUM TESTS ====================

    @Test
    @Order(20)
    @DisplayName("Test Gender - From String Valid")
    public void testGender_FromString_Valid() {
        // Act & Assert
        assertEquals(Gender.MALE, Gender.fromString("Male"));
        assertEquals(Gender.FEMALE, Gender.fromString("female"));
        assertEquals(Gender.OTHER, Gender.fromString("OTHER"));
        assertEquals(Gender.MALE, Gender.fromString("MALE"));
    }

    @Test
    @Order(21)
    @DisplayName("Test Gender - From String Invalid")
    public void testGender_FromString_Invalid() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            Gender.fromString("Invalid");
        });
    }

    @Test
    @Order(22)
    @DisplayName("Test Gender - From String Null")
    public void testGender_FromString_Null() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            Gender.fromString(null);
        });
    }

    @Test
    @Order(23)
    @DisplayName("Test Gender - Display Name")
    public void testGender_DisplayName() {
        // Assert
        assertEquals("Male", Gender.MALE.getDisplayName());
        assertEquals("Female", Gender.FEMALE.getDisplayName());
        assertEquals("Other", Gender.OTHER.getDisplayName());
    }

    // ==================== MEMBERSHIP TIER TESTS ====================

    @Test
    @Order(24)
    @DisplayName("Test MembershipTier - Calculate Tier BASIC")
    public void testMembershipTier_CalculateTier_Basic() {
        // Act
        MembershipTier tier = MembershipTier.calculateMembershipTier(500.0);

        // Assert
        assertEquals(MembershipTier.BASIC, tier);
    }

    @Test
    @Order(25)
    @DisplayName("Test MembershipTier - Calculate Tier BRONZE")
    public void testMembershipTier_CalculateTier_Bronze() {
        // Act
        MembershipTier tier = MembershipTier.calculateMembershipTier(1500.0);

        // Assert
        assertEquals(MembershipTier.BRONZE, tier);
    }

    @Test
    @Order(26)
    @DisplayName("Test MembershipTier - Calculate Tier SILVER")
    public void testMembershipTier_CalculateTier_Silver() {
        // Act
        MembershipTier tier = MembershipTier.calculateMembershipTier(2500.0);

        // Assert
        assertEquals(MembershipTier.SILVER, tier);
    }

    @Test
    @Order(27)
    @DisplayName("Test MembershipTier - Calculate Tier GOLDEN")
    public void testMembershipTier_CalculateTier_Golden() {
        // Act
        MembershipTier tier = MembershipTier.calculateMembershipTier(3500.0);

        // Assert
        assertEquals(MembershipTier.GOLDEN, tier);
    }

    @Test
    @Order(28)
    @DisplayName("Test MembershipTier - Calculate Tier Boundary")
    public void testMembershipTier_CalculateTier_Boundary() {
        // Assert exact boundaries
        assertEquals(MembershipTier.BASIC, MembershipTier.calculateMembershipTier(0.0));
        assertEquals(MembershipTier.BRONZE, MembershipTier.calculateMembershipTier(1000.0));
        assertEquals(MembershipTier.SILVER, MembershipTier.calculateMembershipTier(2000.0));
        assertEquals(MembershipTier.GOLDEN, MembershipTier.calculateMembershipTier(3000.0));
    }

    @Test
    @Order(32)
    @DisplayName("Test MembershipTier - Calculate Tier Negative")
    public void testMembershipTier_CalculateTier_Negative() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            MembershipTier.calculateMembershipTier(-100.0);
        });
    }

    @Test
    @Order(33)
    @DisplayName("Test MembershipTier - Calculate Discount")
    public void testMembershipTier_CalculateDiscount() {
        // Assert
        assertEquals(150.0, MembershipTier.GOLDEN.calculateDiscount(1000.0), 0.01);
        assertEquals(100.0, MembershipTier.SILVER.calculateDiscount(1000.0), 0.01);
        assertEquals(50.0, MembershipTier.BRONZE.calculateDiscount(1000.0), 0.01);
        assertEquals(0.0, MembershipTier.BASIC.calculateDiscount(1000.0), 0.01);
    }

    @Test
    @Order(34)
    @DisplayName("Test MembershipTier - Calculate Discount Negative Price")
    public void testMembershipTier_CalculateDiscount_NegativePrice() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            MembershipTier.GOLDEN.calculateDiscount(-100.0);
        });
    }

    @Test
    @Order(35)
    @DisplayName("Test MembershipTier - Getters")
    public void testMembershipTier_Getters() {
        // Assert
        assertEquals("Golden Membership", MembershipTier.GOLDEN.getDisplayName());
        assertEquals(3000.0, MembershipTier.GOLDEN.getAmtRequired());
        assertEquals(0.15, MembershipTier.GOLDEN.getDiscountRate());
    }

    // ==================== MEMBER CONTROLLER TESTS ====================

    @Test
    @Order(37)
    @DisplayName("Test Controller - Creation With Initial Data")
    public void testController_Creation_WithInitialData() {

        List<Member> sampleData = MemberData.initiallizeMembersData();

        memberController = new MemberController(memberView, sampleData);

        // Assert
        assertEquals(5, memberController.getMemberCount());
    }

    @Test
    @DisplayName("Test Controller - Add Member Direct")
    public void testController_AddMemberDirect() {
        memberController = new MemberController(memberView);
        Member member = MemberData.createTestMember();

        // Act
        boolean result = memberController.addMemberDirect(member);

        // Assert
        assertTrue(result);
        assertEquals(1, memberController.getMemberCount());
    }

    @Test
    @Order(39)
    @DisplayName("Test Controller - Add Duplicate Member")
    public void testController_AddDuplicateMember() {
        // Arrange
        memberController = new MemberController(memberView);
        Member member1 = new Member("M001", "Test", Gender.MALE, "950101-01-1234", "123456789", LocalDate.now(),
                MembershipTier.BASIC);
        Member member2 = new Member("M001", "Test2", Gender.FEMALE, "990101-01-9999", "999999999", LocalDate.now(),
                MembershipTier.BASIC);

        // Act
        memberController.addMemberDirect(member1);
        boolean result = memberController.addMemberDirect(member2);

        // Assert
        assertFalse(result);
        assertEquals(1, memberController.getMemberCount());
    }

    @Test
    @Order(40)
    @DisplayName("Test Controller - Add Null Member")
    public void testController_AddNullMember() {
        // Arrange
        memberController = new MemberController(memberView);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            memberController.addMemberDirect(null);
        });
    }

    @Test
    @Order(41)
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
    @Order(42)
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
    @Order(43)
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
    @Order(44)
    @DisplayName("Test Controller - Search Members By Name")
    public void testController_SearchMembersByName() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());

        // Act
        List<Member> results = memberController.searchMembersByName("John");

        // Assert
        assertFalse(results.isEmpty());
        assertTrue(results.get(0).getName().contains("John"));
    }

    @Test
    @Order(45)
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
    @Order(46)
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
    @Order(47)
    @DisplayName("Test Controller - Update Member Direct")
    public void testController_UpdateMemberDirect() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());
        Member updatedMember = new Member("M999", "Updated Name", Gender.OTHER, "990101-01-9999", "999999999",
                LocalDate.now(), MembershipTier.SILVER);

        // Act
        boolean result = memberController.updateMemberDirect("M001", updatedMember);

        // Assert
        assertTrue(result);
        Member found = memberController.findMemberById("M001");
        assertEquals("Updated Name", found.getName());
    }

    @Test
    @Order(48)
    @DisplayName("Test Controller - Update Non-Existent Member")
    public void testController_UpdateNonExistentMember() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());
        Member updatedMember = MemberData.createTestMember();

        // Act
        boolean result = memberController.updateMemberDirect("M999", updatedMember);
        // Assert
        assertFalse(result);
    }

    @Test
    @Order(49)
    @DisplayName("Test Controller - Delete Member By ID")
    public void testController_DeleteMemberById() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());
        int initialCount = memberController.getMemberCount();

        // Act
        boolean result = memberController.deleteMemberById("M001");

        // Assert
        assertTrue(result);
        assertEquals(initialCount - 1, memberController.getMemberCount());
        assertNull(memberController.findMemberById("M001"));
    }

    @Test
    @Order(50)
    @DisplayName("Test Controller - Delete Non-Existent Member")
    public void testController_DeleteNonExistentMember() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());

        // Act
        boolean result = memberController.deleteMemberById("M999");

        // Assert
        assertFalse(result);
    }

    // ==================== MEMBER DATA TESTS ====================

    @Test
    @Order(53)
    @DisplayName("Test MemberData - Initialize Sample Data")
    public void testMemberData_InitializeSampleData() {
        // Act
        List<Member> data = MemberData.initiallizeMembersData();

        // Assert
        assertNotNull(data);
        assertEquals(5, data.size());
    }

    @Test
    @Order(55)
    @DisplayName("Test MemberData - Create Test Member")
    public void testMemberData_CreateTestMember() {
        // Act
        Member member = MemberData.createTestMember();

        // Assert
        assertNotNull(member);
        assertEquals("T001", member.getId());
    }

    @Test
    @Order(56)
    @DisplayName("Test MemberData - Get Members By Tier")
    public void testMemberData_GetMembersByTier() {
        // Act
        List<Member> goldenMembers = MemberData.getMembersByTier(MembershipTier.GOLDEN);
        List<Member> basicMembers = MemberData.getMembersByTier(MembershipTier.BASIC);

        // Assert
        assertFalse(goldenMembers.isEmpty());
        assertFalse(basicMembers.isEmpty());
    }

    // ==================== INTEGRATION TESTS ====================

    @Test
    @Order(57)
    @DisplayName("Integration Test - Complete Member Lifecycle")
    public void integrationTest_CompleteMemberLifecycle() {
        // Arrange
        MemberView view = new MemberView();
        MemberController controller = new MemberController(view);

        // Act 1: Add Member
        Member newMember = new Member(
                "M100",
                "Integration Test User",
                Gender.MALE,
                "950101-01-1234",
                "123456789",
                LocalDate.now(),
                MembershipTier.BASIC);
        controller.addMemberDirect(newMember);

        // Assert 1: Member exists
        assertEquals(1, controller.getMemberCount());
        Member found = controller.findMemberById("M100");
        assertNotNull(found);

        // Act 2: Update Member
        Member updatedInfo = new Member(
                "M999",
                "Updated Name",
                Gender.FEMALE,
                "990101-01-9999",
                "999999999",
                LocalDate.now(),
                MembershipTier.BASIC);
        controller.updateMemberDirect("M100", updatedInfo);

        // Assert 2: Member updated
        found = controller.findMemberById("M100");
        assertEquals("Updated Name", found.getName());

        // Act 3: Add Spending
        found.addSpending(1500.0);

        // Assert 3: Tier upgraded
        assertEquals(MembershipTier.BRONZE, found.getMembershipTier());

        // Act 4: Delete Member
        controller.deleteMemberById("M100");

        // Assert 4: Member deleted
        assertEquals(0, controller.getMemberCount());
        assertNull(controller.findMemberById("M100"));
    }

    @Test
    @Order(58)
    @DisplayName("Integration Test - Search Operations")
    public void integrationTest_SearchOperations() {
        // Arrange
        MemberView view = new MemberView();
        MemberController controller = new MemberController(view, MemberData.initiallizeMembersData());

        // Act & Assert: Search by various criteria
        List<Member> byName = controller.searchMembersByName("Smith");
        assertFalse(byName.isEmpty());

        List<Member> byGender = controller.searchMembersByGender(Gender.MALE);
        assertFalse(byGender.isEmpty());

        List<Member> byTier = controller.searchMembersByTier(MembershipTier.BASIC);
        assertFalse(byTier.isEmpty());
    }
}
