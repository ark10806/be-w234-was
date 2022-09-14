package webserver;

import java.util.Map;

public class HttpRequest {

    private Method method;
    private String path;
    private String protocol;
    private Map<String, String> parameters;

    public HttpRequest(Method method, String path, String protocol, Map<String, String> parameters) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
        this.parameters = parameters;
    }

    public Method getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
}
