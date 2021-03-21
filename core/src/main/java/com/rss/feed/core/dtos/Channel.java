package com.rss.feed.core.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
@XmlAccessorType(XmlAccessType.FIELD)
public class Channel {
				@XmlElement(name="item")
				private List<Item> items;
				
				public List<Item> getItems() {
								return items;
				}
				
				public void setItems(List<Item> items) {
								this.items = items;
				}
}
