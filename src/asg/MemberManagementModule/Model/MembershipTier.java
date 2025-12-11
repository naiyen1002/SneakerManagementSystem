package asg.MemberManagementModule.Model;

/**
 * MembershipTier Enum
 */
public enum MembershipTier {
    GOLDEN("Golden Membership", 3000.0, 0.15),
    SILVER("Silver Membership", 2000.0, 0.10),
    BRONZE("Bronze Membership", 1000.0, 0.05),
    BASIC("Basic Membership", 0.0, 0.0);

    private final String displayName;
    private final double amtRequired;
    private final double discountRate;

    MembershipTier(String displayName, double amtRequired, double discountRate) {
        this.displayName = displayName;
        this.amtRequired = amtRequired;
        this.discountRate = discountRate;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getAmtRequired() {
        return amtRequired;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    /**
     * Calculate membership tier based on total spending
     * Business rule: Higher spending leads to better tier
     * 
     * @param totalSpending The total amount spent by member
     * @return Appropriate MembershipTier
     */
    public static MembershipTier calculateMembershipTier(double totalSpending) {
        if (totalSpending < 0) {
            throw new IllegalArgumentException("Total spending cannot be negative");
        }

        if (totalSpending >= GOLDEN.amtRequired) {
            return GOLDEN;
        } else if (totalSpending >= SILVER.amtRequired) {
            return SILVER;
        } else if (totalSpending >= BRONZE.amtRequired) {
            return BRONZE;
        } else {
            return BASIC;
        }
    }

    /**
     * Get discount amount for a given price
     * 
     * @param price Original price
     * @return Discount amount
     */
    public double calculateDiscount(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        return price * discountRate;
    }
}
