package asg.MemberManagementModule.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import asg.MemberManagementModule.Constants.MemberConstants;
import asg.MemberManagementModule.Constants.MemberOptions;
import asg.MemberManagementModule.Model.Gender;
import asg.MemberManagementModule.Model.Member;
import asg.MemberManagementModule.Model.MembershipTier;
import asg.MemberManagementModule.Service.MemberService;
import asg.MemberManagementModule.View.MemberView;
import asg.SalesManagementModule.Constants.SalesData;

/**
 * MemberController - UI Logic Controller
 * Handles user interaction and delegates business logic to MemberService
 * Follows MVC pattern with Service Layer
 */
public class MemberController {
    private final MemberService memberService;
    private final MemberView memberView;

    public MemberController(MemberView memberView) {
        this.memberView = memberView;
        this.memberService = new MemberService();
    }

    public MemberController(MemberView memberView, List<Member> initialMembers) {
        this.memberView = memberView;
        this.memberService = new MemberService(initialMembers);
    }

    // ==================== DISPLAY OPERATIONS ====================

    /**
     * Display all members in the system
     */
    public void displayAllMembers() {
        memberView.displayAllMembers(memberService.getAllMembers());
    }

    /**
     * Display membership tier report
     */
    public void displayMembershipReport() {
        Map<MembershipTier, Integer> tierCounts = memberService.getMemberCountByTier();
        memberView.displayMembershipReport(memberService.getAllMembers(), tierCounts);
    }

    // ==================== INPUT UNIQUE MEMBER ID ====================

    /**
     * Input unique member ID with validation
     * Ensures no duplicate IDs in the system
     * 
     * @return Unique member ID
     */
    public String inputUniqueMemberId() {
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
    public boolean isMemberIdExists(String memberId) {
        return memberService.isMemberIdExists(memberId);
    }

    // ==================== ADD OPERATION ====================

    /**
     * Add new member to the system with full validation.
     * Implements the complete add workflow with confirmation
     * Automatically checks for existing sales and calculates tier
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

            // Check for existing sales and calculate tier
            try {
                double totalSpending = SalesData.getTotalSalesByMember(memberId);
                if (totalSpending > 0) {
                    newMember.setTotalSpending(totalSpending);
                    newMember.setMembershipTier(MembershipTier.calculateMembershipTier(totalSpending));
                    System.out.println(String.format(MemberConstants.MSG_SALES_FOUND, totalSpending));
                    System.out.println(String.format(MemberConstants.MSG_TIER_ASSIGNED,
                            newMember.getMembershipTier().getDisplayName()));
                }
            } catch (Exception e) {
                // If SalesData is not available, continue with BASIC tier
                System.out.println(MemberConstants.MSG_NO_SALES_FOUND);
            }

            // Final confirmation before adding
            if (memberView.confirmAction(MemberConstants.MSG_CONFIRM_ADD)) {
                memberService.addMember(newMember);
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
                MemberOptions searchOption = MemberOptions.fromSearchMenuValue(searchChoice);

                switch (searchOption) {
                    case SEARCH_BY_ID:
                        String id = memberView.inputMemberId();
                        Member member = findMemberById(id);
                        if (member != null) {
                            listMemberResults.add(member);
                        }
                        break;

                    case SEARCH_BY_NAME:
                        String name = memberView.inputName();
                        listMemberResults = searchMembersByName(name);
                        break;

                    case SEARCH_BY_GENDER:
                        Gender gender = memberView.inputGender();
                        listMemberResults = searchMembersByGender(gender);
                        break;

                    case SEARCH_BY_IC:
                        String ic = memberView.inputIcNumber();
                        listMemberResults = searchMembersByIcNumber(ic);
                        break;

                    case SEARCH_BY_CONTACT:
                        String contact = memberView.inputContactNumber();
                        listMemberResults = searchMembersByContact(contact);
                        break;

                    case SEARCH_BY_DATE:
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
        return memberService.findMemberById(id);
    }

    /**
     * Search members by name (partial match, case-insensitive)
     * 
     * @param name Name to search
     * @return List of matching members
     */
    public List<Member> searchMembersByName(String name) {
        return memberService.searchMembersByName(name);
    }

    /**
     * Search members by gender
     * 
     * @param gender Gender to search
     * @return List of matching members
     */
    public List<Member> searchMembersByGender(Gender gender) {
        return memberService.searchMembersByGender(gender);
    }

    /**
     * Search members by IC number (exact match)
     * 
     * @param icNumber IC number to search
     * @return List of matching members
     */
    public List<Member> searchMembersByIcNumber(String icNumber) {
        return memberService.searchMembersByIcNumber(icNumber);
    }

    /**
     * Search members by contact number (exact match)
     * 
     * @param contactNumber Contact number to search
     * @return List of matching members
     */
    public List<Member> searchMembersByContact(String contactNumber) {
        return memberService.searchMembersByContact(contactNumber);
    }

    /**
     * Search members by join date
     * 
     * @param joinDate Join date to search
     * @return List of matching members
     */
    public List<Member> searchMembersByJoinDate(LocalDate joinDate) {
        return memberService.searchMembersByJoinDate(joinDate);
    }

    /**
     * Search members by membership tier
     * 
     * @param tier Membership tier to search
     * @return List of matching members
     */
    public List<Member> searchMembersByTier(MembershipTier tier) {
        return memberService.searchMembersByTier(tier);
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
                    MemberOptions updateOption = MemberOptions.fromUpdateMenuValue(updateChoice);

                    switch (updateOption) {
                        case UPDATE_NAME:
                            String newName = memberView.inputName();
                            member.setName(newName);
                            updated = true;
                            memberView.showSuccessMessage(MemberConstants.SUCCESS_UPDATE);
                            break;

                        case UPDATE_GENDER:
                            Gender newGender = memberView.inputGender();
                            member.setGender(newGender);
                            updated = true;
                            memberView.showSuccessMessage(MemberConstants.SUCCESS_UPDATE);
                            break;

                        case UPDATE_IC:
                            String newIc = memberView.inputIcNumber();
                            member.setIcNumber(newIc);
                            updated = true;
                            memberView.showSuccessMessage(MemberConstants.SUCCESS_UPDATE);
                            break;

                        case UPDATE_CONTACT:
                            String newContact = memberView.inputContactNumber();
                            member.setContactNumber(newContact);
                            updated = true;
                            memberView.showSuccessMessage(MemberConstants.SUCCESS_UPDATE);
                            break;

                        case UPDATE_FINISH:
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

                    if (updateOption == MemberOptions.UPDATE_FINISH) {
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

    /**
     * Update member for testing
     * 
     * @param memberId      ID of member to update
     * @param updatedMember Member with updated information
     * @return true if updated successfully, false if member not found
     */
    public boolean updateMemberDirect(String memberId, Member updatedMember) {
        if (memberId == null || updatedMember == null) {
            throw new IllegalArgumentException("Member ID and updated member cannot be null");
        }

        Member existing = findMemberById(memberId);
        if (existing == null) {
            return false;
        }

        // Update mutable fields
        existing.setName(updatedMember.getName());
        existing.setGender(updatedMember.getGender());
        existing.setIcNumber(updatedMember.getIcNumber());
        existing.setContactNumber(updatedMember.getContactNumber());
        existing.setJoinDate(updatedMember.getJoinDate());
        existing.setMembershipTier(updatedMember.getMembershipTier());
        existing.setTotalSpending(updatedMember.getTotalSpending());

        return true;
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
                if (memberService.deleteMember(member.getId())) {
                    memberView.showSuccessMessage(MemberConstants.SUCCESS_DELETE);
                } else {
                    memberView.showErrorMessage(MemberConstants.ERROR_CATCH_DELETE);
                }
            } else {
                memberView.showCancelMessage(MemberConstants.MSG_CANCEL_DELETE);
            }

        } while (memberView.askContinue(MemberConstants.MSG_CONTINUE_DELETE));
    }

    /**
     * Delete member by ID programmatically (for testing)
     * 
     * @param memberId ID of member to delete
     * @return true if deleted successfully, false if member not found
     */
    public boolean deleteMemberById(String memberId) {
        return memberService.deleteMember(memberId);
    }

    /**
     * Get total number of members
     * 
     * @return Member count
     */
    public int getMemberCount() {
        return memberService.getMemberCount();
    }

}
