package urlHandler.handler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class StaticHtmlHandler implements UrlHandler{

    @Override
    public byte[] handle(String url) throws IOException {
        return Files.readAllBytes(new File("./webapp" + url).toPath());
    }
}
