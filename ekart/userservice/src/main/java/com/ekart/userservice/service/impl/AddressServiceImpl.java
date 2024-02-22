package com.ekart.userservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ekart.userservice.entity.Address;
import com.ekart.userservice.repository.AddressRepository;
import com.ekart.userservice.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

	private static final Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

	@Autowired
	private AddressRepository addrRepo;

	public List<Address> getAllAddresses(String name) {
		return addrRepo.getAllAddresses(name).get();
	}

	public Optional<Address> findById(Long addressId) {
		return addrRepo.findById(addressId);
	}

	public Address save(Address address) {
		return addrRepo.save(address);
	}

	public Address update(Address address) {
		return addrRepo.save(address);
	}

	public boolean deleteById(Long addressId) {

		try {
			addrRepo.deleteById(addressId);
			return true;
		} catch (Exception e) {
			logger.error("Exception while deleting address ", e);
			return false;
		}
	}

}
