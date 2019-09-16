package net.pythia.spider.website.spider;

import net.pythia.spider.website.entity.Website;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class WebsiteProcess implements PageProcessor {
    @Override
    public void process(Page page) {
        List<Selectable> nodes = page.getHtml().css("div.Taleft a").nodes();
        if (nodes.size() == 0) {
            this.saveWebsite(page);
        }else {
            for (Selectable node : nodes) {
                String url = node.links().toString();
                page.addTargetRequest( url );
            }
        }

    }

    private void saveWebsite(Page page) {
        Html html = page.getHtml();
        List<Selectable> selectableList = html.css("div.ListPageWrap a").nodes();
        for (Selectable selectable : selectableList) {
            String url = selectable.links().toString();
            if(!"".equals(url)){
                page.addTargetRequest(url);
            }

        }
        String websiteType = "未知分类";
        String websiteTypeHtml = html.css("div.TopListCent-Head p.Headtitle").toString();
        if(websiteTypeHtml != null){
            websiteType = Jsoup.parse(websiteTypeHtml).text();
            websiteType = websiteType.substring(1,websiteType.lastIndexOf("\""));
        }

        List<Selectable> nodes = html.css("ul.listCentent > li div.CentTxt").nodes();
        List<Website> list = new ArrayList<>();
        Date date = new Date();
        String operator = "systemOperator";
        int classfication = 1;
        for (Selectable node : nodes) {
            String websiteName = Jsoup.parse(node.css("h3.rightTxtHead a").toString()).text();
            String websiteUrl = Jsoup.parse(node.css("h3.rightTxtHead span").toString()).text();
            int websiteWeight = 0;
            String s = node.css("div.RtCPart > p.RtCData > a > img").toString();
            Document document = Jsoup.parse(s);
            String src = document.select("img").attr("src");
            String substring = src.substring(src.lastIndexOf(".") - 1, src.lastIndexOf("."));
            if(!substring.equals("")){
                websiteWeight = Integer.parseInt(substring);
            }
            if(websiteName.length() > 45 || websiteType.length() > 45 || websiteUrl.length() > 45){
                continue;
            }

            Website website = new Website();
            website.setWebsiteName(websiteName);
            website.setWebsiteUrl(websiteUrl);
            website.setWebsiteType(websiteType);
            website.setCreatedTime(date);
            website.setWebsiteWeight(websiteWeight);
            website.setOperator(operator);
            website.setClassfication(classfication);
            list.add(website);
        }
        page.putField("list",list);
    }


    private Site site = Site.me().setCharset("utf-8").setTimeOut(10*1000).setRetrySleepTime(10*1000).setRetryTimes(3);
    @Override
    public Site getSite() {
        return this.site;
    }

    @Autowired
    private WebsitePipeline websitePipeline;

    @Scheduled(initialDelay = 200 ,fixedDelay = 1000*100000)
    public void process(){
        Spider.create(new WebsiteProcess())
                .addUrl("http://top.chinaz.com/hangyemap.html")
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
                .thread(10)
                .addPipeline(websitePipeline)
                .run();
    }
}
