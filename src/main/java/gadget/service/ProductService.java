package gadget.service;
import gadget.dto.product.PaginationResponse;
import gadget.dto.product.request.ProductRequest;
import gadget.dto.product.response.CountProductResponse;
import gadget.dto.product.response.FavoriteResponse;
import gadget.dto.product.response.ProductRequest3;
import gadget.dto.product.response.ProductResponse;
import gadget.dto.user.SimpleResponse;
import gadget.enums.Category;

import java.util.List;

public interface ProductService {
    ProductResponse add(ProductRequest request);
    ProductResponse update(long id,ProductRequest request);
    SimpleResponse deleteById(Long id);
    List<ProductResponse>getAll();
    ProductResponse getById(Long id);
    CountProductResponse countProductOfUserByBasket();
    PaginationResponse getAllByFilter(Category category, int minPrice, int maxPrice, int page, int size);
    FavoriteResponse getAllProductFromUserFavorite();
    Long countFavoritesByProductName(ProductRequest3 request3);



}
