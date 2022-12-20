package com.saucedemo;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features/"},
        glue = {"com.saucedemo.steps"},
        // monochrome = true,
        plugin = {"pretty","html:target/cucumber/cucumber.html",
                "json:target/cucumber-reports/cucumber.json",
                "junit:target/cucumber-reports/cucumber-results.xml",
                "rerun:target/cucumber-reports/rerun.txt"},
        // snippets = SnippetType.CAMELCASE,
        //dryRun = true,
        tags = "@loginIntoAccount"
)

public class Runner {
}
