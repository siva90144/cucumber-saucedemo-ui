package com.saucedemo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class GetConfig {
    private static final Logger log = LoggerFactory.getLogger(GetConfig.class.getName());

    @SuppressWarnings("finally")
    public static String getConfigProperty(String property) {
        String propertyValue = "";
        // If no inputs passed in, look for a configuration file
        String configFile = System.getProperty("user.dir") + "/src/test/resources/config/Configuration.cnf";
        try {
            InputStream configFileStream = new FileInputStream(configFile);
            Properties p = new Properties();
            p.load(configFileStream);
            configFileStream.close();

            propertyValue = (String) p.get(property);

        } catch (Exception e) { // IO or NullPointer exceptions possible in
            // block above
            log.info(String.format("Error reading Config file! please fix and rebuild!"));
            e.printStackTrace();
            System.exit(1);
        } finally {
            return propertyValue.trim();
        }
    }
}
