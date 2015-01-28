package com.github.hokutomc.lib.function;

/**
 * Created by user on 2015/01/27.
 */
public final class HT_FunctionUtil {
    private HT_FunctionUtil () {}

    public static <T> HT_Predicate<T> and (final HT_Predicate<? super T> a, final HT_Predicate<? super T> b) {
        return new HT_Predicate<T>() {
            @Override
            public boolean test (T t) {
                return a.test(t) && b.test(t);
            }
        };
    }

    public static <T> HT_Predicate<T> or (final HT_Predicate<? super T> a, final HT_Predicate<? super T> b) {
        return new HT_Predicate<T>() {
            @Override
            public boolean test (T t) {
                return a.test(t) || b.test(t);
            }
        };
    }
}
