package asg.MakeOrderModule.Strategy;

import java.util.List;
import asg.MakeOrderModule.Model.StockItem;

public interface SearchStrategy {
    List<StockItem> search(List<StockItem> products, String keyword);
}

