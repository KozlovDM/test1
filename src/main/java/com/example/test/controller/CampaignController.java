package com.example.test.controller;

import com.example.test.model.Campaign;
import com.example.test.service.CampaignService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/campaign")
public class CampaignController {

    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @PostMapping("/save")
    @ResponseBody
    public UUID createCampaign(@RequestBody Campaign campaign) {
        return campaignService.createCampaign(campaign);
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public Campaign getCampaign(@PathVariable UUID id) {
        return campaignService.getCampaign(id);
    }

    @PatchMapping("/update/{id}")
    @ResponseBody
    public void getCampaign(@PathVariable UUID id, @RequestBody Campaign campaign) {
        campaignService.updateCampaign(campaign, id);
    }
}
