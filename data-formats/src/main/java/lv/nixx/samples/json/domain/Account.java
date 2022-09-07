package lv.nixx.samples.json.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = {"id", "number", "name"})
public class Account {

    private final Long id;
    private final String number;
    private final String name;

    @JsonCreator
    public Account(@JsonProperty("id") Long id, @JsonProperty("number") String number, @JsonProperty("name") String name) {
        this.id = id;
        this.number = number;
        this.name = name;
    }

}
