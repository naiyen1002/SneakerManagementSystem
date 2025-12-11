package asg.MemberManagementModule;

import asg.MemberManagementModule.Constants.MemberData;
import asg.MemberManagementModule.Constants.MemberOptions;
import asg.MemberManagementModule.Controller.MemberController;
import asg.MemberManagementModule.View.MemberView;

public class MemberManagementApp {

    public static void main(String[] args) {
        MemberView memberView = new MemberView();
        MemberController memberController = new MemberController(memberView, MemberData.initiallizeMembersData());

        boolean running = true;

        // Main application loop
        while (running) {
            memberView.displayMemberMainMenu();
            int choice = memberView.getMenuChoice();
            MemberOptions menuOption = MemberOptions.fromMainMenuValue(choice);

            switch (menuOption) {
                case DISPLAY_ALL_MEMBERS:
                    memberController.displayAllMembers();
                    break;
                case ADD_MEMBER:
                    memberController.addMember();
                    break;
                case SEARCH_MEMBER:
                    memberController.searchMember();
                    break;
                case UPDATE_MEMBER:
                    memberController.updateMember();
                    break;
                case DELETE_MEMBER:
                    memberController.deleteMember();
                    break;
                case SHOW_MEMBERSHIP_REPORT:
                    memberController.displayMembershipReport();
                    break;
                case EXIT:
                    running = false;
                    break;
                default:
                    break;
            }
        }

        // Cleanup and exit
        memberView.displayExitMessage();
    }

}
