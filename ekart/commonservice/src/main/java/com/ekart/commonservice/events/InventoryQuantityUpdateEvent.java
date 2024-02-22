package com.ekart.commonservice.events;

import java.util.List;

import com.ekart.commonservice.dto.ProductSagaDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class InventoryQuantityUpdateEvent {

	private List<ProductSagaDto> products;
	private String orderCode;
}
