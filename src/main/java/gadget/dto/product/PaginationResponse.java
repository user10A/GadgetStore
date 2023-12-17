package gadget.dto.product;
import gadget.dto.product.response.ProductResponse4;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PaginationResponse {

    private List<ProductResponse4> productProfileResponses;
    private int page;
    private int size;


    public PaginationResponse(List<ProductResponse4> productProfileResponses, int page, int size) {
        this.productProfileResponses = productProfileResponses;
        this.page = page;
        this.size = size;
    }
}
