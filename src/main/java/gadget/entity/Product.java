package gadget.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gadget.enums.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Entity
@Data
@Builder
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    @ElementCollection()
    private List<String>images;
    private String characteristic;
    private boolean isFavorite;
    private String madeIn;
    private Category category;
    @OneToMany(mappedBy = "fProduct",cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE},fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Favorite>pFavorite;
    @OneToMany(mappedBy = "cProduct",cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE},fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Comment>pComments;
    @ManyToOne
    @JsonIgnore
    private Brand pBrand;


    public Product() {

    }
}
