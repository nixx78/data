package lv.nixx.samples.json.domain;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonRootName("additionalProperties")
public class AdditionalProperties {

    private Map<String, String> additionalProperties = new HashMap<>();

    @JsonAnySetter
    public void addProperty(String key, String value) {
        additionalProperties.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, String> getAdditionalProperties() {
        return additionalProperties;
    }

    public String getProperty(String key) {
        return additionalProperties.get(key);
    }

}
