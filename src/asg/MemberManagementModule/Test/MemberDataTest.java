package asg.MemberManagementModule.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import asg.MemberManagementModule.Constants.MemberData;
import asg.MemberManagementModule.Model.Member;
import asg.MemberManagementModule.Model.MembershipTier;

public class MemberDataTest {
    @Test
    @Order(1)
    @DisplayName("Test MemberData - Initialize Sample Data")
    public void testMemberData_InitializeSampleData() {
        // Act
        List<Member> data = MemberData.initiallizeMembersData();

        // Assert
        assertNotNull(data);
        assertEquals(5, data.size());
    }

    @Test
    @Order(2)
    @DisplayName("Test MemberData - Create Test Member")
    public void testMemberData_CreateTestMember() {
        // Act
        Member member = MemberData.createTestMember();

        // Assert
        assertNotNull(member);
        assertEquals("T001", member.getId());
    }

    @Test
    @Order(3)
    @DisplayName("Test MemberData - Get Members By Tier")
    public void testMemberData_GetMembersByTier() {
        // Act
        List<Member> goldenMembers = MemberData.getMembersByTier(MembershipTier.GOLDEN);
        List<Member> basicMembers = MemberData.getMembersByTier(MembershipTier.BASIC);

        // Assert
        assertFalse(goldenMembers.isEmpty());
        assertFalse(basicMembers.isEmpty());
    }

}
