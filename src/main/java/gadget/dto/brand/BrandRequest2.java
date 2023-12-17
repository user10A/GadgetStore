package gadget.dto.brand;

import lombok.Builder;

@Builder
public record BrandRequest2(
        String oldBrandName,
        String brandName,
        String image
) {

}
