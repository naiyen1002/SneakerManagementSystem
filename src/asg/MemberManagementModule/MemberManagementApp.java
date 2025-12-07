package asg.MemberManagementModule;

import asg.MemberManagementModule.Constants.MemberData;
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

            switch (choice) {
                case 1:
                    memberController.displayAllMembers();
                    break;
                case 2:
                    memberController.addMember();
                    break;
                case 3:
                    memberController.searchMember();
                    break;
                case 4:
                    memberController.updateMember();
                    break;
                case 5:
                    memberController.deleteMember();
                    break;
                case 6:
                    memberController.displayMembershipReport();
                    break;
                case 7:
                    running = false;
                    break;
            }
        }

        // Cleanup and exit
        memberView.displayExitMessage();
    }

}
