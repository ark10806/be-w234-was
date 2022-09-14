package webserver.basic;

import java.util.HashMap;
import java.util.Map;

import static webserver.basic.StatusCode.OK;

public class Response {
    private String protocol="";
    private StatusCode statusCode=OK;
    private Map<String,String> headers=new HashMap<>();
    private byte[] body;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }



    public Response(String protocol, StatusCode statusCode, Map<String, String> headers, byte[] body) {
        this.protocol = protocol;
        this.statusCode = statusCode;
        this.headers = headers;
        this.body = body;
    }

    public Response(){}

}
