package net.pythia.spider.website.spider;

import net.pythia.spider.website.entity.Website;
import net.pythia.spider.website.service.WebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

@Component
public class WebsitePipeline implements Pipeline {
    @Autowired
    private WebsiteService websiteService;
    @Override
    public void process(ResultItems resultItems, Task task) {
        List<Website> list = resultItems.get("list");
        if (list != null){
            websiteService.saveAll(list);
        }
    }
}
