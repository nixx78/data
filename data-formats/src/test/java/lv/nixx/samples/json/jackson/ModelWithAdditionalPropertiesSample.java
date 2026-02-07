package lv.nixx.samples.json.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import lv.nixx.samples.json.ObjectMapperService;
import lv.nixx.samples.json.domain.ModelWithAdditionalProperties;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ModelWithAdditionalPropertiesSample {

    private final ObjectMapperService om = new ObjectMapperService();

    @Test
    void modelWithAdditionalPropertiesSample() throws JsonProcessingException {

        ModelWithAdditionalProperties initialModel = new ModelWithAdditionalProperties();
        initialModel.setName("Name.Value");
        initialModel.setSurname("Surname.Value");
        initialModel.addAdditionalProperty("add1", "add1.Value");
        initialModel.addAdditionalProperty("add2", "add2.Value");

        final String modelAsJSON = om.writerWithDefaultPrettyPrinter().writeValueAsString(initialModel);

        System.out.println("Model as JSON\n" + modelAsJSON);


        ModelWithAdditionalProperties modelWithAdditionalProperties = om.readValue(modelAsJSON, ModelWithAdditionalProperties.class);

        System.out.println(modelWithAdditionalProperties);

        assertThat(modelWithAdditionalProperties).usingRecursiveComparison().isEqualTo(
                initialModel
        );


    }

}
