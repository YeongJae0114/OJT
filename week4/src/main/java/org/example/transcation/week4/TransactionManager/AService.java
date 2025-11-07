package org.example.transcation.week4.TransactionManager;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AService {
    private final JdbcTemplate jdbcTemplate1;

    @Transactional(transactionManager = "transactionManager1")
    public void insertA(String v) {
        jdbcTemplate1.update("INSERT INTO a_tbl(v) VALUES (?)", v);
    }

    public int count() {
        return jdbcTemplate1.queryForObject("SELECT COUNT(*) FROM a_tbl", Integer.class);
    }

}
