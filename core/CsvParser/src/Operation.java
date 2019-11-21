import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Operation {
    private String accountType;
    private String accountNumber;
    private String currency;
    private Date date;
    private String reference;
    private String description;
    private int income;
    private int expence;

    public Operation(String accountType, String accountNumber, String currency, String date, String reference, String description, double income, double expense){
        this.accountType = accountType;
        this.accountNumber = accountNumber;
        this.currency = currency;
        this.date = FormatDate(date);
        this.reference = reference;
        this.description = description;
        this.income = (int)(income * 100);
        this.expence = (int)(expense * 100);
    }

    private Date FormatDate(String dateString){
        SimpleDateFormat format = new SimpleDateFormat("MM.dd.yy");
        Date parsedDate = null;
        try {
            parsedDate = format.parse(dateString);
        } catch (ParseException ex){
            ex.printStackTrace();
        }
        return parsedDate;
    }

    public int getIncome() {
        return income;
    }

    public int getExpense() {
        return expence;
    }

    public String getDescription() {
        return description;
    }

    public String getShortDescription(){
        String shortDescription;

        if (description.contains("/")){
            shortDescription = description.substring(description.lastIndexOf('/') + 1);
        } else {
            shortDescription = description.substring(description.lastIndexOf('\\') + 1);
        }

        String[] shortDescriptionSplit = shortDescription.split("[0-9]+.[0-9]+.[0-9]+\\s");
        return shortDescriptionSplit[0].trim();
    }
}
