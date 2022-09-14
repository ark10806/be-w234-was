package http.response;

import java.io.InputStream;

public interface ResponseBody {
    public InputStream getInputStream();
    public long getContentLength();
}
