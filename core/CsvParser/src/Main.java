import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Main {
    private static final char DEFAULT_SEPARATOR = ',';
    private static final char DEFAULT_QUOTE = '"';

    public static void main(String[] args) {
        ArrayList<Operation> operations = loadOperationsFromFile();

        long totalIncome = operations.stream().filter(operation -> operation.getIncome() > 0)
                .mapToLong(operation -> operation.getIncome()).sum();
        long totalExpense = operations.stream().filter(operation -> operation.getExpense() > 0)
                .mapToLong(operation -> operation.getExpense()).sum();

        System.out.println("Всего поступлений по выписке: "
                + (totalIncome / 10) + ((totalIncome % 10 < 10 ? ",0" + (totalIncome % 10) : "," + (totalIncome % 10))) + " руб.");
        System.out.println("Всего трат по выписке: "
                + (totalExpense / 10) + ((totalExpense % 10) < 10 ? ",0" + (totalExpense % 10) : "," + (totalExpense % 10)) + " руб.");

        System.out.println("Траты по категориям:");

        Set<String> categories = new TreeSet<>();
        operations.stream().forEach(operation -> categories.add(operation.getShortDescription()));

        String[] categoriesArray = categories.toArray(new String[0]);

        for (int i = 0; i < categoriesArray.length; i++){
            String cat = categoriesArray[i];
            long categoryExpenses = operations.stream().filter(operation -> operation.getShortDescription().equalsIgnoreCase(cat))
                    .mapToLong(operation -> operation.getExpense()).sum();

            System.out.println("\t" + categoriesArray[i] + " - " + (categoryExpenses / 10)
                    + ((categoryExpenses % 10 < 10 ? ",0" + (categoryExpenses % 10) : "," + categoryExpenses % 10)) + " руб.");
        }
    }

    private static ArrayList<Operation> loadOperationsFromFile(){
        ArrayList<Operation> operations = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Path.of("data/movementList.csv"));
            lines.remove(0);
            for (String line : lines) {
                String[] fragments = parseLine(line).toArray(String[]::new);

                if (fragments.length != 8){
                    System.out.println("Wrong line: " + line);
                    continue;
                }
                operations.add(new Operation(
                        fragments[0],
                        fragments[1],
                        fragments[2],
                        fragments[3],
                        fragments[4],
                        fragments[5],
                        Double.parseDouble(fragments[6].replaceAll(",", ".")),
                        Double.parseDouble(fragments[7].replaceAll(",", "."))
                ));
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

        return operations;
    }

    private static List<String> parseLine(String cvsLine) {

        List<String> result = new ArrayList<>();

        if (cvsLine == null && cvsLine.isEmpty()) {
            return result;
        }

        StringBuffer curVal = new StringBuffer();
        boolean inQuotes = false;

        char[] chars = cvsLine.toCharArray();

        for (char ch : chars) {

            if (inQuotes) {
                if (ch == DEFAULT_QUOTE) {
                    inQuotes = false;
                } else {
                    curVal.append(ch);
                }
            } else {
                if (ch == DEFAULT_QUOTE) {
                    inQuotes = true;
                } else if (ch == DEFAULT_SEPARATOR) {
                    result.add(curVal.toString());
                    curVal = new StringBuffer();
                } else if (ch == '\r') {
                    continue;
                } else if (ch == '\n') {
                    break;
                } else {
                    curVal.append(ch);
                }
            }
        }

        result.add(curVal.toString());

        return result;
    }

}
