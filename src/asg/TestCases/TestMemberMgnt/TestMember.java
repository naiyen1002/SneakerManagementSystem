package asg.TestCases.TestMemberMgnt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
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
    }

    @AfterEach
    public void tearDown() {
        // Restore standard output
        System.setOut(standardOut);
    }

    // ==================== MEMBER MODEL TESTS ====================
    @Test
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
    @DisplayName("Test Controller - Add Member Direct")
    public void testController_AddMemberDirect() {
        // Arrange
        MemberView view = new MemberView();
        MemberController controller = new MemberController(view);
        Member member = MemberData.createTestMember();

        // Act
        boolean result = controller.addMemberDirect(member);

        // Assert
        assertTrue(result);
        assertEquals(1, controller.getMemberCount());
    }
}
