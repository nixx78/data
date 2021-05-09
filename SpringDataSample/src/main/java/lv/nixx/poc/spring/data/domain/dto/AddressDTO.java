package lv.nixx.poc.spring.data.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AddressDTO {
    private Long id;
    private String line1;
    private String line2;

    public boolean notEmpty() {
        return line1 != null && line2 != null;
    }
}
