package gadget.dto.product.request;

import gadget.enums.Category;
import lombok.Builder;

import java.util.List;
@Builder
public record ProductRequest(
         String name,
         String brandName,
         double price,
         List<String>images,
         String characteristic,
         String madeIn,
         Category category
) {
}
