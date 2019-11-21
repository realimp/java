
public class Loader
{
    public static void main(String[] args)
    {
        Cat murka = new Cat();

        System.out.println("Murka weighs: " + murka.getWeight());
        System.out.println("Murka color - " + murka.getColor());
        murka.feed(murka.getWeight()/100);
        System.out.println("Murka weighs: " + murka.getWeight());
        System.out.println("Murka status: " + murka.getStatus());
        System.out.println("Counting cats: " + Cat.getCatsCount());

        Cat vaska = new Cat();

        System.out.println("Vaska weighs: " + vaska.getWeight());
        System.out.println("Vaska color - " + vaska.getColor());
        vaska.feed(vaska.getWeight()*10);
        System.out.println("Vaska has eaten: " + vaska.getEatenAmount());
        System.out.println("Vaska status: " + vaska.getStatus());
        System.out.println("Counting cats: " + Cat.getCatsCount());

        Cat manka = new Cat();

        System.out.println("Manka weighs: " + manka.getWeight());
        System.out.println("Manka color - " + manka.getColor());
        while (manka.getStatus() != "Dead"){
            manka.meow();
        }
        System.out.println("Manka status: " + manka.getStatus());
        System.out.println("Counting cats: " + Cat.getCatsCount());

        Cat ryzhik = new Cat();

        System.out.println("Ryzhik weighs: " + ryzhik.getWeight());
        ryzhik.drink(ryzhik.getWeight()/150);
        System.out.println("Ryzhik weighs: " + ryzhik.getWeight());
        System.out.println("Ryzhik status: " + ryzhik.getStatus());
        System.out.println("Counting cats: " + Cat.getCatsCount());

        Cat leha = new Cat();

        System.out.println("Leha weighs: " + leha.getWeight());
        leha.meow();
        System.out.println("Leha weighs: " + leha.getWeight());
        leha.poop();
        System.out.println("Leha weighs: " + leha.getWeight());
        System.out.println("Leha status: " + leha.getStatus());

        leha.setColor(Colors.BLACK);
        System.out.println("Leha color - " + leha.getColor());

        System.out.println("Counting cats: " + Cat.getCatsCount());

        Cat tom = generateCat(3600.0);
        System.out.println("Tom weighs: " + tom.getWeight());
        System.out.println("Tom color - " + tom.getColor());
        System.out.println("Counting cats: " + Cat.getCatsCount());

        Cat jerry = tom.copyCat();
        System.out.println("Jerry weighs: " + jerry.getWeight());
        System.out.println("Jerry color - " + jerry.getColor());
        System.out.println("Counting cats: " + Cat.getCatsCount());

    }

    public static Cat generateCat(double weight)
    {
        Cat cat = new Cat(weight);
        return cat;
    }
}