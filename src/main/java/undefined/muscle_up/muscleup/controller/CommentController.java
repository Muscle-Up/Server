package undefined.muscle_up.muscleup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import undefined.muscle_up.muscleup.payload.request.CommentRequest;
import undefined.muscle_up.muscleup.payload.response.QnaBoardCommentResponse;
import undefined.muscle_up.muscleup.service.comment.CommentService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{boardId}")
    public void postComment(@PathVariable Integer boardId,
                            @RequestBody @Valid CommentRequest commentRequest) {
        commentService.postComment(boardId, commentRequest);
    }

    @PostMapping("/sub/{commentId}")
    public void postSubComment(@PathVariable Integer commentId,
                               @RequestBody @Valid CommentRequest commentRequest) {
        commentService.postSubComment(commentId, commentRequest);
    }

    @GetMapping("/{boardId}")
    public List<QnaBoardCommentResponse> getComment(@PathVariable Integer boardId) {
        return commentService.getComment(boardId);
    }

    @PutMapping("/{commentId}")
    public void changeComment(@PathVariable Integer commentId,
                              @RequestBody @Valid CommentRequest commentRequest) {
        commentService.changeComment(commentId, commentRequest);
    }

    @PutMapping("/sub/{subCommentId}")
    public void changeSubComment(@PathVariable Integer subCommentId,
                                 @RequestBody @Valid CommentRequest commentRequest) {
        commentService.changeSubComment(subCommentId, commentRequest);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Integer commentId) {
        commentService.deleteComment(commentId);
    }

    @DeleteMapping("/sub/{subCommentId}")
    public void deleteSubComment(@PathVariable Integer subCommentId) {
        commentService.deleteSubComment(subCommentId);
    }
}
