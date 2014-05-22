package org.adscale.spritnesse;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import javax.annotation.Resource;

@ContextConfiguration("classpath:/spring.xml")
public class SpringBean_AppVerify extends AbstractJUnit4SpringContextTests {

    @Resource(name = "thingy")
    String thingy;


    @Test
    public void demo() {
        assertNotNull(thingy);
    }
}
