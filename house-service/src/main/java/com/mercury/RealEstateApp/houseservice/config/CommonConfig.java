package com.mercury.RealEstateApp.houseservice.config;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.NoConnectionReuseStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CommonConfig {

  private static final Integer HTTPCLIENT_CONNECTTIMEOUT = 1000;
  private static final Integer HTTPCLIENT_SOCKETTIMEOUT  = 30000;
  private static final String HTTPCLIENT_AGENT           = "mooc-agent";

  @Bean
  HttpClient httpClient() {
    Integer connectTimeOut = HTTPCLIENT_CONNECTTIMEOUT;
    Integer socketTimeOut  = HTTPCLIENT_SOCKETTIMEOUT;
    String agentStr        = HTTPCLIENT_AGENT;
    RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeOut)
        .setSocketTimeout(socketTimeOut).build();

    HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig)
        .setUserAgent(agentStr).setMaxConnPerRoute(10).setMaxConnTotal(50).disableAutomaticRetries()
        .setConnectionReuseStrategy(new NoConnectionReuseStrategy()).build();
    return client;
  }

  @Bean
  @LoadBalanced
  RestTemplate lbRestTemplate() {
    return new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient()));
  }

  @Bean
  RestTemplate plainRestTemplate() {
    return new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient()));
  }
  
  

}
