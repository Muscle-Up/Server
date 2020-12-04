package undefined.muscle_up.muscleup.payload.response.expert_page;

import lombok.Getter;

@Getter
public class TargetExpertPageResponse extends ExpertPageResponse {

    private boolean isMine;

    public TargetExpertPageResponse(ExpertPageResponse expertPageResponse) {
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

    public void isMine(boolean isMine) {
        this.isMine = isMine;
    }

}
