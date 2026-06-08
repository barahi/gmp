package org.barahi.infra;


import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Functional {
    public static <IN, OUT> List<OUT> map(Collection<IN> coll, Function<IN, OUT> mappingFunc) {
        return coll.stream().map(mappingFunc).toList();
    }

    public static <T> List<T> filter(Collection<T> coll, Predicate<T> predicate) {
        return coll.stream().filter(predicate).toList();
    }

    public static <IN, K, V> Map<K, V> createMap(Collection<IN> coll, Function<IN, K> keyFunc, Function<IN, V> valFunc) {
        Map<K, V> results = new HashMap<>();
        coll.forEach(in -> results.put(keyFunc.apply(in), valFunc.apply(in)));
        return results;
    }

    public static <IN, K> Map<K, IN> createMap(Collection<IN> coll, Function<IN, K> keyFunc) {
        return createMap(coll, keyFunc, Function.identity());
    }

    public static <IN, OUT> List<OUT> mapToList(Collection<IN> coll, Function<IN, OUT> mappingFunc) {
        return coll.stream()
          .map(mappingFunc)
          .toList();
    }

    public static <IN, OUT> Set<OUT> mapToSet(Collection<IN> coll, Function<IN, OUT> mappingFunc) {
        return coll.stream()
          .map(mappingFunc)
          .collect(Collectors.toSet());
    }

    public static <IN, OUT, IN_COLL extends Collection<IN>, OUT_COLL extends Collection<OUT>> OUT_COLL
    map(IN_COLL inputCollection, OUT_COLL outputCollection, Function<IN, OUT> mappingFunc) {
        outputCollection.clear();
        inputCollection.stream().map(mappingFunc).forEach(outputCollection::add);
        return outputCollection;
    }

    public static <T> boolean contains(Collection<T> coll, T item) {
        return coll.stream().anyMatch(e -> e.equals(item));
    }
}
