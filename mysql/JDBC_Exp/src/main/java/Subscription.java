import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity()
@Table(name = "subscriptions")
public class Subscription implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;

    @Column(name = "subscription_date")
    private Date subscriptionDate;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name = "subscription_date")
    private Purchase purchase;

    @OneToOne(mappedBy = "subscription", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private SubscriptionExtended subscriptionExtended;

    public Subscription(){}

    public Subscription(Student student, Course course, Date subscriptionDate){
        this.subscriptionDate = subscriptionDate;
        this.course = course;
        this.student = student;
        this.purchase = new Purchase(student, course, subscriptionDate);
        this.subscriptionExtended = new SubscriptionExtended(student, course, subscriptionDate);
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
        this.student = purchase.getStudent();
        this.course = purchase.getCourse();
        this.subscriptionDate = purchase.getSubscriptionDate();
    }

    public SubscriptionExtended getSubscriptionExtended() {
        return subscriptionExtended;
    }

    public void setSubscriptionExtended(SubscriptionExtended subscriptionExtended) {
        this.subscriptionExtended = subscriptionExtended;
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

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    @Override
    public boolean equals(Object o){
        if (this == o){
            return true;
        }

        if (o == null || o.getClass() != getClass()){
            return false;
        }

        Subscription subscription = (Subscription) o;
        return Objects.equals(student, subscription.student) && Objects.equals(course, subscription.course);
    }

    @Override
    public int hashCode(){
        return Objects.hash(student, course);
    }
}
