package com.example.test.repository.impl;

import com.example.test.model.Scenario;
import com.example.test.repository.ScenariosRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
public class ScenariosRepoImpl implements ScenariosRepo<Scenario> {

    private static final Logger log = LoggerFactory.getLogger(ScenariosRepoImpl.class);

    private JdbcTemplate jdbcTemplate;

    public ScenariosRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createList(List<Scenario> scenarios) {
        String sql = "INSERT INTO SCENARIOS(name, start_date, end_date, campaign_id) values (?, ?, ?, ?::uuid)";
        try {
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Scenario scenario = scenarios.get(i);
                    ps.setString(1, scenario.getName());
                    ps.setDate(2, new Date(scenario.getStartDate().getTime()));
                    ps.setDate(3, new Date(scenario.getEndDate().getTime()));
                    ps.setString(4, scenario.getCampaignId().toString());
                }

                @Override
                public int getBatchSize() {
                    return scenarios.size();
                }

            });
        } catch (Exception e) {
            log.info("Не сохранена запись с именем");
        }
    }
}
