package gadget.repo;

import gadget.dto.product.response.ProductResponse;
import gadget.dto.product.response.ProductResponse4;
import gadget.entity.Product;
import gadget.enums.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {
    @Query("select new gadget.dto.product.response.ProductResponse (p.id, p.name , p.price , p.images , p.characteristic , p.isFavorite, p.madeIn, p.category, p.pComments ) from Product p")
    List<ProductResponse>getAllByProducts();

    @Query("select new gadget.dto.product.response.ProductResponse (p.id, p.name , p.price , p.images , p.characteristic , p.isFavorite, p.madeIn, p.category, p.pComments ) from Product p where p.id =:id")
    ProductResponse findProduct(@Param("id") Long id);
    @Query("select p from Product p join p.pFavorite f where f.fUser.email = :email")
    List<Product> findByFavorite(@Param("email") String email);
    @Query("SELECT NEW gadget.dto.product.response.ProductResponse4(p.id, p.name , p.price , p.characteristic , p.isFavorite, p.madeIn, p.category) " +
            "FROM Product p " +
            "WHERE p.category = :category " +
            "AND p.price BETWEEN :price AND :price2 " +
            "ORDER BY p.price")
    Page<ProductResponse4> findByCategoryAndPriceBetweenOrderByPrice(
            @Param("category") Category category,
            @Param("price") double price,
            @Param("price2") double price2,
            Pageable pageable
    );
}
