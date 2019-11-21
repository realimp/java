import java.util.*;

public class Main {

    private static final String[] LETTERS = {"A", "B", "C", "E", "H", "K", "M", "O", "P", "T", "X"};

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < LETTERS.length; i++){
            for (int j = 1; j < 10; j++) {
                String plateNumber = LETTERS[i] + j + j + j;

                for (int k = 0; k < LETTERS.length; k++){
                    String plateNumber2 = plateNumber + LETTERS[k];

                    for (int l = 0; l < LETTERS.length; l++){
                        String plateNumber3 = plateNumber2 + LETTERS[l];

                        for (int m = 1; m < 198; m++)
                        {
                            String region = m < 10 ? "0" + m : String.valueOf(m);
                            list.add(plateNumber3 + region);
                        }
                    }
                }
            }
        }

        Collections.sort(list);

        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.addAll(list);

        HashSet<String> hashSet = new HashSet<>();
        hashSet.addAll(list);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите номер для поиска:");

        for(;;) {
            String userInput = scanner.nextLine().toUpperCase();

            long startListSearch = System.currentTimeMillis();
            if (list.contains(userInput)) {
                long duration = System.currentTimeMillis() - startListSearch;
                System.out.println("Время поиска перебором - " + duration + "ms");
            }
            else
            {
                long duration = System.currentTimeMillis() - startListSearch;
                System.out.println("Время поиска перебором - " + duration + "ms");
            }


            int index;
            long binarySearchStart = System.currentTimeMillis();
            index = Collections.binarySearch(list, userInput);
            long binarySearchDuration = System.currentTimeMillis() - binarySearchStart;
            System.out.println("Время бинарного поиска - " + binarySearchDuration + "ms");

            long treeSetSearchStart = System.currentTimeMillis();
            if (treeSet.contains(userInput))
            {
                long treeSetSearchDuration = System.currentTimeMillis() - treeSetSearchStart;
                System.out.println("Время поиска через TreeSet - " + treeSetSearchDuration + "ms");
            }
            else {
                long treeSetSearchDuration = System.currentTimeMillis() - treeSetSearchStart;
                System.out.println("Время поиска через TreeSet - " + treeSetSearchDuration + "ms");
            }

            long hashSetSearchStart = System.currentTimeMillis();
            if (hashSet.contains(userInput)){
                long hashSetSearchDuration = System.currentTimeMillis() - hashSetSearchStart;
                System.out.println("Время поиска через HashSet - " + hashSetSearchDuration + "ms");
            }
            else {
                long hashSetSearchDuration = System.currentTimeMillis() - hashSetSearchStart;
                System.out.println("Время поиска через HashSet - " + hashSetSearchDuration + "ms");
            }
        }
    }
}
