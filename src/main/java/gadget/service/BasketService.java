package gadget.service;
import gadget.dto.product.response.ProductResponse;
import gadget.dto.user.SimpleResponse;
import gadget.entity.Product;
import gadget.entity.User;

import java.util.List;

public interface BasketService {
    SimpleResponse Basket(Long productId);
    void addToBaskets(User user, Product product);
    SimpleResponse removeFromBaskets(User user, Product product);
    SimpleResponse clearBasket();
    List<ProductResponse> getAll();
}
