package org.example.transcation.week4.TransactionManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.UnexpectedRollbackException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CServiceTest {
    @Autowired
    private CService cService;

    @Autowired
    private AService aService;

    @Autowired
    private BService bService;

    @Autowired
    private JdbcTemplate jdbcTemplate1;

    @Autowired
    private JdbcTemplate jdbcTemplate2;

    @BeforeEach
    void setup() {
        jdbcTemplate1.execute("CREATE TABLE IF NOT EXISTS a_tbl(id INT AUTO_INCREMENT PRIMARY KEY, v VARCHAR(50))");
        jdbcTemplate2.execute("CREATE TABLE IF NOT EXISTS b_tbl(id INT AUTO_INCREMENT PRIMARY KEY, v VARCHAR(50))");
        jdbcTemplate1.execute("DELETE FROM a_tbl");
        jdbcTemplate2.execute("DELETE FROM b_tbl");
    }

    @Test
    @DisplayName("1. 외부 트랜잭션 없음 → A만 커밋, B는 롤백")
    void callA_then_B_fail_noOuterTx() {
        // when
        cService.callA_then_B_fail_noOuterTx();

        // then
        assertThat(aService.count())
                .as("DB1(AService)는 트랜잭션 커밋되어야 한다")
                .isEqualTo(1);
        assertThat(bService.count())
                .as("DB2(BService)는 예외로 롤백되어야 한다")
                .isZero();
    }

    @Test
    @DisplayName("2. 외부 트랜잭션 TM1 → A 커밋, B는 별도로 롤백")
    void outerTM1_A_under_TM1_B_independent_TM2() {
        cService.callA_then_B_fail_outerTM1();

        // TM1이 관리하는 건 DB1뿐
        // 현재 메서드에서 예외를 삼켜 커밋 진행 → A도 커밋됨.
        assertThat(aService.count()).isEqualTo(1); // commit
        assertThat(bService.count()).isZero();     // (B 내부 예외로 TM2 롤백)
    }

    @Test
    @DisplayName("3. 외부 트랜잭션 TM2 → A는 커밋, B는 롤백 (UnexpectedRollbackException 발생)")
    void outerTM2_A_commit_B_rollback() {
        // BService → TM2 내부 트랜잭션 참여했기 떄문에 예외 발생
        assertThatThrownBy(() -> cService.outerTM2_A_commit_B_rollback())
                .isInstanceOf(UnexpectedRollbackException.class)
                .hasMessageContaining("rollback-only"); // TM2 커밋 시도 → 이미 rollback-only 상태라 예외 발생

        assertThat(aService.count()).isEqualTo(1); // commit
        assertThat(bService.count()).isZero();     // (B 내부 예외로 TM2 롤백)
    }

    @Test
    @DisplayName("분산 트랜잭션 성공: A, B 모두 커밋")
    void distributedTransaction_success() {
        // when
        cService.distributedTransaction_success();

        // then
        assertThat(aService.count())
                .as("A(DB1)는 커밋되어야 한다")
                .isEqualTo(1);
        assertThat(bService.count())
                .as("B(DB2)도 커밋되어야 한다")
                .isEqualTo(1);
    }

    @Test
    @DisplayName("분산 트랜잭션 실패: B 예외 발생 → 전체 롤백")
    void distributedTransaction_fail() {
        // when
        assertThatThrownBy(() -> cService.distributedTransaction_fail())
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("BService 예외");

        // then
        assertThat(aService.count())
                .as("롤A(DB1)도 함께 백되어야 한다 (2PC로 인해)")
                .isZero();
        assertThat(bService.count())
                .as("B(DB2)도 롤백되어야 한다")
                .isZero();
    }
}