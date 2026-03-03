package ug.daes.ra.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigInteger;

@Entity
@Table(name = "subscriber_count_view")
public class SubscriberCountView {

        @Id
        private Long id = 1L; // dummy ID needed for JPA

        @Column(name = "subscriber_count")
        private BigInteger subscriberCount;

        @Column(name = "active_subscriber_count")
        private BigInteger activeSubscriberCount;

        @Column(name = "inactive_subscriber_count")
        private BigInteger inactiveSubscriberCount;

        @Column(name = "disable_subscriber_count")
        private BigInteger disableSubscriberCount;

        @Column(name = "cert_revoke_subscriber_count")
        private BigInteger certRevokeSubscriberCount;

        @Column(name = "cert_expired_subscriber_count")
        private BigInteger certExpiredSubscriberCount;

        @Column(name = "onboarded_subscriber_count")
        private BigInteger onboardedSubscriberCount;

        // getters/setters
}




