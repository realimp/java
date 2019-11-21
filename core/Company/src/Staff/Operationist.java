package Staff;

import NamesGenerator.*;

public class Operationist implements Employee {

    private int fixedSalary;
    private String firstName;
    private String lastName;

    public Operationist(int fixedSalary){
        this.fixedSalary = fixedSalary;
        setName();
    }

    @Override
    public int getMonthlySalary() {
        return fixedSalary * 100;
    }

    @Override
    public String getName() {
        return firstName + " " + lastName;
    }

    @Override
    public String getPosition(){
        return "Операционист";
    }

    private void setName() {
        FirstName firstName = new FirstName();
        LastName lastName = new LastName();
        this.firstName = firstName.getFirstName();
        if (firstName.isMaleName()){
            this.lastName = lastName.getMaleLastName();
        }
        else {
            this.lastName = lastName.getFemaleLastName();
        }
    }
}
