package undefined.muscle_up.muscleup.service.image;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Service
public class ImageServiceImpl implements ImageService{

    @Value("${image.upload.dir}")
    private String imageDirPath;

    @SneakyThrows
    @Override
    public byte[] getImage(String imageName) {
        File file = new File(imageDirPath, imageName);
        if (!file.exists())
            throw new RuntimeException();

        InputStream inputStream = new FileInputStream(file);

        return IOUtils.toByteArray(inputStream);
    }
}
