package utils.helpers;

import org.junit.Assert;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyManager {

    private Map<String, Properties> map = new HashMap<>();

    public static PropertyManager propertyManager = new PropertyManager();

    public PropertyManager() {
        loadProperties("env.properties");
    }

    private void loadProperties(String fileName) {
        try {
            Properties props = new Properties();
            props.load(getClass().getClassLoader().getResourceAsStream("properties/"+ fileName));
            map.put(fileName, props);
        } catch (IOException e) {
            Assert.fail("Error loading properties file: " + fileName + " - " + e.getMessage());
        }
    }

    public String getProperty(String file, String key) {
        Properties props = map.get(file);
        if (props != null) {
            return props.getProperty(key);
        }
        return null;
    }
}