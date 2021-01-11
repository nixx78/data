package lv.nixx.samples.json.domain.action;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class ActionDeserializer extends JsonDeserializer<GenericAction> {

    @Override
    public GenericAction deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);

        Action action = Action.valueOf(node.get("action").asText());
        Entity entity = Entity.valueOf(node.get("entity").asText());
        String data = node.get("data").asText();

        System.out.println("Action [" + action + "] [" + entity + "]");

        GenericAction object = createObject(action, entity);
        object.setAction(action);
        object.setEntity(entity);
        object.setData(data);

        return object;
    }

    private GenericAction createObject(Action action, Entity entity) {

        if (action == Action.ADD && entity == Entity.CARD) {
            return new CardAddAction();
        }

        if (action == Action.REMOVE && entity == Entity.PERSON) {
            return new PersonRemoveAction();
        }

        throw new IllegalStateException("Not possible to create object for:" + action + ":" + entity);
    }

}
