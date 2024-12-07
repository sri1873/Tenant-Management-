package com.tenant.management.user.services;

import com.tenant.management.user.entities.Tenant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TenantLogger implements TenantObserver {
    private static final Logger logger = LoggerFactory.getLogger(TenantLogger.class);

    @Override
    public void onTenantChange(Tenant tenant) {
        logger.info("Notification: Tenant details changed - {}", tenant);
    }
}
