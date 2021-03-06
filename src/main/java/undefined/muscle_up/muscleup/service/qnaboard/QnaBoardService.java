package undefined.muscle_up.muscleup.service.qnaboard;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import undefined.muscle_up.muscleup.payload.response.QnaBoardContentResponse;
import undefined.muscle_up.muscleup.payload.response.QnaBoardListResponse;
import undefined.muscle_up.muscleup.payload.response.QnaBoardResponse;

import java.util.List;

public interface QnaBoardService {

    void write(String title, String content, MultipartFile[] images);
    QnaBoardResponse getBoardList(Pageable pageable);
    QnaBoardContentResponse getBoardContent(Integer boardId);
    void like(Integer boardId);
    void updateBoard(Integer boardId, String title, String content, MultipartFile[] images);
    void deleteBoard(Integer boardId);
}
