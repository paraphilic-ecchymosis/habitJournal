package com.crookedcoder.habitjournal;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.crookedcoder.habitjournal.entity.Habit;
import com.crookedcoder.habitjournal.repository.HabitRepository;
import com.crookedcoder.habitjournal.service.HabitQueryServiceNoSql;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class HabitJournalApplication extends SpringBootServletInitializer {

	HabitQueryServiceNoSql habitQueryServiceNoSql;
	
	@EventListener(ApplicationReadyEvent.class)
	public void intializeUserData() {
		habitQueryServiceNoSql = new HabitQueryServiceNoSql();		
		Habit pushUps = new Habit("Pushups", "Complete ten pushups.");
		Habit pullUps = new Habit("Pullups", "Complete ten pushups.");
		habitQueryServiceNoSql.save(pushUps);
		habitQueryServiceNoSql.save(pullUps);
	}

	public static void main(String[] args) throws IOException, GeneralSecurityException {
		SpringApplication.run(HabitJournalApplication.class, args);

	}

	@Bean
	public ServletWebServerFactory servletContainer() {
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
			@Override
			protected void postProcessContext(Context context) {
				SecurityConstraint securityConstraint = new SecurityConstraint();
				securityConstraint.setUserConstraint("CONFIDENTIAL");
				SecurityCollection collection = new SecurityCollection();
				collection.addPattern("/*");
				securityConstraint.addCollection(collection);
				context.addConstraint(securityConstraint);
			}
		};
		tomcat.addAdditionalTomcatConnectors(redirectConnector());
		return tomcat;
	}

	private Connector redirectConnector() {
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setScheme("http");
		connector.setPort(80);
		connector.setRedirectPort(443);
		return connector;
	}
}
