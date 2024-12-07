package com.tenant.management.user.services.AdminServices;

import com.tenant.management.property.entities.Property;
import com.tenant.management.property.repositories.PropertyRepository;
import com.tenant.management.user.entities.Admin;
import com.tenant.management.user.entities.RTB;
import com.tenant.management.user.repositories.AdminRepository;
import com.tenant.management.user.repositories.RtbRepository;
import com.tenant.management.user.requestdtos.RtbRequestDto;
import com.tenant.management.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class RtbService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private RtbRepository rtbRepository;

    public ApiResponse updateRtbStatus(RtbRequestDto requestDto) {
        // Admin Validation
        Optional<Admin> adminOptional = adminRepository.findByUuid(requestDto.getAdminId());
        if (adminOptional.isEmpty()) {
            return ApiResponse.builder().status(HttpStatus.NOT_FOUND).message("Admin not found")
                    .success(false).build();
        }
        //Property Validation
        Optional<Property> propertyOptional = propertyRepository.findById(requestDto.getPropertyId());
        if (propertyOptional.isEmpty()) {
            return ApiResponse.builder().status(HttpStatus.NOT_FOUND).message("Property not found")
                    .success(false).build();
        }
        RTB rtb = RTB.builder()
                .rtbVerified(requestDto.isRtbVerified())
                .verifiedDate(LocalDate.now())
                .remarks(requestDto.getRemarks())
                .build();
        rtbRepository.save(rtb);
        return ApiResponse.builder().status(HttpStatus.OK).message("RTB status updated successfully")
                .success(true).build();
    }
}
