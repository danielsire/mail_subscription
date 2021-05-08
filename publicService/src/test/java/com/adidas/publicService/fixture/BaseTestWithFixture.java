package com.adidas.publicService.fixture;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.BeforeClass;

public class BaseTestWithFixture {

    protected static String FIXTURES_PATH = "com.adidas.publicService.fixture";

    @BeforeClass
    public static void beforeTestClass() {
        FixtureFactoryLoader.loadTemplates(FIXTURES_PATH);
    }
}
