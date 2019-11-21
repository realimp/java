import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.concurrent.*;

public class Main {

    private static SiteMap map;

    public static void main(String[] args) {

        System.out.println("Введите URL адресс сайта в формате: https://website.ru");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine().trim();

        System.out.println("Введите путь к папке в которую хотите скопировать файлы:");
        String destinationFolder = scanner.nextLine().trim();
        Path destinationFolderPath = Path.of(destinationFolder);
        if (Files.notExists(destinationFolderPath)){
            try {
                Files.createDirectory(destinationFolderPath);
            } catch (IOException ex){
                System.out.println(ex.getMessage());
            }
        }
        System.out.println("Введите имя файла:");
        String fileName = scanner.nextLine().trim();
        map = new SiteMap(userInput);

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        TreeSet<String> mainPageChildren = getChildren(userInput);
        List<Future<WebPageNode>> results = new ArrayList<>();

        mainPageChildren.forEach(child -> {
            Callable<WebPageNode> task = () -> {
                Thread.sleep(1000);
                return  new WebPageNode(child, 0);
            };
            results.add(executorService.submit(task));
            map.addNode(new WebPageNode(child, 0));
        });

        results.forEach(result -> {
            try {
                map.addNode(result.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();

        try (PrintWriter file = new PrintWriter(destinationFolder + "/" + fileName + ".txt")){
            System.out.println("Сохраняем карту сайта в файл...");
            file.println(map.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Карта сайта " + userInput + " сохранена как " + destinationFolder + "/" + fileName + ".txt");
        }
    }

    private static TreeSet<String> getChildren(String urlString){
        Document document = null;

        System.out.println("Получаем данные от " + urlString);
        try {
            document = Jsoup.connect(urlString).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements links = document.getElementsByTag("a");
        TreeSet<String> children = new TreeSet<>();
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

            if (url.contains("#") || url.equalsIgnoreCase(urlStringToCompare) || url.equalsIgnoreCase(urlStringToCompare + "/") || url.equalsIgnoreCase(urlStringWww + "/")) {
                link = null;
            }

            if (url.contains("?")){
                url = url.substring(0, url.indexOf("?"));
            }

            WebPageNode webPageNode = new WebPageNode(url, 0);

            if (link != null && !map.getSiteMap().contains(webPageNode) && (url.contains(urlStringToCompare) || url.contains(urlStringWww))) {
                if (!url.equalsIgnoreCase(urlStringToCompare +"/"))
                children.add(url);
            }
        });

        return children;
    }
}
