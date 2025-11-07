package org.example.transcation.week4.exapmle.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.transcation.week4.exapmle.domain.PrimaryTable;
import org.example.transcation.week4.exapmle.domain.SecondaryTable;
import org.example.transcation.week4.exapmle.dto.TestDto;
import org.example.transcation.week4.exapmle.dto.UpdateDto;
import org.example.transcation.week4.exapmle.mapper.TestMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestService {
    private final TestMapper testMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    public  void save(TestDto data) {
        testMapper.insertPrimary(data);
        testMapper.insertSecondary(data);
    }

    @Transactional(readOnly = true)
    public List<PrimaryTable> findFirst() {
        return testMapper.findAllFirst();
    }

    @Transactional(readOnly = true)
    public List<SecondaryTable> findSecond() {
        return testMapper.findAllSecond();
    }



}
