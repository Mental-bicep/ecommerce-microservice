package com.order.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.order.DTO.ProductResponseDTO;
import com.order.client.PaymentClient;
import com.order.client.ProductSearchServiceClient;
import com.order.entity.OrderEntity;
import com.order.event.OrderPlacedEvent;
import com.order.kafkaproducer.OrderSuccessfulEventProducer;
import com.order.repository.OrderRepository;
import com.order.request.OrderRequest;

@Service
public class OrderService {
	
	private static final Logger log = LoggerFactory.getLogger(OrderService.class);
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	ProductSearchServiceClient productSearchServiceClient;
	
	@Autowired
	PaymentClient paymentClient;
	
	@Autowired
	OrderSuccessfulEventProducer orderSuccessfulEventProducer;
	
	
	public Integer checkout(OrderRequest req) {
		OrderEntity ent = new OrderEntity();
		ent.setUserId(req.getUserId());
		ent.setStatus("Order Recived");
		orderRepository.save(ent);
		
		log.info("Making feign call to ProductSeachService");
		// feign call to product search service to get price and quantity available in stock
		ResponseEntity<ProductResponseDTO> res = productSearchServiceClient.searchProductById(req.getProdId());
		if(res.getStatusCode() == HttpStatus.NOT_FOUND || res == null) {
			log.info("Null object Received from ProductSearchService");
			ent.setStatus("Order Cancelled: Invalid Product");
			orderRepository.save(ent);
		}
		
		
		// calculate the total amount and set in the ent
		ent.setTotalAmount(req.getQty() * res.getBody().getPrice());
		//check if quantity_req > inStock quantity
		// set status = failed , out of stock and return the msg
		
		if(res.getBody().getQty() < req.getQty()) {
			ent.setStatus("Failed: Could not process -Not enough stock");
			orderRepository.save(ent);
		}
		
		log.info("Making feign call to payment-service orderId: {} userId: {} totalAmount: {}",
				ent.getOrderId(), req.getUserId(), ent.getTotalAmount());
		// else make a feign call to payment service
		Map<String,Integer> payResp = paymentClient.pay(ent.getOrderId(), req.getUserId(), 
															ent.getTotalAmount()).getBody();
		
		// if payment failed - 
		// mark the status as payment failed and return with  msg
		if(payResp.get("paymentId") < 0) {
			log.info("Order Service :: payment failed for orderId ="+ent.getOrderId()
					+" userId= "+req.getUserId());
			ent.setStatus("Failed: Payment Failed");
			orderRepository.save(ent);
		}
		
		
		// if success mark the status as successful 
		// save the ent in the DB
		log.info("Both feign calls successful: order successful - saving order details to DB");
		ent.setStatus("Successful : order placed successfully");
		orderRepository.save(ent);
		
		OrderPlacedEvent orderPlacedEvent = orderPlacedEventMapper(ent, req);
		
		log.info("Publishing the Msg to kafka topic");
		// using kafka producers sent msgs to the kafka topic of
		// notification service and delivery service
		orderSuccessfulEventProducer.publishOrderPlaced(orderPlacedEvent);
		
		return ent.getOrderId();
		
	}
	
	private OrderPlacedEvent orderPlacedEventMapper(OrderEntity oEnt, OrderRequest req) {
		OrderPlacedEvent event = new OrderPlacedEvent();
		event.setOrderId(oEnt.getOrderId());
		event.setProdId(req.getProdId());
		event.setUserId(req.getUserId());
		event.setTotalAmount(oEnt.getTotalAmount());
		event.setStatus(oEnt.getStatus());
		event.setQty(req.getQty());
		event.setProdName(req.getProdName());
		return event;
	}
	
	
}
