package gadget.dto.brand;

import lombok.Builder;

@Builder
public record BrandResponse(
        String brandName,
        String image
) {

}
