package br.com.fiap.gamefinderapi;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.service.registry.ImportHttpServices;

@Configuration
@ImportHttpServices(IgdbClient.class)
public class HttpClientConfig {
}
