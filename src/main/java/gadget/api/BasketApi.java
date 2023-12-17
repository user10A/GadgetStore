package gadget.api;

import gadget.dto.comment.CommentRequest;
import gadget.dto.product.response.ProductResponse;
import gadget.dto.user.SimpleResponse;
import gadget.repo.BasketRepo;
import gadget.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/basket")
@RequiredArgsConstructor
public class BasketApi {
    private final BasketService basketService;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/{productId}")
    SimpleResponse add (@PathVariable Long productId){
        return basketService.Basket(productId);
    }


    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping()
    SimpleResponse clear (){
        return basketService.clearBasket();
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping()
     List<ProductResponse>getAll (){
        return basketService.getAll();
    }

}
