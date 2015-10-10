package chanjet.spring.boot.activemq.config;


import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jms.core.JmsTemplate;


@Configuration
public class ActiveMQConfig implements EnvironmentAware{
	
	private static Log logger = LogFactory.getLog(ActiveMQConfig.class); 
	
	private RelaxedPropertyResolver propertyResolver;
	
	@Override
	public void setEnvironment(Environment environment) {
		 this.propertyResolver = new RelaxedPropertyResolver(environment, "activeMQ."); 
	}
	
	public ConnectionFactory connectionFactory(){
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(propertyResolver.getProperty("userName")
				,propertyResolver.getProperty("password"),propertyResolver.getProperty("brokerURL"));
		connectionFactory.setMaxThreadPoolSize(Integer.parseInt(propertyResolver.getProperty("maxThreadPoolSize")));
		connectionFactory.setUseAsyncSend(Boolean.parseBoolean(propertyResolver.getProperty("useAsyncSend")));
		return connectionFactory;
	}
	
	public PooledConnectionFactory pooledConnectionFactory(){
		PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory((ActiveMQConnectionFactory)connectionFactory());
		logger.info("init activemq pool success");
		return pooledConnectionFactory;
	}
	
	@Bean(name="queue")
	public Queue queue(){
		ActiveMQQueue activeMQ = new ActiveMQQueue(propertyResolver.getProperty("name"));
		return activeMQ;
	}
	
	@Bean(name="jmsTemplate")
	public JmsTemplate jmsTemplate(){
		JmsTemplate jmsTemplate = new JmsTemplate(pooledConnectionFactory());
		return jmsTemplate;
	}
}
