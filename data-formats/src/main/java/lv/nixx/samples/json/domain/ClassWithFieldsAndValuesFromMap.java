package lv.nixx.samples.json.domain;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class ClassWithFieldsAndValuesFromMap {

    // Пример, класса, поля которого в JSON записывается на одном уровне с полями из Map

    private final Long id;
    private final String description;

    private final Map<String, Holder> map = new HashMap<>();

    @JsonAnyGetter
    public Map<String, Holder> getMap() {
        return map;
    }

    public ClassWithFieldsAndValuesFromMap put(String field, BigDecimal value, String status) {
        this.map.put(field, new Holder(value, status));
        return this;
    }

    @AllArgsConstructor
    @Getter
    static class Holder {
        final BigDecimal value;
        final String status;
    }

}
