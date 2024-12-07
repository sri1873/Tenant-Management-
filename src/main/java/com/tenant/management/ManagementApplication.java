package com.tenant.management;

import com.tenant.management.user.services.LandlordServices.LandlordLogger;
import com.tenant.management.user.services.LandlordServices.LandlordService;
import com.tenant.management.user.services.TenantServices.TenantLogger;
import com.tenant.management.user.services.TenantServices.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ManagementApplication {

    private final TenantService tenantService;
    private final TenantLogger tenantLogger;
    private final LandlordService landlordService;
    private final LandlordLogger landlordLogger;

    @Autowired
    public ManagementApplication(TenantService tenantService, TenantLogger tenantLogger,
                                 LandlordService landlordService, LandlordLogger landlordLogger) {
        this.tenantService = tenantService;
        this.tenantLogger = tenantLogger;
        this.landlordService = landlordService;
        this.landlordLogger = landlordLogger;
    }

    public static void main(String[] args) {
        SpringApplication.run(ManagementApplication.class, args);
    }

    @Autowired
    public void initializeObservers() {
        tenantService.attach(tenantLogger);
        landlordService.attach(landlordLogger);
    }
}
