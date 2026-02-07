package org.barahi.infra;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class Functional {
    public static <IN, OUT> List<OUT> map(Collection<IN> coll, Function<IN, OUT> mappingFunc) {
        return coll.stream().map(mappingFunc).toList();
    }
}
