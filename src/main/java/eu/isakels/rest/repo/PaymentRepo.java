package eu.isakels.rest.repo;

import eu.isakels.rest.repo.dto.PaymentDto;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PaymentRepo
        extends CrudRepository<PaymentDto, UUID>, JpaSpecificationExecutor<PaymentDto> {
}
