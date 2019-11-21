import java.util.Random;

public class Cat
{
    public static final int EYES_COUNT = 2;
    public static final double MIN_WEIGHT = 1000.0;
    public static final double MAX_WEIGHT = 9000.0;

    private double originWeight;
    private double weight;

    private double minWeight;
    private double maxWeight;

    private static boolean isAlive;

    private Colors color;

    public static int catsCount = 0;

    public static int getCatsCount(){
        return catsCount;
    }

    public Cat()
    {
        weight = 1500.0 + 3000.0 * Math.random();
        originWeight = weight;
        minWeight = 1000.0;
        maxWeight = 9000.0;
        color = color();
        isAlive = true;
        catsCount++;
    }

    public Cat(double weight){
        this();
        this.weight = weight;
    }

    public Cat copyCat()
    {
        Cat newCat = new Cat();
        newCat.weight = this.weight;
        newCat.originWeight = this.originWeight;
        newCat.minWeight = this.minWeight;
        newCat.maxWeight = this.maxWeight;
        newCat.color = this.color;

        return newCat;
    }

    public Colors color()
    {
        int i = new Random().nextInt(Colors.values().length);
        return Colors.values()[i];
    }

    public void setColor(Colors color)
    {
        this.color = color;
    }

    public Colors getColor()
    {
        return this.color;
    }

    public void meow()
    {
        if (isAlive){
            weight = weight - 1;
            if (weight < minWeight)
            {
                catsCount--;
                isAlive = false;
            }
            System.out.println("Meow");
        }
    }

    public void feed(Double amount)
    {
        if (isAlive){
            weight = weight + amount;
            if (weight > maxWeight)
            {
                catsCount--;
                isAlive = false;
            }
        }
    }

    public double getEatenAmount()
    {
        return  weight - originWeight;
    }

    public void drink(Double amount)
    {
        if (isAlive){
            weight = weight + amount;
            if (weight > maxWeight)
            {
                catsCount--;
                isAlive = false;
            }
        }
    }

    public Double getWeight()
    {
        return weight;
    }

    public void poop()
    {
        if (isAlive){
            weight = weight - weight / 20;
            if (weight < minWeight)
            {
                catsCount--;
                isAlive = false;
            }
            System.out.println("Please clean up after me");
        }
    }

    public String getStatus()
    {
        if(weight < minWeight) {
            return "Dead";
        }
        else if(weight > maxWeight) {
            return "Exploded";
        }
        else if(weight > originWeight) {
            return "Sleeping";
        }
        else {
            return "Playing";
        }
    }
}