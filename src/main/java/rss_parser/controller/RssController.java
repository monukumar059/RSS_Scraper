package rss_parser.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

public interface RssController {

    String addRss(String url);
    String removeRss(String id);
    ResponseEntity<byte[]> getRss(String id);
    ModelAndView getRssList(ModelAndView modelAndView);
}
