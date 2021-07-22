package cucumber;

import java.util.List;
import java.util.ArrayList;
import org.apache.commons.io.IOUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        logger.info("Hello world");
    }

    public static String getData(String fileName) throws Exception {
        return IOUtils.toString(new Main().getClass().getClassLoader().getResourceAsStream(fileName), "UTF-8");
    }
}
