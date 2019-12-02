import java.io.File;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int newWidth = 300;
        String srcFolder = "D:/src";    // path to source folder
        String dstFolder = "D:/dst";    // path to destination folder

        File srcDir = new File(srcFolder);
        File[] files = srcDir.listFiles();

        int processorThreadsAvailable = Runtime.getRuntime().availableProcessors();
        int imagesPerThreadCount = files.length / processorThreadsAvailable;

        System.out.println("Started resizing images...");
        
        for (int thread = 0; thread < processorThreadsAvailable - 1; thread++){
            File[] imagesPerThread = Arrays.copyOfRange(files, imagesPerThreadCount * thread, imagesPerThreadCount * (thread + 1));
            ImageResizer resizer = new ImageResizer(imagesPerThread, newWidth, dstFolder);
            new Thread(resizer).start();
        }

        File[] imagesForLastThread = Arrays.copyOfRange(files, imagesPerThreadCount * (processorThreadsAvailable - 1), files.length);
        ImageResizer lastThreadResizer = new ImageResizer(imagesForLastThread, newWidth, dstFolder);
        new Thread(lastThreadResizer).start();
    }
}
