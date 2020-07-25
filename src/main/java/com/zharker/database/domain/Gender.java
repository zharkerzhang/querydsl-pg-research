package com.zharker.database.domain;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum Gender {

    male("nanren"),
    female("nvren");

    private String name;
}
