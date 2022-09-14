package http.response;

public class ResponseHeader {
    private final String name;
    private final String value;

    public ResponseHeader(String name, String value){
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString(){
        return this.name + ": " + this.value;
    }
}
