public class Main {
    private static String text = "The rich keep getting richer. Amazon founder and CEO Jeff Bezos, whose estimated net worth of $157 billion makes him the richest man in the world, is expected to haul in hundreds of millions of dollars from Uber's anticipated initial public offering next week.The 55-year-old tech entrepreneur was one of the early investors in the ride-hailing app. When Uber goes public, it's expected to be valued at more than $80 billion. Uber said it anticipates that the initial public offering will be priced between $44 and $50 per share. Bezos invested both personally and through Benchmark, a venture capital firm known for its early-stage funding of Dropbox, Twitter, Snapchat and other successful tech companies, tech website The Information reported. He personally sunk $3 million into Uber for a stake that's now estimated to be worth $400 million, said The Information, citing a person familiar with the matter. Benchmark, which invested $30 million, stands to make roughly $7.9 billion on its early investment. Bezos's wealth was scrutinized earlier this year when he and wife MacKenzie Bezos announced their divorce, which is expected to make her one of the richest women in the world. The bulk of his wealth is tied to his 12 percent stake in Amazon, which is valued at more than $108 billion.";

    public static void main(String[] args) {
        String[] words = text.replaceAll("[.,!?]", "").split("\\s+");
        for (int i = 0; i < words.length; i++)
        {
            System.out.println(words[i]);
        }
        System.out.println(words.length);
    }
}
