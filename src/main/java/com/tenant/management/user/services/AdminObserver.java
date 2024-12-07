package com.tenant.management.user.services;

import com.tenant.management.user.entities.Admin;

public interface AdminObserver {
    void onAdminChange(Admin admin);

}
