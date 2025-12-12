package asg.MakeOrderModule.Strategy;

import java.util.ArrayList;
import java.util.List;
import asg.MakeOrderModule.Model.StockItem;

public class SearchByCode implements SearchStrategy {
    @Override
    public List<StockItem> search(List<StockItem> products, String keyword) {
        List<StockItem> result = new ArrayList<>();
        for (StockItem item : products) {
            if (item.getItemCode().equalsIgnoreCase(keyword)) {
                result.add(item);
            }
        }
        return result;
    }
}
