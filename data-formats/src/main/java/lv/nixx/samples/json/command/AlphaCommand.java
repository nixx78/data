package lv.nixx.samples.json.command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AlphaCommand extends Command<AlphaCommand.Payload>{

    @JsonCreator
    public AlphaCommand(@JsonProperty("payload.fieldOne") String fieldOne,  @JsonProperty("payload.fieldTwo") String fieldTwo ) {
        super("AlphaCommand", new Payload(fieldOne, fieldTwo));
    }

    @Getter
    @ToString
    static class Payload {
        String fieldOne;
        String fieldTwo;

        @JsonCreator
        Payload(@JsonProperty("fieldOne") String fieldOne, @JsonProperty("fieldTwo") String fieldTwo) {
            this.fieldOne = fieldOne;
            this.fieldTwo = fieldTwo;
        }
    }

}
