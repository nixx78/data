package lv.nixx.samples.json.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import lv.nixx.samples.json.ObjectMapperService;
import lv.nixx.samples.json.domain.command.AlphaCommand;
import lv.nixx.samples.json.domain.command.BetaCommand;
import lv.nixx.samples.json.domain.command.Command;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandTest {

    final ObjectMapperService om = new ObjectMapperService();

    @Test
    public void alphaCommandTest() throws JsonProcessingException {
        AlphaCommand ac = new AlphaCommand("v1", "v2");

        String j1 = om.writeValueAsString(ac);
        System.out.println(j1);

        assertEquals("AlphaCommand:AlphaCommand.AlphaCommandPayload(fieldOne=v1, fieldTwo=v2)", processJson(j1));
    }

    @Test
    public void betaCommandTest() throws JsonProcessingException {

        BetaCommand bc = new BetaCommand()
                .withF1("f1")
                .withF2("f2")
                .withF3("f3");

        System.out.println("---------------------");
        String j2 = om.writeValueAsString(bc);
        System.out.println(j2);
        assertEquals("BetaCommand:BetaCommand.BetaCommandPayload(f1=f1, f2=f2, f3=f3)", processJson(j2));
    }

    private String processJson(String s) throws JsonProcessingException {
        return switch (om.readValue(s, Command.class)) {
            case AlphaCommand c -> process(c);
            case BetaCommand c -> process(c);
            default -> "Unknown entity";
        };
    }

    private String process(BetaCommand c) {
        return "BetaCommand:" + c.getPayload();
    }

    private String process(AlphaCommand c) {
        return "AlphaCommand:" + c.getPayload();
    }


}
