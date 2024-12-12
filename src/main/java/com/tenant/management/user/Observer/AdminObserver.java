//Author: Mamtha Patalay
//ID:24182559

package com.tenant.management.user.Observer;

import com.tenant.management.user.entities.Admin;
import com.tenant.management.user.entities.Landlord;
import com.tenant.management.user.entities.Tenant;

public interface AdminObserver {
    void onTenantChange(Tenant tenant);
    void onAdminChange(Admin admin);
    void onLandlordChange(Landlord landlord);

}
