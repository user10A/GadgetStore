package gadget.service.Impl;

import gadget.dto.product.response.ProductResponse;
import gadget.dto.user.SimpleResponse;
import gadget.entity.Basket;
import gadget.entity.Product;
import gadget.entity.User;
import gadget.exceptions.NotFoundException;
import gadget.repo.BasketRepo;
import gadget.repo.ProductRepo;
import gadget.repo.UserRepo;
import gadget.service.BasketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {

    private final BasketRepo basketRepo;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;
    @Override
    public SimpleResponse Basket(Long productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Long userId = ((User) authentication.getPrincipal()).getId();

            User user = userRepo.findById(userId)
                    .orElseThrow(() -> new NotFoundException("User not found"));
            log.info("user find by id {}", userId);

            Product product = productRepo.findById(productId)
                    .orElseThrow(() -> new NotFoundException("Product not found"));
            log.info("product find by id {}", productId);
                addToBaskets(user, product);

        }
        return new SimpleResponse("Product successfully added basket",HttpStatus.OK);

    }

    @Override
    public void addToBaskets(User user, Product product) {
        Basket basket = new Basket();
        basket.setBProducts(Collections.singletonList(product));
        user = userRepo.save(user); // Обновляем сущность пользователя
        basket.setBUser(user);
        basketRepo.save(basket);
    }

    @Override
    public SimpleResponse removeFromBaskets(User user, Product product) {
        Basket basket = user.getUBasket();

        // Получаем список продуктов из корзины
        List<Product> products = basket.getBProducts();

        // Удаляем конкретный продукт из списка
        products.remove(product);

        // Сохраняем обновленный список продуктов в корзине
        basket.setBProducts(products);
        basketRepo.save(basket);

        return new SimpleResponse("Product successfully removed from basket", HttpStatus.OK);
    }



    @Override
    public SimpleResponse clearBasket() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Long userId = ((User) authentication.getPrincipal()).getId();
            basketRepo.clear(userId);
        }
        return new SimpleResponse("Product successfully basket clearing", HttpStatus.OK);
    }

    @Override
    public List<ProductResponse> getAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Long userId = ((User) authentication.getPrincipal()).getId();
            User user = userRepo.findById(userId).orElseThrow(() -> new NotFoundException("User by id:" + userId + " not found"));

            // Проверка на наличие корзины у пользователя
            Basket userBasket = user.getUBasket();
            if (userBasket != null) {
                return mapProductsToProductResponses(userBasket.getBProducts());
            }
        }

        return Collections.emptyList();
    }

    // Вспомогательный метод для преобразования списка продуктов в список ProductResponse
    private List<ProductResponse> mapProductsToProductResponses(List<Product> products) {
        return products.stream()
                .map(product -> ProductResponse.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .images(product.getImages())
                        .characteristic(product.getCharacteristic())
                        .isFavorite(product.isFavorite())
                        .madeIn(product.getMadeIn())
                        .category(product.getCategory())
                        .pComments(product.getPComments())
                        .build())
                .collect(Collectors.toList());
    }

}
