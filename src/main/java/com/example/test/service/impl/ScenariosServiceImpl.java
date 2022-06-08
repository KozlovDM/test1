package com.example.test.service.impl;

import com.example.test.model.Campaign;
import com.example.test.model.Scenario;
import com.example.test.repository.CampaignRepo;
import com.example.test.repository.ScenariosRepo;
import com.example.test.service.ScenariosService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
public class ScenariosServiceImpl implements ScenariosService {

    private final ScenariosRepo<Scenario> scenariosRepo;
    private final CampaignRepo<Campaign> campaignRepo;

    public ScenariosServiceImpl(ScenariosRepo<Scenario> scenariosRepo, CampaignRepo<Campaign> campaignRepo) {
        this.scenariosRepo = scenariosRepo;
        this.campaignRepo = campaignRepo;
    }

    @Override
    public List<Campaign> uploadScenarios(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            List<Scenario> scenarios = new ArrayList<>();
            bufferedReader.lines().forEach(line -> {
                String[] split = line.split(";");
                if (split.length != 4) {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "В строке недостаточно атрибутов " + line
                    );
                }
                Scenario scenario = new Scenario();
                scenario.setName(split[0]);
                try {
                    scenario.setStartDate(formatter.parse(split[1]));
                    scenario.setEndDate(formatter.parse(split[2]));
                } catch (ParseException e) {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Не верный формат дат. Ныжны даты по шаблону dd-MM-yyyy. Строка с ошибкой " + line
                    );
                }
                try {
                    scenario.setCampaignId(UUID.fromString(split[3]));
                } catch (Exception e) {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Не верный uuid. Строка с ошибкой " + line
                    );
                }
                scenarios.add(scenario);
            });
            scenariosRepo.createList(scenarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return campaignRepo.list();
    }
}
