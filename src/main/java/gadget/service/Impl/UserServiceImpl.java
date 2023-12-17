package gadget.service.Impl;
import gadget.config.jwt.JwtService;
import gadget.dto.user.AuthenticationResponse;
import gadget.dto.user.SignInRequest;
import gadget.dto.user.SignUpRequest;
import gadget.dto.user.SimpleResponse;
import gadget.entity.User;
import gadget.enums.Role;
import gadget.exceptions.AlreadyExistsException;
import gadget.exceptions.NotFoundException;
import gadget.repo.UserRepo;
import gadget.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.ZonedDateTime;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepo userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;
    @Override
    public AuthenticationResponse signUp(SignUpRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new AlreadyExistsException(
                    "user with email: " + request.email() + " already exists!"
            );
        }
        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(encoder.encode(request.password()))
                .build();
        user.setCreatedDate(ZonedDateTime.now());
        user.setRole(Role.USER);
        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder().
                token(token)
                .email(user.getEmail())
                .build();
    }
    @Override
    public AuthenticationResponse singIn(SignInRequest request) {
        User user = userRepository.getUserByEmail(request.email()).orElseThrow(
                () -> new NotFoundException(
                        "user with email: " + request.email() + " not fount"));
        if (request.email().isBlank()) {
            throw new BadCredentialsException("email is blank");
        }
        if (!encoder.matches(request.password(), user.getPassword())) {
            throw new BadCredentialsException("wrong password");
        }
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().
                token(jwtToken)
                .email(user.getEmail())
                .build();
    }

    @Override
    public SimpleResponse deleteUser(String email) {
        User user = userRepository.getUserByEmail(email).orElseThrow(
                () -> new NotFoundException(
                        "user with email: " + email + " not fount"));
        if (email.isBlank()) {
            throw new gadget.exceptions.BadCredentialsException("email is blank");
        }
        userRepository.delete(user);
        return new SimpleResponse("User successfully deleted !", HttpStatus.OK);
    }

    @Override
    public SimpleResponse update(SignUpRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((User) authentication.getPrincipal()).getId();
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User with id: " + userId + " not found"));
        log.info("user find by id: {}",userId);
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPassword(encoder.encode(request.password()));
        user.setUpdateDate(ZonedDateTime.now());
        user.setRole(Role.USER);
        userRepository.save(user);
        log.info("saving");

        return new SimpleResponse("User successfully updated !", HttpStatus.OK);
    }

}