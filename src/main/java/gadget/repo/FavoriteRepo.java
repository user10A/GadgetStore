package gadget.repo;

import gadget.entity.Favorite;
import gadget.entity.Product;
import gadget.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FavoriteRepo extends JpaRepository<Favorite,Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Favorite f WHERE f.fUser = :user AND f.fProduct = :product")
    void deleteByUserAndProduct(@Param("user") User user, @Param("product") Product product);

    @Query("select count(f) from Favorite f where f.fProduct.name = :name")
    Long countByProductName(@Param("name") String name);
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Favorite f WHERE f.fUser = :user AND f.fProduct = :product")
    boolean existsByUserAndProduct(@Param("user") User user, @Param("product") Product product);
}
