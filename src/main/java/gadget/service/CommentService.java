package gadget.service;

import gadget.dto.comment.CommentRequest;
import gadget.dto.comment.CommentResponse;
import gadget.dto.comment.CommentResponse2;
import gadget.dto.user.SimpleResponse;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CommentService {
    SimpleResponse addComment (Long productId,CommentRequest request);
    SimpleResponse deleteComment(Long productId);
    List<CommentResponse2> getAllComments ();
    SimpleResponse updateComment(Long productId,CommentRequest request);
}
