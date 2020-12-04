package undefined.muscle_up.muscleup.service.pose;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import okhttp3.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class PoseServiceImpl implements PoseService{

    @Value("${kakao.pose.url}")
    private String url;

    @Value("${kakao.pose.key}")
    private String key;

    @Value("${kakao.pose.prefix}")
    private String prefix;

    @SneakyThrows
    @Override
    public LinkedHashMap poseCoordinates(MultipartFile image) {
        InputStream inputStream = image.getInputStream();
        File poseImage = File.createTempFile("pose", ".jpg");

        try {
            FileUtils.copyInputStreamToFile(inputStream, poseImage);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", image.getOriginalFilename(),
                        RequestBody.create(MediaType.parse("image/*"), poseImage))
                .build();

        Request request = new Request.Builder()
                .addHeader("Authorization", prefix + " " + key)
                .addHeader("Content-Type", "multipart/form-data")
                .url(url)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        ObjectMapper mapper = new ObjectMapper();
        String message = response.body().string();

        List<LinkedHashMap> imagePoise = mapper.readValue(message, List.class);

        return imagePoise.get(0);
    }
}
