//Author: Mamtha Patalay
//ID:24182559

package com.tenant.management.user.services.LandlordServices;

import com.tenant.management.user.entities.Landlord;

public interface LandlordObserver {
    void onLandlordChange(Landlord landlord);
}
