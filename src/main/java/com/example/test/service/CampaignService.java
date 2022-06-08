package com.example.test.service;

import com.example.test.model.Campaign;

import java.util.UUID;

public interface CampaignService {
    UUID createCampaign(Campaign campaign);

    Campaign getCampaign(UUID id);

    void updateCampaign(Campaign campaign, UUID id);
}
