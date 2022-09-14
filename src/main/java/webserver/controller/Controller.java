package webserver.controller;

import webserver.basic.ModelAndView;
import webserver.basic.Request;
import webserver.basic.Response;

public interface Controller {
    ModelAndView process(Request request, Response response);
}
