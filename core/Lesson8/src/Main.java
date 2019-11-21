import com.skillbox.airport.*;
import java.util.List;

public class Main
{
    public static void main(String[] args) {
        Airport airport = Airport.getInstance();
        List<Aircraft> aircraftList = airport.getAllAircrafts();
        System.out.println("Количество самолетов в аэропорту: " + aircraftList.size());
    }
}
