package com.example.test;

import com.example.test.model.Campaign;
import com.example.test.model.Scenario;
import com.example.test.model.Status;
import com.example.test.repository.CampaignRepo;
import com.example.test.repository.ScenariosRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Transactional
class ApplicationTests extends BaseTest {

    @Resource
    private CampaignRepo<Campaign> campaignRepo;

    @Resource
    private ScenariosRepo<Scenario> scenariosRepo;

    @Test
    void createTest() {
        Campaign campaign = new Campaign();
        campaign.setName("6666");
        UUID uuid = campaignRepo.create(campaign);
        campaign = campaignRepo.get(uuid).orElse(null);
        Assertions.assertNotNull(campaign);
        Assertions.assertEquals(uuid, campaign.getId());
    }

    @Test
    void getNullCampaignTest() {
        Campaign campaign = campaignRepo.get(UUID.randomUUID()).orElse(null);
        Assertions.assertNull(campaign);
    }

    @Test
    void updateCampaignTest() {
        Campaign campaign = new Campaign();
        campaign.setName("6666");
        campaign.setStatus(Status.ACTIVE);
        UUID uuid = campaignRepo.create(campaign);
        campaign.setStatus(Status.DEACTIVATED);
        campaign.setName("updated");
        campaignRepo.update(campaign, uuid);
        campaign = campaignRepo.get(uuid).orElse(null);
        Assertions.assertNotNull(campaign);
        Assertions.assertEquals(uuid, campaign.getId());
        Assertions.assertEquals("updated", campaign.getName());
        Assertions.assertEquals(Status.DEACTIVATED, campaign.getStatus());
    }

    @Test
    void createListScenariosTest() {
        Campaign campaign = new Campaign();
        campaign.setName("6666");
        UUID uuid = campaignRepo.create(campaign);
        List<Scenario> scenarios = new ArrayList<>();
        scenarios.add(new Scenario("qwerty1", new Date(), new Date(), uuid));
        scenarios.add(new Scenario("qwerty2", new Date(), new Date(), uuid));
        scenarios.add(new Scenario("qwerty3", new Date(), new Date(), uuid));
        scenariosRepo.createList(scenarios);
        campaign = campaignRepo.get(uuid).orElse(null);
        Assertions.assertNotNull(campaign);
        Assertions.assertEquals(3, campaign.getScenarios().size());
        Assertions.assertEquals("qwerty1", campaign.getScenarios().get(0).getName());
    }
}
