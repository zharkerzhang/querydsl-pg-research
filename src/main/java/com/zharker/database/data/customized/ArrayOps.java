package com.zharker.database.data.customized;

import com.querydsl.core.types.Operator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ArrayOps implements Operator {

    CONTAINS_STR(Boolean.class,
            String.class,
            "transform_string_array_str({0}, {1}) = true",
            "transform_string_array_str",
            new SQLFunctionTemplate(StandardBasicTypes.BOOLEAN, "?1 @> array[?2]::text[]")),

    CONTAINS_INT(Boolean.class,
            Integer.class,
            "transform_int_array_str({0}, {1}) = true",
            "transform_int_array_str",
            new SQLFunctionTemplate(StandardBasicTypes.BOOLEAN, "?1 @> array[?2]::int[]"));


    private final Class<?> type;
    private final Class<?> paramType;
    private final String queryDslPattern;
    private final String functionName;
    private final SQLFunction sqlFunction;

    static ArrayOps getByParamType(Class<?> clazz) {

        return Arrays.stream(ArrayOps.values()).filter(value -> value.paramType.equals(clazz))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("clazz %s is not supported", clazz)));
    }

    @Override
    public Class<?> getType() {
        return type;
    }


}