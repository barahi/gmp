package org.barahi.infra;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Functional {
    public static <IN, OUT> List<OUT> map(Collection<IN> coll, Function<IN, OUT> mappingFunc) {
        return coll.stream().map(mappingFunc).toList();
    }

    public static <IN, INTERMEDIATE1, OUT> List<OUT> map(Collection<IN> coll, Function<IN, INTERMEDIATE1> mappingFunc1, Function<INTERMEDIATE1, OUT> mappingFunc2) {
        return coll.stream().map(mappingFunc1).map(mappingFunc2).toList();
    }

    public static <T> List<T> filter(Collection<T> coll, Predicate<T> predicate) {
        return coll.stream().filter(predicate).toList();
    }
}
