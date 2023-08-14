package me.combimagnetron.sunscreen.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public final class Values<T> {
    private final List<T> values = new ArrayList<>();

    @SafeVarargs
    private Values(T... t) {
        this.values.addAll(List.of(t));
    }

    @SafeVarargs
    public static <T> Values<T> of(T... t) {
        return new Values<>(t);
    }

    public Stream<T> stream() {
        return values.stream();
    }

    public Collection<T> values() {
        return ImmutableList.copyOf(values);
    }

    public Values<T> add(T t) {
        values.add(t);
        return this;
    }

}
