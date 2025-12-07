package asg.MemberManagementModule.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import asg.MemberManagementModule.Constants.MemberConstants;

public class Member {

    private final String id;
    private String name;
    private Gender gender;
    private String icNumber;
    private String contactNumber;
    private LocalDate joinDate;
    private MembershipTier membershipTier;
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private double totalSpending;

    /**
     * Primary Constructor - Full parameter initialization
     * Validates all inputs during construction
     */
    public Member(String id, String name, Gender gender, String icNumber,
            String contactNumber, LocalDate joinDate, MembershipTier membershipTier) {
        this.id = validateId(id);
        this.name = validateName(name);
        this.gender = validateGender(gender);
        this.icNumber = validateIcNumber(icNumber);
        this.contactNumber = validateContactNumber(contactNumber);
        this.joinDate = validateJoinDate(joinDate);
        this.membershipTier = membershipTier != null ? membershipTier : MembershipTier.BASIC;
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

    public double getTotalSpending() {
        return totalSpending;
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

    public void setTotalSpending(double totalSpending) {
        if (totalSpending < 0) {
            throw new IllegalArgumentException(MemberConstants.ERROR_NEGATIVE_SPENDING);
        }
        this.totalSpending = totalSpending;
    }

    // ==================== BUSINESS METHODS ====================

    /**
     * Add spending and automatically update membership tier
     * Follows business rule: tier upgrade based on total spending
     * 
     * @param amount Amount to add
     */
    public void addSpending(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Spending amount cannot be negative");
        }
        this.totalSpending += amount;
        this.membershipTier = MembershipTier.calculateMembershipTier(this.totalSpending);
    }

    /**
     * Calculate discount for a given price based on current tier
     * 
     * @param price Original price
     * @return Discounted price
     */
    public double calculateDiscountedPrice(double price) {
        double discount = membershipTier.calculateDiscount(price);
        return price - discount;
    }

    // Formatting Methods
    /**
     * Format join date for display
     * 
     * @return Formatted date string (dd/MM/yyyy)
     */
    public String getFormattedJoinDate() {
        return joinDate.format(DATE_FORMATTER);
    }

    /**
     * Parse date string to LocalDate
     * 
     * @param dateString Date in format dd/MM/yyyy
     * @return LocalDate object
     * @throws DateTimeParseException if format is invalid
     */
    public static LocalDate parseJoinDate(String dateString) throws DateTimeParseException {
        if (dateString == null || dateString.trim().isEmpty()) {
            throw new IllegalArgumentException("Date string cannot be null or empty");
        }
        return LocalDate.parse(dateString.trim(), DATE_FORMATTER);
    }

    // Validation Methods
    private String validateId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException(MemberConstants.ERROR_EMPTY_ID);
        }
        return id.trim().toUpperCase();
    }

    private String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(MemberConstants.ERROR_EMPTY_NAME);
        }
        if (name.trim().length() < 2) {
            throw new IllegalArgumentException("Name must be at least 2 characters long");
        }
        return name.trim();
    }

    private Gender validateGender(Gender gender) {
        if (gender == null) {
            throw new IllegalArgumentException(MemberConstants.ERROR_EMPTY_GENDER);
        }
        return gender;
    }

    private String validateIcNumber(String icNumber) {
        if (icNumber == null || !icNumber.matches(MemberConstants.PATTERN_IC_NUMBER)) {
            throw new IllegalArgumentException(MemberConstants.ERROR_INVALID_IC);
        }
        return icNumber.trim();
    }

    private String validateContactNumber(String contactNumber) {
        if (contactNumber == null || !contactNumber.matches(MemberConstants.PATTERN_CONTACT)) {
            throw new IllegalArgumentException(MemberConstants.ERROR_INVALID_CONTACT);
        }
        return contactNumber.trim();
    }

    private LocalDate validateJoinDate(LocalDate joinDate) {
        if (joinDate == null) {
            throw new IllegalArgumentException(MemberConstants.ERROR_EMPTY_JOIN_DATE);
        }
        if (joinDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(MemberConstants.ERROR_FUTURE_DATE);
        }
        return joinDate;
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