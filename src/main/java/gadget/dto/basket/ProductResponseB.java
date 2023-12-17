package gadget.dto.basket;

import gadget.enums.Category;
import lombok.Builder;
import org.springframework.web.bind.annotation.BindParam;

import java.util.List;
@Builder
public record ProductResponseB(
        Long id,
        String name,
        double price,
        List<String> images,
        String characteristic,
        boolean isFavorite,
        String madeIn,
        Category category
) {
}
