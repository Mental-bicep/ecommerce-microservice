package com.refund.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.refund.entity.RefundEntity;
import com.refund.event.OrderRefundEvent;
import com.refund.repository.RefundRepository;

@Service
public class RefundService {

	private static final Logger log = LoggerFactory.getLogger(RefundService.class);
	
	@Autowired
	RefundRepository refundRepository;
	
	public void processRefund(OrderRefundEvent event) {
		RefundEntity ent = refundEntityMapper(event);
		refundRepository.save(ent);
		log.info("Refund entry saved successfully ");
	}
	
	private RefundEntity refundEntityMapper(OrderRefundEvent e) {
		RefundEntity ent = new RefundEntity();
		ent.setOrderId(e.getOrderId());
		ent.setTotalAmount(e.getTotalAmount());
		ent.setUserId(e.getUserId());
		return ent;
	}
	
}
