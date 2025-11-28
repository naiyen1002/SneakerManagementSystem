package asg;


public class MemberDetails extends SMdetails {

    private String icNumber;
    private int memberContact;
    private dateJoin dateJoin;

    public MemberDetails(String id, String name, String gender, String icNumber, int memberContact, dateJoin dateJoin) {
        super(id, name, gender);
        this.icNumber = icNumber;
        this.memberContact = memberContact;
        this.dateJoin = dateJoin;
    }

    public String getIcNumber() {
        return icNumber;
    }

    public void setIcNumber(String icNumber) {
        this.icNumber = icNumber;
    }

    public int getMemberContact() {
        return memberContact;
    }

    public void setMemberContact(int memberContact) {
        this.memberContact = memberContact;
    }

    public dateJoin getDateJoin() {
        return dateJoin;
    }

    public void setDateJoin(dateJoin dateJoin) {
        this.dateJoin = dateJoin;
    }


    @Override
    public String toString() {
        return "MemberDetails{" + "icNumber=" + icNumber + ", memberContact=" + memberContact + ", dateJoin=" + dateJoin + '}';
    }

}
