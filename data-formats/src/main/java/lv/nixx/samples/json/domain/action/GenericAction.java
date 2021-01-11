package lv.nixx.samples.json.domain.action;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

@Data
@JsonDeserialize(using = ActionDeserializer.class)
public class GenericAction {

    private Action action;
    private Entity entity;
    private String data;

    GenericAction() {
    }

    GenericAction(Action action, Entity entity) {
        this.action = action;
        this.entity = entity;
    }


}
