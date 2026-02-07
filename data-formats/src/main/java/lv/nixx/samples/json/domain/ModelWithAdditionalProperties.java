package lv.nixx.samples.json.domain;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)

@ToString
public class ModelWithAdditionalProperties {

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String surname;

    private final Map<String, Object> additionalProperties = new HashMap<>();

    @JsonAnySetter
    public void addAdditionalProperty(String key, Object value) {
        additionalProperties.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }
}
