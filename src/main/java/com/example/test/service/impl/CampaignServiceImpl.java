package com.example.test.service.impl;

import com.example.test.model.Campaign;
import com.example.test.repository.CampaignRepo;
import com.example.test.service.CampaignService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepo<Campaign> campaignRepo;

    public CampaignServiceImpl(CampaignRepo<Campaign> campaignRepo) {
        this.campaignRepo = campaignRepo;
    }

    @Override
    public UUID createCampaign(Campaign campaign) {
        return campaignRepo.create(campaign);
    }

    @Override
    public Campaign getCampaign(UUID id) {
        return getCampaignById(id);
    }

    @Override
    public void updateCampaign(Campaign campaign, UUID id) {
        Campaign updatedCampaign = getCampaignById(id);
        if (campaign.getName() != null) {
            updatedCampaign.setName(campaign.getName());
        }
        if (campaign.getStatus() != null) {
            updatedCampaign.setStatus(campaign.getStatus());
        }
        campaignRepo.update(updatedCampaign, id);
    }

    private Campaign getCampaignById(UUID id) {
        Optional<Campaign> campaign = campaignRepo.get(id);
        if (campaign.isPresent()) {
            return campaign.get();
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Не найденно компаний по id"
        );
    }
}
