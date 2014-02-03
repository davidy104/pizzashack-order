package co.nz.pizzashack.client.utils;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.api.json.JSONConfiguration;

@Component
public class JerseyClientSupport {

	protected Client client;

	public static String AUTH_HEADER = "Authorization";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(JerseyClientSupport.class);

	@PostConstruct
	private void init() {
		com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
		config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,
				Boolean.TRUE);
		client = Client.create(config);
		client.setConnectTimeout(10000);
		client.setReadTimeout(10000);
		client.addFilter(new LoggingFilter(System.out));
	}

	protected String getResponsePayload(ClientResponse response)
			throws IOException {
		StringBuffer buffer = new StringBuffer();
		InputStream in = null;
		try {
			if (response.getEntityInputStream() != null) {
				in = response.getEntityInputStream();
				int length;
				byte[] tmp = new byte[2048];
				while ((length = in.read(tmp)) != -1) {
					buffer.append(new String(tmp, 0, length));
				}
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return buffer.toString();
	}

	@PreDestroy
	public void cleanup() {
		client.destroy();
	}

}
