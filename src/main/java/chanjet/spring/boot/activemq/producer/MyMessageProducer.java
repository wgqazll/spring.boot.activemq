package chanjet.spring.boot.activemq.producer;

import javax.jms.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import chanjet.spring.boot.activemq.po.Message;

@Component
public class MyMessageProducer {
	@Autowired
	private Queue queue;
	@Autowired
	private JmsTemplate jmsTemplate;
	
	public void sendMessage(Message message){
		String json = JSON.toJSONString(message);
		jmsTemplate.convertAndSend(queue, json);
    }

}
