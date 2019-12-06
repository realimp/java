
public class Loader
{
    public static void main(String[] args)
    {
        String text = "Вася заработал 5000 рублей, Петя - 7563 рубля, а Маша - 30000 рублей";

        String earnings[] = text.replaceAll("[^0-9 \\s]", "").trim().split("\\s+");
        int totalEarnings = 0;

        for (int i = 0; i < earnings.length; i++)
        {
            totalEarnings += Integer.parseInt(earnings[i]);
        }

        System.out.println(totalEarnings);
    }
}