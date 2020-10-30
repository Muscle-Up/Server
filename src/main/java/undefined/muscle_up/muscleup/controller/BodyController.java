package undefined.muscle_up.muscleup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import undefined.muscle_up.muscleup.payload.response.BodyResponse;
import undefined.muscle_up.muscleup.service.body.BodyService;

import java.util.List;

@RestController
@RequestMapping("/body")
@RequiredArgsConstructor
public class BodyController {

    private final BodyService bodyService;

    @PostMapping
    public void bodyBoardCreate(@RequestParam String title,
                                @RequestParam String content,
                                @RequestParam MultipartFile bodyImage) {

        bodyService.bodyCreate(title, content, bodyImage);
    }

    @PutMapping
    public void bodyBoardUpdate(@RequestParam String title,
                                @RequestParam String content,
                                @RequestParam Integer id) {

        bodyService.bodyUpdate(title, content, id);
    }

    @PutMapping("/image")
    public void bodyImageUpdate(@RequestParam MultipartFile image,
                                @RequestParam Integer bodyId) {
        bodyService.bodyImageUpdate(image, bodyId);
    }

    @DeleteMapping("{boardId}")
    public void bodyBoardDelete(@PathVariable Integer boardId) {
        bodyService.bodyDelete(boardId);
    }

    @DeleteMapping("/image/{bodyId}")
    public void bodyImageDelete(@PathVariable Integer bodyId) {
        bodyService.bodyImageDelete(bodyId);
    }

    @GetMapping
    public List<BodyResponse> bodyBoardList() {
        return bodyService.getBodyList();
    }

    @GetMapping(
            value = "/image/{imageName}",
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE}
    )
    public byte[] getBodyImage(@PathVariable String imageName) {
        return bodyService.getBodyImage(imageName);
    }
}
