package asg.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class Member {

    private final String id;
    private String name;
    private Gender gender;
    private String icNumber;
    private String contactNumber;
    private LocalDate joinDate;
    private MembershipTier membershipTier;
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Member(String id, String name, Gender gender, String icNumber, String contactNumber, LocalDate joinDate,
            MembershipTier membershipTier) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.icNumber = icNumber;
        this.contactNumber = contactNumber;
        this.joinDate = joinDate;
        this.membershipTier = membershipTier.BASIC;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public String getIcNumber() {
        return icNumber;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public MembershipTier getMembershipTier() {
        return membershipTier;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setIcNumber(String icNumber) {
        this.icNumber = icNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setMembershipTier(MembershipTier membershipTier) {
        this.membershipTier = membershipTier;
    }

    // Format for OUTPUT
    public String getFormattedJoinDate() {
        return joinDate.format(DATE_FORMATTER); 
    }

    // Format for INPUT
    public static LocalDate parseJoinDate(String dateString) throws DateTimeParseException {
        return LocalDate.parse(dateString, DATE_FORMATTER);
    }

    // Validation Methods
    private String validateId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Member ID cannot be empty");
        }
        return id.trim();
    }

    private String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        return name.trim();
    }

    private String validateGender(String gender) {
        if (gender == null || gender.trim().isEmpty()) {
            throw new IllegalArgumentException("Gender cannot be empty");
        }
        return gender.trim();
    }

    private String validateIcNumber(String icNumber) {
        if (icNumber == null || !icNumber.matches("\\d{6}-\\d{2}-\\d{4}")) {
            throw new IllegalArgumentException("IC Number must be in format: xxxxxx-xx-xxxx");
        }
        return icNumber;
    }

    private String validateContactNumber(String contactNumber) {
        if (contactNumber == null || !contactNumber.matches("\\d{9,11}")) {
            throw new IllegalArgumentException("Contact number must be 9-11 digits");
        }
        return contactNumber;
    }

    /**
     * The equals() method is used to compare two objects for equality based on
     * their content/values, not their memory location.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}