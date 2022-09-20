package http;

import java.util.HashMap;
import java.util.Map;

public class Body {
    public Map<String, String> params = new HashMap<>();

    public void parseParams(String queryString) {
        for (String param : queryString.split("&")) {
            String[] kv = param.split("\\=");
            if (kv.length == 2) {
                this.params.put(kv[0], kv[1]);
            } else {
                this.params.put(kv[0], null);
            }
        }
    }

    public void prn() {
        System.out.println("Body: " + params);
    }
}
