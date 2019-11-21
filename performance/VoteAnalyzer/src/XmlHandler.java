import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.text.SimpleDateFormat;
import java.util.*;

public class XmlHandler extends DefaultHandler {
    private Voter voter;
    private WorkTime workTime;

    private static SimpleDateFormat birthDayFormat = new SimpleDateFormat("yyyy.MM.dd");
    private static SimpleDateFormat visitDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

    private static HashMap<Integer, WorkTime> voteStationWorkTimes = new HashMap<>();
    private HashMap<Voter, Integer> votersCount;

    public XmlHandler() {
        votersCount = new HashMap<>();
        voteStationWorkTimes = new HashMap<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        try {
            if (qName.equalsIgnoreCase("voter") && voter == null) {
                voter = new Voter(attributes.getValue("name"), birthDayFormat.parse(attributes.getValue("birthDay")));
            } else if (qName.equalsIgnoreCase("visit") && voter != null && workTime == null) {
                int count = votersCount.getOrDefault(voter, 0);
                votersCount.put(voter, count + 1);

                workTime = new WorkTime();
                workTime.addVisitTime(visitDateFormat.parse(attributes.getValue("time")).getTime());
                voteStationWorkTimes.put(Integer.parseInt(attributes.getValue("station")), workTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("voter")) {
            voter = null;
        }
        if (qName.equalsIgnoreCase("visit")) {
            workTime = null;
        }
    }

    public void printVoteStationsWorkingTimes() {
        System.out.println("Voting station work times: ");
        for (int station : voteStationWorkTimes.keySet()) {
            System.out.println("\t" + station + " - " + voteStationWorkTimes.get(station).toString());
        }
    }

    public void printDuplicateVoters() {
        System.out.println("Duplicated voters: ");
        for (Voter voter : votersCount.keySet()){
            int count = votersCount.get(voter);
            if (count > 1) {
                System.out.println("\t" + voter.toString() + " - " + count);
            }
        }
    }
}
