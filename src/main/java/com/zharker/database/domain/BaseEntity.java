package com.zharker.database.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vladmihalcea.hibernate.type.array.EnumArrayType;
import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@SuperBuilder
@TypeDefs({
        @TypeDef(
                name = "jsonb",
                typeClass = JsonBinaryType.class
        ),
        @TypeDef(
                name = "string-array",
                typeClass = StringArrayType.class
        ),
        @TypeDef(
                name = "int-array",
                typeClass = IntArrayType.class
        ),
        @TypeDef(
                name = "enum-array",
                typeClass = StringArrayType.class
        )
}
)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "t_id", columnDefinition = "uuid", updatable = false)
    protected UUID tId;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdAt;


    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    protected Date modifiedAt;

    @JsonIgnore
    public UUID getTId() {
        return tId;
    }

    @JsonIgnore
    public Date getCreatedAt() {
        return createdAt;
    }

    @JsonIgnore
    public Date getModifiedAt() {
        return modifiedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(tId, that.tId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tId);
    }
}
