package com.payment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.entity.PaymentEntity;
import com.payment.repository.PaymentRepository;

@Service
public class PaymentService {
	
	private static final Logger log = LoggerFactory.getLogger(PaymentService.class);
	
	@Autowired
	PaymentRepository paymentRepository;
	
	public Integer makePayment(Integer orderId, String userId, Double amt) {
		
		PaymentEntity ent = new PaymentEntity();
		ent.setOrderId(orderId);
		ent.setUserId(userId);
		ent.setTotalAmount(amt);
		
		paymentRepository.save(ent);
		if(ent.getId() > 0) {
			log.info("payment successful for orderId= "+orderId+" userId= "+userId+" amt="+amt);
			return ent.getId();
		}
		return -1;
		
	}
	
}
