package teamproject.decorativka.search;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class ProductSearchParameters implements SearchParameters {
    private List<String> name;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private List<String> country;
    private List<String> producer;
    private List<String> collection;
    private List<String> tone;
    private List<String> type;
    private List<String> room;
    private List<String> code;
    private List<Long> categoryId;
    private List<Long> id;
}
