package undefined.muscle_up.muscleup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import undefined.muscle_up.muscleup.payload.response.QnaBoardContentResponse;
import undefined.muscle_up.muscleup.payload.response.QnaBoardListResponse;
import undefined.muscle_up.muscleup.service.qnaboard.QnaBoardService;

import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class QnaBoardController {

    private final QnaBoardService qnaBoardService;

    @PostMapping
    private void write(@RequestParam String title,
                       @RequestParam String content,
                       @RequestParam MultipartFile[] images) {
        qnaBoardService.write(title, content, images);
    }

    @GetMapping
    private List<QnaBoardListResponse> getBoardList() {
        return qnaBoardService.getBoardList();
    }

    @GetMapping("/{boardId}")
    public QnaBoardContentResponse getBoardContent(@PathVariable Integer boardId) {
        return qnaBoardService.getBoardContent(boardId);
    }

    @PutMapping("/{boardId}/like")
    public void like(@PathVariable Integer boardId) {
        qnaBoardService.like(boardId);
    }

    @PutMapping("/{boardId}")
    public void updateBoard(@PathVariable Integer boardId,
                            @RequestParam String title,
                            @RequestParam String content,
                            @RequestParam MultipartFile[] images) {
        qnaBoardService.updateBoard(boardId, title, content, images);
    }

    @DeleteMapping("/{boardId}")
    public void deleteBoard(@PathVariable Integer boardId) {
        qnaBoardService.deleteBoard(boardId);
    }
}
