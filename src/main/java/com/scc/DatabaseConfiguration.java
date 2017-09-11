package com.scc;

import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

import com.scc.model.Player;

@Configuration
public class DatabaseConfiguration extends RepositoryRestMvcConfiguration {

	@Bean
	ServletRegistrationBean h2servletRegistration() {
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
		registrationBean.addUrlMappings("/console/*");
		return registrationBean;
	}

	@Override
	public RepositoryRestConfiguration config() {
		RepositoryRestConfiguration configuration = super.config();
		configuration.setBasePath("/api");
		configuration.exposeIdsFor(Player.class);
		return configuration;
	}
}