package gadget.service;

import gadget.dto.brand.BrandRequest;
import gadget.dto.brand.BrandRequest2;
import gadget.dto.brand.BrandResponse;
import gadget.dto.user.SimpleResponse;
import org.springframework.stereotype.Service;

public interface BrandService {
    BrandResponse saveBrand(BrandRequest request);
    SimpleResponse deleteBrand (String BranName);
    BrandResponse updateBrand (BrandRequest2 request);
}
