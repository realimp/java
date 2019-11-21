import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import com.google.gson.*;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {
    private static TreeSet<Line> readLines = new TreeSet<>();

    public static void main(String[] args) {
        Document parsedDocument = parseHtml("https://ru.wikipedia.org/wiki/Список_станций_Московского_метрополитена");
        Elements tables = parsedDocument.getElementsByClass("standard sortable");
        createJson(tables);
        readJson();
    }

    private static void readJson(){
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonData = (JSONObject) parser.parse(getJsonFile());

            JSONArray linesArray = (JSONArray) jsonData.get("lines");
            parseLines(linesArray);

            JSONObject stationsObject = (JSONObject) jsonData.get("stations");
            parseStations(stationsObject);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private static void parseLines(JSONArray linesArray){
        linesArray.forEach(lineObject -> {
            JSONObject lineJsonObject = (JSONObject) lineObject;
            Line line = new Line(
                    (String) lineJsonObject.get("number"),
                    (String) lineJsonObject.get("name")
            );
            readLines.add(line);
        });
    }

    private static void parseStations(JSONObject stationsObject){
        stationsObject.keySet().forEach(lineNumber -> {


            JSONArray stationsArray = (JSONArray) stationsObject.get(lineNumber);
            stationsArray.forEach(stationObject -> {
                Station station = new Station((String) lineNumber, (String) stationObject);
                readLines.forEach(line -> {
                    if (line.getNumber().equalsIgnoreCase((String) lineNumber)){
                        line.addStation(station);
                    }
                });
            });
        });

        writeLinesInformation();
    }

    private static void writeLinesInformation(){
        readLines.forEach(line -> System.out.println(line.toString() + " - количество станций: " + line.getStations().size()));
    }

    private static String getJsonFile(){
        StringBuilder stringBuilder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Path.of("data/map.json"));
            lines.forEach(line -> stringBuilder.append(line));
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private static void createJson(Elements tables){
        ArrayList<Station> stations = new ArrayList<>();
        TreeSet<Line> lines  = new TreeSet<>();
        HashSet<ArrayList> connections = new HashSet<>();

        tables.forEach(table -> {
            Elements rows = table.select("tr");
            rows.forEach(row -> {
                Elements columns = row.select("td");
                if (columns.size() > 0){
                    Elements linesForStation = columns.get(0).getElementsByClass("sortkey");
                    for (int i = 0; i < linesForStation.size() - 1; i++){
                        String lineNumber = linesForStation.get(i).text();
                        if (lineNumber.charAt(0) == '0') lineNumber = lineNumber.substring(1);
                        lines.add(new Line(lineNumber, columns.get(0).getElementsByTag("a").attr("title")));
                        Station station = new Station(lineNumber, columns.get(1).getElementsByTag("a").get(0).text());
                        stations.add(station);
                        if (!columns.get(3).attr("data-sort-value").equalsIgnoreCase("infinity")){
                            ArrayList<Station> connection = new ArrayList<>();
                            connection.add(station);
                            columns.get(3).getElementsByTag("a").forEach(a -> {
                                String stationName = URLDecoder.decode(a.attr("href"), StandardCharsets.UTF_8)
                                        .replaceAll("_", " ")
                                        .substring(6)
                                        .replaceAll(" \\(.+\\)", "");
                                String stationLine = URLDecoder.decode(
                                        a.getElementsByTag("img").get(0).attr("src"), StandardCharsets.UTF_8)
                                        .replaceAll(".+Moskwa_Metro_Line_", "")
                                        .replaceAll(".svg.png", "");
                                connection.add(new Station(stationLine, stationName));
                            });
                            connections.add(connection);
                        }
                    }
                }
            });
        });

        saveJson(stations, lines, connections);
    }

    private static void saveJson(ArrayList<Station> stations, TreeSet<Line> lines, HashSet<ArrayList> connections){
        JsonObject jsonToWriteToFile = new JsonObject();
        JsonObject jsonStations = new JsonObject();
        JsonArray jsonLines = new JsonArray();
        JsonArray jsonConnections = new JsonArray();

        lines.forEach(line -> {
            stations.forEach(station -> {
                if (station.getLine().equalsIgnoreCase(line.getNumber())) {
                    line.addStation(station);
                }
            });

            JsonArray stationsList = new JsonArray();
            line.getStations().stream().forEach(station -> stationsList.add(station.getName()));


            jsonStations.add(line.getNumber(), stationsList);

            JsonObject jsonLine = new JsonObject();
            jsonLine.add("number", new JsonPrimitive(line.getNumber()));
            jsonLine.add("name", new JsonPrimitive(line.getName()));
            jsonLines.add(jsonLine);
        });

        connections.forEach(connection -> {
            JsonArray connectedStations = new JsonArray();
            connection.forEach(station -> {
                JsonObject stationJson = new JsonObject();
                stationJson.add("line", new JsonPrimitive(((Station)station).getLine()));
                stationJson.add("Name", new JsonPrimitive(((Station)station).getName()));
                connectedStations.add(stationJson);
            });
            jsonConnections.add(connectedStations);
        });

        jsonToWriteToFile.add("stations", jsonStations);
        jsonToWriteToFile.add("connections", jsonConnections);
        jsonToWriteToFile.add("lines", jsonLines);

        try {
            if (Files.notExists(Path.of("data"))){
                Files.createDirectory(Path.of("data"));
            }
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileWriter file = new FileWriter("data/map.json");
            file.write(gson.toJson(jsonToWriteToFile));
            file.flush();
            file.close();

            System.out.println("Схема метро сохранена в файл: " + Path.of("data/map.json").toAbsolutePath());
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private static Document parseHtml(String urlString) {
        URL url;
        InputStream is = null;
        BufferedReader br;
        String line;
        StringBuilder sb = new StringBuilder();
        Document parsedDocument = null;

        try {
            System.out.println("Инициализация подключения к " + urlString + " ...");
            url = new URL(urlString);
            is = url.openStream();
            System.out.println("Получаем содержимое страницы...");
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null){
                sb.append(line);
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            try {
                if (is != null) is.close();
                parsedDocument = Jsoup.parse(sb.toString());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return parsedDocument;
    }
}
