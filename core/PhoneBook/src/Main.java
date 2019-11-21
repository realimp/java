import java.util.*;

public class Main {
    private static HashMap<String, Contact> phoneBook2 = new HashMap<>();

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String userInput;

        while (true){
            System.out.println("Введите данные для поиска контакта:");
            userInput = scanner.nextLine().trim();
            if (!userInput.equalsIgnoreCase("LIST")){
                if (checkInputForNumber(userInput))
                {
                    phoneNumberLookup(userInput.replaceAll("[^0-9]", ""));
                }
                else
                {
                    if (!nameLookup(userInput)){
                        System.out.println("Контакта с ткаим именем или фамилией не найдено.");
                        addContact(userInput);
                    }

                    //nameLookup(userInput);
                }
            }
            else {
                printPhoneBook(phoneBook2);
            }
        }
    }

    private static boolean checkInputForNumber(String testString)
    {
        boolean result = false;

        if (testString.replaceAll("\\s+", "").
                matches("\\+?[0-9]+\\(?[0-9]+\\)?[0-9]+\\-?[0-9]+\\-?[0-9]+"))
        {
            result = true;
        }

        return result;
    }

    private static void phoneNumberLookup(String phoneNumber){
        if (phoneBook2.containsKey(phoneNumber))
        {
            System.out.println(phoneBook2.get(phoneNumber).firstName + " " + phoneBook2.get(phoneNumber).lastName + " " + phoneNumber);
        }
        else
        {
            System.out.println("Введите имя и фамилию нового контакта:");
            String name = scanner.nextLine().trim();
            String firstName;
            String lastName;
            if (name.contains(" ")) {
                firstName = name.substring(0, 1).toUpperCase() + name.substring(1, name.indexOf(" ")).toLowerCase();
                lastName = name.substring(name.indexOf(" ") + 1, name.indexOf(" ") + 2).toUpperCase() + name.substring(name.indexOf(" ") + 2).toLowerCase();
            }
            else {
                firstName = name.substring(0, 1).toUpperCase() + name.substring(1);
                lastName = "";
            }
            phoneBook2.put(phoneNumber, new Contact(
                    firstName,
                    lastName,
                    phoneNumber
            ));
        }
    }

    private static boolean nameLookup(String nameString){
        boolean result = false;

        String testName = nameString.contains(" ") ? nameString.substring(0, nameString.indexOf(" ")) : nameString;
        if (phoneBook2.size() > 0) {
            TreeSet<String> sortedList = new TreeSet<>();
            for (String key : phoneBook2.keySet()) {
                if (testName.equalsIgnoreCase(phoneBook2.get(key).firstName) || testName.equalsIgnoreCase(phoneBook2.get(key).lastName)) {
                    result = true;
                    sortedList.add(phoneBook2.get(key).firstName + " " + phoneBook2.get(key).lastName + " " + key);
                }
            }
            for (String entry : sortedList){
                System.out.println(entry);
            }
        }

        return result;
    }

    private static void addContact(String name){
        System.out.println("Введите номер телефона для создания нового контакта:");
        String phoneNumber = scanner.nextLine().trim().replaceAll("[^0-9]", "");

        while (true) {
            if (phoneBook2.containsKey(phoneNumber)) {
                    System.out.println("Контакт с таким номером уже существует! Чтобы заменить его введите Y\n" +
                            "чтобы ввести новый номер введите N");
                    String userInput = scanner.nextLine().trim();
                    if (userInput.equalsIgnoreCase("Y")) {
                        break;
                    }
                    else {
                        System.out.println("Введите новый номер телефона:");
                        phoneNumber = scanner.nextLine().trim().replaceAll("[^0-9]", "");
                        if (!phoneBook2.containsKey(phoneNumber)){
                            break;
                        }
                    }
            }
            else {
                break;
            }
        }

        String fullName;
        String firstName;
        String lastName;

        if (name.length() > 0){
            System.out.println("Теперь введите фамилию нового контакта:");
            firstName = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
            String userInput = scanner.nextLine().trim();
            lastName = userInput.substring(0, 1).toUpperCase() + userInput.substring(1).toLowerCase();
        }
        else {
            System.out.println("Введите имя и фамилию нового контакта:");
            fullName = scanner.nextLine().trim();
            if (fullName.contains(" ")) {
                firstName = fullName.substring(0, 1).toUpperCase() + fullName.substring(1, fullName.indexOf(" ")).toLowerCase();
                lastName = fullName.substring(fullName.indexOf(" ") + 1, fullName.indexOf(" ") + 2).toUpperCase() + fullName.substring(fullName.indexOf(" ") + 2).toLowerCase();
            } else {
                firstName = fullName.substring(0, 1).toUpperCase() + fullName.substring(1);
                lastName = "";
            }
        }

        phoneBook2.put(phoneNumber, new Contact(
                firstName,
                lastName,
                phoneNumber
        ));
    }

    private static void printPhoneBook(Map<String, Contact> map)
    {
        if (phoneBook2.size() > 0) {
            System.out.println("Контакты в Вашей телефонной книге:");

            TreeSet<String> sortedList = new TreeSet<>();

            for (String key : map.keySet()) {
                sortedList.add(map.get(key).firstName + " " + map.get(key).lastName + " " + key);
            }

            for (String contactInfo : sortedList) {
                System.out.println(contactInfo);
            }
        }
        else {
            System.out.println("В вашей телефонной книге нет ни одного контакта!");
            addContact("");
        }
    }
}
