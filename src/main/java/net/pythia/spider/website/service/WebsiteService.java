package net.pythia.spider.website.service;

import net.pythia.spider.website.entity.Website;

import java.util.List;

public interface WebsiteService {
    void save(Website website);
    List<Website> selectByWebsite(Website website);
    void saveAll(List<Website> list);
}
