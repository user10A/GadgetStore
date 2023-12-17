package gadget.service;

import gadget.dto.user.SimpleResponse;
import gadget.entity.Product;
import gadget.entity.User;
import org.springframework.stereotype.Service;

public interface FavoriteService {
    SimpleResponse favorite(Long productId);
    void addToFavorites(User user, Product product);
    void removeFromFavorites(User user, Product product);
    boolean isProductInFavorites(User user, Product product);
}
