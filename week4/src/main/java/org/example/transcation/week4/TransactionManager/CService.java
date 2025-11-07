package org.example.transcation.week4.TransactionManager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CService {
    private final AService aService;
    private final BService bService;

    // 외부 트랜잭션 없음: A(DB1, TM1)과 B(DB2, TM2)는 각자 독립 트랜잭션
    public void callA_then_B_fail_noOuterTx() {
        try {
            aService.insertA("A1");
            bService.insertB_thenFail("B1");
        } catch (RuntimeException ignore) {}
    }

    // 외부 트랜잭션을 TM1로 잡아도 B(DB2, TM2)에는 영향 없다.
    // (= 분산 트랜잭션이 아니면 서로 다른 TM은 절대 한 트랜잭션으로 묶이지 않음)
    @Transactional(transactionManager = "transactionManager1")
    public void callA_then_B_fail_outerTM1() {
        try {
            aService.insertA("A2");
            bService.insertB_thenFail("B2");
        } catch (RuntimeException ignore) {}
    }

    // 외부 트랜잭션을 TM2로 잡아도 A(DB1, TM1)에는 영향 없다.
    @Transactional(transactionManager = "transactionManager2")
    public void outerTM2_A_commit_B_rollback() {
        try {
            aService.insertA("A2");
            bService.insertB_thenFail("B2");
        } catch (RuntimeException ignore) {}
    }


    @Transactional
    public void distributedTransaction_success() {
        aService.insertA("A2");
        bService.insertB_ok("B2");
    }

    @Transactional
    public void distributedTransaction_fail() {
            aService.insertA("A2");
            bService.insertB_thenFail("B2");
    }


}

