package rss_parser.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import rss_parser.data.RssData;

@Repository
public interface RssRepository extends MongoRepository<RssData, String> {

}
