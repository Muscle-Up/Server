package undefined.muscle_up.muscleup.payload.response.expert_page;

import lombok.Getter;

@Getter
public class MyExpertPageResponse extends ExpertPageResponse {

    public MyExpertPageResponse(ExpertPageResponse expertPageResponse) {
        super(
                expertPageResponse.name,
                expertPageResponse.introduction,
                expertPageResponse.age,
                expertPageResponse.type,
                expertPageResponse.userImage,
                expertPageResponse.certificateName,
                expertPageResponse.acquisitionDate,
                expertPageResponse.certificateImage);
    }
}
