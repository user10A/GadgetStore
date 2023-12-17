package gadget.dto.user;
import lombok.Builder;

@Builder
public record AuthenticationResponse(
        String email,
        String token
) {
}
