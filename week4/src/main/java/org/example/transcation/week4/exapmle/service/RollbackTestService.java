package org.example.transcation.week4.exapmle.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.transcation.week4.exapmle.dto.UpdateDtoRollBack;
import org.example.transcation.week4.exapmle.mapper.TestMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
@RequiredArgsConstructor
@Slf4j
public class RollbackTestService {
    private final UpdateService updateService;
    private final TestMapper testMapper;

    // @Transactional(rollbackFor = Exception.class)// 체크 예외도 롤백 시키고 싶다면
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateWithRollback(UpdateDtoRollBack dto)  {
        updateService.updatePrimary(dto.getPrimaryId(), dto.getFirstValue());
        updateService.updateSecondary(dto.getSecondaryId(), dto.getSecondValue());
        throw new RuntimeException("의도적으로 발생시킨 예외입니다.");
        // throw new Exception("체크 예외 발생");
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void updateBothWithRollback(UpdateDtoRollBack dto) {
        log.info("트랜잭션 시작 여부: {}", TransactionSynchronizationManager.isActualTransactionActive());
        log.info("트랜잭션 이름: {}", TransactionSynchronizationManager.getCurrentTransactionName());

        testMapper.updatePrimaryByParam(dto.getPrimaryId(), dto.getFirstValue());
        testMapper.updateSecondaryByParam(dto.getSecondaryId(), dto.getSecondValue());

        throw new RuntimeException("의도적으로 발생시킨 예외입니다.");
        // throw new Exception("체크 예외 발생");
    }

}

