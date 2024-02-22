package com.ekart.commonservice.kafaka.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StatusUpdateEvent {

	private String code;
	private String status;
	private String token;
	
}
