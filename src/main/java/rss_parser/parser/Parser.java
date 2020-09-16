package rss_parser.parser;

import org.bson.types.Binary;
import java.io.File;


public interface Parser {

    public static final String TELEGRAM_PATTERN = "t.me";
    public static final String TELEGRAM = "telegram";

    boolean startParsing(String url);

    boolean parseData();

    File saveToFile();

    Binary saveToBinary();
}
