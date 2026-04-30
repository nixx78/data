package lv.nixx.poc.orm;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BATCH_DATA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityForBatchSave {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen")
    @SequenceGenerator(name = "seq_gen", sequenceName = "my_seq", allocationSize = 50)
    @Column(name = "id")
    private Long id;

    @Column(name = "sValue")
    private String value;
}
