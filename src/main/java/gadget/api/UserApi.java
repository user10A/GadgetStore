package gadget.api;
import gadget.dto.user.AuthenticationResponse;
import gadget.dto.user.SignInRequest;
import gadget.dto.user.SignUpRequest;
import gadget.dto.user.SimpleResponse;
import gadget.service.Impl.UserServiceImpl;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserApi {
    private final UserServiceImpl userService;

    @PermitAll
    @PostMapping("/signUp")
    AuthenticationResponse signUp(@RequestBody SignUpRequest request){
        return userService.signUp(request);
    }

    @PermitAll
    @PostMapping("/signIn")
    AuthenticationResponse signIn(@RequestBody SignInRequest request){
        return userService.singIn(request);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PatchMapping("/update")
    SimpleResponse update(@RequestBody SignUpRequest request){
        return userService.update(request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping
    SimpleResponse delete(@RequestParam String email){
       return userService.deleteUser(email);
    }

}
