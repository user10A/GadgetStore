package gadget.entity;

import gadget.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.TimeZone;

@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private ZonedDateTime createdDate;
    private ZonedDateTime updateDate;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "fUser",cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private List<Favorite>uFavorites;
    @OneToMany(mappedBy = "cUser",cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private List<Comment>uComments;
    @OneToOne(mappedBy = "bUser")
    private Basket uBasket;
    public User() {

    }

    public User(Long id,String email, String password, Role role) {
        Id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return getEmail();
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
