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
import asg.MemberManagementModule.Constants.MemberOptions;
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

    // ==================== MEMBER OPTIONS ENUM TESTS ====================

    @Test
    @Order(59)
    @DisplayName("Test MemberOptions - Main Menu Options Values")
    public void testMemberOptions_MainMenuOptionsValues() {
        // Assert Main Menu options
        assertEquals(1, MemberOptions.DISPLAY_ALL_MEMBERS.getValue());
        assertEquals(2, MemberOptions.ADD_MEMBER.getValue());
        assertEquals(3, MemberOptions.SEARCH_MEMBER.getValue());
        assertEquals(4, MemberOptions.UPDATE_MEMBER.getValue());
        assertEquals(5, MemberOptions.DELETE_MEMBER.getValue());
        assertEquals(6, MemberOptions.SHOW_MEMBERSHIP_REPORT.getValue());
        assertEquals(7, MemberOptions.EXIT.getValue());
    }

    @Test
    @Order(60)
    @DisplayName("Test MemberOptions - Main Menu Options Descriptions")
    public void testMemberOptions_MainMenuOptionsDescriptions() {
        // Assert descriptions
        assertEquals("Display All Members Details",
                MemberOptions.DISPLAY_ALL_MEMBERS.getDescription());
        assertEquals("Add New Records", MemberOptions.ADD_MEMBER.getDescription());
        assertEquals("Search For Member Records",
                MemberOptions.SEARCH_MEMBER.getDescription());
        assertEquals("Modify Member's Records",
                MemberOptions.UPDATE_MEMBER.getDescription());
        assertEquals("Delete Member's Records",
                MemberOptions.DELETE_MEMBER.getDescription());
        assertEquals("Show All Type Of Member",
                MemberOptions.SHOW_MEMBERSHIP_REPORT.getDescription());
        assertEquals("Exit/Back To Menu", MemberOptions.EXIT.getDescription());
    }

    @Test
    @Order(61)
    @DisplayName("Test MemberOptions - Search Menu Options Values")
    public void testMemberOptions_SearchMenuOptionsValues() {
        // Assert Search Menu options
        assertEquals(1, MemberOptions.SEARCH_BY_ID.getValue());
        assertEquals(2, MemberOptions.SEARCH_BY_NAME.getValue());
        assertEquals(3, MemberOptions.SEARCH_BY_GENDER.getValue());
        assertEquals(4, MemberOptions.SEARCH_BY_IC.getValue());
        assertEquals(5, MemberOptions.SEARCH_BY_CONTACT.getValue());
        assertEquals(6, MemberOptions.SEARCH_BY_DATE.getValue());
    }

    @Test
    @Order(62)
    @DisplayName("Test MemberOptions - Search Menu Options Descriptions")
    public void testMemberOptions_SearchMenuOptionsDescriptions() {
        // Assert descriptions
        assertEquals("Member ID", MemberOptions.SEARCH_BY_ID.getDescription());
        assertEquals("Name", MemberOptions.SEARCH_BY_NAME.getDescription());
        assertEquals("Gender Male / Female / Others",
                MemberOptions.SEARCH_BY_GENDER.getDescription());
        assertEquals("IC Number (xxxxxx-xx-xxxx)",
                MemberOptions.SEARCH_BY_IC.getDescription());
        assertEquals("Contact Number (+60)",
                MemberOptions.SEARCH_BY_CONTACT.getDescription());
        assertEquals("Date Become A Member (dd/mm/yyyy)",
                MemberOptions.SEARCH_BY_DATE.getDescription());
    }

    @Test
    @Order(63)
    @DisplayName("Test MemberOptions - Update Menu Options Values")
    public void testMemberOptions_UpdateMenuOptionsValues() {
        // Assert Update Menu options
        assertEquals(1, MemberOptions.UPDATE_NAME.getValue());
        assertEquals(2, MemberOptions.UPDATE_GENDER.getValue());
        assertEquals(3, MemberOptions.UPDATE_IC.getValue());
        assertEquals(4, MemberOptions.UPDATE_CONTACT.getValue());
        assertEquals(5, MemberOptions.UPDATE_FINISH.getValue());
    }

    @Test
    @Order(64)
    @DisplayName("Test MemberOptions - Update Menu Options Descriptions")
    public void testMemberOptions_UpdateMenuOptionsDescriptions() {
        // Assert descriptions
        assertEquals("Update Name", MemberOptions.UPDATE_NAME.getDescription());
        assertEquals("Update Gender",
                MemberOptions.UPDATE_GENDER.getDescription());
        assertEquals("Update IC Number", MemberOptions.UPDATE_IC.getDescription());
        assertEquals("Update Contact Number",
                MemberOptions.UPDATE_CONTACT.getDescription());
        assertEquals("Finish", MemberOptions.UPDATE_FINISH.getDescription());
    }

    @Test
    @Order(67)
    @DisplayName("Test MemberOptions - fromMainMenuValue Valid")
    public void testMemberOptions_fromMainMenuValue_Valid() {
        // Assert all valid main menu values
        assertEquals(MemberOptions.DISPLAY_ALL_MEMBERS,
                MemberOptions.fromMainMenuValue(1));
        assertEquals(MemberOptions.ADD_MEMBER,
                MemberOptions.fromMainMenuValue(2));
        assertEquals(MemberOptions.SEARCH_MEMBER,
                MemberOptions.fromMainMenuValue(3));
        assertEquals(MemberOptions.UPDATE_MEMBER,
                MemberOptions.fromMainMenuValue(4));
        assertEquals(MemberOptions.DELETE_MEMBER,
                MemberOptions.fromMainMenuValue(5));
        assertEquals(MemberOptions.SHOW_MEMBERSHIP_REPORT,
                MemberOptions.fromMainMenuValue(6));
        assertEquals(MemberOptions.EXIT,
                MemberOptions.fromMainMenuValue(7));
    }

    @Test
    @Order(68)
    @DisplayName("Test MemberOptions - fromMainMenuValue Invalid")
    public void testMemberOptions_fromMainMenuValue_Invalid() {
        // Assert throws exception for invalid values
        assertThrows(IllegalArgumentException.class, () -> {
            MemberOptions.fromMainMenuValue(0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            MemberOptions.fromMainMenuValue(8);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            MemberOptions.fromMainMenuValue(-1);
        });
    }

    @Test
    @Order(69)
    @DisplayName("Test MemberOptions - fromSearchMenuValue Valid")
    public void testMemberOptions_fromSearchMenuValue_Valid() {
        // Assert all valid search menu values
        assertEquals(MemberOptions.SEARCH_BY_ID,
                MemberOptions.fromSearchMenuValue(1));
        assertEquals(MemberOptions.SEARCH_BY_NAME,
                MemberOptions.fromSearchMenuValue(2));
        assertEquals(MemberOptions.SEARCH_BY_GENDER,
                MemberOptions.fromSearchMenuValue(3));
        assertEquals(MemberOptions.SEARCH_BY_IC,
                MemberOptions.fromSearchMenuValue(4));
        assertEquals(MemberOptions.SEARCH_BY_CONTACT,
                MemberOptions.fromSearchMenuValue(5));
        assertEquals(MemberOptions.SEARCH_BY_DATE,
                MemberOptions.fromSearchMenuValue(6));
    }

    @Test
    @Order(70)
    @DisplayName("Test MemberOptions - fromSearchMenuValue Invalid")
    public void testMemberOptions_fromSearchMenuValue_Invalid() {
        // Assert throws exception for invalid values
        assertThrows(IllegalArgumentException.class, () -> {
            MemberOptions.fromSearchMenuValue(0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            MemberOptions.fromSearchMenuValue(7);
        });
    }

    @Test
    @Order(71)
    @DisplayName("Test MemberOptions - fromUpdateMenuValue Valid")
    public void testMemberOptions_fromUpdateMenuValue_Valid() {
        // Assert all valid update menu values
        assertEquals(MemberOptions.UPDATE_NAME,
                MemberOptions.fromUpdateMenuValue(1));
        assertEquals(MemberOptions.UPDATE_GENDER,
                MemberOptions.fromUpdateMenuValue(2));
        assertEquals(MemberOptions.UPDATE_IC,
                MemberOptions.fromUpdateMenuValue(3));
        assertEquals(MemberOptions.UPDATE_CONTACT,
                MemberOptions.fromUpdateMenuValue(4));
        assertEquals(MemberOptions.UPDATE_FINISH,
                MemberOptions.fromUpdateMenuValue(5));
    }

    @Test
    @Order(72)
    @DisplayName("Test MemberOptions - fromUpdateMenuValue Invalid")
    public void testMemberOptions_fromUpdateMenuValue_Invalid() {
        // Assert throws exception for invalid values
        assertThrows(IllegalArgumentException.class, () -> {
            MemberOptions.fromUpdateMenuValue(0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            MemberOptions.fromUpdateMenuValue(6);
        });
    }

    // ==================== ADDITIONAL CONTROLLER TESTS ====================

    @Test
    @Order(75)
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
    @Order(76)
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
    @Order(78)
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
    @Order(77)
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
    @Order(81)
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
    @Order(82)
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
    @Order(78)
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
    @Order(85)
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
    @Order(86)
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
    @Order(80)
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
    @Order(81)
    @DisplayName("Test Controller - Delete Member By ID - Empty Input")
    public void testController_DeleteMemberById_EmptyInput() {
        // Arrange
        memberController = new MemberController(memberView, MemberData.initiallizeMembersData());

        // Act
        boolean result = memberController.deleteMemberById("");

        // Assert
        assertFalse(result);
    }

    @Test
    @Order(91)
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
    @Order(94)
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
    @Order(95)
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

    // ==================== MEMBER VIEW TESTS ====================

    @Test
    @Order(96)
    @DisplayName("Test View - Display Member Main Menu")
    public void testView_DisplayMemberMainMenu() {
        // Act
        memberView.displayMemberMainMenu();

        // Assert
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Member Main Menu"));
    }

    @Test
    @Order(97)
    @DisplayName("Test View - Display All Members - Empty List")
    public void testView_DisplayAllMembers_EmptyList() {
        // Act
        memberView.displayAllMembers(new java.util.ArrayList<>());

        // Assert
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("No members found"));
    }

    @Test
    @Order(98)
    @DisplayName("Test View - Display All Members - Null List")
    public void testView_DisplayAllMembers_NullList() {
        // Act
        memberView.displayAllMembers(null);

        // Assert
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("No members found"));
    }

    @Test
    @Order(99)
    @DisplayName("Test View - Display All Members - With Data")
    public void testView_DisplayAllMembers_WithData() {
        // Arrange
        List<Member> members = MemberData.initiallizeMembersData();

        // Act
        memberView.displayAllMembers(members);

        // Assert
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("M001"));
    }

    @Test
    @Order(100)
    @DisplayName("Test View - Display Member Details - Null Member")
    public void testView_DisplayMemberDetails_NullMember() {
        // Act
        memberView.displayMemberDetails(null);

        // Assert
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("ERROR"));
    }

    @Test
    @Order(101)
    @DisplayName("Test View - Display Member Details - Valid Member")
    public void testView_DisplayMemberDetails_ValidMember() {
        // Arrange
        Member member = MemberData.createTestMember();

        // Act
        memberView.displayMemberDetails(member);

        // Assert
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("T001"));
    }

    @Test
    @Order(102)
    @DisplayName("Test View - Display Membership Report - Empty List")
    public void testView_DisplayMembershipReport_EmptyList() {
        // Arrange
        java.util.Map<MembershipTier, Integer> tierCounts = new java.util.HashMap<>();

        // Act
        memberView.displayMembershipReport(new java.util.ArrayList<>(), tierCounts);

        // Assert
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("No members found"));
    }

    @Test
    @Order(103)
    @DisplayName("Test View - Display Membership Report - Null List")
    public void testView_DisplayMembershipReport_NullList() {
        // Arrange
        java.util.Map<MembershipTier, Integer> tierCounts = new java.util.HashMap<>();

        // Act
        memberView.displayMembershipReport(null, tierCounts);

        // Assert
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("No members found"));
    }

    @Test
    @Order(104)
    @DisplayName("Test View - Display Membership Report - With Data")
    public void testView_DisplayMembershipReport_WithData() {
        // Arrange
        List<Member> members = MemberData.initiallizeMembersData();
        java.util.Map<MembershipTier, Integer> tierCounts = new java.util.HashMap<>();
        tierCounts.put(MembershipTier.BASIC, 2);
        tierCounts.put(MembershipTier.BRONZE, 1);
        tierCounts.put(MembershipTier.SILVER, 1);
        tierCounts.put(MembershipTier.GOLDEN, 1);

        // Act
        memberView.displayMembershipReport(members, tierCounts);

        // Assert
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("M001"));
    }

    @Test
    @Order(105)
    @DisplayName("Test View - Show Success Message")
    public void testView_ShowSuccessMessage() {
        // Act
        memberView.showSuccessMessage("Test Success");

        // Assert
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Test Success"));
    }

    @Test
    @Order(106)
    @DisplayName("Test View - Show Error Message")
    public void testView_ShowErrorMessage() {
        // Act
        memberView.showErrorMessage("Test Error");

        // Assert
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Test Error"));
    }

    @Test
    @Order(107)
    @DisplayName("Test View - Show Cancel Message")
    public void testView_ShowCancelMessage() {
        // Act
        memberView.showCancelMessage("Test Cancel");

        // Assert
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Test Cancel"));
    }

    @Test
    @Order(108)
    @DisplayName("Test View - Show Not Found Message")
    public void testView_ShowNotFoundMessage() {
        // Act
        memberView.showNotFoundMessage();

        // Assert
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("ERROR"));
    }

    @Test
    @Order(109)
    @DisplayName("Test View - Display Exit Message")
    public void testView_DisplayExitMessage() {
        // Act
        memberView.displayExitMessage();

        // Assert
        String output = outputStreamCaptor.toString();
        assertTrue(output.length() > 0);
    }

    // ==================== INTERACTIVE METHOD TESTS ====================

    /**
     * Helper method to create a Scanner from simulated input
     */
    private Scanner createTestScanner(String input) {
        return new Scanner(new java.io.ByteArrayInputStream(input.getBytes()));
    }

    @Test
    @Order(114)
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
    @Order(115)
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
    @Order(116)
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
    @Order(117)
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
    @Order(118)
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
    @Order(119)
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
    @Order(120)
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
    @Order(121)
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
}
