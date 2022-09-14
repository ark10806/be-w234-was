package webserver.controller;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import webserver.basic.ModelAndView;
import webserver.basic.Request;
import webserver.basic.Response;
import webserver.basic.StatusCode;

import java.util.Map;

public class UserSaveController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(UserSaveController.class);
    //private static Database repository = Database.getDatabase();
    private UserService userService = new UserService();
    @Override
    public ModelAndView process(Request request,Response response){
        Map<String,String> params = request.getParams();
        User user = new User(params.get("userId"),params.get("password"),params.get("name"),params.get("email"));

        userService.SignUp(user);
        
        ModelAndView modelAndView = new ModelAndView("/index.html");
        modelAndView.getParams().put("User",user);
        response.setStatusCode(StatusCode.FOUND);

        logger.debug("userId: " + user.getUserId());

        return modelAndView;
    }
}
