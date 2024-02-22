package com.ekart.userservice.model.dto.model;

import com.ekart.userservice.entity.RoleName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RolesDto {

	
    private Long id;

    private RoleName name;
    
}
