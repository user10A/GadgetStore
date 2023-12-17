package gadget.dto.user;
import gadget.enums.Role;
import lombok.Builder;

@Builder
public record SignUpRequest(
        String firstName,
        String lastName,
        String email,
        String password
) {
}
