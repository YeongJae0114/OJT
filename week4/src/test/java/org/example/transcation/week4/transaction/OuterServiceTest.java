package org.example.transcation.week4.transaction;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.IllegalTransactionStateException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OuterServiceTest {
    @Autowired
    private OuterService outerService;

    @Test
    @DisplayName("1. 내부 REQUIRED → 외부 트랜잭션 참여")
    void requiredTest() {
        outerService.callInnerWithRequired();
    }

    @Test
    @DisplayName("2. 내부 REQUIRES_NEW → 새 트랜잭션 시작")
    void requiresNewTest() {
        outerService.callInnerWithRequiresNew();
    }

    @Test
    @DisplayName("3. 내부 NESTED → 저장점 생성")
    void nestedTest() {
        outerService.callInnerWithNested();
    }

    @Test
    @DisplayName("4. 내부 NOT_SUPPORTED → 트랜잭션 중단")
    void notSupportedTest() {
        outerService.callInnerWithNotSupported();
    }

    @Test
    @DisplayName("5. 내부 SUPPORTS → 외부 트랜잭션 참여")
    void supportsTest() {
        outerService.callInnerWithSupports();
    }

    @Test
    @DisplayName("6. SUPPORTS - 트랜잭션 없이 실행")
    void supports_noTx() {
        outerService.callInnerWithSupports_noTx();
    }

    @Test
    @DisplayName("7. IllegalTransactionStateException 발생")
    void NeverTest() {
        assertThatThrownBy(() -> outerService.callInnerWithNever())
                .isInstanceOf(IllegalTransactionStateException.class);

    }

    @Test
    @DisplayName("8. NAVER -  트랜잭션이 없는 실행")
    void NeverTest_noTx() {
        outerService.callInnerWithNever_noTx();
    }
}