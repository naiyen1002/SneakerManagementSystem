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

        staffList.add(new Staff("ST001", "JiaHui", "Male", "Manager", 5000.0, "HR"));
        staffList.add(new Staff("ST002", "NaiYen", "Female", "Engineer", 4800.0, "IT"));
        staffList.add(new Staff("ST003", "Edwin", "Male", "Accountant", 4800.0, "Finance"));
        staffList.add(new Staff("ST004", "July", "Female", "Creator", 4800.0, "Multimedia"));
        staffList.add(new Staff("ST005", "ShiYee", "Female", "Designer", 4800.0, "Design"));

        return staffList;
    }

    public static final double MIN_SALARY = 0.0;

}
