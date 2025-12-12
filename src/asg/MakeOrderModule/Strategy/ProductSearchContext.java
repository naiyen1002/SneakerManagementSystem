package asg.MakeOrderModule.Strategy;

import java.util.List;
import asg.MakeOrderModule.Model.StockItem;

public class ProductSearchContext {
    private SearchStrategy strategy;

    public void setStrategy(SearchStrategy strategy) {
        this.strategy = strategy;
    }

    public List<StockItem> executeSearch(List<StockItem> products, String keyword) {
        return strategy.search(products, keyword);
    }
}
