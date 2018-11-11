package eu.isakels.rest.repo;

import eu.isakels.rest.repo.dto.NotificationDto;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface NotificationRepo extends CrudRepository<NotificationDto, UUID> {
}
