import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(" - dd.MM.yyyy - EEEE", Locale.forLanguageTag("ru"));

        Calendar calendar = new GregorianCalendar(1989, Calendar.FEBRUARY, 13);

        int i=0;

        while (calendar.getTime().getTime() <= Calendar.getInstance().getTime().getTime()){
            System.out.println(i + dateFormat.format(calendar.getTime()));
            calendar.add(Calendar.YEAR, 1);
            i++;
        }
    }
}
