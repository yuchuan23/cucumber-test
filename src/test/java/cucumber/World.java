package cucumber;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class World {
    private static final Logger logger = LoggerFactory.getLogger(World.class);

    public Context context = new Context();

    public class Context {
        private final Map<String, Object> data = new HashMap<>();

        public Object get(String key) {
            return data.get(key);
        }

        public void set(String key, Object val) {
            data.put(key, val);
        }
    }
}
