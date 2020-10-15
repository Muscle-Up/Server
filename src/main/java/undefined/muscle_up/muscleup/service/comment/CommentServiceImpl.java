package undefined.muscle_up.muscleup.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import undefined.muscle_up.muscleup.entitys.comment.Comment;
import undefined.muscle_up.muscleup.entitys.comment.SubComment;
import undefined.muscle_up.muscleup.entitys.comment.repository.CommentRepository;
import undefined.muscle_up.muscleup.entitys.comment.repository.SubCommentRepository;
import undefined.muscle_up.muscleup.entitys.qnaboard.QnaBoard;
import undefined.muscle_up.muscleup.entitys.qnaboard.repository.QnaBoardRepository;
import undefined.muscle_up.muscleup.entitys.user.User;
import undefined.muscle_up.muscleup.entitys.user.repository.UserRepository;
import undefined.muscle_up.muscleup.payload.response.QnaBoardCommentResponse;
import undefined.muscle_up.muscleup.payload.response.QnaBoardSubCommentResponse;
import undefined.muscle_up.muscleup.security.auth.AuthenticationFacade;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final QnaBoardRepository qnaBoardRepository;
    private final SubCommentRepository subCommentRepository;

    private final AuthenticationFacade authenticationFacade;

    @Override
    public void postComment(Integer boardId, String content) {
        User user = userRepository.findById(authenticationFacade.getReceiptCode())
                .orElseThrow(RuntimeException::new);

        QnaBoard qnaBoard = qnaBoardRepository.findById(boardId)
                .orElseThrow(RuntimeException::new);

        commentRepository.save(
                Comment.builder()
                        .boardId(qnaBoard.getId())
                        .content(content)
                        .userId(user.getId())
                        .createdAt(LocalDateTime.now())
                        .build()
        );
    }

    @Override
    public void postSubComment(Integer commentId, String content) {
        User user = userRepository.findById(authenticationFacade.getReceiptCode())
                .orElseThrow(RuntimeException::new);

        commentRepository.findById(commentId).orElseThrow(RuntimeException::new);

        subCommentRepository.save(
                SubComment.builder()
                        .commentId(commentId)
                        .content(content)
                        .userId(user.getId())
                        .createdAt(LocalDateTime.now())
                        .build()
        );
    }

    @Override
    public List<QnaBoardCommentResponse> getComment(Integer boardId) {
        List<Comment> comments = commentRepository.findAllByBoardIdOrderByIdAsc(boardId);
        List<QnaBoardCommentResponse> qnaBoardCommentResponses = new ArrayList<>();

        for (Comment comment : comments) {
            List<QnaBoardSubCommentResponse> subComments = new ArrayList<>();

            for (SubComment subComment : subCommentRepository.findAllByCommentIdOrderByCommentIdAsc(comment.getId())) {
                User subCommentWriter = userRepository.findById(subComment.getUserId())
                        .orElseThrow(RuntimeException::new);

                subComments.add(
                        QnaBoardSubCommentResponse.builder()
                                .subCommentId(subComment.getId())
                                .content(subComment.getContent())
                                .writer(subCommentWriter.getName())
                                .createdAt(subComment.getCreatedAt())
                                .isMine(subComment.getUserId().equals(subCommentWriter.getId()))
                                .build()
                );
            }
            User user = userRepository.findById(comment.getUserId())
                    .orElseThrow(RuntimeException::new);

            qnaBoardCommentResponses.add(
                    QnaBoardCommentResponse.builder()
                            .commendId(comment.getId())
                            .content(comment.getContent())
                            .writer(user.getName())
                            .createdAt(comment.getCreatedAt())
                            .isMine(user.getId().equals(comment.getUserId()))
                            .build()
            );
        }
        return qnaBoardCommentResponses;
    }

    @Override
    public void changeComment(Integer commentId, String content) {
        User user = userRepository.findById(authenticationFacade.getReceiptCode())
                .orElseThrow(RuntimeException::new);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(RuntimeException::new);

        if(!user.getId().equals(comment.getUserId())) throw new RuntimeException();

        commentRepository.save(comment.updateContent(content));
    }

    @Override
    public void changeSubComment(Integer commentId, String content) {
        User user = userRepository.findById(authenticationFacade.getReceiptCode())
                .orElseThrow(RuntimeException::new);

        SubComment subComment = subCommentRepository.findById(commentId)
                .orElseThrow(RuntimeException::new);

        if(!user.getId().equals(subComment.getUserId())) throw new RuntimeException();

        subCommentRepository.save(subComment.updateSubContent(content));
    }

    @Override
    public void deleteComment(Integer commentId) {
        User user = userRepository.findById(authenticationFacade.getReceiptCode())
                .orElseThrow(RuntimeException::new);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(RuntimeException::new);

        if(!user.getId().equals(comment.getUserId())) throw new RuntimeException();

        commentRepository.deleteById(commentId);
    }

    @Override
    public void deleteSubComment(Integer commentId) {
        User user = userRepository.findById(authenticationFacade.getReceiptCode())
                .orElseThrow(RuntimeException::new);

        SubComment subComment = subCommentRepository.findById(commentId)
                .orElseThrow(RuntimeException::new);

        if(!user.getId().equals(subComment.getUserId())) throw new RuntimeException();

        subCommentRepository.deleteById(commentId);
    }
}
