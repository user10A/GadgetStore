package gadget.service.Impl;
import gadget.dto.comment.CommentRequest;
import gadget.dto.comment.CommentResponse;
import gadget.dto.comment.CommentResponse2;
import gadget.dto.user.SimpleResponse;
import gadget.entity.Comment;
import gadget.entity.Product;
import gadget.entity.User;
import gadget.exceptions.NotFoundException;
import gadget.repo.CommentRepo;
import gadget.repo.ProductRepo;
import gadget.repo.UserRepo;
import gadget.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepo commentRepo;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;
    @Override
    public SimpleResponse addComment(Long productId,CommentRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((User) authentication.getPrincipal()).getId();
        User user = userRepo.findById(userId).orElseThrow(()-> new NotFoundException("User with id:"+userId+"npt found"));
        log.info("user found by id {}",userId);
        Product product = productRepo.findById(productId).orElseThrow(()-> new NotFoundException("Product with id:"+productId+"not found"));
        log.info("product found by id {}",productId);
        Comment comment =new Comment();
        comment.setCUser(user);
        comment.setCProduct(product);
        comment.setCreatedDate(ZonedDateTime.now());
        comment.setComment(request.comment());
        commentRepo.save(comment);
        log.info("comment successfully added");
        return new SimpleResponse("comment successfully added", HttpStatus.OK);
    }

    @Override
    public SimpleResponse deleteComment(Long commentId) {
        Comment comment =commentRepo.findById(commentId).orElseThrow(()-> new NotFoundException("comment with id:"+commentId+"not found"));
        commentRepo.delete(comment);
        return new SimpleResponse("Comment successfully deleted",HttpStatus.OK);
    }

    @Override
    public List<CommentResponse2> getAllComments() {
         return commentRepo.getAllComments();
    }

    @Override
    public SimpleResponse updateComment(Long commentId,CommentRequest request) {
        Comment comment =commentRepo.findById(commentId).orElseThrow(()-> new NotFoundException("comment with id:"+commentId+"not found"));
        log.info("find comment {}",comment);
        comment.setComment(request.comment());
        comment.setCreatedDate(ZonedDateTime.now());
        commentRepo.save(comment);
        log.info("comment updated ");
        return new SimpleResponse("Comment successfully updated",HttpStatus.OK);
    }
}
