package com.rss.feed.core.clients;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FeedClient {
				
				private static final CloseableHttpClient httpClient = HttpClients.createDefault();
				
				
				/**
					* Get request to RSS Feed
					*
					* @param url endpoint url
					*
					* @return response as string
					*
					* @throws IOException IOException
					*/
				public String get(String url) throws IOException {
								HttpGet httpGet = new HttpGet(url);
								try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
												HttpEntity entity = handleError(response).getEntity();
												return EntityUtils.toString(entity, StandardCharsets.UTF_8);
								}
				}
				
				/**
					* Handle Error response after the http call
					*
					* @param response http response
					*
					* @return response
					*
					* @throws IOException IOException
					*/
				private static CloseableHttpResponse handleError(CloseableHttpResponse response) throws IOException {
								int statusCode = response.getStatusLine().getStatusCode();
								if (statusCode != HttpStatus.SC_OK && statusCode != HttpStatus.SC_CREATED) {
												String content = EntityUtils.toString(response.getEntity()) + response.getStatusLine().getReasonPhrase();
												throw new IOException("Server Returned Error : " + content);
								}
								return response;
				}
}
