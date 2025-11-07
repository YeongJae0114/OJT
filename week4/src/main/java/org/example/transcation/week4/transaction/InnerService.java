package org.example.transcation.week4.transaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
@Slf4j
public class InnerService {
    @Transactional(propagation = Propagation.REQUIRED)
    public void innerRequired() {
        printTransactionInfo("innerRequired(REQUIRED)");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void innerRequiresNew() {
        printTransactionInfo("innerRequiresNew(REQUIRES_NEW)");
    }

    @Transactional(propagation = Propagation.NESTED)
    public void innerNested() {
        printTransactionInfo("innerNested(NESTED)");
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void innerNotSupported() {
        printTransactionInfo("innerNotSupported(NOT_SUPPORTED)");
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void innerSupports() {
        printTransactionInfo("innerSupports(SUPPORTS)");
    }

    @Transactional(propagation = Propagation.NEVER)
    public void innerNever() {
        printTransactionInfo("innerNever(NEVER)");
    }

    private void printTransactionInfo(String methodName) {
        log.info("----------------------------------------------------");
        log.info("실행 메서드: {}", methodName);
        log.info("트랜잭션 활성화 여부: {}", TransactionSynchronizationManager.isActualTransactionActive());
        log.info("트랜잭션 이름: {}", TransactionSynchronizationManager.getCurrentTransactionName());
        log.info("----------------------------------------------------");
    }
}

