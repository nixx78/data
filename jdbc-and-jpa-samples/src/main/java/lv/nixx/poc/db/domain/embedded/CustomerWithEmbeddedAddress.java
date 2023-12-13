package lv.nixx.poc.db.domain.embedded;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "CUSTOMER_WITH_EMBEDDED_ADDRESS")
@Data
@Builder
public class CustomerWithEmbeddedAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long customerId;

    @Embedded
    @AttributeOverrides(
            value = {
                    @AttributeOverride(name = "line1", column = @Column(name = "firstAddressLine1")),
                    @AttributeOverride(name = "line2", column = @Column(name = "firstAddressLine2"))
            }
    )
    private AddressEmbeddable firstAddress;

    @Embedded
    @AttributeOverrides(
            value = {
                    @AttributeOverride(name = "line1", column = @Column(name = "secondAddressLine1")),
                    @AttributeOverride(name = "line2", column = @Column(name = "secondAddressLine2"))
            }
    )
    private AddressEmbeddable secondAddress;

}
