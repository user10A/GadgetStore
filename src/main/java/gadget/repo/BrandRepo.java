package gadget.repo;

import gadget.entity.Brand;
import gadget.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Repository
public interface BrandRepo extends JpaRepository<Brand,Long> {
    @Query("select b from Brand b where b.brandName = :brandName")
    Optional<Brand> getBrandByBrandName(@Param("brandName") String brandName);

    @Modifying
    @Query("update Brand b set b.image = :image , b.brandName = :brandName where b.id = :id")
    @Transactional
    void updateBrandByBrandNameAndAndImage(@Param("brandName") String brandName, @Param("image") String image ,@Param("id")Long id);

}
