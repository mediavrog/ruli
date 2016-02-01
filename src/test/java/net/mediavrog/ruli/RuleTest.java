package net.mediavrog.ruli;

import java.lang.reflect.*;

import org.junit.Test;

import static org.junit.Assert.*;

public class RuleTest {
    @Test
    public void provides_evaluate_interface() throws Exception {
        class YesManRule extends Rule {
            @Override
            public boolean evaluate() {
                return true;
            }
        }

        Method m = YesManRule.class.getMethod("evaluate");
        assertEquals(m.getName(), "evaluate");
    }
}
