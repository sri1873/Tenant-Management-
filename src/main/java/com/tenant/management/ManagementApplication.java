package com.tenant.management;

import com.tenant.management.property.services.PropertyServiceImpl;
import com.tenant.management.user.services.AdminServices.AdminLogger;
import com.tenant.management.user.services.AdminServices.AdminService;
import com.tenant.management.user.services.LandlordServices.LandlordService;
import com.tenant.management.user.services.TenantServices.TenantLogger;
import com.tenant.management.user.services.TenantServices.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ManagementApplication {
    private final PropertyServiceImpl propertyService;
    private final TenantService tenantService;
    private final TenantLogger tenantLogger;
    private final LandlordService landlordService;
    private final AdminService adminService;
    private final AdminLogger adminLogger;

    @Autowired
    public ManagementApplication(TenantService tenantService, TenantLogger tenantLogger,PropertyServiceImpl propertyService,
                                 LandlordService landlordService, AdminService adminService, AdminLogger adminLogger) {
        this.tenantService = tenantService;
        this.propertyService= propertyService;
        this.tenantLogger = tenantLogger;
        this.landlordService = landlordService;
        this.adminService = adminService;
        this.adminLogger = adminLogger;
    }

    public static void main(String[] args) {
        SpringApplication.run(ManagementApplication.class, args);
    }

    @Autowired
    public void initializeObservers() {
        propertyService.attach(tenantLogger);
        tenantService.attach(adminLogger);
        landlordService.attach(adminLogger);
        adminService.attach(adminLogger);
    }
}
