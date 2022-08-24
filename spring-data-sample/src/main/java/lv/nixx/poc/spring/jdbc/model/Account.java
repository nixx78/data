package lv.nixx.poc.spring.jdbc.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Collection;

/*
 * Entity объект, для маппинга используются аннотации из Spring-Data-Relational
 * */

@Data
@Accessors(chain = true)
@Table("ACCOUNT_TBL")
@EqualsAndHashCode(of="id")
public class Account implements Persistable {

    @Id
    @Column("ID")
    private String accountId;

    @Transient
    private boolean isNew;

    @Column("NAME")
    private String name;

    @MappedCollection(idColumn = "ACCOUNT_ID", keyColumn = "ID")
    private Collection<Transaction> transactions;

    @Override
    public Object getId() {
        return accountId;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
