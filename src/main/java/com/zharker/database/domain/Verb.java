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
@Table(name = "verb", schema = Constants.DB_SCHEMA)
public class Verb extends IdEntity{

  private String name;

  @Type(type = "jsonb")
  @Column(columnDefinition = "json")
  @JsonDeserialize(using = RawJsonDeserializer.class)
  private Serializable attributes;

  @ManyToMany(mappedBy = "verbs")
  private Set<Resource> resources = Sets.newHashSet();
}
