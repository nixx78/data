package lv.nixx.poc.db.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class CustomerWithType {

    private Long id;
    private String firstName;
    private String lastName;
    private String typeId;
    private String description;

}
