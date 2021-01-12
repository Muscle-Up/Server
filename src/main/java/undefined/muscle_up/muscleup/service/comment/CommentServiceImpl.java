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
import undefined.muscle_up.muscleup.exceptions.*;
import undefined.muscle_up.muscleup.payload.request.CommentRequest;
import undefined.muscle_up.muscleup.payload.response.QnaBoardCommentResponse;
import undefined.muscle_up.muscleup.payload.response.QnaBoardSubCommentResponse;
import undefined.muscle_up.muscleup.security.auth.AuthenticationFacade;

import javax.transaction.Transactional;
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
    public void postComment(Integer boardId, CommentRequest commentRequest) {
        User user = userRepository.findById(authenticationFacade.getId())
                .orElseThrow(UserNotFoundException::new);

        QnaBoard qnaBoard = qnaBoardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);

        commentRepository.save(
                Comment.builder()
                        .boardId(qnaBoard.getId())
                        .content(commentRequest.getContent())
                        .userId(user.getId())
                        .createdAt(LocalDateTime.now())
                        .build()
        );
    }

    @Override
    public void postSubComment(Integer commentId, CommentRequest commentRequest) {
        User user = userRepository.findById(authenticationFacade.getId())
                .orElseThrow(UserNotFoundException::new);

        commentRepository.findById(commentId).orElseThrow(CommnetNotFoundException::new);

        subCommentRepository.save(
                SubComment.builder()
                        .commentId(commentId)
                        .content(commentRequest.getContent())
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

            for (SubComment subComment : subCommentRepository.findAllByCommentIdOrderByIdAsc(comment.getId())) {
                User subCommentWriter = userRepository.findById(subComment.getUserId())
                        .orElseThrow(WriterNotFoundException::new);

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
                    .orElseThrow(UserNotFoundException::new);

            qnaBoardCommentResponses.add(
                    QnaBoardCommentResponse.builder()
                            .commendId(comment.getId())
                            .content(comment.getContent())
                            .writer(user.getName())
                            .createdAt(comment.getCreatedAt())
                            .subComment(subComments)
                            .isMine(user.getId().equals(comment.getUserId()))
                            .build()
            );
        }
        return qnaBoardCommentResponses;
    }

    @Override
    public void changeComment(Integer commentId, CommentRequest commentRequest) {
        User user = userRepository.findById(authenticationFacade.getId())
                .orElseThrow(UserNotFoundException::new);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommnetNotFoundException::new);

        if(!user.getId().equals(comment.getUserId())) throw new WriterNotFoundException();

        commentRepository.save(comment.updateContent(commentRequest.getContent()));
    }

    @Override
    public void changeSubComment(Integer subCommentId, CommentRequest commentRequest) {
        User user = userRepository.findById(authenticationFacade.getId())
                .orElseThrow(UserNotFoundException::new);

        SubComment subComment = subCommentRepository.findById(subCommentId)
                .orElseThrow(CocommentNotFoundException::new);

        if(!user.getId().equals(subComment.getUserId())) throw new WriterNotFoundException();

        subCommentRepository.save(subComment.updateSubContent(commentRequest.getContent()));
    }

    @Override
    @Transactional
    public void deleteComment(Integer commentId) {
        User user = userRepository.findById(authenticationFacade.getId())
                .orElseThrow(UserNotFoundException::new);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommnetNotFoundException::new);

        if(!user.getId().equals(comment.getUserId())) throw new WriterNotFoundException();

        subCommentRepository.deleteAllByCommentId(commentId);
        commentRepository.deleteById(commentId);
    }

    @Override
    public void deleteSubComment(Integer subCommentId) {
        User user = userRepository.findById(authenticationFacade.getId())
                .orElseThrow(UserNotFoundException::new);

        SubComment subComment = subCommentRepository.findById(subCommentId)
                .orElseThrow(CocommentNotFoundException::new);

        if(!user.getId().equals(subComment.getUserId())) throw new WriterNotFoundException();

        subCommentRepository.deleteById(subCommentId);
    }
}
