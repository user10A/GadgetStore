package gadget.dto.product.response;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CountProductResponse {

    @NotNull(message = "must be not null")
    private long count;
    @NotNull(message = "must be not null")
    private long price;


    public CountProductResponse(long count, long price) {
        this.count = count;
        this.price = price;
    }
}


