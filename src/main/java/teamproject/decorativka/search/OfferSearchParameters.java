package teamproject.decorativka.search;

import java.util.List;
import lombok.Data;

@Data
public class OfferSearchParameters implements SearchParameters {
    private List<String> name;
    private List<Long> categoryId;
    private List<Long> id;
}
