package rss_parser.data;

import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;


@Document(collection = "rss")
@Data
public class RssData {

    @Id
    @GeneratedValue
    private String id;

    private String url;

    private Binary rss;

    public String getTitle() {
        String[] parts = url.split("/");
        return parts[parts.length - 1];
    }
}
