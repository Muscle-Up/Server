package undefined.muscle_up.muscleup.service.comment;

import undefined.muscle_up.muscleup.payload.response.QnaBoardCommentResponse;

import java.util.List;

public interface CommentService {

    void postComment(Integer boardId, String content);
    void postSubComment(Integer commentId, String content);
    List<QnaBoardCommentResponse> getComment(Integer boardId);
    void changeComment(Integer commentId, String content);
    void changeSubComment(Integer subCommentId, String content);
    void deleteComment(Integer commentId);
    void deleteSubComment(Integer subCommentId);
}
