public class Main {
    public static void main(String[] args) {

        int sideLength = 9;

        String[][] x = new String[sideLength][sideLength];

        for (int rows = 0; rows < sideLength; rows++)
        {
            x[rows][rows] = "X";
            x[rows][sideLength - 1 - rows] = "X";

            for (int columns = 0; columns < sideLength; columns++)
            {
                if (x[rows][columns] == null)
                {
                    x[rows][columns] = " ";
                }
            }
        }

        for (String[] xRow : x){
            for (String xColumn : xRow){
                System.out.print(xColumn);
            }
            System.out.println();
        }
    }
}
