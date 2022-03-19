package com.example.entities.audit;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.OffsetDateTime;

public class AuditableListener {

    @PrePersist
    public void prePersist(AuditableEntity e) {
        e.setCreationDate(OffsetDateTime.now());
    }

    @PreUpdate
    public void preUpdate(AuditableEntity e) {
        e.setUpdatedDate(OffsetDateTime.now());
    }
}
