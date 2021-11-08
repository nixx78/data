package lv.nixx.samples.json.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonPropertyOrder({"name", "id"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Customer {


    private final Long id;
    private final String name;
    private final String type;

    private final AdditionalProperties additionalProperties = new AdditionalProperties();

    @JsonCreator
    public Customer(@JsonProperty("id") Long id, @JsonProperty("name") String name, @JsonProperty("type") String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public void addAdditionalProperty(String key, String value) {
        additionalProperties.addProperty(key, value);
    }


}
