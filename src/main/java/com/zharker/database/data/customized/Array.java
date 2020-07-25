package com.zharker.database.data.customized;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;

import java.util.stream.Stream;

public class Array extends StringPath {

    public Array(PathMetadata metadata) {
        super(metadata);
    }

    public static Array of(Path<?> path) {
        return new Array(path.getMetadata());
    }

    public <T> BooleanExpression contants(T[] arrayValue) {

        if (arrayValue == null) {
            return Expressions.asBoolean(true).isTrue();
        }
        return Stream.of(arrayValue)
                .map(value -> (BooleanExpression) Expressions.booleanOperation(
                        ArrayOps.getByParamType(value.getClass()),
                        mixin, ConstantImpl.create(value)))
                .reduce(BooleanExpression::or).orElse(Expressions.asBoolean(true).isTrue());
    }

}

