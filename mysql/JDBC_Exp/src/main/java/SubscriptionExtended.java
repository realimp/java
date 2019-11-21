import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "subscriptions_extended")
public class SubscriptionExtended implements Serializable {

    @Id
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "student_name")
    private String studentName;

    @Id
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "subscription_date")
    private Date subscriptionDate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumns({
            @PrimaryKeyJoinColumn(name = "student_id"),
            @PrimaryKeyJoinColumn(name = "course_id")
    })
    private Subscription subscription;

    public SubscriptionExtended(Student student, Course course, Date subscriptionDate) {
        this.student = student;
        this.studentName = student.getName();
        this.course = course;
        this.courseName = course.getName();
        this.subscriptionDate = subscriptionDate;
    }

    public SubscriptionExtended(){}

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }

        if(o == null || o.getClass() != getClass()){
            return false;
        }

        SubscriptionExtended subscriptionExtended = (SubscriptionExtended)o;
        return Objects.equals(student, subscriptionExtended.student) && Objects.equals(course, subscriptionExtended.course);
    }

    @Override
    public int hashCode(){
        return Objects.hash(student, course);
    }
}
