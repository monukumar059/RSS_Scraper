package rss_parser.service;

import rss_parser.data.RssData;

import java.util.List;

public interface RssService {

    boolean addRssFeed(String url);

    boolean removeRssFeed(String id);

    RssData getRssFeed(String id);

    List<RssData> getAllRssFeeds();
}
