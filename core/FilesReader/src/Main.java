import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;

public class Main {

    private static long folderSize;

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        GetFileSize getFileSize = new GetFileSize();

        while (true){
            folderSize = 0;
            System.out.println("Введите путь к папке, чтобы узнать её размер:");
            String userInput = scanner.nextLine().trim();
            Path path = Path.of(userInput);

            try {
                if (Files.exists(path)) {
                    System.out.println("Вычисляем размер папки...");
                    Files.walkFileTree(path, getFileSize);
                    System.out.println("Размер папки: " + SizeConverter(folderSize));
                } else {
                    System.out.println("Указанный путь не сущестсует");
                }
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    private static class GetFileSize extends SimpleFileVisitor<Path>
    {
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attributes){
            folderSize += file.toFile().length();
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException ex) throws IOException {
            return FileVisitResult.CONTINUE;
        }
    }

    private static String SizeConverter(long size){
        if (size < 1024) return size + " Б";
        int exp = (int) (Math.log(size) / (Math.log(1024)));
        char unitsPrefix = "КМГТПЭ".charAt(exp - 1);
        return String.format("%.2f %sБ", size / Math.pow(1024, exp), unitsPrefix);
    }
}
