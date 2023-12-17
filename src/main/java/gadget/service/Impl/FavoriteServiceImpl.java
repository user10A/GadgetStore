package gadget.service.Impl;

import gadget.dto.user.SimpleResponse;
import gadget.entity.Favorite;
import gadget.entity.Product;
import gadget.entity.User;
import gadget.exceptions.NotFoundException;
import gadget.repo.FavoriteRepo;
import gadget.repo.ProductRepo;
import gadget.repo.UserRepo;
import gadget.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
@Slf4j // Simple Logging Facade for Java
@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepo favoriteRepo;
    private final UserRepo userRepo;
    private final ProductRepo productRepo;
    @Override
    public SimpleResponse favorite(Long productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();

            // Теперь у вас есть имя пользователя (username), которое можете использовать для извлечения userID
            // Например, если ваш UserDetails содержит метод getUserId():

            Long userId = ((User) authentication.getPrincipal()).getId();

            User user = userRepo.findById(userId)
                    .orElseThrow(() -> new NotFoundException("User not found"));
            log.info("user find by id {}",userId);

            Product product = productRepo.findById(productId)
                    .orElseThrow(() -> new NotFoundException("Product not found"));
            log.info("product find by id {}",productId);
            boolean a = isProductInFavorites(user, product);
            if (a) {
                removeFromFavorites(user, product);
                log.info("removed from favorite");
                return new SimpleResponse("The Product has been successfully removed from favorites", HttpStatus.OK);
            } else {
                addToFavorites(user, product);
                log.info("added to favorite");
                return new SimpleResponse("The Product has been successfully added to favorites", HttpStatus.OK);
            }
        } else {
            // Обработка случая, когда пользователь не аутентифицирован
            return new SimpleResponse("Authentication required", HttpStatus.UNAUTHORIZED);
        }
    }


    @Override
    public boolean isProductInFavorites(User user, Product product) {
        return favoriteRepo.existsByUserAndProduct(user, product);
    }

    @Override
    public void removeFromFavorites(User user, Product product) {
        favoriteRepo.deleteByUserAndProduct(user, product);
    }

    @Override
    public void addToFavorites(User user, Product product) {
        product.setFavorite(true);
        Favorite favorite = new Favorite();
        favorite.setFUser(user);
        favorite.setFProduct(product);
        favoriteRepo.save(favorite);
    }

}
