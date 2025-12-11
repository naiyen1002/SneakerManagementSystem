package asg.StaffManagementModule.Constants;

public enum StaffMenuOptions {

    // Main menu options
    DISPLAY_STAFF(1, "Display Staff Details", MenuType.MAIN),
    ADD_STAFF(2, "Add New Staff", MenuType.MAIN),
    UPDATE_STAFF(3, "Modify Current Staff Info", MenuType.MAIN),
    DELETE_STAFF(4, "Delete Staff", MenuType.MAIN),
    SEARCH_STAFF(5, "Search Staff", MenuType.MAIN),
    SALARY_REPORT(6, "Report of Staff Overview", MenuType.MAIN),
    EXIT(0, "Exit Staff Module", MenuType.MAIN),

    // Modify menu options
    MODIFY_NAME(1, "Modify Name", MenuType.MODIFY),
    MODIFY_GENDER(2, "Modify Gender", MenuType.MODIFY),
    MODIFY_POSITION(3, "Modify Position", MenuType.MODIFY),
    MODIFY_SALARY(4, "Modify Salary", MenuType.MODIFY),
    MODIFY_DEPARTMENT(5, "Modify Department", MenuType.MODIFY),
    FINISH_MODIFICATION(0, "Finish Modification or Exit", MenuType.MODIFY),

    // Report menu options
    OVERALL_REPORT(1, "Overall Report (All Staff)", MenuType.REPORT),
    DEPARTMENT_REPORT(2, "Department Report", MenuType.REPORT),
    BACK_TO_MENU(0, "Back to Staff Menu", MenuType.REPORT);

    // Contains different menu types
    public enum MenuType {
        MAIN,
        MODIFY,
        REPORT
    }

    private final int code;
    private final String description;
    private final MenuType menuType;

    StaffMenuOptions(int code, String description, MenuType menuType) {
        this.code = code;
        this.description = description;
        this.menuType = menuType;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public MenuType getMenuType() {
        return menuType;
    }

    // Get main menu option
    public static StaffMenuOptions fromCode(int code) {
        for (StaffMenuOptions option : StaffMenuOptions.values()) {
            if (option.getCode() == code && option.getMenuType() == MenuType.MAIN) {
                return option;
            }
        }
        return null;
    }

    // Get modify menu option
    public static StaffMenuOptions fromModifyCode(int code) {
        for (StaffMenuOptions option : StaffMenuOptions.values()) {
            if (option.getCode() == code && option.getMenuType() == MenuType.MODIFY) {
                return option;
            }
        }
        return null;
    }

    // Get report menu option 
    public static StaffMenuOptions fromReportCode(int code) {
        for (StaffMenuOptions option : StaffMenuOptions.values()) {
            if (option.getCode() == code && option.getMenuType() == MenuType.REPORT) {
                return option;
            }
        }
        return null;
    }

    // Get all main menu options
    public static StaffMenuOptions[] getMainMenuOptions() {
        return java.util.Arrays.stream(StaffMenuOptions.values())
                .filter(opt -> opt.getMenuType() == MenuType.MAIN)
                .toArray(StaffMenuOptions[]::new);
    }

    // Get all modify menu options
    public static StaffMenuOptions[] getModifyMenuOptions() {
        return java.util.Arrays.stream(StaffMenuOptions.values())
                .filter(opt -> opt.getMenuType() == MenuType.MODIFY)
                .toArray(StaffMenuOptions[]::new);
    }

    // Get all report menu options
    public static StaffMenuOptions[] getReportMenuOptions() {
        return java.util.Arrays.stream(StaffMenuOptions.values())
                .filter(opt -> opt.getMenuType() == MenuType.REPORT)
                .toArray(StaffMenuOptions[]::new);
    }
}
