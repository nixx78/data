package lv.nixx.samples.json.domain.command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AlphaCommand extends Command<AlphaCommand.AlphaCommandPayload>{

    @JsonCreator
    public AlphaCommand(@JsonProperty("payload.fieldOne") String fieldOne,  @JsonProperty("payload.fieldTwo") String fieldTwo ) {
        super("AlphaCommand", new AlphaCommandPayload(fieldOne, fieldTwo));
    }

    @Getter
    @ToString
    static class AlphaCommandPayload {
        String fieldOne;
        String fieldTwo;

        @JsonCreator
        AlphaCommandPayload(@JsonProperty("fieldOne") String fieldOne, @JsonProperty("fieldTwo") String fieldTwo) {
            this.fieldOne = fieldOne;
            this.fieldTwo = fieldTwo;
        }
    }

}
