package root.demo.config;

import java.security.NoSuchAlgorithmException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.discovery.DiscoveryClient.DiscoveryClientOptionalArgs;
import com.netflix.discovery.shared.transport.jersey.EurekaJerseyClientImpl.EurekaJerseyClientBuilder;

@Configuration
public class SecurityConfig {
	
	private static final String KEYSTOREFILEPATH = "src/main/resources/kps_naucna_centrala_keystore.jks";
	private static final String TRUSTSTOREFILEPATH = "src/main/resources/kps_naucna_centrala_truststore.jks";
	
	@Bean 
	public DiscoveryClientOptionalArgs discoveryClientOptionalArgs() throws NoSuchAlgorithmException {
		DiscoveryClientOptionalArgs args = new DiscoveryClientOptionalArgs();
		System.setProperty("javax.net.ssl.keyStore", KEYSTOREFILEPATH);
		System.setProperty("javax.net.ssl.keyStorePassword", "sepstore");
		System.setProperty("javax.net.ssl.trustStore", TRUSTSTOREFILEPATH);
		System.setProperty("javax.net.ssl.trustStorePassword", "sepstore");
		
		EurekaJerseyClientBuilder builder = new EurekaJerseyClientBuilder();
		builder.withClientName("demo");
		builder.withSystemSSLConfiguration();
		builder.withMaxTotalConnections(10);
		builder.withMaxConnectionsPerHost(10);
		args.setEurekaJerseyClient(builder.build());
		
		return args;
	}

}
