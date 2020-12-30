package undefined.muscle_up.muscleup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import undefined.muscle_up.muscleup.payload.request.BodyUpdateRequest;
import undefined.muscle_up.muscleup.payload.response.BodyResponse;
import undefined.muscle_up.muscleup.service.body.BodyService;

import java.util.List;

@RestController
@RequestMapping("/body")
@RequiredArgsConstructor
public class BodyController {

    private final BodyService bodyService;

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

    @PostMapping
    public void bodyBoardCreate(@RequestParam String title,
                                @RequestParam String content,
                                @RequestParam MultipartFile[] bodyImages) {

        for(MultipartFile file : bodyImages) {
            if(!file.isEmpty()) {
                System.out.println("no");
            }
        }

        bodyService.bodyCreate(title, content, bodyImages);
    }

    @PostMapping("/image/{bodyId}")
    public void addBodyImage(@PathVariable Integer bodyId,
                             @RequestParam MultipartFile[] images) {
        bodyService.addBodyImage(images, bodyId);
    }

    @PutMapping("/{bodyId}")
    public void bodyBoardUpdate(@RequestBody BodyUpdateRequest bodyUpdateRequest,
                                @PathVariable Integer bodyId) {

        bodyService.bodyUpdate(bodyUpdateRequest, bodyId);
    }

    @PutMapping("/image/{imageName}")
    public void bodyImageUpdate(@RequestParam MultipartFile image,
                                @PathVariable String imageName) {
        bodyService.bodyImageUpdate(image, imageName);
    }

    @DeleteMapping("/{bodyId}")
    public void bodyBoardDelete(@PathVariable Integer bodyId) {
        bodyService.bodyDelete(bodyId);
    }

    @DeleteMapping("/image/{imageName}")
    public void bodyImageDelete(@PathVariable String imageName) {
        bodyService.bodyImageDelete(imageName);
    }

}
