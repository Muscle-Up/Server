package undefined.muscle_up.muscleup.service.qnaboard;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import undefined.muscle_up.muscleup.entitys.image.QnaImage;
import undefined.muscle_up.muscleup.entitys.image.repository.QnaImageRepository;
import undefined.muscle_up.muscleup.entitys.qnaboard.QnaBoard;
import undefined.muscle_up.muscleup.entitys.qnaboard.QnaBoardLike;
import undefined.muscle_up.muscleup.entitys.qnaboard.QnaBoardView;
import undefined.muscle_up.muscleup.entitys.qnaboard.repository.QnaBoardLikeRepository;
import undefined.muscle_up.muscleup.entitys.qnaboard.repository.QnaBoardRepository;
import undefined.muscle_up.muscleup.entitys.qnaboard.repository.QnaBoardViewRepository;
import undefined.muscle_up.muscleup.entitys.user.User;
import undefined.muscle_up.muscleup.entitys.user.repository.UserRepository;
import undefined.muscle_up.muscleup.payload.response.QnaBoardContentResponse;
import undefined.muscle_up.muscleup.payload.response.QnaBoardListResponse;
import undefined.muscle_up.muscleup.payload.response.QnaBoardResponse;
import undefined.muscle_up.muscleup.security.auth.AuthenticationFacade;

import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QnaBoardServiceImpl implements QnaBoardService {

    private final UserRepository userRepository;
    private final QnaImageRepository qnaImageRepository;
    private final QnaBoardRepository qnaBoardRepository;
    private final QnaBoardViewRepository qnaBoardViewRepository;
    private final QnaBoardLikeRepository qnaBoardLikeRepository;

    private final AuthenticationFacade authenticationFacade;

    @Value("${image.upload.dir}")
    private String imageDirPath;

    @SneakyThrows
    @Override
    public void write(String title, String content, MultipartFile[] images) {
        User user = userRepository.findById(authenticationFacade.getId())
                .orElseThrow(RuntimeException::new);

        QnaBoard qnaBoard = qnaBoardRepository.save(
                QnaBoard.builder()
                        .title(title)
                        .content(content)
                        .userId(user.getId())
                        .view(0)
                        .likeCount(0)
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        if(images != null) {
            for (MultipartFile file : images) {
                String imageName = UUID.randomUUID().toString();

                qnaImageRepository.save(
                        QnaImage.builder()
                                .boardId(qnaBoard.getId())
                                .imageName(imageName)
                                .build()
                );
                file.transferTo(new File(imageDirPath, imageName));
            }
        }
    }

    @Override
    public QnaBoardResponse getBoardList(Pageable pageable) {
        Page<QnaBoard> qnaBoards = qnaBoardRepository.findAll(pageable);
        List<QnaBoardListResponse> listResponses = new ArrayList<>();

        for (QnaBoard qnaBoard : qnaBoards) {
            User user = userRepository.findById(qnaBoard.getUserId())
                    .orElseThrow(RuntimeException::new);

            listResponses.add(
                    QnaBoardListResponse.builder()
                            .boardId(qnaBoard.getId())
                            .title(qnaBoard.getTitle())
                            .writer(user.getName())
                            .createdAt(qnaBoard.getCreatedAt())
                            .build()
            );
        }
        return QnaBoardResponse.builder()
                .totalElements((int) qnaBoards.getTotalElements())
                .totalPage(qnaBoards.getTotalPages())
                .qnaBoardListResponse(listResponses)
                .build();
    }

    @Override
    public QnaBoardContentResponse getBoardContent(Integer boardId) {
        User user = userRepository.findById(authenticationFacade.getId())
                .orElseThrow(RuntimeException::new);

        QnaBoard qnaBoard = qnaBoardRepository.findById(boardId)
                .orElseThrow(RuntimeException::new);

        User writer = userRepository.findById(qnaBoard.getUserId())
                .orElseThrow(RuntimeException::new);

        List<String> imageNames = new ArrayList<>();

        for (QnaImage imageFile : qnaImageRepository.findByBoardIdOrderById(boardId))
            imageNames.add(imageFile.getImageName());

        if(!qnaBoardViewRepository.findByBoardIdAndUserId(boardId, user.getId()).isPresent()) {
            qnaBoardRepository.save(qnaBoard.view());
            qnaBoardViewRepository.save(
                    QnaBoardView.builder()
                            .boardId(qnaBoard.getId())
                            .userId(user.getId())
                            .build()
            );
        }

        return QnaBoardContentResponse.builder()
                .title(qnaBoard.getTitle())
                .content(qnaBoard.getContent())
                .writer(writer.getName())
                .createdAt(qnaBoard.getCreatedAt())
                .view(qnaBoard.getView())
                .likeCount(qnaBoard.getLikeCount())
                .images(imageNames)
                .mine(user.equals(writer))
                .likeTF(qnaBoardLikeRepository.findByBoardIdAndUserId(boardId, user.getId()).isPresent())
                .build();
    }

    @Override
    @Transactional
    public void like(Integer boardId) {
        User user = userRepository.findById(authenticationFacade.getId())
                .orElseThrow(RuntimeException::new);

        QnaBoard qnaBoard = qnaBoardRepository.findById(boardId)
                .orElseThrow(RuntimeException::new);

        if(!qnaBoardLikeRepository.findByBoardIdAndUserId(boardId, user.getId()).isPresent()) {
            qnaBoardRepository.save(qnaBoard.like());
            qnaBoardLikeRepository.save(
                    QnaBoardLike.builder()
                            .boardId(boardId)
                            .userId(user.getId())
                            .build()
            );
        } else {
            qnaBoardRepository.save(qnaBoard.unLike());
            qnaBoardLikeRepository.deleteByBoardId(boardId);
        }
    }

    @SneakyThrows
    @Override
    @Transactional
    public void updateBoard(Integer boardId, String title, String content, MultipartFile[] images) {
        User user = userRepository.findById(authenticationFacade.getId())
                .orElseThrow(RuntimeException::new);

        QnaBoard qnaBoard = qnaBoardRepository.findById(boardId)
                .orElseThrow(RuntimeException::new);

        if (!user.getId().equals(qnaBoard.getUserId())) throw new RuntimeException();

        qnaBoardRepository.save(qnaBoard.updateBoard(title, content));

        List<QnaImage> qnaImages = qnaImageRepository.findByBoardIdOrderById(boardId);

        for (QnaImage image : qnaImages) {
            Files.delete(new File(imageDirPath, image.getImageName()).toPath());
        }

        qnaImageRepository.deleteByBoardId(boardId);

        for (MultipartFile file : images) {
            String fileName = UUID.randomUUID().toString();

            qnaImageRepository.save(
                    QnaImage.builder()
                            .boardId(boardId)
                            .imageName(fileName)
                            .build()
            );
            file.transferTo(new File(imageDirPath, fileName));
        }
    }

    @SneakyThrows
    @Override
    @Transactional
    public void deleteBoard(Integer boardId) {
        User user = userRepository.findById(authenticationFacade.getId())
                .orElseThrow(RuntimeException::new);

        QnaBoard qnaBoard = qnaBoardRepository.findById(boardId)
                .orElseThrow(RuntimeException::new);

        if (!user.getId().equals(qnaBoard.getUserId())) throw new RuntimeException();

        for (QnaImage postImage : qnaImageRepository.findByBoardIdOrderById(boardId)) {
            Files.delete(new File(imageDirPath, postImage.getImageName()).toPath());
        }

        qnaImageRepository.deleteByBoardId(boardId);
        qnaBoardRepository.deleteById(boardId);
    }
}
