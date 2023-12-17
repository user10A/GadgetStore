package gadget.dto.comment;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CommentRequest(
        String comment
) {
}
