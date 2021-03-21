package com.rss.feed.core.servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rss.feed.core.dtos.Item;
import com.rss.feed.core.dtos.Rss;
import com.rss.feed.core.services.FeedService;
import org.apache.commons.collections4.IterableUtils;
import org.apache.http.HttpStatus;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component(service = Servlet.class)
@SlingServletResourceTypes(resourceTypes = "rss-feed/components/feed",
				methods = HttpConstants.METHOD_GET,
				extensions = "json")

public class FeedServlet extends SlingSafeMethodsServlet {
				
				private static final Logger LOG = LoggerFactory.getLogger(FeedServlet.class);
				private static final String FALL_BACK_RESOURCE = "fallback";
				private static final String TITLE = "title";
				private static final String DESCRIPTION = "description";
				private static final String PUBLISH_DATE = "pubDate";
				
				
				@Reference
				private FeedService feedService;
				
				@Override
				protected void doGet(final SlingHttpServletRequest slingRequest,
																									final SlingHttpServletResponse slingResponse) throws IOException {
								Resource resource = slingRequest.getResource();
								List<Item> fallBackList = getFallBackSettings(resource);
								String listSize = resource.getValueMap().get("listSize", String.class);
								int size = listSize != null ? Integer.parseInt(listSize) : 10;
								String response;
								try {
												Rss rss = feedService.getFeed();
												List<Item> itemList = rss.getChannel().getItems();
												if (size > itemList.size()) {
																size = itemList.size();
												}
												if (itemList.isEmpty()) {
																response = convertPojoToJson(fallBackList);
												} else {
																response = convertPojoToJson(itemList.subList(0, size));
												}
								} catch (IOException e) {
												LOG.error("Error response from Feed service", e);
												response = convertPojoToJson(fallBackList);
								}
								slingResponse.setContentType("application/json");
								slingResponse.setCharacterEncoding("utf-8");
								slingResponse.getWriter().write(response);
								slingResponse.setStatus(HttpStatus.SC_OK);
				}
				
				/**
					* Get the Fallback Data inc ase of no response from endpoint
					*
					* @param resource Fallback resource
					*
					* @return List of Items
					*/
				private List<Item> getFallBackSettings(Resource resource) {
								Resource fallBackResource = resource.getChild(FALL_BACK_RESOURCE);
								Iterable<Resource> resourceIterable = IterableUtils.emptyIterable();
								if (fallBackResource != null && fallBackResource.hasChildren()) {
												resourceIterable = fallBackResource.getChildren();
								}
								List<Item> fallBackItemList = new ArrayList<>();
								for (Resource res : resourceIterable) {
												ValueMap valueMap = res.getValueMap();
												Item item = new Item();
												item.setTitle(valueMap.get(TITLE, String.class));
												item.setDescription(valueMap.get(DESCRIPTION, String.class));
												item.setPubDate(valueMap.get(PUBLISH_DATE, String.class));
												fallBackItemList.add(item);
								}
								return fallBackItemList;
				}
				
				/**
					* Convert POJO to Json String
					*
					* @param object Object to be converted
					*
					* @return Json String
					*/
				private String convertPojoToJson(Object object) {
								ObjectMapper mapper = new ObjectMapper();
								try {
												return mapper.writeValueAsString(object);
								} catch (JsonProcessingException e) {
												LOG.error("Error processing POJO to Json", e);
								}
								return "[]";
				}
}
