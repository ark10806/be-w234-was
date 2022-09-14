package http.response.textresponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileResponseBody implements TextResponseBody{

    private File file;

    public FileResponseBody(String filePath){
        this.file = new File(filePath);
    }

    @Override
    public InputStream getInputStream() {
        try {
            return new FileInputStream(this.file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long getContentLength() {
        return this.file.length();
    }
}
