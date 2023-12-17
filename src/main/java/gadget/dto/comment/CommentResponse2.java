package gadget.dto.comment;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
@Builder
@Data
public class CommentResponse2{
    private Long id;
    private String comment;
    private ZonedDateTime createDate;
    private String email;
    private String productName;

    public CommentResponse2(Long id, String comment, ZonedDateTime createDate, String email, String productName) {
        this.id = id;
        this.comment = comment;
        this.createDate = createDate;
        this.email = email;
        this.productName = productName;
    }
}
