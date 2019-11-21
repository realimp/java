import com.skillbox.airport.Aircraft;
import com.skillbox.airport.Airport;

import java.lang.reflect.Field;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        Airport airport = Airport.getInstance();

        Calendar calendar = Calendar.getInstance();
        Date currentTime = new Date();
        calendar.setTime(currentTime);
        calendar.add(Calendar.HOUR, 2);

        TreeMap<Date, Aircraft> nearestDepartures = new TreeMap<>();

        airport.getTerminals().forEach(Terminal -> {
            try{
                Field field = Terminal.getClass().getDeclaredField("departureAircrafts");
                field.setAccessible(true);
                TreeMap<Date, Aircraft> departures = (TreeMap <Date, Aircraft>)field.get(Terminal);
                departures.forEach((date, aircraft) -> {
                    if (!(date.before(currentTime) || date.after(calendar.getTime())))
                        nearestDepartures.put(date, aircraft);
                });
            } catch (Exception ex){
                ex.printStackTrace();
            }
        });

        nearestDepartures.forEach((date, aircraft) -> System.out.println(date + " - " + aircraft.getModel()));
    }
}
