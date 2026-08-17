package com.delivery.kafkaconsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.delivery.event.OrderPlacedEvent;


@Service
public class OrderDeliveryConsumer {
private static final Logger log = LoggerFactory.getLogger(OrderDeliveryConsumer.class);
	
	@KafkaListener(topics="ecom-service.order-placed-topic", groupId = "delivery-group")
	public void sendNotification(OrderPlacedEvent event) {
		log.info("Order Confirmed Notification :: Inititating Shipment for userId= "+event.getUserId()
				+" orderId= "+event.getOrderId()+ " product= "+event.getProdName()+" amount= "+
				event.getTotalAmount());
	}
}
