package org.adscale.spritnesse;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import javax.annotation.Resource;

@SuppressWarnings("SpringJavaAutowiringInspection")
@ContextConfiguration("classpath:/spring.xml")
public class Database_AppVerify extends AbstractJUnit4SpringContextTests {

    @Resource
    BasicDataSource masterDataSource;


    @Test
    public void demo() {
        new JdbcTemplate(masterDataSource).update("truncate account cascade");
    }
}
