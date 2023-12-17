package gadget.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;
import java.time.ZonedDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    private ZonedDateTime createdDate;
    @ManyToOne(cascade ={})
    private User cUser;
    @ManyToOne(cascade ={})
    private Product cProduct;
    public Comment() {

    }
}
