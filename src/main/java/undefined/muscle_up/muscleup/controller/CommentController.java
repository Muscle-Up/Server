package undefined.muscle_up.muscleup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import undefined.muscle_up.muscleup.payload.response.QnaBoardCommentResponse;
import undefined.muscle_up.muscleup.service.comment.CommentService;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{boardId}")
    public void postComment(@PathVariable Integer boardId,
                            @RequestParam String content) {
        commentService.postComment(boardId, content);
    }

    @PostMapping("/sub/{commentId}")
    public void postSubComment(@PathVariable Integer commentId,
                               @RequestParam String content) {
        commentService.postSubComment(commentId, content);
    }

    @GetMapping("/{boardId}")
    public List<QnaBoardCommentResponse> getComment(@PathVariable Integer boardId) {
        return commentService.getComment(boardId);
    }

    @PutMapping("/{commentId}")
    public void changeComment(@PathVariable Integer commentId,
                              @RequestParam String content) {
        commentService.changeComment(commentId, content);
    }

    @PutMapping("/sub/{commentId}")
    public void changeSubComment(@PathVariable Integer commentId,
                                 @RequestParam String content) {
        commentService.changeSubComment(commentId, content);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Integer commentId) {
        commentService.deleteComment(commentId);
    }

    @DeleteMapping("/sub/{commentId}")
    public void deleteSubComment(@PathVariable Integer commentId) {
        commentService.deleteSubComment(commentId);
    }
}
