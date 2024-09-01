package lv.nixx.poc.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class CustomerSearch {

    private final String name;
    private final String type;

    @JsonCreator
    public CustomerSearch(String name, String type) {
        this.name = name;
        this.type = type;
    }


}
