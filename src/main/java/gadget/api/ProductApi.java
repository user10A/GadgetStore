package gadget.api;

import gadget.dto.product.PaginationResponse;
import gadget.dto.product.request.ProductRequest;
import gadget.dto.product.response.CountProductResponse;
import gadget.dto.product.response.FavoriteResponse;
import gadget.dto.product.response.ProductRequest3;
import gadget.dto.product.response.ProductResponse;
import gadget.dto.user.SimpleResponse;
import gadget.enums.Category;
import gadget.service.FavoriteService;
import gadget.service.ProductService;
import jakarta.annotation.security.PermitAll;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductApi {
    private final ProductService productService;
    private final FavoriteService favoriteService;
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    ProductResponse add (@RequestBody ProductRequest request){
        return productService.add(request);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/f/{id}")
    SimpleResponse favoriteAddOrDelete(@PathVariable Long id){
        return favoriteService.favorite(id);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    SimpleResponse delete(@PathVariable Long id){
       return productService.deleteById(id);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    ProductResponse update(@PathVariable Long id ,@RequestBody ProductRequest request){
         return productService.update(id,request);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    List<ProductResponse> getAll(){
        return productService.getAll();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/filter")
    public PaginationResponse getFilteredAndSortedProducts(
            @RequestParam(name = "category") Category category,
            @RequestParam(name = "minPrice") int minPrice,
            @RequestParam(name = "maxPrice") int maxPrice,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size
    ) {return productService.getAllByFilter(category, minPrice, maxPrice, page, size);}

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/count2")
    public CountProductResponse count(){
        return productService.countProductOfUserByBasket();
    }
    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping("/getAll")
    public FavoriteResponse getAllF(){
        return productService.getAllProductFromUserFavorite();
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping("/count")
    public ResponseEntity<Long> countFavorites(@RequestBody ProductRequest3 request3) {
        Long count = productService.countFavoritesByProductName(request3);
        return ResponseEntity.ok(count);
    }
}
