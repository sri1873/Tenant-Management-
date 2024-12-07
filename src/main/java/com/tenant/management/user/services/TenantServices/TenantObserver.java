package com.tenant.management.user.services.TenantServices;

import com.tenant.management.user.entities.Tenant;

public interface TenantObserver {
    void onTenantChange(Tenant tenant);
}
