package undefined.muscle_up.muscleup.service.comment;

import undefined.muscle_up.muscleup.payload.request.CommentRequest;
import undefined.muscle_up.muscleup.payload.response.QnaBoardCommentResponse;

import java.util.List;

public interface CommentService {

    void postComment(Integer boardId, CommentRequest commentRequest);
    void postSubComment(Integer commentId, CommentRequest commentRequest);
    List<QnaBoardCommentResponse> getComment(Integer boardId);
    void changeComment(Integer commentId, CommentRequest commentRequest);
    void changeSubComment(Integer subCommentId, CommentRequest commentRequest);
    void deleteComment(Integer commentId);
    void deleteSubComment(Integer subCommentId);
}
