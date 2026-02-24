package lv.nixx.samples.json.domain;

import lombok.Builder;

@Builder
public record GenericFields(
        String gField1,
        String gField2
) {
}
