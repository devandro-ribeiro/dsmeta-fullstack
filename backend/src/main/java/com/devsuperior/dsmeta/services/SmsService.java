package com.devsuperior.dsmeta.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SmsService {

	@Value("${twilio.sid}")
	private String twilioSid;

	@Value("${twilio.key}")
	private String twilioKey;

	@Value("${twilio.phone.from}")
	private String twilioPhoneFrom;

	@Value("${twilio.phone.to}")
	private String twilioPhoneTo;
	
	@Autowired
	private SaleRepository saleRepository;

	public void sendSms(Long SaleId) {
		
		Sale sale = saleRepository.findById(SaleId).get();
		String date = sale.getDate().getMonthValue()+ "/" +sale.getDate().getYear();
		String sms = "Olá Dra Monique, tudo bem com você? Espero que sim, eu me chamo Evandro, sou estudante de TI e estou te mostrando através desta msn o que estou aprendendo com a programação.\n Veja só: Vendedor  " + sale.getSellerName()+ " foi destaque em " + date
				+ " com o total de venda R$ " + String.format("%.2f", sale.getAmount());
		
		Twilio.init(twilioSid, twilioKey);

		PhoneNumber to = new PhoneNumber(twilioPhoneTo);
		PhoneNumber from = new PhoneNumber(twilioPhoneFrom);

		Message message = Message.creator(to, from, sms).create();

		System.out.println(message.getSid());
	}
}
