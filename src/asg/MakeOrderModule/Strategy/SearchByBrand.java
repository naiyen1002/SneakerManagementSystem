package asg.MakeOrderModule.Strategy;

import java.util.ArrayList;
import java.util.List;
import asg.MakeOrderModule.Model.StockItem;

public class SearchByBrand implements SearchStrategy {
    @Override
    public List<StockItem> search(List<StockItem> products, String keyword) {
        List<StockItem> result = new ArrayList<>();
        for (StockItem item : products) {
            if (item.getBrand().equalsIgnoreCase(keyword)) {
                result.add(item);
            }
        }
        return result;
    }
}
