import com.mongodb.*;

import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final char DEFAULT_SEPARATOR = ',';
    private static final char DEFAULT_QUOTE = '"';

    public static void main(String[] args) throws UnknownHostException {
        ArrayList<Student> students = loadStudentsFromFile();

        MongoClient mongoClient = new MongoClient( "127.0.0.1" , 27017 );
        DB database = mongoClient.getDB("mongoTest");
        DBCollection collection = database.getCollection("students");

        for (int i = 0; i < students.size(); i++) {
            DBObject dbStudent = new BasicDBObject("_id", i)
                    .append("name", students.get(i).getName())
                    .append("age", students.get(i).getAge())
                    .append("courses", students.get(i).getCourses());
            collection.insert(dbStudent);
        }

        System.out.println("Total students - " + collection.count());
        System.out.println("Students over 40 - " + collection.count(new BasicDBObject("age", new BasicDBObject("$gt", 40))));
        System.out.println("Youngest student - " + collection.find().sort(new BasicDBObject("age", 1)).limit(1).one().get("name"));
        System.out.println("Courses of oldest student - " + collection.find().sort(new BasicDBObject("age", -1)).limit(1).one().get("courses"));
    }

    private static ArrayList<Student> loadStudentsFromFile(){

        ArrayList<Student> students = new ArrayList<Student>();

        try {
            List<String> lines = Files.readAllLines(Path.of("data/mongo.csv"));
            for (String line : lines) {
                String[] fragments = parseLine(line).toArray(String[]::new);
                if (fragments.length != 3) {
                    System.out.println("Wrong line - " + line);
                    continue;
                }
                students.add(new Student(
                        fragments[0],
                        Integer.valueOf(fragments[1]),
                        fragments[2]
                ));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return students;
    }

    private static List<String> parseLine(String line) {
        List<String> result = new ArrayList<>();

        if (line == null || line.isEmpty()){
            return result;
        }

        StringBuffer currentValue = new StringBuffer();
        boolean inQuotes = false;

        char[] chars = line.toCharArray();

        for (char ch : chars){
            if (inQuotes) {
                if (ch == DEFAULT_QUOTE) {
                    inQuotes = false;
                } else {
                    currentValue.append(ch);
                }
            } else {
                if (ch == DEFAULT_QUOTE) {
                    inQuotes = true;
                } else if (ch == DEFAULT_SEPARATOR) {
                    result.add(currentValue.toString());
                    currentValue = new StringBuffer();
                } else if (ch == '\r') {
                    continue;
                } else if (ch == '\n') {
                    break;
                } else {
                    currentValue.append(ch);
                }
            }
        }
        result.add(currentValue.toString());
        System.out.println(result);
        return result;
    }
}


