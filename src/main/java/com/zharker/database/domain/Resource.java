package com.zharker.database.domain;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.collect.Sets;
import com.zharker.database.utils.Constants;
import com.zharker.database.utils.RawJsonDeserializer;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "resource", schema = Constants.DB_SCHEMA)
public class Resource extends IdEntity {

  private String asset;
  private String uri;

  @Type(type = "jsonb")
  @Column(columnDefinition = "json")
  @JsonDeserialize(using = RawJsonDeserializer.class)
  private Serializable attributes;
  private String name;

  @ManyToMany
  @JoinTable(
          foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT),
          name = "resource_verb_relation",
          inverseJoinColumns = @JoinColumn(name = "verb_id"),
          joinColumns = @JoinColumn(name = "resource_id")
  )
  private Set<Verb> verbs = Sets.newHashSet();

}
