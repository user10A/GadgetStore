package gadget.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brandName;
    private String image;
    @OneToMany(mappedBy = "pBrand",cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private List<Product>brProduct;
    public Brand() {
    }
}
