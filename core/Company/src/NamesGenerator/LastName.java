package NamesGenerator;

import java.util.Random;

public class LastName {

    private String lastName;

    private static final String[] LAST_NAMES = {"Николаев", "Климов", "Языков"};

    public LastName(){
        int random = new Random().nextInt(LAST_NAMES.length);
        this.lastName = LAST_NAMES[random];
    }

    public String getMaleLastName() {
        return lastName;
    }

    public String getFemaleLastName(){
        return lastName + "а";
    }
}
