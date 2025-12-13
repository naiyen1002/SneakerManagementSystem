package asg.SalesManagementModule.Constants;

import java.util.ArrayList;
import java.util.List;

import asg.SalesManagementModule.Model.SalesItem;
import asg.SalesManagementModule.Service.SalesDAO;

/**
 * Provides sample sales data for testing and initialization.
 * Sales are linked to members from MemberData (M001-M005).
 */
public final class SalesData {

        /**
         * Initialize sample sales data with realistic sneaker products
         * 
         * @return List of sample sales
         */
        public static List<SalesItem> initializeSalesData() {
                List<SalesItem> salesList = new ArrayList<>();

                // Sale 1 - Golden Member (M001 - John Smith) - Nike Air Jordan
                SalesItem sale1 = new SalesItem(
                                "S001", // salesId
                                "M001", // memberId (John Smith - Golden)
                                "I001", // itemCode
                                "Nike", // brand
                                "Air Jordan 1 Retro High", // itemDescription
                                "Black/Red", // colour
                                900.00, // itemPrice
                                2 // quantitySales
                );
                salesList.add(sale1);

                // Sale 2 - Golden Member (M001) - Adidas Yeezy
                SalesItem sale2 = new SalesItem(
                                "S002",
                                "M001", // John Smith - Golden
                                "I002",
                                "Adidas",
                                "Yeezy Boost 350 V2",
                                "Cream", // colour
                                1200.00,
                                1);
                salesList.add(sale2);

                // Sale 3 - Silver Member (M002 - Jane Doe) - Nike Dunk
                SalesItem sale3 = new SalesItem(
                                "S003",
                                "M002", // Jane Doe - Silver
                                "I003",
                                "Nike",
                                "Dunk Low Retro",
                                "Panda", // colour
                                600.00,
                                3);
                salesList.add(sale3);

                // Sale 4 - Silver Member (M002) - New Balance
                SalesItem sale4 = new SalesItem(
                                "S004",
                                "M002", // Jane Doe - Silver
                                "I004",
                                "NewBalance",
                                "550 Classic",
                                "White/Red", // colour
                                450.00,
                                1);
                salesList.add(sale4);

                // Sale 5 - Bronze Member (M003 - Alex Wong) - Converse
                SalesItem sale5 = new SalesItem(
                                "S005",
                                "M003", // Alex Wong - Bronze
                                "I005",
                                "Converse",
                                "Chuck Taylor All Star",
                                "Black", // colour
                                300.00,
                                2);
                salesList.add(sale5);

                // Sale 6 - Bronze Member (M003) - Vans
                SalesItem sale6 = new SalesItem(
                                "S006",
                                "M003", // Alex Wong - Bronze
                                "I006",
                                "Vans",
                                "Old Skool Classic",
                                "Navy/White", // colour
                                350.00,
                                2);
                salesList.add(sale6);

                // Sale 7 - Basic Member (M004 - Sarah Lee) - Puma
                SalesItem sale7 = new SalesItem(
                                "S007",
                                "M004", // Sarah Lee - Basic
                                "I007",
                                "Puma",
                                "Suede Classic XXI",
                                "Blue", // colour
                                400.00,
                                1);
                salesList.add(sale7);

                // Sale 8 - Basic Member (M005 - Michael Chen) - Reebok
                SalesItem sale8 = new SalesItem(
                                "S008",
                                "M005", // Michael Chen - Basic
                                "I008",
                                "Reebok",
                                "Classic Leather",
                                "White", // colour
                                250.00,
                                1);
                salesList.add(sale8);

                // Sale 9 - Golden Member (M001) - Another high-value purchase
                SalesItem sale9 = new SalesItem(
                                "S009",
                                "M001", // John Smith - Golden
                                "I009",
                                "Nike",
                                "Air Max 97",
                                "Silver", // colour
                                750.00,
                                1);
                salesList.add(sale9);

                // Sale 10 - Bronze Member (M003) - Budget sneaker
                SalesItem sale10 = new SalesItem(
                                "S010",
                                "M003", // Alex Wong - Bronze
                                "I010",
                                "Skechers",
                                "D'Lites Fresh Start",
                                "Gray", // colour
                                200.00,
                                1);
                salesList.add(sale10);

                SalesItem sale11 = new SalesItem(
                                "S011",
                                "M006",
                                "I011",
                                "Skechers",
                                "D'Lites Fresh Start",
                                "Gray", // colour
                                2000.00,
                                1);
                salesList.add(sale11);

                return salesList;
        }

        /**
         * Get an empty sales list for testing
         * 
         * @return Empty list of sales
         */
        public static List<SalesItem> getEmptyList() {
                return new ArrayList<>();
        }

        /**
         * Create a single test sale for unit testing
         * 
         * @return A basic test sale
         */
        public static SalesItem createTestSale() {
                return new SalesItem(
                                "T001", // salesId
                                "M001", // memberId
                                "T999", // itemCode
                                "TestBrand", // brand
                                "Test Sneaker Model", // itemDescription
                                "Red", // colour
                                100.00, // itemPrice
                                1 // quantitySales
                );
        }

        /**
         * Get sales data for a specific member
         * Reads from SalesDAO (runtime data) to include dynamically added sales
         * 
         * @param memberId The member ID to filter by
         * @return List of sales for the specified member
         */
        public static List<SalesItem> getSalesByMember(String memberId) {
                // Read from SalesDAO to get runtime data (includes newly added sales)
                List<SalesItem> allSales = SalesDAO.getInstance().findAll();
                List<SalesItem> filtered = new ArrayList<>();

                for (SalesItem sale : allSales) {
                        if (sale.getMemberId().equals(memberId)) {
                                filtered.add(sale);
                        }
                }

                return filtered;
        }

        /**
         * Get total sales amount for a specific member
         * 
         * @param memberId The member ID
         * @return Total sales amount
         */
        public static double getTotalSalesByMember(String memberId) {
                List<SalesItem> memberSales = getSalesByMember(memberId);
                double total = 0.0;

                for (SalesItem sale : memberSales) {
                        total += sale.getTotalPrice();
                }

                return total;
        }

        // Private constructor to prevent instantiation
        private SalesData() {
                throw new AssertionError("Cannot instantiate data class");
        }
}
