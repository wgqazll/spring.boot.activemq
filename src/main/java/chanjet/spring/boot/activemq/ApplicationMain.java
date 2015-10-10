package chanjet.spring.boot.activemq;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import chanjet.spring.boot.activemq.po.Message;
import chanjet.spring.boot.activemq.producer.MyMessageProducer;


@SpringBootApplication
public class ApplicationMain {
	
	private static MyMessageProducer producer;
	
    public MyMessageProducer getProducer() {
		return producer;
	}
    
    @Autowired
	public void setProducer(MyMessageProducer producer) {
		ApplicationMain.producer = producer;
	}

	public static void main( String[] args ){
    	SpringApplication.run(ApplicationMain.class, args);
    	
    	Message message = new Message();
    	message.setHeader("header");
    	message.setInfo("info");
    	
		producer.sendMessage(message);
    }
}
