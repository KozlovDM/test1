package com.example.test.controller;

import com.example.test.model.Campaign;
import com.example.test.service.ScenariosService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/scenarios")
public class ScenariosController {

    private final ScenariosService scenariosService;

    public ScenariosController(ScenariosService scenariosService) {
        this.scenariosService = scenariosService;
    }

    @PostMapping("/upload")
    @ResponseBody
    public List<Campaign> uploadScenarios(@RequestParam("file") MultipartFile file) {
        return scenariosService.uploadScenarios(file);
    }
}
