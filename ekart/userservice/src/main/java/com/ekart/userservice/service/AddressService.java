package com.ekart.userservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ekart.userservice.entity.Address;


public interface AddressService {

	public List<Address> getAllAddresses(String name);
}
