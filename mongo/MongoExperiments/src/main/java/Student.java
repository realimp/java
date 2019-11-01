public class Student {

    private String name;
    private int age;
    private String[] courses;

    public Student(String name, int age, String courses) {
        this.name = name;
        this.age = age;
        this.courses = courses.split(", ");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String[] getCourses() {
        return courses;
    }

    public void setCourses(String[] courses) {
        this.courses = courses;
    }
}
