package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.basic.HttpMethod;
import webserver.basic.ModelAndView;
import webserver.basic.Request;
import webserver.basic.Response;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import static util.Parser.StringDivideAndCheckNum;
import static util.Parser.getParamsFromString;

public class HttpParser {

    private static final Logger logger = LoggerFactory.getLogger(HttpParser.class);
    private BufferedReader br;
    private DataOutputStream dos;
    public HttpParser(InputStream in, OutputStream out) throws IOException {
        br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        dos = new DataOutputStream(out);
    }

    public void setResponseFromModel(ModelAndView model, Request request, Response response) throws IOException {
        byte[] body = Files.readAllBytes(new File("./webapp" + model.getPath()).toPath());
        response.setProtocol(request.getProtocol());
        response.getHeaders().put("Content-Type","text/html;charset=utf-8");
        /*
        if (request.getHeaders().get("Accept").contains("text/css")){
            response.getHeaders().put("Content-Type", "text/css");
        }
        */

        if (request.getUrl().contains(".css")) {
            response.getHeaders().put("Content-Type", "text/css");
        }

        response.setBody(body);
        response.getHeaders().put("content-Length", String.valueOf(body.length));
    }

    public void writeOutputStreamFromResponse(Response response) throws IOException{
        try{
            dos.writeBytes(response.getProtocol()+" "+
                              response.getStatusCode().getValue()+" "+
                              response.getStatusCode().getDescription() + "\r\n");

            response.getHeaders().forEach((key,value)-> {
                try {
                    dos.writeBytes(key+": "+value+" "+"\r\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            dos.writeBytes("\r\n");
            dos.write(response.getBody(), 0, response.getBody().length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


    public Request getProcessedRequestFromHttpRequest() throws IOException {

        String line = br.readLine();
        logger.debug("request line : {}", line);
        RequestLineData requestLineData = getRequestDataFromRequestLine(line);
        Map<String, String> headers = getHeadersFromInputStream();
        String body = getBodyFromInputStream();
        return new Request(requestLineData.getHttpMethod(), requestLineData.getUrl(), requestLineData.getParams(), headers, body, requestLineData.getProtocol());
    }

    private RequestLineData getRequestDataFromRequestLine(String line) {
        String[] request = StringDivideAndCheckNum(line,"\\s",3);

        if (request[1].contains("?")) {
            String[] req = request[1].split("\\?");
            return new RequestLineData(HttpMethod.valueOf(request[0]), req[0], getParamsFromString(req[1]), request[2]);
        }
        return new RequestLineData(HttpMethod.valueOf(request[0]), request[1], request[2]);
    }

    public Map<String, String> getHeadersFromInputStream() throws IOException {
        Map<String, String> headers = new HashMap<>();
        String line = br.readLine();
        while (!"".equals(line)) {
            if (line == null) {
                logger.error("header error");
                throw new IllegalArgumentException("");
            }
            logger.debug("header : {}", line);
            String[] header = line.split(":", 2);
            headers.put(header[0].trim(), header[1].trim());
            line = br.readLine();
        }
        return headers;
    }

    public String getBodyFromInputStream() throws IOException {
        return "";
    }

    public class RequestLineData {
        private HttpMethod httpMethod;
        private String url;
        private Map<String,String> params;
        private String protocol;

        public RequestLineData(HttpMethod httpMethod, String url, Map<String,String> params, String protocol){
            this.httpMethod = httpMethod;
            this.url = url;
            this.params = params;
            this.protocol = protocol;
        }

        public RequestLineData(HttpMethod httpMethod, String url, String protocol){
            this.httpMethod = httpMethod;
            this.url = url;
            this.params = new HashMap<String,String>();
            this.protocol = protocol;
        }

        public HttpMethod getHttpMethod() {return httpMethod;}
        public String getUrl(){ return url; }
        public Map<String,String> getParams() { return params; }
        public String getProtocol(){ return protocol;}
    }
}
