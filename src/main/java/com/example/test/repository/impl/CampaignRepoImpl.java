package com.example.test.repository.impl;

import com.example.test.model.Campaign;
import com.example.test.model.Scenario;
import com.example.test.model.Status;
import com.example.test.repository.CampaignRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CampaignRepoImpl implements CampaignRepo<Campaign> {

    private static final Logger log = LoggerFactory.getLogger(CampaignRepoImpl.class);

    private JdbcTemplate jdbcTemplate;

    public CampaignRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UUID create(Campaign campaign) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = String.format("INSERT INTO CAMPAIGN(name, status) values (%s, '%s')", campaign.getName(), campaign.getStatus().getName());
        jdbcTemplate.update(connection -> connection.prepareStatement(sql, new String[]{"id"}), keyHolder);
        UUID id = keyHolder.getKeyAs(UUID.class);
        log.info("Сохранена компания с id = " + id);
        return id;
    }

    @Override
    public Optional<Campaign> get(UUID id) {
        String sql = "SELECT c.id as c1, c.name as c2, c.status as c3, s.id as c4, s.name as c5, s.start_date as c6, s.end_date as c7 FROM CAMPAIGN c " +
                "LEFT JOIN SCENARIOS s on campaign_id = c.id " +
                "where c.id = ?";
        Campaign campaign = null;
        try {
            List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, id);
            for (Map<String, Object> res : result) {
                campaign = fillCampaign(campaign, res);
            }
        } catch (DataAccessException e) {
            log.info("Не найденно компаний по id = " + id.toString());
        }

        return Optional.ofNullable(campaign);
    }

    @Override
    public void update(Campaign campaign, UUID id) {
        String sql = "UPDATE CAMPAIGN set name = ?, status = ?::STATUS_TYPE where id::text = ?";
        int update = jdbcTemplate.update(sql, campaign.getName(), campaign.getStatus().toString(), id.toString());
        if (update == 1) {
            log.info("Обновлена компания с id = " + id);
        }
    }

    @Override
    public List<Campaign> list() {
        String sql = "SELECT c.id as c1, c.name as c2, c.status as c3, s.id as c4, s.name as c5, s.start_date as c6, s.end_date as c7 FROM CAMPAIGN c " +
                "LEFT JOIN SCENARIOS s on campaign_id = c.id order by c1";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
        LinkedList<Campaign> campaigns = new LinkedList<>();
        UUID currentId = null;
        for (Map<String, Object> res : result) {
            if (currentId != null && currentId.equals(res.get("c1"))) {
                fillCampaign(campaigns.getLast(), res);
            } else {
                currentId = (UUID) res.get("c1");
                campaigns.add(fillCampaign(null, res));
            }
        }
        return campaigns;
    }

    private Campaign fillCampaign(Campaign campaign, Map<String, Object> res) {
        if (campaign == null) {
            campaign = new Campaign();
            campaign.setId((UUID) res.get("c1"));
            campaign.setName((String) res.get("c2"));
            campaign.setStatus(Status.valueOf((String) res.get("c3")));
            campaign.setScenarios(new ArrayList<>());
        }
        Scenario scenario = new Scenario();
        scenario.setId((UUID) res.get("c4"));
        scenario.setName((String) res.get("c5"));
        scenario.setStartDate((Date) res.get("c6"));
        scenario.setEndDate((Date) res.get("c7"));
        campaign.getScenarios().add(scenario);
        return campaign;
    }
}
