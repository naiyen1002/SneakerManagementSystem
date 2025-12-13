package asg.MemberManagementModule.Constants;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import asg.MemberManagementModule.Model.Gender;
import asg.MemberManagementModule.Model.Member;
import asg.MemberManagementModule.Model.MembershipTier;
import asg.SalesManagementModule.Constants.SalesData;

public final class MemberData {
    public static List<Member> initiallizeMembersData() {
        List<Member> memberList = new ArrayList<>();

        // Member 1 - Edwin Soo
        // Tier will be calculated based on total spending from sales
        Member Member1 = new Member("M001", "Edwin Soo", Gender.MALE, "950101-01-1234", "123456789",
                LocalDate.of(2023, 1, 15), MembershipTier.BASIC);

        double spending1 = SalesData.getTotalSalesByMember("M001");
        Member1.setTotalSpending(spending1);
        Member1.setMembershipTier(MembershipTier.calculateMembershipTier(spending1));
        memberList.add(Member1);

        // Member 2 - Juju
        // Tier will be calculated based on total spending from sales
        Member Member2 = new Member("M002", "Juju", Gender.FEMALE, "980505-02-5678", "987654321",
                LocalDate.of(2023, 3, 20), MembershipTier.BASIC);
        double spending2 = SalesData.getTotalSalesByMember("M002");
        Member2.setTotalSpending(spending2);
        Member2.setMembershipTier(MembershipTier.calculateMembershipTier(spending2));
        memberList.add(Member2);

        // Member 3 - Shiyee
        // Tier will be calculated based on total spending from sales
        Member Member3 = new Member("M003", "Shiyee", Gender.OTHER, "000808-03-9012", "112233445",
                LocalDate.of(2024, 6, 10), MembershipTier.BASIC);
        double spending3 = SalesData.getTotalSalesByMember("M003");
        Member3.setTotalSpending(spending3);
        Member3.setMembershipTier(MembershipTier.calculateMembershipTier(spending3));
        memberList.add(Member3);

        // Member 4 - Jiahui
        // Tier will be calculated based on total spending from sales
        Member Member4 = new Member("M004", "Jiahui", Gender.FEMALE, "020202-04-3456", "555666777",
                LocalDate.of(2024, 11, 5), MembershipTier.BASIC);
        double spending4 = SalesData.getTotalSalesByMember("M004");
        Member4.setTotalSpending(spending4);
        Member4.setMembershipTier(MembershipTier.calculateMembershipTier(spending4));
        memberList.add(Member4);

        // Member 5 - Naiyen
        // Tier will be calculated based on total spending from sales
        Member Member5 = new Member("M005", "Naiyen", Gender.MALE, "990315-05-7890", "666777888",
                LocalDate.of(2024, 12, 1), MembershipTier.BASIC);
        double spending5 = SalesData.getTotalSalesByMember("M005");
        Member5.setTotalSpending(spending5);
        Member5.setMembershipTier(MembershipTier.calculateMembershipTier(spending5));
        memberList.add(Member5);

        return memberList;
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
