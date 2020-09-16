package rss_parser.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import rss_parser.data.RssData;
import rss_parser.parser.Parser;
import rss_parser.repository.RssRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RssServiceImpl implements RssService {

    private final RssRepository repository;

    private final Map<String, Parser> parsers;

    public RssServiceImpl(RssRepository repository, Map<String, Parser> parsers) {
        this.repository = repository;
        this.parsers = parsers;
    }

    private Parser getInstance(String url) {
        if (url.contains(Parser.TELEGRAM_PATTERN))
            return parsers.get(Parser.TELEGRAM);
        return null;
    }

    @Override
    public boolean addRssFeed(String url) {
        if (checkForExistence(url)) {
            repository.save(getRssDataFromUrl(url));
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean removeRssFeed(String id) {
        repository.deleteById(id);
        return true;
    }

    public boolean checkForExistence(String url) {
        List<String> urls = repository.findAll().stream().map(RssData::getUrl).collect(Collectors.toList());
        return !urls.contains(url);
    }

    @Scheduled(fixedRate = 100000)
    public void updateRss() {
        List<RssData> data = getAllRssFeeds();
        for (RssData rss : data) {
            Parser parser = getInstance(rss.getUrl());
            if (parser == null)
                return;
            parser.startParsing(rss.getUrl());
            parser.parseData();
            rss.setRss(parser.saveToBinary());
            repository.save(rss);
        }
    }

    @Override
    public RssData getRssFeed(String id) {
        return repository.findById(id).get();
    }

    public List<RssData> getAllRssFeeds() {
        return repository.findAll();
    }

    private RssData getRssDataFromUrl(String url) {
        RssData rss = new RssData();
        rss.setUrl(url);
        Parser parser = getInstance(url);
        if (parser == null) {
            return null;
        }
        parser.startParsing(url);
        parser.parseData();
        rss.setRss(parser.saveToBinary());
        return rss;
    }
}
