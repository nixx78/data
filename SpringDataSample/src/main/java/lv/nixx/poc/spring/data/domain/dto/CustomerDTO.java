package lv.nixx.poc.spring.data.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = "id")
public class CustomerDTO {

    private Long id;
    private String name;
    private Collection<AddressDTO> address = new ArrayList<>();

    public void addAddress(AddressDTO a) {
        this.address.add(a);
    }

}
