import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Document parsedDocument = parseHtml("https://lenta.ru/");
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-YYYY-HH-mm");
        String folderPath = ("data/" + format.format(new Date()));
        Elements elements = parsedDocument.getElementsByTag("img");
        try {
            Files.createDirectory(Paths.get(folderPath));
        } catch (Exception ex){
            ex.printStackTrace();
        }

        System.out.println("Сохраняем изображения...");
        elements.forEach(element -> {
            String imgUrl = element.attributes().get("src");
            if (imgUrl.contains("lenta.ru")){
                String imgName = imgUrl.substring(imgUrl.lastIndexOf("/"));
                String imgFormat = imgName.substring(imgName.lastIndexOf("."));
                saveImage(imgUrl, folderPath + imgName.substring(0, imgName.indexOf(".")), imgFormat.substring(1));
            }
        });
        System.out.println("Изображения со страницы https://lenta.ru/ успешно сохранены в папку:\n" +
                Path.of(folderPath).toAbsolutePath());
    }

    private static Document parseHtml(String url1){
        URL url;
        InputStream is = null;
        BufferedReader br;
        String line;
        StringBuilder sb = new StringBuilder();
        Document parsedHtml = null;

        try {
            System.out.println("Инициализация подключения к " + url1 + " ...");
            url = new URL(url1);
            is = url.openStream();
            System.out.println("Получаем содержимое страницы...");
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null){
                sb.append(line);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
                parsedHtml = Jsoup.parse(sb.toString());
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return parsedHtml;
    }

    private static void saveImage(String webUrl, String savePath, String format){
        URL url;
        try {
            url = new URL(webUrl);
            BufferedImage image = ImageIO.read(url);
            if (image != null){
                File file = new File(savePath + "." + format);
                ImageIO.write(image, format, file);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
