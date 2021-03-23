package com.rss.feed.core.servlets;

import com.rss.feed.core.dtos.Rss;
import com.rss.feed.core.services.FeedService;
import com.rss.feed.core.services.impl.FeedServiceImpl;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class FeedServletTest {
				
				AemContext aemContext = new AemContext();
				
				@Mock
				private FeedService feedService;
				
				@InjectMocks
				FeedServlet feedServlet = new FeedServlet();
				
				@BeforeEach
				void setUp() throws IOException {
								aemContext.load().json("/com/rss/feed/core/servlets/FeedServletTest.json", "/content");
								aemContext.requestPathInfo().setExtension("json");
								aemContext.currentResource("/content/feed");
								Mockito.lenient().when(feedService.getFeed()).thenReturn(new Rss());
								feedService = aemContext.registerService(feedService);
				}
				
				@Test
				void doGetTest() throws IOException {
								feedServlet.doGet(aemContext.request(), aemContext.response());
								String expected = "[{\"title\":\"Sample 1\",\"description\":\"Sample 1 Description\",\"pubDate\":\"2021-03-21T10:20:14.000+01:00\"},{\"title\":\"Sample 2\",\"description\":\"Sample 2 Description\",\"pubDate\":\"2021-03-21T09:10:42.000+01:00\"}]";
								assertEquals(expected, aemContext.response().getOutputAsString());
				}
}
