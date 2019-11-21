import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.*;

public class SiteMap {
    private String baseUrl;
    private TreeSet<WebPageNode> siteMap;

    public SiteMap(String baseUrl) {
        this.baseUrl = baseUrl;
        this.siteMap = new TreeSet<>();
    }

    public void addNode(WebPageNode node) {
        siteMap.add(node);

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        List<Future<WebPageNode>> results = new ArrayList<>();

        try {
            for (WebPageNode child : getChildren(node.getUrlString())) {
                Callable<WebPageNode> webPageNodeCallable = () -> {
                    Thread.sleep(1000);
                    return new WebPageNode(child.getUrlString(), child.getLevel());
                };
                results.add(executorService.submit(webPageNodeCallable));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        results.forEach(result -> {
            WebPageNode webPageNode = null;
            try {
                webPageNode = result.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            addNode(webPageNode);
        });

        executorService.shutdown();
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public TreeSet<WebPageNode> getSiteMap() {
        return siteMap;
    }

    private TreeSet<WebPageNode> getChildren(String urlString){
        Document document = null;

        System.out.println("Получаем данные от " + urlString);
        try {
            document = Jsoup.connect(urlString).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements links = document.getElementsByTag("a");
        TreeSet<WebPageNode> children = new TreeSet<>();
        links.forEach(link -> {
            String url = link.attr("abs:href");
            String urlStringToCompare = urlString;
            String urlStringWww = urlStringToCompare.substring(0, urlStringToCompare.indexOf("/") + 2) + "www." + urlStringToCompare.substring(urlStringToCompare.indexOf("/") + 2);
            if (urlStringToCompare.matches(".*\\.....")){
                urlStringToCompare = urlStringToCompare.substring(0, urlStringToCompare.length() - 5);
            }

            if (urlStringToCompare.matches(".*\\....")){
                urlStringToCompare = urlStringToCompare.substring(0, urlStringToCompare.length() - 5);
            }

            if (url.contains("#") || url.equalsIgnoreCase(urlString + "/") || url.equalsIgnoreCase(urlStringWww + "/")) {
                link = null;
            }

            if (url.contains("?")){
                url = url.substring(0, url.indexOf("?"));
            }

            WebPageNode webPageNode = new WebPageNode(url, 0);
            if (link != null && !siteMap.contains(webPageNode) && (url.contains(urlStringToCompare) || url.contains(urlStringWww))) {
                children.add(webPageNode);
            }
        });

        return children;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(baseUrl + "\n");
        siteMap.forEach(webPageNode -> {
            for (int i = webPageNode.getLevel(); i > 0; i--){
                builder.append("\t");
            }
            builder.append(webPageNode.getUrlString() + "\n");
        });

        return builder.toString();
    }
}