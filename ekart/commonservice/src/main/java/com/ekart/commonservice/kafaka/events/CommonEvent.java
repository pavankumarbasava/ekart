package com.ekart.commonservice.kafaka.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonEvent {

	private String orderCode;
	private String token;
}
