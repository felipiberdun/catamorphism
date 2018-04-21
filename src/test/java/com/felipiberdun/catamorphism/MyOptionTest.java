package com.felipiberdun.catamorphism;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static com.felipiberdun.catamorphism.MyOptionBuilder.none;
import static com.felipiberdun.catamorphism.MyOptionBuilder.some;
import static org.junit.Assert.*;

public class MyOptionTest {

    private static final String DUMMY_STRING = "abc";

    @Test
    public void mapNotEmptyOptionShouldModifyTheValue() {
        final Integer expectedValue = 13;

        assertEquals(expectedValue, some("Hello, Option").map(String::length).get());
    }

    @Test(expected = IllegalStateException.class)
    public void mapEmptyOptionShouldDoNothingAndStillThrowException() {
        MyOptionBuilder.<String>none()
                .map(String::length)
                .get();
    }

    @Test
    public void flatMapNotEmptyOptionShouldTransformTheValue() {
        final String originalValue = "my option";
        final String expectedValue = "MY OPTION";

        assertEquals(expectedValue, some(originalValue).flatMap(s -> some(s.toUpperCase())).get());
        assertEquals(expectedValue, some(originalValue).map(String::toUpperCase).flatMap(MyOptionBuilder::some).get());
    }

    @Test(expected = IllegalStateException.class)
    public void flatMapEmptyOptionShouldDoNothingAndStillThrowException() {
        none().flatMap(MyOptionBuilder::some).get();
    }

    @Test
    public void getOrElseNotEmptyOptionShouldReturnOriginalValue() {
        final Long originalValue = 99L;
        final Long anotherValue = 2L;

        assertEquals(originalValue, some(originalValue).getOrElse(anotherValue));
    }

    private Function<Long, Boolean> isBiggerThan(final Long limit) {
        return (l) -> l > limit;
    }

    @Test
    public void getOrElseEmptyOptionShouldReturnTheOtherValue() {
        final Long anotherValue = 3L;

        assertEquals(anotherValue, none().getOrElse(anotherValue));
    }

    @Test
    public void filterNotEmptyOptionShouldHaveValueIfFulfillTheCondition() {
        final Long originalValue = 25L;
        final Long limit = 15L;

        assertTrue(some(originalValue).filter(isBiggerThan(limit)).isDefined());
    }

    @Test
    public void filterNotEmptyOptionShouldBeEmptyIfNotFulfillTheCondition() {
        final Long originalValue = 25L;
        final Long limit = 50L;

        assertTrue(some(originalValue).filter(isBiggerThan(limit)).isEmpty());
    }

    @Test
    public void filterEmptyOptionShouldReturnEmpty() {
        final Long limit = 0L;

        assertTrue(MyOptionBuilder.<Long>none().filter(isBiggerThan(limit)).isEmpty());
    }

    @Test
    public void forEachForNotEmptyOptionShouldExecuteSideEffect() {
        final AtomicInteger counter = new AtomicInteger(0);

        some(DUMMY_STRING).forEach(s -> counter.incrementAndGet());
        assertEquals(1, counter.get());
    }

    @Test
    public void forEachForEmptyOptionShouldNotExecuteSideEffect() {
        final AtomicInteger counter = new AtomicInteger(0);

        none().forEach((a) -> counter.incrementAndGet());
        assertEquals(0, counter.get());
    }

    @Test
    public void isDefinedNotEmptyOptionShouldBeTrue() {
        assertTrue(some(DUMMY_STRING).isDefined());
    }

    @Test
    public void isDefinedForEmptyOptionShouldBeFalse() {
        assertFalse(none().isDefined());
    }

    @Test
    public void isEmptyForNotEmptyOptionShouldBeFalse() {
        assertFalse(some(DUMMY_STRING).isEmpty());
    }

    @Test
    public void isEmptyForEmptyOptionShouldBeTrue() {
        assertTrue(none().isEmpty());
    }

    @Test
    public void getFromNotEmptyOptionShouldReturnSomething() {
        final Long numericValue = 1L;
        final LocalDateTime localDateTimeValue = LocalDateTime.of(2018, 4, 16, 16, 33, 0);

        assertEquals(DUMMY_STRING, some(DUMMY_STRING).get());
        assertEquals(numericValue, some(numericValue).get());
        assertEquals(localDateTimeValue, some(localDateTimeValue).get());
    }

    @Test(expected = IllegalStateException.class)
    public void getFromEmptyOptionShouldThrowException() {
        none().get();
    }

    @Test
    public void orElseForNotEmptyOptionShouldReturnTheOriginalOption() {
        assertEquals(DUMMY_STRING, some(DUMMY_STRING).orElse(some("Another Option Value")).get());
    }

    @Test
    public void orElseForEmptyOptionShouldReturnTheOtherOption() {
        assertEquals(DUMMY_STRING, none().orElse(some(DUMMY_STRING)).get());
    }

    @Test
    public void toListForNotEmptyOptionShouldReturnAListWithSingleValue() {
        final List<String> list = some(DUMMY_STRING).toList();

        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(DUMMY_STRING, list.get(0));
    }

    @Test
    public void toListForEmptyOptionShouldReturnAnEmptyList() {
        final List<String> list = MyOptionBuilder.<String>none().toList();

        assertNotNull(list);
        assertEquals(0, list.size());
    }

    @Test
    public void iteratorForNotEmptyOptionShouldHaveASingleValue() {
        final Iterator<String> iterator = some(DUMMY_STRING).iterator();

        assertNotNull(iterator);
        assertTrue(iterator.hasNext());
        assertEquals(DUMMY_STRING, iterator.next());
    }

    @Test
    public void iteratorForEmptyOptionShouldHaveNoValue() {
        final Iterator<String> iterator = MyOptionBuilder.<String>none().iterator();

        assertNotNull(iterator);
        assertFalse(iterator.hasNext());
    }

}
