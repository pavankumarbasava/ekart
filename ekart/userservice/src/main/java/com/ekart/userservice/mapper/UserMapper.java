package com.ekart.userservice.mapper;

import org.springframework.beans.BeanUtils;

import com.ekart.userservice.entity.Address;
import com.ekart.userservice.entity.User;
import com.ekart.userservice.model.dto.model.AddressDto;
import com.ekart.userservice.model.dto.model.UserDto;

public interface UserMapper {

	
	 public static UserDto map(final User user) {
	        return UserDto.builder()
	        		.id(user.getId())
	        		.name(user.getName())
	        		.username(user.getUsername())
	        		.email(user.getEmail())
	        		.avatar(user.getAvatar())
	        		.roles(user.getRoles())
	        		.addresses(user.getAddresses())
	        		.paymentInformation(user.getPaymentInformation())
	        		.gender(user.getGender())
	        		.build();
	        	
	    }
	    
	    public static User map(final UserDto userDto) {
	        return User.builder()
	        		.id(userDto.getId())
	        		.name(userDto.getName())
	        		.username(userDto.getUsername())
	        		.email(userDto.getEmail())
	        		.avatar(userDto.getAvatar())
	        		.roles(userDto.getRoles())
	        		.addresses(userDto.getAddresses())
	        		.paymentInformation(userDto.getPaymentInformation())
	        		.gender(userDto.getGender())
	        		.build();
	                

	                
	    }
	    
		public static AddressDto addressMap(Address address) {
			AddressDto addressDto=new AddressDto();
			BeanUtils.copyProperties(address, addressDto);
			
			return addressDto;
		}
		public static Address addressMap(AddressDto addressDto) {
			Address address=new Address();
			BeanUtils.copyProperties(addressDto, address);
			
			return address;
		}

}
