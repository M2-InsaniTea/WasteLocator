package com.m2dl.helloandroid.wastelocator.backend.utils;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.googlecode.objectify.Ref;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by flemoal on 25/01/16.
 */
public class Deref {
    public static class Func<T> implements Function<Ref<T>, T> {
        public static Func<Object> INSTANCE = new Func<Object>();

        @Override
        public T apply(Ref<T> ref) {
            return deref(ref);
        }
    }

    public static <T> T deref(Ref<T> ref) {
        return ref == null ? null : ref.get();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> List<T> deref(Set<Ref<T>> reflist) {
        return Lists.transform(new ArrayList<>(reflist), (Func) Func.INSTANCE);
    }
}

