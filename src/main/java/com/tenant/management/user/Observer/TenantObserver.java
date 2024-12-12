//Author: Mamtha Patalay
//ID:24182559

package com.tenant.management.user.Observer;

import com.tenant.management.property.entities.Property;

public interface TenantObserver {
    void onPropertyChange(Property property);
}
