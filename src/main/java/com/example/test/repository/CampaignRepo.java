package com.example.test.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CampaignRepo<T> {
    UUID create(T t);

    Optional<T> get(UUID id);

    void update(T t, UUID id);

    List<T> list();
}
