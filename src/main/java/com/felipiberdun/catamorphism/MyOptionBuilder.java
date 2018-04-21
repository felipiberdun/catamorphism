package com.felipiberdun.catamorphism;

import java.util.function.Function;
import java.util.function.Supplier;

public class MyOptionBuilder {

    private MyOptionBuilder() {
    }

    public static <A> MyOption<A> some(final A value) {
        return new MyOption<A>() {
            @Override
            protected <X> X cata(final Function<A, X> some, final Supplier<X> none) {
                return some.apply(value);
            }
        };
    }

    public static <A> MyOption<A> none() {
        return new MyOption<A>() {
            @Override
            protected <X> X cata(final Function<A, X> some, final Supplier<X> none) {
                return none.get();
            }
        };
    }

}
