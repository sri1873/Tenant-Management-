package com.tenant.management.user.services;

import com.tenant.management.user.entities.Landlord;

public interface LandlordObserver {
    void onLandlordChange(Landlord landlord);
}
