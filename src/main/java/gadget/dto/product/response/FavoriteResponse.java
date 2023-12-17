package gadget.dto.product.response;


import gadget.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class FavoriteResponse {


    private List<Product> products;


    public FavoriteResponse(List<Product> products) {
        this.products = products;
    }
}
