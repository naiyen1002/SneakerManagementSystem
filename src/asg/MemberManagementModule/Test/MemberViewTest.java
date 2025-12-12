package asg.MemberManagementModule.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import asg.MemberManagementModule.Constants.MemberData;
import asg.MemberManagementModule.Model.Member;
import asg.MemberManagementModule.Model.MembershipTier;
import asg.MemberManagementModule.View.MemberView;

public class MemberViewTest {
    private MemberView memberView;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;

    @BeforeEach
    public void setUp() {
        // Capture console output for testing
        System.setOut(new PrintStream(outputStreamCaptor));
        memberView = new MemberView();
    }

    @AfterEach
    public void tearDown() {
        // Restore standard output
        System.setOut(standardOut);
        memberView = null;
    }

    @Test
    @Order(1)
    @DisplayName("Test View - Display Member Main Menu")
    public void testView_DisplayMemberMainMenu() {
        // Act
        memberView.displayMemberMainMenu();

        // Assert
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Member Main Menu"));
    }

    @Test
    @Order(2)
    @DisplayName("Test View - Display All Members - Empty List")
    public void testView_DisplayAllMembers_EmptyList() {
        // Act
        memberView.displayAllMembers(new java.util.ArrayList<>());

        // Assert
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("No members found"));
    }

    @Test
    @Order(3)
    @DisplayName("Test View - Display All Members - Null List")
    public void testView_DisplayAllMembers_NullList() {
        // Act
        memberView.displayAllMembers(null);

        // Assert
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("No members found"));
    }

    @Test
    @Order(4)
    @DisplayName("Test View - Display All Members - With Data")
    public void testView_DisplayAllMembers_WithData() {
        // Arrange
        List<Member> members = MemberData.initiallizeMembersData();

        // Act
        memberView.displayAllMembers(members);

        // Assert
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("M001"));
    }

    @Test
    @Order(5)
    @DisplayName("Test View - Display Member Details - Null Member")
    public void testView_DisplayMemberDetails_NullMember() {
        // Act
        memberView.displayMemberDetails(null);

        // Assert
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("ERROR"));
    }

    @Test
    @Order(6)
    @DisplayName("Test View - Display Member Details - Valid Member")
    public void testView_DisplayMemberDetails_ValidMember() {
        // Arrange
        Member member = MemberData.createTestMember();

        // Act
        memberView.displayMemberDetails(member);

        // Assert
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("T001"));
    }

    @Test
    @Order(7)
    @DisplayName("Test View - Display Membership Report - Empty List")
    public void testView_DisplayMembershipReport_EmptyList() {
        // Arrange
        java.util.Map<MembershipTier, Integer> tierCounts = new java.util.HashMap<>();

        // Act
        memberView.displayMembershipReport(new java.util.ArrayList<>(), tierCounts);

        // Assert
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("No members found"));
    }

    @Test
    @Order(8)
    @DisplayName("Test View - Display Membership Report - Null List")
    public void testView_DisplayMembershipReport_NullList() {
        // Arrange
        java.util.Map<MembershipTier, Integer> tierCounts = new java.util.HashMap<>();

        // Act
        memberView.displayMembershipReport(null, tierCounts);

        // Assert
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("No members found"));
    }

    @Test
    @Order(9)
    @DisplayName("Test View - Show Success Message")
    public void testView_ShowSuccessMessage() {
        // Act
        memberView.showSuccessMessage("Test Success");

        // Assert
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Test Success"));
    }

    @Test
    @Order(10)
    @DisplayName("Test View - Show Error Message")
    public void testView_ShowErrorMessage() {
        // Act
        memberView.showErrorMessage("Test Error");

        // Assert
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Test Error"));
    }

    @Test
    @Order(11)
    @DisplayName("Test View - Show Cancel Message")
    public void testView_ShowCancelMessage() {
        // Act
        memberView.showCancelMessage("Test Cancel");

        // Assert
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Test Cancel"));
    }

    @Test
    @Order(12)
    @DisplayName("Test View - Show Not Found Message")
    public void testView_ShowNotFoundMessage() {
        // Act
        memberView.showNotFoundMessage();

        // Assert
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("ERROR"));
    }

    @Test
    @Order(13)
    @DisplayName("Test View - Display Exit Message")
    public void testView_DisplayExitMessage() {
        // Act
        memberView.displayExitMessage();

        // Assert
        String output = outputStreamCaptor.toString();
        assertTrue(output.length() > 0);
    }

}
