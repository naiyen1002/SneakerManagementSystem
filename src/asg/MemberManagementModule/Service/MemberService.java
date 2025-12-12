package asg.MemberManagementModule.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import asg.MemberManagementModule.Model.Gender;
import asg.MemberManagementModule.Model.Member;
import asg.MemberManagementModule.Model.MembershipTier;
import asg.SalesManagementModule.Constants.SalesData;

/**
 * MemberService - Business Logic Layer
 * Acts as a bridge between Controller (UI logic) and Model (Data logic)
 * Handles business rules, data coordination, and validation
 * 
 * Responsibilities:
 * - Member CRUD operations with business rules
 * - Search and filtering logic
 * - Tier calculation and sales integration
 * - Data validation and business rule enforcement
 */
public class MemberService {

    private List<Member> memberList;

    /**
     * Constructor with empty member list
     */
    public MemberService() {
        this.memberList = new ArrayList<>();
    }

    /**
     * Constructor with initial data
     * 
     * @param initialData Initial list of members
     */
    public MemberService(List<Member> initialData) {
        this.memberList = initialData != null ? new ArrayList<>(initialData) : new ArrayList<>();
    }

    // ==================== MEMBER CRUD OPERATIONS ====================

    /**
     * Add a new member with validation
     * Automatically checks for existing sales and calculates tier
     * 
     * @param member Member to add
     * @return true if added successfully, false if duplicate ID exists
     */
    public boolean addMember(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }

        // Business Rule: No duplicate member IDs
        if (isMemberIdExists(member.getId())) {
            return false;
        }

        // Business Rule: Auto-calculate tier from existing sales
        try {
            double totalSpending = SalesData.getTotalSalesByMember(member.getId());
            if (totalSpending > 0) {
                member.setTotalSpending(totalSpending);
                member.setMembershipTier(MembershipTier.calculateMembershipTier(totalSpending));
            }
        } catch (Exception e) {
            // If sales data unavailable, continue with provided tier
        }

        return memberList.add(member);
    }

    /**
     * Update an existing member
     * 
     * @param memberId      ID of member to update
     * @param updatedMember Member with updated information
     * @return true if updated successfully, false if member not found
     */
    public boolean updateMember(String memberId, Member updatedMember) {
        if (memberId == null || updatedMember == null) {
            throw new IllegalArgumentException("Member ID and updated member cannot be null");
        }

        Member existingMember = findMemberById(memberId);
        if (existingMember == null) {
            return false;
        }

        // Update fields (ID cannot be changed)
        existingMember.setName(updatedMember.getName());
        existingMember.setGender(updatedMember.getGender());
        existingMember.setIcNumber(updatedMember.getIcNumber());
        existingMember.setContactNumber(updatedMember.getContactNumber());
        existingMember.setJoinDate(updatedMember.getJoinDate());
        existingMember.setMembershipTier(updatedMember.getMembershipTier());
        existingMember.setTotalSpending(updatedMember.getTotalSpending());

        return true;
    }

    /**
     * Delete a member by ID
     * 
     * @param memberId ID of member to delete
     * @return true if deleted successfully, false if member not found
     */
    public boolean deleteMember(String memberId) {
        if (memberId == null || memberId.trim().isEmpty()) {
            throw new IllegalArgumentException("Member ID cannot be null or empty");
        }

        Member member = findMemberById(memberId);
        if (member == null) {
            return false;
        }

        return memberList.remove(member);
    }

    // ==================== SEARCH OPERATIONS ====================

    /**
     * Find a member by ID (case-insensitive)
     * 
     * @param memberId Member ID to search for
     * @return Member if found, null otherwise
     */
    public Member findMemberById(String memberId) {
        if (memberId == null || memberId.trim().isEmpty()) {
            return null;
        }

        return memberList.stream()
                .filter(m -> m.getId().equalsIgnoreCase(memberId.trim()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Search members by name (partial match, case-insensitive)
     * 
     * @param name Name to search for
     * @return List of matching members
     */
    public List<Member> searchMembersByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return new ArrayList<>();
        }

        return memberList.stream()
                .filter(m -> m.getName().toLowerCase().contains(name.toLowerCase().trim()))
                .collect(Collectors.toList());
    }

    /**
     * Search members by gender
     * 
     * @param gender Gender to search for
     * @return List of matching members
     */
    public List<Member> searchMembersByGender(Gender gender) {
        if (gender == null) {
            return new ArrayList<>();
        }

        return memberList.stream()
                .filter(m -> m.getGender() == gender)
                .collect(Collectors.toList());
    }

    /**
     * Search members by IC number (exact match)
     * 
     * @param icNumber IC number to search for
     * @return List of matching members
     */
    public List<Member> searchMembersByIcNumber(String icNumber) {
        if (icNumber == null || icNumber.trim().isEmpty()) {
            return new ArrayList<>();
        }

        return memberList.stream()
                .filter(m -> m.getIcNumber().equals(icNumber.trim()))
                .collect(Collectors.toList());
    }

    /**
     * Search members by contact number (exact match)
     * 
     * @param contactNumber Contact number to search for
     * @return List of matching members
     */
    public List<Member> searchMembersByContact(String contactNumber) {
        if (contactNumber == null || contactNumber.trim().isEmpty()) {
            return new ArrayList<>();
        }

        return memberList.stream()
                .filter(m -> m.getContactNumber().equals(contactNumber.trim()))
                .collect(Collectors.toList());
    }

    /**
     * Search members by join date (exact match)
     * 
     * @param joinDate Join date to search for
     * @return List of matching members
     */
    public List<Member> searchMembersByJoinDate(LocalDate joinDate) {
        if (joinDate == null) {
            return new ArrayList<>();
        }

        return memberList.stream()
                .filter(m -> m.getJoinDate().equals(joinDate))
                .collect(Collectors.toList());
    }

    /**
     * Search members by membership tier
     * 
     * @param tier Membership tier to search for
     * @return List of matching members
     */
    public List<Member> searchMembersByTier(MembershipTier tier) {
        if (tier == null) {
            return new ArrayList<>();
        }

        return memberList.stream()
                .filter(m -> m.getMembershipTier() == tier)
                .collect(Collectors.toList());
    }

    // ==================== BUSINESS LOGIC OPERATIONS ====================

    /**
     * Check if a member ID already exists
     * 
     * @param memberId Member ID to check
     * @return true if exists, false otherwise
     */
    public boolean isMemberIdExists(String memberId) {
        return findMemberById(memberId) != null;
    }

    /**
     * Get count of members by tier
     * Business logic for generating membership reports
     * 
     * @return Map of tier to count
     */
    public Map<MembershipTier, Integer> getMemberCountByTier() {
        Map<MembershipTier, Integer> tierCounts = new HashMap<>();

        for (MembershipTier tier : MembershipTier.values()) {
            long count = memberList.stream()
                    .filter(m -> m.getMembershipTier() == tier)
                    .count();
            tierCounts.put(tier, (int) count);
        }

        return tierCounts;
    }

    /**
     * Update member spending and recalculate tier
     * Business rule: Tier automatically upgrades based on spending
     * 
     * @param memberId Member ID
     * @param amount   Amount to add to spending
     * @return true if updated successfully
     */
    public boolean addMemberSpending(String memberId, double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Spending amount cannot be negative");
        }

        Member member = findMemberById(memberId);
        if (member == null) {
            return false;
        }

        member.addSpending(amount);
        return true;
    }

    /**
     * Sync member spending from sales data
     * Business rule: Member spending should match their sales total
     * 
     * @param memberId Member ID to sync
     * @return true if synced successfully
     */
    public boolean syncMemberSpendingFromSales(String memberId) {
        Member member = findMemberById(memberId);
        if (member == null) {
            return false;
        }

        try {
            double totalSpending = SalesData.getTotalSalesByMember(memberId);
            member.setTotalSpending(totalSpending);
            member.setMembershipTier(MembershipTier.calculateMembershipTier(totalSpending));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Sync all members' spending from sales data
     * Useful for batch updates or data reconciliation
     * 
     * @return Number of members successfully synced
     */
    public int syncAllMembersSpendingFromSales() {
        int syncedCount = 0;
        for (Member member : memberList) {
            if (syncMemberSpendingFromSales(member.getId())) {
                syncedCount++;
            }
        }
        return syncedCount;
    }

    // ==================== DATA ACCESS OPERATIONS ====================

    /**
     * Get all members
     * 
     * @return List of all members
     */
    public List<Member> getAllMembers() {
        return new ArrayList<>(memberList);
    }

    /**
     * Get total member count
     * 
     * @return Number of members
     */
    public int getMemberCount() {
        return memberList.size();
    }

    /**
     * Clear all members
     * Use with caution - typically for testing
     */
    public void clearAllMembers() {
        memberList.clear();
    }

    /**
     * Replace entire member list
     * Use with caution - typically for data import
     * 
     * @param newMemberList New list of members
     */
    public void setMemberList(List<Member> newMemberList) {
        this.memberList = newMemberList != null ? new ArrayList<>(newMemberList) : new ArrayList<>();
    }
}
