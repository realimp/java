import core.*;
import core.Camera;

import java.util.Scanner;

public class RoadController
{
    // Создается переменная типа double
    private static double passengerCarMaxWeight = 3500.0; // kg
    // Создается переменная типа int
    private static int passengerCarMaxHeight = 2000; // mm
    // Создается переменная типа int
    private static int controllerMaxHeight = 3500; // mm

    // Создается переменная типа int
    private static int passengerCarPrice = 100; // RUB
    // Создается переменная типа int
    private static int cargoCarPrice = 250; // RUB
    // Создается переменная типа int
    private static int vehicleAdditionalPrice = 200; // RUB

    public static void main(String[] args)
    {
        System.out.println("Сколько автомобилей сгенерировать?");

        Scanner scanner = new Scanner(System.in);
        // Создается переменная типа int
        int carsCount = scanner.nextInt();

        for(int i = 0; i < carsCount; i++) // Создается переменная i типа int внутри цикла
        {
            Car car = Camera.getNextCar();
            System.out.println(car);

            //Пропускаем автомобили спецтранспорта бесплатно
            if (car.isSpecial) {
                openWay();
                continue;
            }

            //Проверяем высоту и массу автомобиля, вычисляем стоимость проезда
            int price = calculatePrice(car); // Создается переменная типа int
            if(price == -1) {
                continue;
            }

            System.out.println("Общая сумма к оплате: " + price + " руб.");
        }
    }

    /**
     * Расчёт стоимости проезда исходя из массы и высоты
     */
    private static int calculatePrice(Car car)
    {
        // Создается переменная типа int
        int carHeight = car.height;
        // Создается переменная типа double
        double weight = car.weight;
        // Создается переменная типа int
        int price = 0;
        if (carHeight > controllerMaxHeight)
        {
            blockWay("высота вашего ТС превышает высоту пропускного пункта!");
            return -1;
        }
        else if (carHeight > passengerCarMaxHeight || weight > passengerCarMaxWeight)
        {
            //Грузовой автомобиль
            price = cargoCarPrice;
            if (car.hasVehicle) {
                // Если с прицеп добавляем к стоимости оплату за прицеп
                price = price + vehicleAdditionalPrice;
            }
        }
        else {
            //Легковой автомобиль
            price = passengerCarPrice;
            if (car.hasVehicle) {
                // Если с прицеп добавляем к стоимости оплату за прицеп
                price = price + vehicleAdditionalPrice;
            }
        }
        return price;
    }

    /**
     * Открытие шлагбаума
     */
    private static void openWay()
    {
        System.out.println("Шлагбаум открывается... Счастливого пути!");
    }

    /**
     * Сообщение о невозможности проезда
     */
    private static void blockWay(String reason)
    {
        System.out.println("Проезд невозможен: " + reason);
    }
}