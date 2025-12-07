package asg.MemberManagementModule.Constants;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import asg.MemberManagementModule.Model.Gender;
import asg.MemberManagementModule.Model.Member;
import asg.MemberManagementModule.Model.MembershipTier;

public final class MemberData {
    public static List<Member> initiallizeMembersData() {
        List<Member> memberList = new ArrayList<>();

        // Golden Member - High spending customer
        Member golden = new Member(
                "M001",
                "John Smith",
                Gender.MALE,
                "950101-01-1234",
                "123456789",
                LocalDate.of(2023, 1, 15),
                MembershipTier.GOLDEN);
        golden.setTotalSpending(3500.00);
        memberList.add(golden);

        // Silver Member - Medium spending customer
        Member silver = new Member(
                "M002",
                "Jane Doe",
                Gender.FEMALE,
                "980505-02-5678",
                "987654321",
                LocalDate.of(2023, 3, 20),
                MembershipTier.SILVER);
        silver.setTotalSpending(2300.00);
        memberList.add(silver);

        // Bronze Member - Regular customer
        Member bronze = new Member(
                "M003",
                "Alex Wong",
                Gender.OTHER,
                "000808-03-9012",
                "112233445",
                LocalDate.of(2024, 6, 10),
                MembershipTier.BRONZE);
        bronze.setTotalSpending(1500.00);
        memberList.add(bronze);

        // Basic Member - New customer
        Member basic = new Member(
                "M004",
                "Sarah Lee",
                Gender.FEMALE,
                "020202-04-3456",
                "555666777",
                LocalDate.of(2024, 11, 5),
                MembershipTier.BASIC);
        basic.setTotalSpending(500.00);
        memberList.add(basic);

        // Additional Basic Member
        Member basic2 = new Member(
                "M005",
                "Michael Chen",
                Gender.MALE,
                "990315-05-7890",
                "666777888",
                LocalDate.of(2024, 12, 1),
                MembershipTier.BASIC);
        basic2.setTotalSpending(200.00);
        memberList.add(basic2);

        return memberList;
    }

    public static List<Member> getEmptyList() {
        return new ArrayList<>();
    }

    /**
     * Create a single test member for unit testing
     * 
     * @return A basic test member
     */
    public static Member createTestMember() {
        return new Member(
                "T001",
                "Test User",
                Gender.MALE,
                "900101-01-0001",
                "111222333",
                LocalDate.of(2024, 1, 1),
                MembershipTier.BASIC);
    }

    /**
     * Get member data by tier for filtered views
     * 
     * @param tier The membership tier to filter by
     * @return List of members in the specified tier
     */
    public static List<Member> getMembersByTier(MembershipTier tier) {
        List<Member> allMembers = initiallizeMembersData();
        List<Member> filtered = new ArrayList<>();

        for (Member member : allMembers) {
            if (member.getMembershipTier() == tier) {
                filtered.add(member);
            }
        }

        return filtered;
    }

    // Private constructor to prevent instantiation
    private MemberData() {
        throw new AssertionError("Cannot instantiate data class");
    }

}
