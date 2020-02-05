package root.demo.config;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import root.demo.util.TaskUrlEndpoint;


@Configuration
public class CorsConfig {
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
					.allowedHeaders("*")
					.allowedMethods("*")
					.allowedOrigins("*"); 
			}			
		};
	}
	
	@Bean
	public TaskUrlEndpoint getProperty() throws FileNotFoundException, IOException {
		TaskUrlEndpoint taskUrlEndpoint = new TaskUrlEndpoint("src/main/resources/task_url_endpoint.properties");
		
		return taskUrlEndpoint;
	}
	

}
