package lv.nixx.samples.json.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Customer {

    private Long id;
    private String name;
    private AdditionalProperties additionalProperties = new AdditionalProperties();

    @JsonCreator
    public Customer(@JsonProperty("id") Long id, @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }

}
