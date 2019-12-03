## The Task
The task is to create a simple image resizing app that will demonstrate how multithreading works.

#### Side note
The goal of this exercise is to understand how to use multithreading in processor intensive task. So lets assume that we only need to downscale images and that the final image has at least 3 times smaller width than original.

## The Solution
First I'll initialize some helper variables in the Main class:

```java
int newWidth = 300;             // width of resized images
String srcFolder = "D:/src";    // path to source folder
String dstFolder = "D:/dst";    // path to destination folder
```

Next it's time to create ImageResizer class that will handle the downscaling. It must implement *Runnable* interface so it can be used for multithreading and will have three fields.

```java
private File[] files;       // this will hold files in our source folder
private int newWidth;       // this will hold a new width for resized files
private String dstFolder;   // this will hold path for destination folder
```

To do the resizing itself I'm going to override *run* method of *Runnable* interface. The steps to resize an image are as follows:
1. Calculate a new height as we only have a width parameter set.

```java
    int newHeight = (int) Math.round(
        image.getHeight() / (image.getWidth() / (double) newWidth
    );
```

2. To make resized images smoother I'm going to resize them twice. First step is to get an image with width and height two times larger than our destination size by simply taking every *N*-th pixel of a source image to form a new one. Where *N = source width / destination width*.

```java
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
```

3. Downscale intermediate image to our destination size using *java.awt.Graphics2D* with bilinear interpolation and antialiasing.

```java
BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
Graphics2D graphics2D = newImage.createGraphics();
graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
graphics2D.drawImage(intermediateImage, 0, 0, newWidth, newHeight, null);
graphics2D.dispose();
```

4. Write resized image to file
```java
ImageIO.write(newImage, "jpg", newFile);
```


After everything is set all that's left is to start resizing images using multiple threads available. So I'm getting number of processor threads available and divide files in the source folder between them and create a queue of images to be resized for each one. And then just start resizing of every image using *Thread().start()* method.
```java
int processorThreadsAvailable = Runtime.getRuntime().availableProcessors();
int imagesPerThreadCount = files.length / processorThreadsAvailable;
for (int thread = 0; thread < processorThreadsAvailable - 1; thread++){
    File[] imagesPerThread = Arrays.copyOfRange(files, imagesPerThreadCount * thread, imagesPerThreadCount * (thread + 1));
    ImageResizer resizer = new ImageResizer(imagesPerThread, newWidth, dstFolder);
    new Thread(resizer).start();
}

File[] imagesForLastThread = Arrays.copyOfRange(files, imagesPerThreadCount * (processorThreadsAvailable - 1), files.length);
ImageResizer lastThreadResizer = new ImageResizer(imagesForLastThread, newWidth, dstFolder);
new Thread(lastThreadResizer).start();
```

In the output you'll notice that the resizing starts simultaneously for the same number of images as your CPU threads count.
