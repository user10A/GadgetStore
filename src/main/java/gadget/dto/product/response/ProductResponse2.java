package gadget.dto.product.response;

import gadget.entity.Comment;
import gadget.enums.Category;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Builder
public record ProductResponse2 (
         Long id,
         String name,
         double price,
         List<String> images,
         String characteristic,
         boolean isFavorite,
         String madeIn,
         Category category,
         List<Comment>pComments
){


}
