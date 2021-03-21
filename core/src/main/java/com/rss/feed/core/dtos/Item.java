package com.rss.feed.core.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Item {
				private String title;
				private String description;
				private String pubDate;
				
				public String getTitle() {
								return title;
				}
				
				public void setTitle(String title) {
								this.title = title;
				}
				
				public String getDescription() {
								return description;
				}
				
				public void setDescription(String description) {
								this.description = description;
				}
				
				public String getPubDate() {
								return pubDate;
				}
				
				public void setPubDate(String pubDate) {
								this.pubDate = pubDate;
				}
}
