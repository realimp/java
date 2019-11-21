import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "purchaselist")
public class Purchase implements Serializable {

    @Id
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "student_name", referencedColumnName = "name")
    private Student student;

    @Id
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "course_name", referencedColumnName = "name")
    private Course course;

    private int price;

    @Column(name = "subscription_date")
    private Date subscriptionDate;

    public Purchase(){}

    public Purchase(Student student, Course course, Date subscriptionDate) {
        this.student = student;
        this.course = course;
        this.price = course.getPrice();
        this.subscriptionDate = subscriptionDate;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o){
        if (this == o){
            return true;
        }

        if(o == null || getClass() != o.getClass()){
            return false;
        }
        Purchase purchase = (Purchase) o;
        return Objects.equals(student, purchase.student) && Objects.equals(course, purchase.course);
    }

    @Override
    public int hashCode(){
        return Objects.hash(student, course);
    }
}
