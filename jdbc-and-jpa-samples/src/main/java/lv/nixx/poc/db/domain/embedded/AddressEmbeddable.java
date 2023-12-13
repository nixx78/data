package lv.nixx.poc.db.domain.embedded;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressEmbeddable {

    @Column(name = "line1")
    private String line1;

    @Column(name = "line2")
    private String line2;

}
