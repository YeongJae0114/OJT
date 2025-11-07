package org.example.transcation.week4.transaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
@RequiredArgsConstructor
@Slf4j
public class OuterService {
    private final InnerService innerService;

    // REQUIRED로 트랜잭션 시작
    @Transactional(propagation = Propagation.REQUIRED)
    public void callInnerWithRequired() {
        log.info(">>> [outer] 트랜잭션 시작 (REQUIRED)");
        log.info("트랜잭션 이름: {}", TransactionSynchronizationManager.getCurrentTransactionName());
        innerService.innerRequired();
        log.info(">>> [outer] innerRequired() 호출 종료");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void callInnerWithRequiresNew() {
        log.info(">>> [outer] 트랜잭션 시작 (REQUIRED)");
        log.info("트랜잭션 이름: {}", TransactionSynchronizationManager.getCurrentTransactionName());
        innerService.innerRequiresNew();
        log.info(">>> [outer] innerRequiresNew() 호출 종료");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void callInnerWithNested() {
        log.info(">>> [outer] 트랜잭션 시작 (REQUIRED)");
        log.info("트랜잭션 이름: {}", TransactionSynchronizationManager.getCurrentTransactionName());
        innerService.innerNested();
        log.info(">>> [outer] innerNested() 호출 종료");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void callInnerWithNotSupported() {
        log.info(">>> [outer] 트랜잭션 시작 (REQUIRED)");
        log.info("트랜잭션 이름: {}", TransactionSynchronizationManager.getCurrentTransactionName());
        innerService.innerNotSupported();
        log.info(">>> [outer] innerNotSupported() 호출 종료");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void callInnerWithSupports() {
        log.info(">>> [outer] 트랜잭션 시작 (REQUIRED)");
        log.info("트랜잭션 이름: {}", TransactionSynchronizationManager.getCurrentTransactionName());
        innerService.innerSupports();
        log.info(">>> [outer] innerSupports() 호출 종료");
    }

    public void callInnerWithSupports_noTx() {
        log.info(">>> [outer] 트랜잭션 시작 (REQUIRED)");
        log.info("트랜잭션 이름: {}", TransactionSynchronizationManager.getCurrentTransactionName());
        innerService.innerSupports();
        log.info(">>> [outer] innerSupports() 호출 종료");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void callInnerWithNever() {
        log.info(">>> [outer] 트랜잭션 시작 (REQUIRED)");
        log.info("트랜잭션 이름: {}", TransactionSynchronizationManager.getCurrentTransactionName());
        innerService.innerNever();
        log.info(">>> [outer] innerSupports() 호출 종료");
    }

    public void callInnerWithNever_noTx() {
        log.info(">>> [outer] 트랜잭션 시작 (REQUIRED)");
        log.info("트랜잭션 이름: {}", TransactionSynchronizationManager.getCurrentTransactionName());
        innerService.innerNever();
        log.info(">>> [outer] innerSupports() 호출 종료");
    }


}
