package rss_parser.parser;

import com.sun.syndication.feed.synd.*;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component("telegram")
public class TelegramParser extends BaseParser {

    public static final String SELECTOR_FEED_INFO = ".tgme_channel_info";
    public static final String SELECTOR_FEED_TITLE = ".tgme_channel_info_header_title";
    public static final String SELECTOR_FEED_DESCRIPTION = ".tgme_channel_info_description";
    public static final String SELECTOR_FEED_LINK = ".tgme_channel_info_header";

    public static final String SELECTOR_MESSAGES = ".tgme_widget_message_bubble";
    public static final String SELECTOR_MESSAGE_TITLE = ".link_preview_title";
    public static final String SELECTOR_MESSAGE_DESCRIPTION = ".tgme_widget_message_text";
    public static final String SELECTOR_MESSAGE_DATE = "time";
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");


    private String getNameFromUrl(String url) {
        String[] temp = url.split("/");
        return temp[temp.length - 1];
    }

    SyndFeed getFeed(List<SyndEntry> messages) {
        SyndFeed feed = new SyndFeedImpl();
        Elements feedInfo = getHtmlData().select(SELECTOR_FEED_INFO);
        feed.setTitle(feedInfo.select(SELECTOR_FEED_TITLE).first().text());
        feed.setLink(feedInfo.select(SELECTOR_FEED_LINK).select("a").attr("href"));
        feed.setDescription(feedInfo.select(SELECTOR_FEED_DESCRIPTION).first().text());
        feed.setFeedType("rss_2.0");
        feed.setEntries(messages);
        return feed;
    }

    List<SyndEntry> getMessages() throws ParseException {
        Elements messages = getHtmlData().select(SELECTOR_MESSAGES);
        Collections.reverse(messages);
        List<SyndEntry> result = new ArrayList<>();
        for (Element element: messages) {
            try {
                SyndEntry entry = new SyndEntryImpl();
                entry.setTitle(element.select(SELECTOR_MESSAGE_TITLE).text());
                SyndContent description = new SyndContentImpl();
                description.setValue(element.select(SELECTOR_MESSAGE_DESCRIPTION).first().wholeText());
                entry.setDescription(description);
                entry.setLink(element.select(SELECTOR_MESSAGE_DESCRIPTION).select("a").attr("href"));
                entry.setPublishedDate(sdf.parse(element.select(SELECTOR_MESSAGE_DATE).attr("datetime")));
                result.add(entry);
            } catch (NullPointerException ignored) {
            }
        }
        return result;
    }
}
