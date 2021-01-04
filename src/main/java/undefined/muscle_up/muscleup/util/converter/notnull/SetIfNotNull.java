package undefined.muscle_up.muscleup.util.converter.notnull;

import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class SetIfNotNull {

    public <T> void wholeNumberIfNotNull(Consumer<T> setter, T value){
        if(value != null){
            setter.accept(value);
        }
    }

    public  <T> void minorityIfNotNull(Consumer<T> setter, T value) {
        if(!value.equals(0.0)) {
            setter.accept(value);
        }
    }
}
