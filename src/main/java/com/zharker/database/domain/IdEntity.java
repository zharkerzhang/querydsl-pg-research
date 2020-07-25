package com.zharker.database.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.util.StringUtils;

import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@SuperBuilder
public abstract class IdEntity extends BaseEntity {

    protected String id;

    public void setId() {
        if (StringUtils.isEmpty(id)) {
            id = UUID.randomUUID().toString();
        }
    }
}
