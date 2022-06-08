package com.example.test.service;

import com.example.test.model.Campaign;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ScenariosService {
    List<Campaign> uploadScenarios(MultipartFile file);
}
