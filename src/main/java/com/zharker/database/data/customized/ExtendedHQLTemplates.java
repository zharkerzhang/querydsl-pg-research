package com.zharker.database.data.customized;

import com.querydsl.jpa.HQLTemplates;

public class ExtendedHQLTemplates extends HQLTemplates {
    public static final ExtendedHQLTemplates DEFAULT = new ExtendedHQLTemplates();

    public ExtendedHQLTemplates() {
        super();

        for (JsonbOps ops : JsonbOps.values()) {
            setPrecedence(Precedence.COMPARISON, ops);
            add(ops, ops.getQueryDslPattern());
        }

        for (ArrayOps ops : ArrayOps.values()) {
            setPrecedence(Precedence.COMPARISON, ops);
            add(ops, ops.getQueryDslPattern());
        }
    }
}