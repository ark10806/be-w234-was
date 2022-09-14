package webserver;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import db.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpParser;
import webserver.basic.ModelAndView;
import webserver.basic.Request;
import webserver.basic.Response;
import webserver.controller.Controller;
import webserver.controller.DefaultController;
import webserver.controller.UserSaveController;


public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private Map<String, Controller> urlMappingMap = new HashMap<>();
    private HttpParser httpParser;
    private Database database;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        initUrlMappingMap();
        database = new Database();
    }

    private void initUrlMappingMap(){
        urlMappingMap.put("GET /user/create",new UserSaveController());
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            HttpParser httpParser = new HttpParser(in,out);
            Request request = httpParser.getProcessedRequestFromHttpRequest();
            Response response = new Response();

            Controller controller = getController(request);

            ModelAndView modelAndView = controller.process(request,response);
            httpParser.setResponseFromModel(modelAndView,request,response);
            httpParser.writeOutputStreamFromResponse(response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (RuntimeException e){
            logger.error(e.getMessage());
        }
    }

    private Controller getController(Request request){
        String str = request.getHttpMethod()+" "+request.getUrl();
        Controller controller = urlMappingMap.get(str);
        if (controller == null){
            controller = new DefaultController();
        }
        return controller;
    }
}
