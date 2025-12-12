package asg.StockManagementModule.Test;

import asg.StockManagementModule.Model.StockItem;
import asg.StockManagementModule.View.StockView;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class StockViewTest {

    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    private StockView createViewWithInput(String input) {
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner sc = new Scanner(in);
        return new StockView(sc);
    }

    @Test
    @DisplayName("readInt - should handle non-numeric then valid number")
    void readInt_shouldHandleInvalidThenValid() {
        StockView view = createViewWithInput("abc\n5\n");
        int result = view.readInt("Enter: ");
        assertEquals(5, result);
        String out = outputStream.toString();
        assertTrue(out.toLowerCase().contains("invalid input"));
    }

    @Test
    @DisplayName("readDouble - should handle non-numeric then valid number")
    void readDouble_shouldHandleInvalidThenValid() {
        StockView view = createViewWithInput("xyz\n3.5\n");
        double result = view.readDouble("Enter: ");
        assertEquals(3.5, result, 0.001);
        String out = outputStream.toString();
        assertTrue(out.toLowerCase().contains("invalid input"));
    }

    @Test
    @DisplayName("printItemTable - empty list should show empty message")
    void printItemTable_empty_shouldShowEmptyMessage() {
        StockView view = createViewWithInput("");
        view.printItemTable(Collections.emptyList());
        String out = outputStream.toString().toLowerCase();
        assertTrue(out.contains("empty"));
    }

    @Test
    @DisplayName("printItemTable - should print header and item row")
    void printItemTable_shouldShowHeaderAndItem() {
        StockView view = createViewWithInput("");
        List<StockItem> list = new ArrayList<>();
        list.add(new StockItem("I001", "NIKE", "Test", "Black", 100.0, 5));

        view.printItemTable(list);
        String out = outputStream.toString();
        assertTrue(out.contains("Code"));
        assertTrue(out.contains("I001"));
        assertTrue(out.contains("NIKE"));
    }
}
