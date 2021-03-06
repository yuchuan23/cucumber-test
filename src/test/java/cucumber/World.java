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
        public static final String LAST_RESPONSE_STATUS = "last_response_status";
        public static final String LAST_RESPONSE_BODY = "last_response_body";
        public static final String LAST_RESPONSE_JSON = "last_response_json";

        public Object get(String key) {
            return data.get(key);
        }

        public void set(String key, Object val) {
            data.put(key, val);
        }
    }
}
