package net.mediavrog.ruli;

import java.util.ArrayList;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValueTest {
    @Test
    public void boxes_simple_value() throws Exception {
        assertEquals(new Value<Integer>(){

            @Override
            public Integer get() {
                return 10;
            }
        }.get().intValue(), 10);
    }

    @Test
    public void as_boxes_simple_value() throws Exception {
        assertEquals(Value.as(10).get().intValue(), 10);
    }

    @Test
    public void get_method_returns_boxed_value() throws Exception {
        assertEquals(Value.as(true).get(), true);

        assertEquals(Value.as("OK").get(), "OK");

        assertEquals(Value.as(new ArrayList<Integer>()).get(), new ArrayList<Integer>());

        assertEquals(new Value<String>(){
            @Override
            public String get() {
                return "Hello";
            }
        }.get(), "Hello");
    }

    @Test
    public void describe_returns_boxed_class_name() throws Exception {
        assertEquals("Boolean", Value.as(true).describe());

        assertEquals("String", Value.as("OK").describe());

        assertEquals("ArrayList", Value.as(new ArrayList<Integer>()).describe());
    }
}
