package gadget.dto.user;
import lombok.Builder;

@Builder
public record UpdateRequest(
        String oldEmail,
        String oldPassword,
        String firstName,
        String lastName,
        String newEmail,
        String newPassword
) {
}
