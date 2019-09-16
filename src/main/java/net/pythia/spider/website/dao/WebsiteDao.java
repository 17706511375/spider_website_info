package net.pythia.spider.website.dao;

import net.pythia.spider.website.entity.Website;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebsiteDao extends JpaRepository<Website,Long> {
}
