package gadget.dto.product.response;
import gadget.enums.Category;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductResponse4 {
    private Long id;
    private String name;
    private double price;
    private String characteristic;
    private boolean isFavorite;
    private String madeIn;
    private Category category;


    public ProductResponse4(Long id, String name, double price, String characteristic, boolean isFavorite, String madeIn, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.characteristic = characteristic;
        this.isFavorite = isFavorite;
        this.madeIn = madeIn;
        this.category = category;

    }

}
