package com.zharker.database.domain;

import com.zharker.database.utils.Constants;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "resource_verb_relation", schema = Constants.DB_SCHEMA)
public class ResourceVerbRelation extends BaseEntity {
  private UUID resourceId;
  private UUID verbId;
}
