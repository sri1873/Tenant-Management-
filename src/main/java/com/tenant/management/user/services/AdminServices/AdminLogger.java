// Author: Mamtha Patalay
// ID: 24182559

package com.tenant.management.user.services.AdminServices;

import com.tenant.management.user.Observer.AdminObserver;
import com.tenant.management.user.entities.Admin;
import com.tenant.management.user.entities.Landlord;
import com.tenant.management.user.entities.Tenant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AdminLogger implements AdminObserver {
    private static final Logger logger = LoggerFactory.getLogger(AdminLogger.class);

    @Override
    public void onTenantChange(Tenant tenant) {
        logger.info("Admin Notification: Tenant details have been updated - {}", tenant);

    }

    @Override
    public void onAdminChange(Admin admin) {

            logger.info("Admin Notification: Admin details have been updated - {}", admin);

    }

    @Override
    public void onLandlordChange(Landlord landlord){
        logger.info("Admin Notification: Landlord details have been updated - {}", landlord);

    }
}
