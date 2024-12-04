package com.tenant.management.rental.entities;


import com.tenant.management.utils.ApiResponse;


public interface Command {
    ApiResponse execute();

    ApiResponse undo();
}

