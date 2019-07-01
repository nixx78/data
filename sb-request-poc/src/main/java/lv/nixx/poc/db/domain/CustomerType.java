package lv.nixx.poc.db.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CustomerType")
@Data
@ToString
@EqualsAndHashCode(of= {"id", "description"})
public class CustomerType {

    @Id
    private String id;

    private String description;

    public CustomerType(){}

    public CustomerType(String type, String description) {
        this.id = type;
        this.description = description;
    }

}
