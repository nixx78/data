package lv.nixx.samples.json.domain;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public record CustomDTO(
        Long id,

        @JsonUnwrapped
        GenericFields genericFields
) {
}
