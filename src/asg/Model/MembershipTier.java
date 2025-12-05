package asg.Model;

/**
 * MembershipTier Enum
 */
public enum MembershipTier {
    GOLDEN("Golden Membership", 3000.0),
    SILVER("Silver Membership", 2000.0),
    BRONZE("Bronze Membership", 1000.0),
    BASIC("Basic Membership", 0.0);

    private final String displayName;
    private final double amtRequired;

    MembershipTier(String displayName, double amtRequired) {
        this.displayName = displayName;
        this.amtRequired = amtRequired;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getAmtRequired() {
        return amtRequired;
    }

    /**
     * Calculate membership tier based on total spending
     */
    public static MembershipTier calMembershipTier(double totalSpending) {
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
}
