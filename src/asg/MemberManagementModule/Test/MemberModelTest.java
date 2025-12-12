package asg.MemberManagementModule.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import asg.MemberManagementModule.Constants.MemberData;
import asg.MemberManagementModule.Model.Gender;
import asg.MemberManagementModule.Model.Member;
import asg.MemberManagementModule.Model.MembershipTier;

public class MemberModelTest {

    @Test
    @Order(1)
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
    @Order(2)
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
    @Order(3)
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
    @Order(4)
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
    @Order(5)
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
    @Order(6)
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
    @Order(7)
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
    @Order(8)
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
    @Order(9)
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
    @Order(10)
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
    @Order(11)
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
    @Order(12)
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
    @Order(13)
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
    @Order(14)
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
    @Order(15)
    @DisplayName("Test Member - Parse Invalid Date Format")
    public void testMember_ParseInvalidDateFormat() {
        // Act & Assert
        assertThrows(DateTimeParseException.class, () -> {
            Member.parseJoinDate("2023-01-15");
        });
    }

}
