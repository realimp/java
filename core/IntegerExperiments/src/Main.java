public class Main
{
    public static void main(String[] args)
    {
        Container container = new Container();
        container.count += 7843;

        System.out.println(sumDigits(container.count));
    }

    public static Integer sumDigits(Integer number)
    {
        String numberString = number.toString();
        int sumDigits = 0;

        for (int i = 0; i < numberString.length(); i++)
        {
            sumDigits += Character.getNumericValue(numberString.charAt(i));
        }

        return sumDigits;
    }
}
