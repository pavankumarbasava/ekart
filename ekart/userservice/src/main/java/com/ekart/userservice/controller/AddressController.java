package com.ekart.userservice.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ekart.userservice.entity.Address;
import com.ekart.userservice.entity.User;
import com.ekart.userservice.http.HeaderGeneration;
import com.ekart.userservice.mapper.UserMapper;
import com.ekart.userservice.model.dto.model.AddressDto;
import com.ekart.userservice.model.dto.response.ResponseMessage;
import com.ekart.userservice.service.impl.AddressServiceImpl;
import com.ekart.userservice.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/api/information/address")
public class AddressController {

	private static final Logger logger = LoggerFactory.getLogger(AddressController.class);

	@Autowired
	private AddressServiceImpl addressService;

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private HeaderGeneration headerGenerator;

	@GetMapping(value = "/user/current/getAllAddresses")
	public ResponseEntity<?> getAddressList(Principal principle) {// , @RequestHeader(name = "Authorization") String
																	// authorizationToken) {
		String userName = principle.getName();
		List<Address> allAddresses = addressService.getAllAddresses(userName);
		try {

			if (!allAddresses.isEmpty()) {

				return new ResponseEntity<>(allAddresses, headerGenerator.getHeadersForSuccessGetMethod(),
						HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.error("There is an exception while retriving address ", e);

		}
		return new ResponseEntity<>(new ResponseMessage("There are no address for this user"),
				headerGenerator.getHeadersForError(), HttpStatus.NOT_FOUND);

	}

	@GetMapping("/get/{addressId}")
	public ResponseEntity<?> findById(@PathVariable("addressId") final Long addressId) {
		return ResponseEntity.ok(this.addressService.findById(addressId).get());
	}

	@PostMapping(value = "/save")
	public ResponseEntity<?> saveAddress(@RequestBody  AddressDto addressDto, Principal principle) {
		Address address=null;
		try {
			User user = userService.getUserFromUserName(principle.getName());
			 address = UserMapper.addressMap(addressDto);
			 address.setUser(user);
			address = addressService.save(address);

		} catch (Exception e) {
			logger.error("Exception while saving address ", e);

			return new ResponseEntity<>(new ResponseMessage("Please send proper address fields"), headerGenerator.getHeadersForError(),
					HttpStatus.BAD_REQUEST);
		}
//		return ResponseEntity.ok(this.);

		return new ResponseEntity<>(address, headerGenerator.getHeadersForSuccessGetMethod(),
				HttpStatus.OK);
	}

	@PutMapping(value = "/update")
	public ResponseEntity<Address> update(@RequestBody final Address addressDto, Principal principle) {

		User user = userService.getUserFromUserName(principle.getName());
		addressDto.setUser(user);
		return ResponseEntity.ok(this.addressService.update(addressDto));
	}

	@PutMapping("update/{addressId}")
	public ResponseEntity<Address> update(@PathVariable("addressId") Long addressId,
			@RequestBody final Address addressDto,Principal principle) {
		
		User user = userService.getUserFromUserName(principle.getName());
		addressDto.setUser(user);
		addressDto.setId(addressId);
		return ResponseEntity.ok(this.addressService.update(addressDto));
	}

	@DeleteMapping("delete/{addressId}")
	public ResponseEntity<Boolean> deleteById(@PathVariable("addressId")  Long addressId) {
		
		return ResponseEntity.ok(this.addressService.deleteById(addressId));
	}

//	
//	
//    @GetMapping
//    public ResponseEntity<AddressUserServiceCollectionDtoResponse> findAll() {
//        return ResponseEntity.ok(this.addressClientService.findAll().getBody());
//    }
//
//    @GetMapping("/{addressId}")
//    public ResponseEntity<AddressDto> findById(@PathVariable("addressId") final String addressId) {
//        return ResponseEntity.ok(this.addressClientService.findById(addressId).getBody());
//    }
//
//    @PostMapping
//    public ResponseEntity<AddressDto> save(@RequestBody final AddressDto addressDto) {
//        return ResponseEntity.ok(this.addressClientService.save(addressDto).getBody());
//    }
//
//    @PutMapping
//    public ResponseEntity<AddressDto> update(@RequestBody final AddressDto addressDto) {
//        return ResponseEntity.ok(this.addressClientService.update(addressDto).getBody());
//    }
//
//    @PutMapping("/{addressId}")
//    public ResponseEntity<AddressDto> update(@PathVariable("addressId") final String addressId, @RequestBody final AddressDto addressDto) {
//        return ResponseEntity.ok(this.addressClientService.update(addressDto).getBody());
//    }
//
//    @DeleteMapping("/{addressId}")
//    public ResponseEntity<Boolean> deleteById(@PathVariable("addressId") final String addressId) {
//        return ResponseEntity.ok(this.addressClientService.deleteById(addressId).getBody());
//    }

}