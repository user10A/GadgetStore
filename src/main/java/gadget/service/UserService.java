package gadget.service;
import gadget.dto.user.AuthenticationResponse;
import gadget.dto.user.SignInRequest;
import gadget.dto.user.SignUpRequest;
import gadget.dto.user.SimpleResponse;

public interface UserService {
    AuthenticationResponse signUp(SignUpRequest request);
    AuthenticationResponse singIn(SignInRequest request);
    SimpleResponse deleteUser (String email);
    SimpleResponse update (SignUpRequest request);
}
