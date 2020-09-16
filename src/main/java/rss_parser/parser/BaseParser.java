package rss_parser.parser;

import ch.qos.logback.core.util.FileUtil;
import com.mchange.io.FileUtils;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;


public abstract class BaseParser implements Parser {

    public static final String FILEPATH = "/Users/yuranous/IdeaProjects/RSS_Parser/src/main/resources/static/rss_files/";

    private Document htmlData;

    private SyndFeed feed;

    public BaseParser() {
    }

    public boolean startParsing(String url) {
        try {
            this.htmlData = Jsoup.connect(url).get();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean parseData() {
        try {
            List<SyndEntry> messages = getMessages();
            this.feed = getFeed(messages);
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    abstract List<SyndEntry> getMessages() throws ParseException;
    abstract SyndFeed getFeed(List<SyndEntry> messages);

//    public boolean saveToFile() {
//        try {
//            FileWriter writer = new FileWriter(FILEPATH + feed.getTitle());
//            SyndFeedOutput output = new SyndFeedOutput();
//            output.output(feed, writer);
//            writer.close();
//            return true;
//        } catch (IOException | FeedException e) {
//            return false;
//        }
//    }

    public File saveToFile() {
        File file = new File(FILEPATH + feed.getTitle());
        SyndFeedOutput output = new SyndFeedOutput();
        try {
            output.output(feed, file);
        } catch (IOException | FeedException e) {
            e.printStackTrace();
        }
        return file;
    }

    public Binary saveToBinary() {
        try {
            return new Binary(BsonBinarySubType.BINARY, FileUtils.getBytes(saveToFile()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Document getHtmlData() {
        return htmlData;
    }
}
