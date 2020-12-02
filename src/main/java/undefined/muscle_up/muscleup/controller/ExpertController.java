package undefined.muscle_up.muscleup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import undefined.muscle_up.muscleup.payload.request.RegistrationRequest;
import undefined.muscle_up.muscleup.payload.response.expert_page.MyExpertPageResponse;
import undefined.muscle_up.muscleup.payload.response.PageResponse;
import undefined.muscle_up.muscleup.payload.response.expert_page.TargetExpertPageResponse;
import undefined.muscle_up.muscleup.service.expert.ExpertService;

import java.time.LocalDate;

@RestController
@RequestMapping("/expert")
@RequiredArgsConstructor
public class ExpertController {

    private final ExpertService expertService;

    @GetMapping
    public PageResponse expertList(Pageable page) {
        return expertService.expertList(page);
    }

    @GetMapping("/my")
    public MyExpertPageResponse myExpertPage() {
        return expertService.myExpertPage();
    }

    @GetMapping("/{expertId}")
    public TargetExpertPageResponse targetExpertPage(@PathVariable Integer expertId) {
        return expertService.targetExpertPage(expertId);
    }

    @PostMapping
    public void registration(@RequestParam String introduction,
                             @RequestParam String certificateName,
                             @RequestParam LocalDate acquisitionDate,
                             @RequestParam MultipartFile certificateImage) {

        expertService.registration(
                RegistrationRequest.builder()
                        .introduction(introduction)
                        .certificateName(certificateName)
                        .acquisitionDate(acquisitionDate)
                        .certificateImage(certificateImage)
                        .build()
        );
    }

    @PutMapping("/image")
    public void updateImage(@RequestParam MultipartFile image) {
        expertService.updateImage(image);
    }

    @DeleteMapping
    public void deleteExpert() {
        expertService.deleteExpert();
    }

}
