//Author: Mamtha Patalay
//ID:24182559

package com.tenant.management.user.services.AdminServices;

import com.tenant.management.user.entities.Admin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AdminLogger implements AdminObserver {
    private static final Logger logger = LoggerFactory.getLogger(AdminLogger.class);

    @Override
    public void onAdminChange(Admin admin) {
        logger.info("Notification: Admin details changed - {}", admin);
    }
}
