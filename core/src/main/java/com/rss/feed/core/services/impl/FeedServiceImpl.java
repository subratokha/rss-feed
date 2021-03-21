package com.rss.feed.core.services.impl;

import com.rss.feed.core.clients.FeedClient;
import com.rss.feed.core.dtos.Rss;
import com.rss.feed.core.services.FeedService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
	* FeedService Implementation class to get RSS Feed from endpoint and convert them to RSS Object
	*/
@Component(service = FeedService.class, configurationPolicy = ConfigurationPolicy.REQUIRE)
@Designate(
				ocd = FeedConfigurations.class
)
public class FeedServiceImpl implements FeedService {
				
				private static final Logger LOG = LoggerFactory.getLogger(FeedServiceImpl.class);
				
				private FeedClient feedClient;
				private String feedUrl;
				
				@Activate
				public void activate(FeedConfigurations feedConfigurations) {
								this.feedClient = new FeedClient();
								this.feedUrl = feedConfigurations.url();
				}
				
				/**
					* Get RSS Feed
					*
					* @return RSS Object
					*
					* @throws IOException IOException
					*/
				@Override
				public Rss getFeed() throws IOException {
								String response = this.feedClient.get(feedUrl);
								return convertXmlToPojo(response);
				}
				
				/**
					* Convert String XML Representation of RSS Feed to POJO
					*
					* @param xml String XML
					*
					* @return RSS Object
					*/
				private Rss convertXmlToPojo(String xml) {
								try {
												JAXBContext jaxbContext = JAXBContext.newInstance(Rss.class);
												Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
												Reader reader = new StringReader(xml);
												return (Rss) unmarshaller.unmarshal(reader);
								} catch (JAXBException e) {
												LOG.error("Error parsing the XML to Objects", e);
								}
								return null;
				}
}
