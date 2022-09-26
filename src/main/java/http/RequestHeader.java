package http;

import java.util.HashMap;
import java.util.Map;

// HTTP request header 를 구조화한 클래스
public class RequestHeader {
    public String method;
    public String url;
    public Map<String, String> queryString = new HashMap<>();
    public String httpVersion;
    public HashMap<String, String> entity = new HashMap<>();
    public Cookie cookie;

    // header 에서 Host(URI; FQDN)을 파싱
    public void setUrl(String path) {
        String[] lineitem = path.split("\\?");
        this.url = lineitem[0];
        if (lineitem.length == 2) {
            parseParams(lineitem[1]);
        }
    }

    // header.Host 에서 Querystring 파싱
    public void parseParams(String queryString) {
        for (String param : queryString.split("&")) {
            String[] kv = param.split("\\=");
            if (kv.length == 2) {
                this.queryString.put(kv[0], kv[1]);
            } else {
                this.queryString.put(kv[0], null);
            }
        }
    }

    // 파싱한 querystring 을 출력
    public void prn() {
        System.out.printf("%s %s %s\n", method, url, httpVersion);
        entity.forEach((key, value) -> {
            System.out.println(key + " " + value);
        });
    }
}
