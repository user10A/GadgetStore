package gadget.api;

import gadget.dto.comment.CommentRequest;
import gadget.dto.comment.CommentResponse;

import gadget.dto.comment.CommentResponse2;
import gadget.dto.user.SimpleResponse;
import gadget.service.CommentService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentApi {
    private final CommentService commentService;
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/{productId}")
    SimpleResponse add (@PathVariable Long productId , @RequestBody CommentRequest request){
        return commentService.addComment(productId,request);
    }
    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/{id}")
    SimpleResponse delete(@PathVariable Long id){
        return commentService.deleteComment(id);
    }
    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{id}")
    SimpleResponse update(@PathVariable Long id ,@RequestBody CommentRequest request){
        return commentService.updateComment(id,request);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    List<CommentResponse2> getAll(){
        return commentService.getAllComments();
    }
}
