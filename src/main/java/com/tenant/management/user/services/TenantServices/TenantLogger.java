//Author: Mamtha Patalay
//ID:24182559

package com.tenant.management.user.services.TenantServices;


import com.tenant.management.property.entities.Property;
import com.tenant.management.user.Observer.TenantObserver;
import com.tenant.management.user.entities.Tenant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TenantLogger implements TenantObserver {
    private static final Logger logger = LoggerFactory.getLogger(TenantLogger.class);

    @Override
    public void onPropertyChange(Property property) {
        logger.info("Notification: New Property has been created - {}", property);
    }
}
