public class Main {
    public static void main(String[] args) {
        int j = 200000;

        while (j < 235001){
            System.out.println(j);
            if (j == 210000){
                j = 220000;
            }
            else {
                j++;
            }
        }
    }
}
