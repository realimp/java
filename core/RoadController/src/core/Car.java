package core;

public class Car
{
    // Создается переменная типа String
    public String number;
    // Создается переменная типа int
    public int height;
    // Создается переменная типа double
    public double weight;
    // Создается переменная типа boolean
    public boolean hasVehicle;
    // Создается переменная типа boolean
    public boolean isSpecial;

    public String toString()
    {
        // Создается переменная типа String
        String special = isSpecial ? "СПЕЦТРАНСПОРТ " : "";
        // Создается переменная типа String (показываем наличие прицепа)
        String hasVehicle2 = hasVehicle ? "\n\tАвтомобиль с прицепом" : "";
        return "\n=========================================\n" +
            special + "Автомобиль с номером " + number +
            ":\n\tВысота: " + height + " мм\n\tМасса: " + weight + " кг" + hasVehicle2;
    }
}