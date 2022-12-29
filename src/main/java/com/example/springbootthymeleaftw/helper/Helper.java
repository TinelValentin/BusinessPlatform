package com.example.springbootthymeleaftw.helper;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Helper {
    public static <K, V> Map<K, V> createMapFromLists(List<K> keys, List<V> values) {
        Iterator<K> keyIter = keys.iterator();
        Iterator<V> valIter = values.iterator();
        return IntStream.range(0, keys.size()).boxed()
                .collect(Collectors.toMap(_i -> keyIter.next(), _i -> valIter.next()));
    }
}
