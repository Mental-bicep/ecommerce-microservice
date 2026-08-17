package com.order.kafkaproducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.order.event.OrderPlacedEvent;

@Service
public class OrderSuccessfulEventProducer {
	private static final Logger log = LoggerFactory.getLogger(OrderSuccessfulEventProducer.class);
    private static final String TOPIC = "ecom-service.order-placed-topic";
    
    @Autowired
    private KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
    
    public void publishOrderPlaced(OrderPlacedEvent event) {
    	// using orderId as the key to maintain partitioning per order/OrderId
    	kafkaTemplate.send(TOPIC, event.getOrderId()+"",event)
    		.whenComplete((res,ex) ->{
    			if(ex == null)
    				log.info("Published order Event for orderId: {} ProductName: {} "
    						+ "to partition: {} ",event.getOrderId(), event.getProdName(),
    						res.getRecordMetadata().partition());
    			else 
    				log.error("failed to publish event for orderId: {} productName: {} ",event.getOrderId(),
    						event.getProdName(),ex);
    		});
    	
    }
    
}
