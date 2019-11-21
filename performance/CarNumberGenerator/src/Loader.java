import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Loader
{
    public static void main(String[] args) throws Exception
    {
        long start = System.currentTimeMillis();
        int regionCode = 1;
        //FileOutputStream writer = new FileOutputStream("res/numbers.txt");
        //PrintWriter writer = new PrintWriter("res/numbers.txt");

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        char letters[] = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};
        Collection<Future<?>> futures = new LinkedList<>();
        for (; regionCode < 100; regionCode++) {

            int finalRegionCode = regionCode;
            Runnable task = ()-> {
                String region = padNumber(finalRegionCode, 2);
                PrintWriter writer = null;
                try {
                    writer = new PrintWriter("res/numbers" + padNumber(finalRegionCode, 2) + ".txt");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                StringBuilder builder = new StringBuilder();
                for (int number = 1; number < 1000; number++) {
                    for (char firstLetter : letters) {
                        for (char secondLetter : letters) {
                            for (char thirdLetter : letters) {
                                builder.append(firstLetter);
                                builder.append(padNumber(number, 3));
                                builder.append(secondLetter);
                                builder.append(thirdLetter);
                                builder.append(region);
                                //builder.append(padNumber(regionCode, 2));
                                builder.append("\n");
                            }
                        }
                    }
                }
                writer.write(builder.toString());
                writer.flush();
                writer.close();
            };
            futures.add(executorService.submit(task));
        }
        for (Future<?> future : futures) {
            future.get();
        }
        executorService.shutdown();

        System.out.println((System.currentTimeMillis() - start) + " ms");
    }

    private static String padNumber(int number, int numberLength)
    {
//        StringBuilder builder = new StringBuilder();
//        int padSize = numberLength - Integer.toString(number).length();
//        for (int i = 0; i < padSize; i++) {
//            builder.append(0);
//        }
//        return builder.append(number).toString();
        String numberStr = Integer.toString(number);
        int padSize = numberLength - numberStr.length();
        for(int i = 0; i < padSize; i++) {
            numberStr = '0' + numberStr;
        }
        return numberStr;
    }
}
