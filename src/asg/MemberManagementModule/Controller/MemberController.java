package asg.MemberManagementModule.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import asg.MemberManagementModule.Constants.MemberConstants;
import asg.MemberManagementModule.Model.Gender;
import asg.MemberManagementModule.Model.Member;
import asg.MemberManagementModule.Model.MembershipTier;
import asg.MemberManagementModule.View.MemberView;

/**
 * MemberController - Central business logic controller
 * Follows Single Responsibility Principle: Manages member operations
 */
public class MemberController {
    private final List<Member> memberList;
    private final MemberView memberView;

    public MemberController(MemberView memberView) {
        this.memberList = new java.util.ArrayList<>();
        this.memberView = memberView;
    }

    public MemberController(MemberView memberView, List<Member> initialMembers) {
        this.memberView = memberView;
        this.memberList = new ArrayList<>(initialMembers);
    }

    // ==================== DISPLAY OPERATIONS ====================

    /**
     * Display all members in the system
     */
    public void displayAllMembers() {
        memberView.displayAllMembers(memberList);
    }

    /**
     * Display membership tier report
     */
    public void displayMembershipReport() {
        Map<MembershipTier, Integer> tierCounts = calculateTierCounts();
        memberView.displayMembershipReport(memberList, tierCounts);
    }

    /**
     * Calculate tier distribution statistics
     * 
     * @return Map of tier counts
     */
    private Map<MembershipTier, Integer> calculateTierCounts() {
        Map<MembershipTier, Integer> tierCounts = new HashMap<>();

        // Initialize all tiers with 0 count
        for (MembershipTier tier : MembershipTier.values()) {
            tierCounts.put(tier, 0);
        }

        // Count members in each tier
        for (Member member : memberList) {
            MembershipTier tier = member.getMembershipTier();
            tierCounts.put(tier, tierCounts.get(tier) + 1);
        }

        return tierCounts;
    }

    // ==================== INPUT UNIQUE MEMBER ID ====================

    /**
     * Input unique member ID with validation
     * Ensures no duplicate IDs in the system
     * 
     * @return Unique member ID
     */
    private String inputUniqueMemberId() {
        String memberId;
        while (true) {
            memberId = memberView.inputMemberId();

            if (memberId.isEmpty()) {
                memberView.showErrorMessage(MemberConstants.ERROR_EMPTY_ID);
                continue;
            }

            if (isMemberIdExists(memberId)) {
                memberView.showErrorMessage(MemberConstants.ERROR_DUPLICATE_ID + memberId);
                continue;
            }

            break;
        }
        return memberId;
    }

    /**
     * Check if member ID already exists in the system
     * 
     * @param memberId ID to check
     * @return true if exists, false otherwise
     */
    private boolean isMemberIdExists(String memberId) {
        if (memberId == null || memberId.trim().isEmpty()) {
            return false;
        }

        String normalizedId = memberId.trim().toUpperCase();
        return memberList.stream()
                .anyMatch(m -> m.getId().equals(normalizedId));
    }

    // ==================== ADD OPERATION ====================

    /**
     * Add new member to the system with full validation.
     * Implements the complete add workflow with confirmation
     */
    public void addMember() {
        if (!memberView.confirmAction(MemberConstants.MSG_CONFIRM_ADD)) {
            memberView.showCancelMessage(MemberConstants.MSG_CANCEL_ADD);
            return;
        }

        do {
            System.out.println(MemberConstants.HEADER_ADD_MEMBER);

            String memberId = inputUniqueMemberId();
            String name = memberView.inputName();
            Gender gender = memberView.inputGender();
            String icNumber = memberView.inputIcNumber();
            String contactNumber = memberView.inputContactNumber();
            LocalDate joinDate = memberView.inputJoinDate();

            // Create member with BASIC tier as default
            Member newMember = new Member(memberId, name, gender,
                    icNumber, contactNumber, joinDate, MembershipTier.BASIC);

            // Final confirmation before adding
            if (memberView.confirmAction(MemberConstants.MSG_CONFIRM_ADD)) {
                memberList.add(newMember);
                memberView.showSuccessMessage(MemberConstants.SUCCESS_ADD);
            } else {
                memberView.showCancelMessage(MemberConstants.MSG_CANCEL_ADD);
            }

        } while (memberView.askContinue(MemberConstants.MSG_CONTINUE_ADD));
    }

    // ==================== SEARCH OPERATIONS ====================

    /**
     * Search for members based on user-selected criteria
     */
    public void searchMember() {
        do {
            int searchChoice = memberView.displaySearchMenu();
            List<Member> listMemberResults = new ArrayList<>();

            try {
                switch (searchChoice) {
                    case 1:
                        String id = memberView.inputMemberId();
                        Member member = findMemberById(id);
                        if (member != null) {
                            listMemberResults.add(member);
                        }
                        break;

                    case 2:
                        String name = memberView.inputName();
                        listMemberResults = searchMembersByName(name);
                        break;

                    case 3:
                        Gender gender = memberView.inputGender();
                        listMemberResults = searchMembersByGender(gender);
                        break;

                    case 4:
                        String ic = memberView.inputIcNumber();
                        listMemberResults = searchMembersByIcNumber(ic);
                        break;

                    case 5:
                        String contact = memberView.inputContactNumber();
                        listMemberResults = searchMembersByContact(contact);
                        break;

                    case 6:
                        LocalDate date = memberView.inputJoinDate();
                        listMemberResults = searchMembersByJoinDate(date);
                        break;

                    default:
                        memberView.showErrorMessage(MemberConstants.ERROR_INVALID_SEARCH_OPTION);
                        continue;
                }

                // Display results
                if (listMemberResults.isEmpty()) {
                    memberView.showNotFoundMessage();
                } else if (listMemberResults.size() == 1) {
                    memberView.displayMemberDetails(listMemberResults.get(0));
                } else {
                    memberView.displayAllMembers(listMemberResults);
                }

            } catch (Exception e) {
                memberView.showErrorMessage(MemberConstants.MSG_FORMAT_ERROR + e.getMessage());
            }

        } while (memberView.askContinue(MemberConstants.MSG_CONTINUE_SEARCH));
    }

    /**
     * Find member by exact ID
     * 
     * @param id Member ID to search
     * @return Member if found, null otherwise
     */
    public Member findMemberById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return null;
        }

        String normalizedId = id.trim().toUpperCase();
        return memberList.stream()
                .filter(m -> m.getId().equals(normalizedId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Search members by name (partial match, case-insensitive)
     * 
     * @param name Name to search
     * @return List of matching members
     */
    public List<Member> searchMembersByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String searchTerm = name.trim().toLowerCase();
        return memberList.stream()
                .filter(m -> m.getName().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }

    /**
     * Search members by gender
     * 
     * @param gender Gender to search
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
     * @param icNumber IC number to search
     * @return List of matching members
     */
    public List<Member> searchMembersByIcNumber(String icNumber) {
        if (icNumber == null || icNumber.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String searchTerm = icNumber.trim();
        return memberList.stream()
                .filter(m -> m.getIcNumber().equals(searchTerm))
                .collect(Collectors.toList());
    }

    /**
     * Search members by contact number (exact match)
     * 
     * @param contactNumber Contact number to search
     * @return List of matching members
     */
    public List<Member> searchMembersByContact(String contactNumber) {
        if (contactNumber == null || contactNumber.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String searchTerm = contactNumber.trim();
        return memberList.stream()
                .filter(m -> m.getContactNumber().equals(searchTerm))
                .collect(Collectors.toList());
    }

    /**
     * Search members by join date
     * 
     * @param joinDate Join date to search
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
     * @param tier Membership tier to search
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

    // ==================== UPDATE OPERATION ====================

    /**
     * Update member information interactively
     */
    public void updateMember() {
        do {
            String memberId = memberView.inputMemberId();
            Member member = findMemberById(memberId);

            if (member == null) {
                memberView.showNotFoundMessage();
                continue;
            }

            // Display current member details
            memberView.displayMemberDetails(member);

            // Confirm update
            if (!memberView.confirmAction(MemberConstants.MSG_CONFIRM_UPDATE)) {
                memberView.showCancelMessage(MemberConstants.MSG_CANCEL_UPDATE);
                continue;
            }

            // Update loop - allow multiple field updates
            boolean updated = false;
            while (true) {
                int updateChoice = memberView.displayUpdateMenu();

                try {
                    switch (updateChoice) {
                        case 1:
                            String newName = memberView.inputName();
                            member.setName(newName);
                            updated = true;
                            memberView.showSuccessMessage(MemberConstants.SUCCESS_UPDATE);
                            break;

                        case 2:
                            Gender newGender = memberView.inputGender();
                            member.setGender(newGender);
                            updated = true;
                            memberView.showSuccessMessage(MemberConstants.SUCCESS_UPDATE);
                            break;

                        case 3:
                            String newIc = memberView.inputIcNumber();
                            member.setIcNumber(newIc);
                            updated = true;
                            memberView.showSuccessMessage(MemberConstants.SUCCESS_UPDATE);
                            break;

                        case 4:
                            String newContact = memberView.inputContactNumber();
                            member.setContactNumber(newContact);
                            updated = true;
                            memberView.showSuccessMessage(MemberConstants.SUCCESS_UPDATE);
                            break;

                        case 5:
                            if (updated) {
                                memberView.showSuccessMessage(MemberConstants.SUCCESS_UPDATE);
                                memberView.displayMemberDetails(member);
                            } else {
                                memberView.showCancelMessage(MemberConstants.MSG_CANCEL_UPDATE);
                            }
                            break;

                        default:
                            memberView.showErrorMessage(MemberConstants.ERROR_INVALID_UPDATE_OPTION);
                            continue;
                    }

                    if (updateChoice == 5) {
                        break;
                    }

                } catch (IllegalArgumentException e) {
                    memberView.showErrorMessage(MemberConstants.ERROR_CATCH_INVALID + e.getMessage());
                } catch (Exception e) {
                    memberView.showErrorMessage(MemberConstants.ERROR_CATCH_UPDATE + e.getMessage());
                }
            }

        } while (memberView.askContinue(MemberConstants.MSG_CONTINUE_UPDATE));
    }

    // ==================== DELETE OPERATION ====================

    /**
     * Delete member from the system interactively
     */
    public void deleteMember() {
        do {
            String memberId = memberView.inputMemberId();
            Member member = findMemberById(memberId);

            if (member == null) {
                memberView.showNotFoundMessage();
                continue;
            }

            memberView.displayMemberDetails(member);

            // Confirm deletion
            if (memberView.confirmAction(MemberConstants.MSG_CONFIRM_DELETE)) {
                if (memberList.remove(member)) {
                    memberView.showSuccessMessage(MemberConstants.SUCCESS_DELETE);
                } else {
                    memberView.showErrorMessage(MemberConstants.ERROR_CATCH_DELETE);
                }
            } else {
                memberView.showCancelMessage(MemberConstants.MSG_CANCEL_DELETE);
            }

        } while (memberView.askContinue(MemberConstants.MSG_CONTINUE_DELETE));
    }

}
