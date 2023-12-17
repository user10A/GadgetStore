package gadget.repo;

import gadget.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    @Query("select u from User u where u.email =:email")
    Optional<User> getUserByEmail(String email);
    boolean existsByEmail(String email);
}
