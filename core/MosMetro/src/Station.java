public class Station implements Comparable<Station> {
    private String line;
    private String name;

    public Station(String line, String name){
        this.line = line;
        this.name = name;
    }

    public String getLine() {
        return line;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Station o) {
        int lineComparison = line.compareTo(o.getLine());
        if (lineComparison != 0){
            return lineComparison;
        }
        return name.compareToIgnoreCase(o.getName());
    }

    @Override
    public boolean equals(Object o){
        return compareTo((Station) o) == 0;
    }

    @Override
    public String toString(){
        return name;
    }
}
