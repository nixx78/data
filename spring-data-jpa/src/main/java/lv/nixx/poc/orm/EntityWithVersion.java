package lv.nixx.poc.orm;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "ENTITY_WITH_VERSION")
@Getter
@Data
@NoArgsConstructor
@ToString
public class EntityWithVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "data")
    private String data;

    // Данная аннотация нужна для работы Optimistic/Pessimistic Lock
    @Version
    @Column(name = "version")
    private Long version;

    public EntityWithVersion(String data) {
        this.data = data;
    }
}
