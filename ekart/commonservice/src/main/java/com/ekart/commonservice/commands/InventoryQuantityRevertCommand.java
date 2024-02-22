package com.ekart.commonservice.commands;

import java.util.List;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.ekart.commonservice.dto.ProductSagaDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryQuantityRevertCommand {

	@TargetAggregateIdentifier
	private String orderCode;
	private List<ProductSagaDto> products;
	
}
