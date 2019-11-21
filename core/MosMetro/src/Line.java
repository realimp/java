import java.util.ArrayList;
import java.util.List;

public class Line implements Comparable<Line> {

    private String number;
    private String name;
    private List<Station> stations;

    public Line(String number, String name){
        this.number = number;
        this.name = name;
        stations = new ArrayList<>();
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void addStation(Station station){
        stations.add(station);
    }

    @Override
    public int compareTo(Line o) {
        int number = Integer.parseInt(o.getNumber().replaceAll("\\D", ""));

        int numberComparison = Integer.compare(Integer.parseInt(this.number.replaceAll("\\D", "")), number);
        if (numberComparison != 0){
            return numberComparison;
        }

        return  this.number.compareToIgnoreCase(o.getNumber());
    }

    @Override
    public boolean equals(Object o){
        return compareTo((Line) o) == 0;
    }

    @Override
    public String toString(){
        return number + " - " + name;
    }
}
