package asg.MemberManagementModule.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import asg.MemberManagementModule.Constants.MemberData;
import asg.MemberManagementModule.Constants.MemberOptions;
import asg.MemberManagementModule.Model.Gender;
import asg.MemberManagementModule.Model.Member;
import asg.MemberManagementModule.Model.MembershipTier;

public class EnumTest {

    @Test
    @Order(1)
    @DisplayName("Test Gender - From String Valid")
    public void testGender_FromString_Valid() {
        // Act & Assert
        assertEquals(Gender.MALE, Gender.fromString("Male"));
        assertEquals(Gender.FEMALE, Gender.fromString("female"));
        assertEquals(Gender.OTHER, Gender.fromString("OTHER"));
        assertEquals(Gender.MALE, Gender.fromString("MALE"));
    }

    @Test
    @Order(2)
    @DisplayName("Test Gender - From String Invalid")
    public void testGender_FromString_Invalid() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            Gender.fromString("Invalid");
        });
    }

    @Test
    @Order(3)
    @DisplayName("Test Gender - From String Null")
    public void testGender_FromString_Null() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            Gender.fromString(null);
        });
    }

    @Test
    @Order(4)
    @DisplayName("Test Gender - Display Name")
    public void testGender_DisplayName() {
        // Assert
        assertEquals("Male", Gender.MALE.getDisplayName());
        assertEquals("Female", Gender.FEMALE.getDisplayName());
        assertEquals("Other", Gender.OTHER.getDisplayName());
    }

    // ==================== MEMBERSHIP TIER TESTS ====================

    @Test
    @Order(5)
    @DisplayName("Test MembershipTier - Calculate Tier Boundary and above")
    public void testMembershipTier_CalculateTier_Boundary() {
        assertEquals(MembershipTier.BASIC, MembershipTier.calculateMembershipTier(0.0));
        assertEquals(MembershipTier.BRONZE, MembershipTier.calculateMembershipTier(1500.0));
        assertEquals(MembershipTier.SILVER, MembershipTier.calculateMembershipTier(2500.0));
        assertEquals(MembershipTier.GOLDEN, MembershipTier.calculateMembershipTier(3500.0));

        // Assert exact boundaries
        assertEquals(MembershipTier.BASIC, MembershipTier.calculateMembershipTier(0.0));
        assertEquals(MembershipTier.BRONZE, MembershipTier.calculateMembershipTier(1000.0));
        assertEquals(MembershipTier.SILVER, MembershipTier.calculateMembershipTier(2000.0));
        assertEquals(MembershipTier.GOLDEN, MembershipTier.calculateMembershipTier(3000.0));
    }

    @Test
    @Order(6)
    @DisplayName("Test MembershipTier - Calculate Tier Negative")
    public void testMembershipTier_CalculateTier_Negative() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            MembershipTier.calculateMembershipTier(-100.0);
        });
    }

    @Test
    @Order(7)
    @DisplayName("Test MembershipTier - Calculate Discount")
    public void testMembershipTier_CalculateDiscount() {
        // Assert
        assertEquals(150.0, MembershipTier.GOLDEN.calculateDiscount(1000.0), 0.01);
        assertEquals(100.0, MembershipTier.SILVER.calculateDiscount(1000.0), 0.01);
        assertEquals(50.0, MembershipTier.BRONZE.calculateDiscount(1000.0), 0.01);
        assertEquals(0.0, MembershipTier.BASIC.calculateDiscount(1000.0), 0.01);
    }

    @Test
    @Order(8)
    @DisplayName("Test MembershipTier - Calculate Discount Negative Price")
    public void testMembershipTier_CalculateDiscount_NegativePrice() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            MembershipTier.GOLDEN.calculateDiscount(-100.0);
        });
    }

    @Test
    @Order(9)
    @DisplayName("Test MembershipTier - Getters")
    public void testMembershipTier_Getters() {
        // Assert
        assertEquals("Golden Membership", MembershipTier.GOLDEN.getDisplayName());
        assertEquals(3000.0, MembershipTier.GOLDEN.getAmtRequired());
        assertEquals(0.15, MembershipTier.GOLDEN.getDiscountRate());
    }

    // ==================== MEMBER DATA TESTS ====================

    @Test
    @Order(10)
    @DisplayName("Test MemberData - Initialize Sample Data")
    public void testMemberData_InitializeSampleData() {
        // Act
        List<Member> data = MemberData.initiallizeMembersData();

        // Assert
        assertNotNull(data);
        assertEquals(5, data.size());
    }

    @Test
    @Order(11)
    @DisplayName("Test MemberData - Create Test Member")
    public void testMemberData_CreateTestMember() {
        // Act
        Member member = MemberData.createTestMember();

        // Assert
        assertNotNull(member);
        assertEquals("T001", member.getId());
    }

    @Test
    @Order(12)
    @DisplayName("Test MemberData - Get Members By Tier")
    public void testMemberData_GetMembersByTier() {
        // Act
        List<Member> goldenMembers = MemberData.getMembersByTier(MembershipTier.GOLDEN);
        List<Member> basicMembers = MemberData.getMembersByTier(MembershipTier.BASIC);

        // Assert
        assertFalse(goldenMembers.isEmpty());
        assertFalse(basicMembers.isEmpty());
    }

    // ==================== MEMBER OPTIONS ENUM TESTS ====================

    @Test
    @Order(13)
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
    @Order(14)
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
    @Order(15)
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
    @Order(16)
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
    @Order(17)
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
    @Order(18)
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
    @Order(19)
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
    @Order(20)
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
    @Order(21)
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
    @Order(22)
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
    @Order(23)
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
    @Order(24)
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

}
