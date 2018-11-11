package eu.isakels.rest.dao.factory;

import eu.isakels.rest.model.Notification;
import eu.isakels.rest.repo.dto.NotificationDto;

public abstract class NotificationDtoFactory {

    public static NotificationDto ofNotif(final Notification notif) {
        return new NotificationDto(notif.getPaymentId(), notif.isSuccess());
    }
}
