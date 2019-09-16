package net.pythia.spider.website.service.impl;

import net.pythia.spider.website.dao.WebsiteDao;
import net.pythia.spider.website.entity.Website;
import net.pythia.spider.website.service.WebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class WebsiteServiceImpl implements WebsiteService {

    @Autowired
    private WebsiteDao websiteDao;
    @Override
    @Transactional
    public void save(Website website) {
        Website params = new Website();
        params.setWebsiteName(website.getWebsiteName());
        params.setWebsiteUrl(website.getWebsiteUrl());
        List<Website> websites = this.selectByWebsite(params);
        if(websites.size() == 0)
            websiteDao.save(website);
    }

    @Override
    public List<Website> selectByWebsite(Website website) {
        Example<Website> example = Example.of(website);
        return websiteDao.findAll(example);
    }

    @Override
    @Transactional
    public void saveAll(List<Website> list) {
         websiteDao.saveAll(list);
    }
}
