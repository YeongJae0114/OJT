package org.example.transcation.week4.TransactionManager;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BService {
    private final JdbcTemplate jdbcTemplate2;


    @Transactional(transactionManager = "transactionManager2")
    public void insertB_thenFail(String v) {
        jdbcTemplate2.update("INSERT INTO b_tbl(v) VALUES (?)", v);
        throw new RuntimeException("BService 예외");
    }

    @Transactional(transactionManager = "transactionManager2")
    public void insertB_ok(String v) {
        jdbcTemplate2.update("INSERT INTO b_tbl(v) VALUES (?)", v);
    }

    public int count() { return jdbcTemplate2.queryForObject("SELECT COUNT(*) FROM b_tbl", Integer.class); }
}
