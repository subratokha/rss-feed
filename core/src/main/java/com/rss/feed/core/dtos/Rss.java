package com.rss.feed.core.dtos;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="rss")
public class Rss {
				private Channel channel;
				
				public Channel getChannel() {
								return channel;
				}
				
				public void setChannel(Channel channel) {
								this.channel = channel;
				}
}
