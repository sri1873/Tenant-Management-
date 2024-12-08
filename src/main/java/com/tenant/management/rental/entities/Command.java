package com.tenant.management.rental.entities;


import com.tenant.management.utils.ApiResponse;

//Author : K S SRI KUMAR
//Id : 24177474
public interface Command {
    ApiResponse execute();

    ApiResponse undo();
}

