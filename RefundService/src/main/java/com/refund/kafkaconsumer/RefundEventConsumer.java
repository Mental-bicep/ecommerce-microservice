package com.refund.kafkaconsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


import com.refund.event.OrderRefundEvent;

@Service
public class RefundEventConsumer {

	private static final Logger log = LoggerFactory.getLogger(RefundEventConsumer.class);
	@KafkaListener(topics = "ecom-service.order-refund-topic", groupId = "refund-group")
	public void initiateRefund(OrderRefundEvent event) {
		
		log.info("Refund of Rs. {}: successful for userid: {} orderId: {} ",
				event.getTotalAmount(),event.getUserId(),event.getOrderId());
	}
	
}
