public class Main {
    public static void main(String[] args) {
        String text = "Каждый охотник желает знать, где сидит фазан";

        String[] colors = text.split(",?\\s+");

        String[] invertedColors = new String[colors.length];

        for (int i = colors.length - 1; i >= 0; i--)
        {
            invertedColors[(colors.length - 1 - i)] = colors[i];
        }

        colors = invertedColors;

        for (String invertedColorsStrings : colors){
            System.out.println(invertedColorsStrings);
        }
    }
}
