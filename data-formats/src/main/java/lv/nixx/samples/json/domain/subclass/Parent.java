package lv.nixx.samples.json.domain.subclass;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.*;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.*;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonTypeInfo(use = NAME, include =  EXISTING_PROPERTY, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Child1.class, name = "TYPE_1"),
        @JsonSubTypes.Type(value = Child2.class, name = "TYPE_2") }
)
public abstract class Parent {
    String parentField;
    Type type;

    Parent() {
    }
}

