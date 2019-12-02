import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageResizer implements Runnable {
    private File[] files;
    private int newWidth;
    private String dstFolder;

    public ImageResizer(File[] files, int newWidth, String dstFolder) {
        this.files = files;
        this.newWidth = newWidth;
        this.dstFolder = dstFolder;
    }

    @Override
    public void run() {
        try {
            for(File file : files){
                System.out.println("Resizing image " + file.getAbsolutePath());
                BufferedImage image = ImageIO.read(file);
                if(image == null){
                    System.out.println("Was not able to read image: " + file.getAbsolutePath());
                    continue;
                }

                int newHeight = (int) Math.round(
                        image.getHeight() / (image.getWidth() / (double) newWidth)
                );

                int intermediateWidth = newWidth * 2;
                int intermediateHeight = newHeight * 2;

                BufferedImage intermediateImage = new BufferedImage(
                        intermediateWidth, intermediateHeight, BufferedImage.TYPE_INT_RGB
                );

                int intWidthStep = image.getWidth() / intermediateWidth;
                int intHeightStep = image.getHeight() / intermediateHeight;

                for (int x = 0; x < intermediateWidth; x++){
                    for (int y = 0; y < intermediateHeight; y++){
                        int rgb = image.getRGB(x * intWidthStep, y * intHeightStep);
                        intermediateImage.setRGB(x, y, rgb);
                    }
                }

                BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics2D = newImage.createGraphics();
                graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphics2D.drawImage(intermediateImage, 0, 0, newWidth, newHeight, null);
                graphics2D.dispose();

                System.out.println("Writing resized image " + file.getName() + " to disk as " + dstFolder + "/" + file.getName());
                File newFile = new File(dstFolder + "/" + file.getName());
                ImageIO.write(newImage, "jpg", newFile);
                System.out.println("Resized copy of " + file.getName() + " saved successfully!");
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
