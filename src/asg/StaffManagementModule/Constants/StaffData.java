package asg.StaffManagementModule.Constants;

import java.util.ArrayList;
import java.util.List;

import asg.StaffManagementModule.Model.Staff;

public class StaffData {

    /**
     * Staff data
     * 
     * @return List of staff members
     */
    public static List<Staff> getStaffData() {
        List<Staff> staffList = new ArrayList<>();

        staffList.add(new Staff("1", "JiaHui", "male", "Manager", 5000.0, "HR"));
        staffList.add(new Staff("2", "NaiYen", "female", "Engineer", 4800.0, "IT"));
        staffList.add(new Staff("3", "Edwin", "male", "Accountant", 4800.0, "Finance"));
        staffList.add(new Staff("4", "July", "female", "Creator", 4800.0, "Multimedia"));
        staffList.add(new Staff("5", "ShiYee", "female", "Designer", 4800.0, "Design"));

        return staffList;
    }

    /**
     * Minimum salary for staff members
     */
    public static final double MIN_SALARY = 0.0;

}
