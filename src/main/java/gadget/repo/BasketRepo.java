package gadget.repo;
import gadget.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface BasketRepo extends JpaRepository<Basket,Long> {
    @Modifying
    @Query("DELETE FROM Basket b WHERE b.bUser.Id = :userId")
    @Transactional
    void clear(@Param("userId") Long userId);
}
