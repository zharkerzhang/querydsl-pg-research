package com.zharker.database.data.customized;

import org.hibernate.dialect.PostgreSQL95Dialect;

public class ExtendedPostgreSQLDialect extends PostgreSQL95Dialect {

    public ExtendedPostgreSQLDialect() {
        super();

        for (JsonbOps ops : JsonbOps.values()) {
            registerFunction(ops.getFunctionName(), ops.getSqlFunction());
        }

        for (ArrayOps ops : ArrayOps.values()) {
            registerFunction(ops.getFunctionName(), ops.getSqlFunction());
        }
    }

}

