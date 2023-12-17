package gadget.repo;
import gadget.dto.comment.CommentResponse;
import gadget.dto.comment.CommentResponse2;
import gadget.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comment,Long> {
    @Query("select new gadget.dto.comment.CommentResponse2 (c.id,c.comment,c.createdDate,c.cUser.email ,c.cProduct.name )from Comment c")
    List<CommentResponse2>getAllComments();
}
