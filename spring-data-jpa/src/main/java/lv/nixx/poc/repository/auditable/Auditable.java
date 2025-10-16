package lv.nixx.poc.repository.auditable;

import java.time.LocalDateTime;

public interface Auditable {

    void setUpdatedBy(String user);

    String getUpdatedBy();

    void setUpdatedAt(LocalDateTime timestamp);

    LocalDateTime getUpdatedAt();

}
