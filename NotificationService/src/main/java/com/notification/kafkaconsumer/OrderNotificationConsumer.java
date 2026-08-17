package com.notification.kafkaconsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.notification.event.OrderPlacedEvent;

@Service
public class OrderNotificationConsumer {
	private static final Logger log = LoggerFactory.getLogger(OrderNotificationConsumer.class);
	
	@KafkaListener(topics="ecom-service.order-placed-topic", groupId = "notification-group")
	public void sendNotification(OrderPlacedEvent event) {
		log.info("Order Confirmed Notification :: Order Confirmed for userId= "+event.getUserId()
				+" orderId= "+event.getOrderId()+ " product= "+event.getProdName()+" amount= "+
				event.getTotalAmount());
	}
	
}
