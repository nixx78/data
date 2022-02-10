package lv.nixx.samples.json.command;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.EXISTING_PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@Getter
@JsonTypeInfo(use = NAME, include = EXISTING_PROPERTY, property = "action", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AlphaCommand.class, name = "AlphaCommand"),
        @JsonSubTypes.Type(value = BetaCommand.class, name = "BetaCommand")
}
)
public abstract class Command<T> {
    private final String action;
    private final T payload;

    Command(String action, T payload) {
        this.action = action;
        this.payload = payload;
    }

}
