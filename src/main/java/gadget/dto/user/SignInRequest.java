package gadget.dto.user;

import lombok.Builder;

@Builder
public record SignInRequest(
        String email,
        String password) {
}
