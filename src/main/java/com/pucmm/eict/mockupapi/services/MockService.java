package com.pucmm.eict.mockupapi.services;

import com.pucmm.eict.mockupapi.models.Mock;
import com.pucmm.eict.mockupapi.repositories.MockRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MockService {

    private final MockRepository mockRepository;

    @Autowired
    public MockService(MockRepository mockRepository) {
        this.mockRepository = mockRepository;
    }

    public List<Mock> getAllMocks() {
        return mockRepository.findAll();
    }

    public List<Mock> getAllMocksByUserId(UUID id) {
        return mockRepository.findAllByUserId(id);
    }

    public Mock getMockById(UUID id) {
        return mockRepository.findById(id);
    }

    public Mock createMock(Mock mock) {
        return mockRepository.save(mock);
    }

    public Mock getMockByHash(String hash) {
        return mockRepository.findByHash(hash);
    }

    public List<Mock> getAllMocksByProjectId(UUID id) {
        return mockRepository.findAllByProjectId(id);
    }

    @Transactional
    public void deleteMockByID(UUID id) {
        mockRepository.deleteById(id);
    }
}
