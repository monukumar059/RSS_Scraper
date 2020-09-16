package rss_parser.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import rss_parser.data.RssData;
import rss_parser.service.RssService;

import java.util.List;

@Controller
public class RssControllerImpl implements RssController {

    private RssService service;

    public RssControllerImpl(RssService service) {
        this.service = service;
    }

    @PostMapping("/rss/add")
    public String addRss(@RequestParam("url") String url) {
        service.addRssFeed(url);
        return "redirect:/rss/all";
    }

    @GetMapping("rss/delete")
    public String removeRss(@RequestParam("id") String id) {
        service.removeRssFeed(id);
        return "redirect:/rss/all";
    }

    @GetMapping("rss/file")
    @ResponseBody
    public ResponseEntity<byte[]> getRss(@RequestParam("id") String id) {
        RssData rss = service.getRssFeed(id);
        byte[] bytes = rss.getRss().getData();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.setContentLength(bytes.length);
        headers.setContentDispositionFormData("filename", rss.getTitle());

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    @GetMapping("/rss/all")
    public ModelAndView getRssList(ModelAndView modelAndView) {
        List<RssData> list = service.getAllRssFeeds();
        modelAndView.setViewName("index");
        modelAndView.addObject("rssList", list);
        return modelAndView;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/rss/all";
    }
}
