package lv.nixx.samples.json.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Person {
    private int id;
    private String name;
    private Date dateOfBirth;
    private BigDecimal salary;
}
