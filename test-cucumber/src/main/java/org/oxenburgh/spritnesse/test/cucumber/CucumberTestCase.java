package org.oxenburgh.spritnesse.test.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"}, monochrome = true)
public class CucumberTestCase {
}
