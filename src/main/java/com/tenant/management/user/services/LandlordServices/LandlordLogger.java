//Author: Mamtha Patalay
//ID:24182559

package com.tenant.management.user.services.LandlordServices;

import com.tenant.management.user.entities.Landlord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LandlordLogger implements LandlordObserver {
    private static final Logger logger = LoggerFactory.getLogger(LandlordLogger.class);

    @Override
    public void onLandlordChange(Landlord landlord) {
        logger.info("Notification: Landlord details changed - {}", landlord);
    }
}
