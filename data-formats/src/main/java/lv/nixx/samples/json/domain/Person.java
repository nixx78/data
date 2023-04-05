package lv.nixx.samples.json.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Accessors(chain = true)
public class Person {
    private int id;
    private String name;
    private Date dateOfBirth;
    private LocalDateTime timestamp;
    private BigDecimal salary;
}
