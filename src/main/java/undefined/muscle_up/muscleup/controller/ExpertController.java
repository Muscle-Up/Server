package undefined.muscle_up.muscleup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import undefined.muscle_up.muscleup.payload.response.PageResponse;
import undefined.muscle_up.muscleup.service.expert.ExpertService;

@RestController
@RequestMapping("/expert")
@RequiredArgsConstructor
public class ExpertController {

    private final ExpertService expertService;

    @GetMapping
    public PageResponse expertList(Pageable page) {
        return expertService.expertList(page);
    }

    @PostMapping
    public void registration(@RequestParam String introduction,
                             MultipartFile image) {
        expertService.registration(introduction, image);
    }

    @DeleteMapping
    public void deleteExpert() {
        expertService.deleteExpert();
    }

    @GetMapping(
            value = "/image/{imageName}",
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE}
    )
    public byte[] getImage(@PathVariable String imageName) {
        return expertService.getImage(imageName);
    }

    @PutMapping("/image")
    public void updateImage(@RequestParam MultipartFile image) {
        expertService.updateImage(image);
    }

}
