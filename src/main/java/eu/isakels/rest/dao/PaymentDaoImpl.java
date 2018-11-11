package eu.isakels.rest.dao;

import eu.isakels.rest.Constants;
import eu.isakels.rest.model.Notification;
import eu.isakels.rest.model.payment.BasePayment;
import eu.isakels.rest.model.payment.PaymentFactory;
import eu.isakels.rest.repo.NotificationRepo;
import eu.isakels.rest.repo.PaymentRepo;
import eu.isakels.rest.repo.dto.NotificationDto;
import eu.isakels.rest.repo.dto.PaymentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class PaymentDaoImpl implements PaymentDao {

    private final PaymentRepo repo;
    private final NotificationRepo repoNotif;

    @Autowired
    public PaymentDaoImpl(final PaymentRepo repo, final NotificationRepo repoNotif) {
        this.repo = repo;
        this.repoNotif = repoNotif;
    }

    @Override
    public BasePayment create(final BasePayment payment) {
        repo.save(PaymentDto.ofPayment(payment));

        return payment;
    }

    @Override
    public Notification create(final Notification notif) {
        repoNotif.save(NotificationDto.ofNotif(notif));

        return notif;
    }

    @Override
    public Optional<BasePayment> query(final UUID id) {
        return repo.findById(id).map(dto -> PaymentFactory.ofDto(dto));
    }

    @Override
    public Set<BasePayment> query(final Map<String, ? extends Serializable> params) {
        final var set = repo.findAll(spec(params)).stream()
                .map(dto -> PaymentFactory.ofDto(dto))
                .collect(Collectors.toSet());

        return Set.copyOf(set);
    }

    private Specification<PaymentDto> spec(final Map<String, ? extends Serializable> params) {
        return (root, query, builder) -> {
            final var pList = new ArrayList<Predicate>();
            pList.add(builder.greaterThan(root.get("createdInstant"),
                    Instant.now().minus(Period.ofDays(3))));
            if (params.containsKey(Constants.cancelledParam)) {
                pList.add(builder.equal(root.get("cancelled"), params.get(Constants.cancelledParam)));
            }
            if (params.containsKey(Constants.amountGtParam)) {
                pList.add(builder.greaterThan(root.get("amount"),
                        (BigDecimal) params.get(Constants.amountGtParam)));
            }
            if (params.containsKey(Constants.amountLtParam)) {
                pList.add(builder.lessThan(root.get("amount"),
                        (BigDecimal) params.get(Constants.amountLtParam)));
            }
            return query.where(pList.toArray(new Predicate[0])).getRestriction();
        };
    }
}
