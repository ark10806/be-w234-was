package http;

public class Cookie {
  public String name;
  public String value;

  public Cookie(String line) {
    String[] lineitem = line.split("=");
    if (lineitem.length >= 2) {
      this.name = lineitem[0];
      this.value = lineitem[1];
    }
  }
}
