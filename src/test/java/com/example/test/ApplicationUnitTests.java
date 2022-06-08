package com.example.test;

import com.example.test.model.Campaign;
import com.example.test.model.Scenario;
import com.example.test.model.Status;
import com.example.test.repository.CampaignRepo;
import com.example.test.repository.ScenariosRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.net.URI;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class ApplicationUnitTests extends BaseTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;

    @MockBean
    private CampaignRepo<Campaign> campaignRepo;

    @MockBean
    private ScenariosRepo<Scenario> scenariosRepo;

    private static final String BASE_URL = "http://localhost:";

    @Test
    void createTest() throws Exception {
        Campaign campaign = new Campaign("test", Status.ACTIVE, new ArrayList<>());
        when(campaignRepo.create(any())).thenReturn(UUID.randomUUID());
        HttpEntity<Campaign> request = new HttpEntity<>(campaign);
        ResponseEntity<UUID> response = restTemplate.postForEntity(new URI(BASE_URL + randomServerPort + "/campaign/save"), request, UUID.class);
        Assertions.assertEquals(200, response.getStatusCodeValue());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    void getCampaign() {
        Campaign campaign = new Campaign("test", Status.ACTIVE, new ArrayList<>());
        when(campaignRepo.create(any())).thenReturn(UUID.randomUUID());
        when(campaignRepo.get(any())).thenReturn(Optional.of(campaign));
        HttpEntity<Campaign> request = new HttpEntity<>(campaign);
        ResponseEntity<UUID> uuid = restTemplate.postForEntity(BASE_URL + randomServerPort + "/campaign/save", request, UUID.class);
        Assertions.assertNotNull(uuid.getBody());
        ResponseEntity<Campaign> response = restTemplate.getForEntity(BASE_URL + randomServerPort + "/campaign/get/{id}", Campaign.class, uuid.getBody().toString());
        Assertions.assertEquals(200, response.getStatusCodeValue());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(campaign.getName(), response.getBody().getName());
        Assertions.assertEquals(campaign.getStatus(), response.getBody().getStatus());
        Assertions.assertEquals(campaign.getScenarios(), response.getBody().getScenarios());
    }

    @Test
    void updateCampaign() {
        Campaign campaign = new Campaign("test", Status.ACTIVE, new ArrayList<>());
        when(campaignRepo.create(any())).thenReturn(UUID.randomUUID());
        when(campaignRepo.get(any())).thenReturn(Optional.of(campaign));
        doNothing().when(campaignRepo).update(any(), any());
        HttpEntity<Campaign> request = new HttpEntity<>(campaign);
        ResponseEntity<UUID> uuid = restTemplate.postForEntity(BASE_URL + randomServerPort + "/campaign/save", request, UUID.class);
        Assertions.assertNotNull(uuid.getBody());
        campaign.setStatus(Status.DEACTIVATED);
        campaign.setName("updated");
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        restTemplate.patchForObject(BASE_URL + randomServerPort + "/campaign/update/{id}", request, Void.class, uuid.getBody().toString());
        ResponseEntity<Campaign> response = restTemplate.getForEntity(BASE_URL + randomServerPort + "/campaign/get/{id}", Campaign.class, uuid.getBody().toString());
        Assertions.assertEquals(200, response.getStatusCodeValue());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(campaign.getName(), response.getBody().getName());
        Assertions.assertEquals(campaign.getStatus(), response.getBody().getStatus());
        Assertions.assertEquals(campaign.getScenarios(), response.getBody().getScenarios());
    }
}
