package undefined.muscle_up.muscleup.util.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LocalDateConverter implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(String dateTime) {
        return LocalDate.parse(dateTime);
    }
}
