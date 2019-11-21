public class Main {
    public static void main(String[] args) {
        for (char ch = 'A'; ch <= 'Z'; ch++)
        {
            System.out.println(ch + " - " + (int)ch);
        }
        for (char ch = 'a'; ch <= 'z'; ch++)
        {
            System.out.println(ch + " - " + (int)ch);
        }
        for (char ch = 'А'; ch <= 'Я'; ch++)
        {
            System.out.println(ch + " - " + (int)ch);
        }
        for (char ch = 'а'; ch <= 'я'; ch++)
        {
            System.out.println(ch + " - " + (int)ch);
        }
    }
}
