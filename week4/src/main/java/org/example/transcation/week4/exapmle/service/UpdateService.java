package org.example.transcation.week4.exapmle.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.transcation.week4.exapmle.domain.PrimaryTable;
import org.example.transcation.week4.exapmle.domain.SecondaryTable;
import org.example.transcation.week4.exapmle.dto.TestDto;
import org.example.transcation.week4.exapmle.dto.UpdateDto;
import org.example.transcation.week4.exapmle.mapper.TestMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateService {
    private final TestMapper testMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    public void updatePrimary(Long id, String firstValue) {
        testMapper.updatePrimaryByParam(id, firstValue);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateSecondary(Long id, String secondValue) {
        testMapper.updateSecondaryByParam(id, secondValue);
    }


}
