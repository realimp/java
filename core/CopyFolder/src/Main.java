import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;

import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true){
            System.out.println("Введите путь к папке содержимое которой хотите скопировать:");
            Path sourceFolderPath = Path.of(scanner.nextLine().trim());

            if (Files.exists(sourceFolderPath)){
                System.out.println("Введите путь к папке в которую хотите скопировать файлы:");
                Path destinationFolderPath = Path.of(scanner.nextLine().trim());
                if (Files.notExists(destinationFolderPath)){
                    try {
                        Files.createDirectory(destinationFolderPath);
                    } catch (IOException ex){
                        System.out.println(ex.getMessage());
                    }
                }

                if (Files.exists(destinationFolderPath) && Files.isDirectory(destinationFolderPath)) {
                        Copier copier = new Copier(sourceFolderPath, destinationFolderPath);
                        try {
                            Files.walkFileTree(sourceFolderPath, copier);
                            System.out.println("Файлы успешно скопированы.");
                        }
                        catch (IOException ex){
                            ex.printStackTrace();
                        }
                } else {
                    System.out.println("Указан не верный путь к папке.");
                }
            } else {
                System.out.println("Указан не верный путь к папке.");
            }
        }
    }

    private static class Copier implements FileVisitor<Path>{
        final Path source;
        final Path target;

        Copier(Path source, Path target){
            this.source = source;
            this.target = target;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            CopyOption[] options = new CopyOption[] {COPY_ATTRIBUTES, REPLACE_EXISTING};
            Path newDir = target.resolve(source.relativize(dir));

            try {
                Files.copy(dir, newDir, options);
            }
            catch (FileAlreadyExistsException ex){
                ex.printStackTrace();
            }
            catch (IOException ex){
                ex.printStackTrace();
                return FileVisitResult.SKIP_SUBTREE;
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            CopyOption[] options = new CopyOption[] {COPY_ATTRIBUTES, REPLACE_EXISTING};
            Path newFile = target.resolve(source.relativize(file));

            try {
                Files.copy(file, newFile, options);
            }
            catch (IOException ex){
                ex.printStackTrace();
            }

            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            if (exc instanceof FileSystemLoopException){
                System.err.println("cycle detected: " + file);
            } else {
                exc.printStackTrace();
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {

            return FileVisitResult.CONTINUE;
        }
    }
}
