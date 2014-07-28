package somanyfeeds;

import static com.somanyfeeds.Utils.readInputStream;

public class TestUtils {
    public static String loadResource(String filename) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        return readInputStream(classloader.getResourceAsStream(filename));
    }
}
