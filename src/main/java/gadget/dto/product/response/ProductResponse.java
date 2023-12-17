package gadget.dto.product.response;

import gadget.entity.Comment;
import gadget.enums.Category;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class ProductResponse{
    private Long id;
    private String name;
    private double price;
    private List<String> images;
    private String characteristic;
    private boolean isFavorite;
    private String madeIn;
    private Category category;
    private List<Comment>pComments ;

    public ProductResponse(Long id, String name, double price, List<String> images, String characteristic, boolean isFavorite, String madeIn, Category category, List<Comment> pComments) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.images = images;
        this.characteristic = characteristic;
        this.isFavorite = isFavorite;
        this.madeIn = madeIn;
        this.category = category;
        this.pComments = pComments;
    }

}
