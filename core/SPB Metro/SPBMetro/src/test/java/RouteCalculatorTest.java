import core.Line;
import core.Station;
import junit.framework.TestCase;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RouteCalculatorTest extends TestCase {

    private static String dataFile = "src/main/resources/map.json";

    RouteCalculator calculator;

    List<Station> route = new ArrayList<>();

    private static StationIndex stationIndex;

    private static void createStationIndex()
    {
        stationIndex = new StationIndex();
        try
        {
            JSONParser parser = new JSONParser();
            JSONObject jsonData = (JSONObject) parser.parse(getJsonFile());

            JSONArray linesArray = (JSONArray) jsonData.get("lines");
            parseLines(linesArray);

            JSONObject stationsObject = (JSONObject) jsonData.get("stations");
            parseStations(stationsObject);

            JSONArray connectionsArray = (JSONArray) jsonData.get("connections");
            parseConnections(connectionsArray);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void parseConnections(JSONArray connectionsArray)
    {
        connectionsArray.forEach(connectionObject ->
        {
            JSONArray connection = (JSONArray) connectionObject;
            List<Station> connectionStations = new ArrayList<>();
            connection.forEach(item ->
            {
                JSONObject itemObject = (JSONObject) item;
                int lineNumber = ((Long) itemObject.get("line")).intValue();
                String stationName = (String) itemObject.get("station");

                Station station = stationIndex.getStation(stationName, lineNumber);
                if(station == null)
                {
                    throw new IllegalArgumentException("core.Station " +
                            stationName + " on line " + lineNumber + " not found");
                }
                connectionStations.add(station);
            });
            stationIndex.addConnection(connectionStations);
        });
    }

    private static void parseStations(JSONObject stationsObject)
    {
        stationsObject.keySet().forEach(lineNumberObject ->
        {
            int lineNumber = Integer.parseInt((String) lineNumberObject);
            Line line = stationIndex.getLine(lineNumber);
            JSONArray stationsArray = (JSONArray) stationsObject.get(lineNumberObject);
            stationsArray.forEach(stationObject ->
            {
                Station station = new Station((String) stationObject, line);
                stationIndex.addStation(station);
                line.addStation(station);
            });
        });
    }

    private static void parseLines(JSONArray linesArray)
    {
        linesArray.forEach(lineObject -> {
            JSONObject lineJsonObject = (JSONObject) lineObject;
            Line line = new Line(
                    ((Long) lineJsonObject.get("number")).intValue(),
                    (String) lineJsonObject.get("name")
            );
            stationIndex.addLine(line);
        });
    }

    private static String getJsonFile()
    {
        StringBuilder builder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(dataFile));
            lines.forEach(line -> builder.append(line));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return builder.toString();
    }

    @Override
    protected void setUp() throws Exception {
        createStationIndex();
        calculator = new RouteCalculator(stationIndex);

        route.add(stationIndex.getStation("Василеостровская"));
        route.add(stationIndex.getStation("Гостиный двор"));
        route.add(stationIndex.getStation("Невский проспект"));
        route.add(stationIndex.getStation("Сенная площадь"));
        route.add(stationIndex.getStation("Садовая"));
        route.add(stationIndex.getStation("Звенигородская"));
        route.add(stationIndex.getStation("Обводный канал"));
    }

    @Test
    public void testGetShortestRoute (){
        int actual = calculator.getShortestRoute(
                stationIndex.getStation("Горьковская"),
                stationIndex.getStation("Фрунзенская")
        ).size();
        int expected = 5;

        assertEquals(expected, actual);
    }

    @Test
    public void testGetShortestRoute1(){
        int actual = calculator.getShortestRoute(
                stationIndex.getStation("Балтийская"),
                stationIndex.getStation("Площадь Александра Невского", 3)
        ).size();
        int expected = 7;

        assertEquals(expected, actual);
    }

    @Test
    public void testGetShortestRoute2(){
        int actual = calculator.getShortestRoute(
                stationIndex.getStation("Василеостровская"),
                stationIndex.getStation("Обводный канал")
        ).size();
        int expected  = 7;

        assertEquals(expected, actual);
    }

    @Test
    public void testCalculateDuration (){
        double actual = RouteCalculator.calculateDuration(route);
        double expected = 17;

        assertEquals(expected, actual);
    }


    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
