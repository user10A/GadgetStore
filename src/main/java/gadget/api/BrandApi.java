package gadget.api;

import gadget.dto.brand.BrandRequest;
import gadget.dto.brand.BrandRequest2;
import gadget.dto.brand.BrandResponse;
import gadget.dto.user.SimpleResponse;
import gadget.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandApi {
    private final BrandService brandService;
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping()
    BrandResponse add (@RequestBody BrandRequest request){
        return brandService.saveBrand(request);
    }


    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping()
    SimpleResponse remove (@RequestParam String brandName){
        return brandService.deleteBrand(brandName);
    }
    @PreAuthorize("hasAuthority('USER')")
    @PutMapping()
    BrandResponse update (@RequestBody BrandRequest2 request){
        return brandService.updateBrand(request);
    }
}
