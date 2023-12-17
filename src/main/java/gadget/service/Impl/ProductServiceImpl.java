package gadget.service.Impl;

import gadget.dto.product.PaginationResponse;
import gadget.dto.product.request.ProductRequest;
import gadget.dto.product.response.*;
import gadget.dto.user.SimpleResponse;
import gadget.entity.*;
import gadget.enums.Category;
import gadget.exceptions.NotFoundException;
import gadget.repo.BrandRepo;
import gadget.repo.FavoriteRepo;
import gadget.repo.ProductRepo;
import gadget.repo.UserRepo;
import gadget.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    private final BrandRepo brandRepo;
    private final UserRepo userRepo;
    private final FavoriteRepo favoriteRepo;

    @Override
    public ProductResponse add(ProductRequest request) {
        Brand brand = brandRepo.getBrandByBrandName(request.brandName()).orElseThrow(() -> new NotFoundException("brand with BrandName:" + request.brandName() + "not found"));
        Product product = new Product();
        product.setName(request.name());
        product.setPrice(request.price());
        product.setImages(request.images());
        product.setCharacteristic(request.characteristic());
        product.setMadeIn(request.madeIn());
        product.setCategory(request.category());
        product.setPBrand(brand);
        productRepo.save(product);
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .images(product.getImages())
                .price(product.getPrice())
                .characteristic(product.getCharacteristic())
                .isFavorite(product.isFavorite())
                .madeIn(product.getMadeIn())
                .category(product.getCategory())
                .pComments(product.getPComments())
                .build();
    }

    @Override
    public ProductResponse update(long id, ProductRequest request) {
        Product product = productRepo.findById(id).orElseThrow(() -> new NotFoundException("Product with id:" + id + "not found"));
        product.setName(request.name());
        product.setPrice(request.price());
        product.setImages(request.images());
        product.setCharacteristic(request.characteristic());
        product.setMadeIn(request.madeIn());
        product.setCategory(request.category());
        productRepo.save(product);
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .images(product.getImages())
                .price(product.getPrice())
                .characteristic(product.getCharacteristic())
                .isFavorite(product.isFavorite())
                .madeIn(product.getMadeIn())
                .category(product.getCategory())
                .pComments(product.getPComments())
                .build();
    }

    @Override
    public SimpleResponse deleteById(Long id) {
        Product product = productRepo.findById(id).orElseThrow(() -> new NotFoundException("Product with id:" + id + "not found"));
        productRepo.delete(product);
        return new SimpleResponse("Product successfully deleted", HttpStatus.OK);
    }


    @Override
    public List<ProductResponse> getAll() {
        return productRepo.getAllByProducts();
    }

    @Override
    public ProductResponse getById(Long id) {
        return productRepo.findProduct(id);
    }

    @Override
    public CountProductResponse countProductOfUserByBasket() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((User) authentication.getPrincipal()).getId();

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        log.info("user find by id {}", userId);
        Basket basket = user.getUBasket();

        long count = 0L;
        long totalPrice = 0L;

        for (Product product : basket.getBProducts()) {
            count++;
            totalPrice += product.getPrice();
        }
        return new CountProductResponse(count, totalPrice);
    }

    @Override
    public PaginationResponse getAllByFilter(Category category, int minPrice, int maxPrice, int page, int size) {
        // Проверка на корректность входных данных
        if (minPrice > maxPrice) {
            throw new IllegalArgumentException("minPrice should not be greater than maxPrice");
        }
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ProductResponse4> product = productRepo.findByCategoryAndPriceBetweenOrderByPrice(category, minPrice, maxPrice, pageable);

        return PaginationResponse.builder()
                .productProfileResponses(product.getContent())
                .page(product.getNumber() + 1)
                .size(product.getTotalPages())
                .build();
    }
    @Override
    public FavoriteResponse getAllProductFromUserFavorite() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((User) authentication.getPrincipal()).getId();
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        log.info("user find by id {}", userId);
        List<Product> products=productRepo.findByFavorite(user.getEmail());

        return FavoriteResponse.builder()
                .products(products)
                .build();
    }

    @Override
    public Long countFavoritesByProductName(ProductRequest3 request3) {
        return favoriteRepo.countByProductName(request3.name());
    }
}
