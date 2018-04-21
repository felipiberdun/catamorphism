package com.felipiberdun.catamorphism;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.felipiberdun.catamorphism.MyOptionBuilder.none;
import static com.felipiberdun.catamorphism.MyOptionBuilder.some;

public abstract class MyOption<A> {

    //    def cata[X](some: A => X, none: => X): X
    protected abstract <X> X cata(final Function<A, X> some, final Supplier<X> none);

    //    def map[B](f: A => B): MyOption[B] = error("todo")
    public <B> MyOption<B> map(final Function<A, B> mapper) {
        return flatMap((a) -> some(mapper.apply(a)));
    }

    //    def flatMap[B](f: A => MyOption[B]): MyOption[B] = error("todo")
    public <B> MyOption<B> flatMap(final Function<A, MyOption<B>> transformation) {
        return cata(transformation, MyOptionBuilder::none);
    }

    //    def getOrElse[AA >: A](e: => AA): AA = error("todo")
    public A getOrElse(final A anotherValue) {
        return cata((a) -> a, () -> anotherValue);
    }

    //    def filter(p: A => Boolean): MyOption[A] = error("todo")
    public MyOption<A> filter(final Function<A, Boolean> predicate) {
        return cata((a) -> predicate.apply(a) ? this : none(), MyOptionBuilder::none);
    }

    //    def foreach(f: A => Unit): Unit = error("todo")
    public void forEach(final Consumer<A> sideeffect) {
        cata((a) -> {
            sideeffect.accept(a);
            return a;
        }, () -> null);
    }

    //    def isDefined: Boolean = error("todo")
    public boolean isDefined() {
        return cata((a) -> true, () -> false);
    }

    //    def isEmpty: Boolean = error("todo")
    public boolean isEmpty() {
        return !isDefined();
    }

    //    def get: A = error("todo") WARNING: not defined for None
    public A get() {
        return cata((a) -> a, () -> {
            throw new IllegalStateException("This option is empty!");
        });
    }

    //    def orElse[AA >: A](o: MyOption[AA]): MyOption[AA] = error("todo")
    public MyOption<A> orElse(final MyOption<A> anotherValue) {
        return cata((a) -> this, () -> anotherValue);
    }

//    def toLeft[X](right: => X): Either[A, X] = error("todo")

//    def toRight[X](left: => X): Either[X, A] = error("todo")

    //    def toList: List[A] = error("todo")
    public List<A> toList() {
        return cata(Collections::singletonList, Collections::emptyList);
    }

    //    def iterator: Iterator[A] = error("todo")
    public Iterator<A> iterator() {
        return toList().iterator();
    }

}
