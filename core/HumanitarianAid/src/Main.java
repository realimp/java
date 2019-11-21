import java.util.Scanner;

public class Main
{
    private static int truckCapacity = 12;
    private static int containerCapacity = 27;

    private static int containerNumber;
    private static int truckNumber;

    private static int cratesLoaded;

    public static void main(String[] args) {
        System.out.println("Введите число ящиков: ");

        Scanner scanner = new Scanner(System.in);
        int cratesCount = scanner.nextInt();

        for (int i = 0; i < cratesCount; i++)
        {
            int containersLoaded = cratesLoaded / containerCapacity;

            if (truckNumber == containersLoaded / truckCapacity)
            {
                System.out.println("Грузовик " + ++truckNumber + ":");
            }
            if (containerNumber == cratesLoaded / containerCapacity)
            {
                System.out.println("\tКонтейнер " + ++containerNumber + ":");
            }

            System.out.println("\t\tЯщик " + (i+1));
            cratesLoaded++;
        }
    }
}
