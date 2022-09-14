package webserver.basic;

import webserver.basic.StatusCode;

import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
    private String path;
    private StatusCode statuscode;
    private Map<String,Object> params = new HashMap<>();

    public ModelAndView(String path){
        this.path = path;
    }

    public String getPath(){ return path; }
    public Map<String,Object> getParams() { return params; }
    public void setParams(Map<String,Object> params){ this.params = params;}
    public void setPath(String path){ this.path = path;}
}
