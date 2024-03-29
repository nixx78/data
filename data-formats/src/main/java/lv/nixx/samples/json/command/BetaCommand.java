package lv.nixx.samples.json.command;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class BetaCommand extends Command<BetaCommand.Payload> {

    public BetaCommand() {
        super("BetaCommand", new Payload());
    }

    public BetaCommand withF1(String f1) {
        getPayload().setF1(f1);
        return this;
    }

    public BetaCommand withF2(String f2) {
        getPayload().setF2(f2);
        return this;
    }

    public BetaCommand withF3(String f3) {
        getPayload().setF3(f3);
        return this;
    }


    @Getter
    @Setter
    @ToString
    static public class Payload {
        String f1;
        String f2;
        String f3;
    }

}
