package com.tenant.management;

import com.tenant.management.user.services.AdminServices.AdminLogger;
import com.tenant.management.user.services.AdminServices.AdminService;
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
    private final AdminService adminService;
    private final AdminLogger adminLogger;

    @Autowired
    public ManagementApplication(TenantService tenantService, TenantLogger tenantLogger,
                                 LandlordService landlordService, LandlordLogger landlordLogger, AdminService adminService, AdminLogger adminLogger) {
        this.tenantService = tenantService;
        this.tenantLogger = tenantLogger;
        this.landlordService = landlordService;
        this.landlordLogger = landlordLogger;
        this.adminService = adminService;
        this.adminLogger = adminLogger;
    }

    public static void main(String[] args) {
        SpringApplication.run(ManagementApplication.class, args);
    }

    @Autowired
    public void initializeObservers() {
        tenantService.attach(tenantLogger);
        landlordService.attach(landlordLogger);
        adminService.attach(adminLogger);
    }
}
