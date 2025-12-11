package asg.StockManagementModule.TestCases;

import asg.StockManagementModule.Controller.StockItemController;
import asg.StockManagementModule.Model.StockItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StockItemControllerTest {

    private StockItemController inventory;

    @BeforeEach
    void setUp() {
        inventory = new StockItemController();
    }

    @Test
    @DisplayName("Initialize default stock should only load once")
    void initializeDefaultStock_shouldLoadOnce() {
        inventory.initializeDefaultStock();
        int firstSize = inventory.getAllItems().size();

        inventory.initializeDefaultStock();
        int secondSize = inventory.getAllItems().size();

        assertTrue(firstSize > 0);
        assertEquals(firstSize, secondSize);
    }

    @Test
    @DisplayName("Add new item with new code should succeed")
    void addItem_shouldSucceed_whenNewCode() {
        StockItem item = new StockItem("I100", "NIKE", "Test", "Black", 100.0, 5);
        assertTrue(inventory.addItem(item));
        assertTrue(inventory.exists("I100"));
    }

    @Test
    @DisplayName("Add item with duplicate code should fail")
    void addItem_shouldFail_whenDuplicate() {
        StockItem item1 = new StockItem("I100", "NIKE", "A", "Black", 100.0, 5);
        StockItem item2 = new StockItem("I100", "ADIDAS", "B", "White", 120.0, 3);

        assertTrue(inventory.addItem(item1));
        assertFalse(inventory.addItem(item2));
    }

    @Test
    @DisplayName("Update existing item should modify its fields")
    void updateItem_shouldUpdateFields() {
        StockItem item = new StockItem("I200", "PUMA", "Old", "Blue", 50.0, 1);
        inventory.addItem(item);

        assertTrue(inventory.updateItem("I200", "PUMA", "New", "Red", 99.0, 10));

        StockItem updated = inventory.findByCode("I200").orElse(null);
        assertNotNull(updated);
        assertEquals("New", updated.getItemDescription());
        assertEquals("Red", updated.getColour());
        assertEquals(99.0, updated.getItemPrice(), 0.0001);
        assertEquals(10, updated.getQuantityInStock());
    }

    @Test
    @DisplayName("Delete existing item by code should remove it")
    void deleteByCode_shouldRemoveItem() {
        StockItem item = new StockItem("I300", "ADIDAS", "ToDelete", "White", 88.0, 2);
        inventory.addItem(item);

        assertTrue(inventory.deleteByCode("I300"));
        assertFalse(inventory.exists("I300"));
    }

    @Test
    @DisplayName("Delete non-existing item should return false")
    void deleteByCode_shouldReturnFalse_whenNotFound() {
        assertFalse(inventory.deleteByCode("I999"));
    }
}
