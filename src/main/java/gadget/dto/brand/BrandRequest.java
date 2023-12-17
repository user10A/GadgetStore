package gadget.dto.brand;

import lombok.Builder;

@Builder
public record BrandRequest(
        String brandName,
        String image
) {

}
