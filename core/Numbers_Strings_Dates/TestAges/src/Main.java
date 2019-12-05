public class Main {
    public static void main(String[] args) {
        int vasyaAge = 35;
        int katyaAge = 35;
        int mishaAge = 25;

        int minAge = -1;
        int middleAge = -1;
        int maxAge = -1;

        boolean twoAgesAreEqual = false;

        if (vasyaAge > katyaAge && katyaAge > mishaAge){
            minAge = mishaAge;
            middleAge = katyaAge;
            maxAge = vasyaAge;
        }
        else if (vasyaAge < katyaAge && katyaAge < mishaAge){
            minAge = vasyaAge;
            middleAge = katyaAge;
            maxAge = mishaAge;
        }
        else if (vasyaAge > katyaAge && katyaAge < mishaAge){
            minAge = katyaAge;
            if (vasyaAge == mishaAge){
                twoAgesAreEqual = true;
                maxAge = mishaAge;
            }
            else {
                maxAge = (vasyaAge > mishaAge ? vasyaAge : mishaAge);
                middleAge = (vasyaAge > mishaAge ? mishaAge : vasyaAge);
            }
        }
        else if (vasyaAge < katyaAge && katyaAge > mishaAge){
            maxAge = katyaAge;
            if (vasyaAge == mishaAge){
                twoAgesAreEqual = true;
                minAge = mishaAge;
            }
            else
            {
                minAge = (vasyaAge > mishaAge ? mishaAge : vasyaAge);
                middleAge = (vasyaAge > mishaAge ? vasyaAge : mishaAge);
            }
        }
        else if (vasyaAge == katyaAge){
            minAge = (katyaAge > mishaAge ? mishaAge : katyaAge);
            maxAge = (katyaAge > mishaAge ? katyaAge : mishaAge);
            twoAgesAreEqual = true;
        }
        else if (katyaAge == mishaAge){
            minAge = (vasyaAge > katyaAge ? katyaAge : vasyaAge);
            maxAge = (vasyaAge > katyaAge ? vasyaAge : katyaAge);
            twoAgesAreEqual = true;
        }

        if (vasyaAge == katyaAge && katyaAge == mishaAge){
            System.out.println("У всех троих одинокаовый возраст");
        }
        else {
            System.out.println("minAge = " + minAge);
            System.out.println(twoAgesAreEqual ? "У двух человек одинаковый возраст, \nсреднего возраста нет" : "middleAge = " + middleAge);
            System.out.println("maxAge = " + maxAge);
        }
    }
}