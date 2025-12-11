package asg.StockManagementModule.TestCases;

import asg.StockManagementModule.Controller.StockController;
import asg.StockManagementModule.Controller.StockItemController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StockControllerTest {

    private StockController controller;

    @BeforeEach
    void setUp() {
        controller = new StockController(new StockItemController());
    }

    @Test
    @DisplayName("Add item should fail when code format is invalid")
    void addItem_shouldFail_whenInvalidCode() {
        assertFalse(controller.addItem("001", "NIKE", "Desc", "Black", 10.0, 1));
    }

    @Test
    @DisplayName("Add item should fail when duplicate code")
    void addItem_shouldFail_whenDuplicateCode() {
        assertTrue(controller.addItem("I101", "NIKE", "Desc", "Black", 10.0, 1));
        assertFalse(controller.addItem("I101", "NIKE", "Desc", "Black", 10.0, 1));
    }

    @Test
    @DisplayName("Add item should fail when brand contains digits")
    void addItem_shouldFail_whenInvalidBrand() {
        assertFalse(controller.addItem("I102", "NIK3", "Desc", "Black", 10.0, 1));
    }

    @Test
    @DisplayName("Add item should fail when color contains digits")
    void addItem_shouldFail_whenInvalidColour() {
        assertFalse(controller.addItem("I103", "NIKE", "Desc", "Bl4ck", 10.0, 1));
    }

    @Test
    @DisplayName("Add item should fail when description is blank")
    void addItem_shouldFail_whenInvalidDescription() {
        assertFalse(controller.addItem("I104", "NIKE", "   ", "Black", 10.0, 1));
    }

    @Test
    @DisplayName("Add item should fail when price or quantity is negative")
    void addItem_shouldFail_whenNegativePriceOrQty() {
        assertFalse(controller.addItem("I105", "NIKE", "Desc", "Black", -1.0, 1));
        assertFalse(controller.addItem("I106", "NIKE", "Desc", "Black", 10.0, -1));
    }

    @Test
    @DisplayName("Add item with valid data should succeed")
    void addItem_shouldSucceed_whenValid() {
        assertTrue(controller.addItem("I107", "NIKE", "Jordan", "Black/Red", 500.0, 10));
        assertTrue(controller.getInventory().exists("I107"));
    }

    @Test
    @DisplayName("Update item should fail when item not found")
    void updateItem_shouldFail_whenNotFound() {
        assertFalse(controller.updateItem("I999", "NIKE", "Desc", "Black", 10.0, 1));
    }

    @Test
    @DisplayName("Delete item should return false when not found")
    void deleteItem_shouldReturnFalse_whenNotFound() {
        assertFalse(controller.deleteItem("I888"));
    }
}
