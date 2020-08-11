package com.rest.spareTypesOfIntegrationTests;

import com.rest.Controller.PersonController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@ActiveProfiles("it")
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationContextTest {

    @Autowired
    private PersonController personController;

    @Test
    public void contextLoadsIT() {
        assertNotNull(personController);
    }
}