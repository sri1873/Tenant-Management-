package com.tenant.management.user.services;

import com.tenant.management.user.entities.Tenant;

public interface TenantObserver {
    void onTenantChange(Tenant tenant);
}
