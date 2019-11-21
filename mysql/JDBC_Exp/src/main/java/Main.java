import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<Purchase> purchaseCriteriaQuery = criteriaBuilder.createQuery(Purchase.class);
        Root<Purchase> purchaseRoot = purchaseCriteriaQuery.from(Purchase.class);
        purchaseCriteriaQuery.select(purchaseRoot);
        List<Purchase> purchaseList = session.createQuery(purchaseCriteriaQuery).getResultList();

        Transaction transaction = session.beginTransaction();

        purchaseList.forEach(purchase -> {
            SubscriptionExtended subscriptionExtended = new SubscriptionExtended(purchase.getStudent(), purchase.getCourse(), purchase.getSubscriptionDate());
            session.save(subscriptionExtended);
            session.flush();
            session.clear();
        });

        transaction.commit();

        session.close();
        sessionFactory.close();
    }
}
