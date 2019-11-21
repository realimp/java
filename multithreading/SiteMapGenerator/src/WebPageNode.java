
public class WebPageNode implements Comparable {
    private String urlString;
    private int level;

    public WebPageNode(String urlString, int parentLevel) {
        this.urlString = urlString;
        this.level = ++parentLevel;
    }

    public String getUrlString() {
        return urlString;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public int compareTo(Object o) {
        if (this == o) {
            return 0;
        }
        WebPageNode webPageNode = (WebPageNode) o;
        return urlString.compareTo(webPageNode.urlString);
    }
}
