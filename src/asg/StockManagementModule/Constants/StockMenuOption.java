package asg.StockManagementModule.Constants;

public enum StockMenuOption {
    ADD_ITEM(1, "Add Item"),
    SEARCH_ITEM(2, "Search Item"),
    MODIFY_ITEM(3, "Modify Item"),
    DISPLAY_ITEMS(4, "Display Stock List"),
    DELETE_ITEM(5, "Delete Item"),
    EXIT(6, "Back to Main Menu");

    private final int code;
    private final String desc;

    StockMenuOption(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static StockMenuOption fromCode(int code) {
        for (StockMenuOption opt : values()) {
            if (opt.code == code)
                return opt;
        }
        return null;
    }
}
