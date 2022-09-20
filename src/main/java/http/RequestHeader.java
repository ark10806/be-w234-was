package http;

import java.util.HashMap;
import java.util.Map;

public class RequestHeader {
    public String method;
    public String url;
    public Map<String, String> params = new HashMap<>();
    public String httpVersion;
    public HashMap<String, String> entity = new HashMap<>();

    // 1개의 요청 -> 1개의 HttpPacket -> 1개의 스레드 이므로 clear로 재활용을 기대하기는 어려울듯?
    // 아마도 필요없는 메서드
    public void clear() {
        method = "";
        url = "";
        httpVersion = "";
        entity.clear();
    }

    public void setUrl(String path) {
        String[] lineitem = path.split("\\?");
        this.url = lineitem[0];
        if (lineitem.length == 2) {
            parseParams(lineitem[1]);
        }
    }

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
        System.out.printf("%s %s %s\n", method, url, httpVersion);
        entity.forEach((key, value) -> {
            System.out.println(key + " " + value);
        });
    }
}
