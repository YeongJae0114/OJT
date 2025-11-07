package org.example.transcation.week4.exapmle.controller;

import lombok.RequiredArgsConstructor;
import org.example.transcation.week4.exapmle.domain.PrimaryTable;
import org.example.transcation.week4.exapmle.domain.SecondaryTable;
import org.example.transcation.week4.exapmle.dto.TestDto;
import org.example.transcation.week4.exapmle.dto.UpdateDto;
import org.example.transcation.week4.exapmle.dto.UpdateDtoRollBack;
import org.example.transcation.week4.exapmle.mapper.TestMapper;
import org.example.transcation.week4.exapmle.service.RollbackTestService;
import org.example.transcation.week4.exapmle.service.TestService;
import org.example.transcation.week4.exapmle.service.UpdateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;
    private final UpdateService updateService;
    private final RollbackTestService rollbackTestService;

    @PostMapping("/aaaa")
    public String save(@RequestBody TestDto data) {
        testService.save(data);
        return "저장 완료";
    }

    @PutMapping("/update/primary")
    public void updatePrimary(@RequestBody UpdateDto dto) {
        updateService.updatePrimary(dto.getId(), dto.getValue());
    }

    @PutMapping("/update/secondary")
    public void updateSecondary(@RequestBody UpdateDto dto) {
        updateService.updateSecondary(dto.getId(), dto.getValue());
    }

    @PutMapping("/cccc")
    public String updateBothAndRollback(@RequestBody UpdateDtoRollBack dto) {
        rollbackTestService.updateBothWithRollback(dto);
        return "정상적으로 업데이트되었습니다.";

    }

    @PutMapping("/dddd")
    public String updateBothAndRollback2(@RequestBody UpdateDtoRollBack dto) {
        rollbackTestService.updateWithRollback(dto);
        return "정상적으로 업데이트되었습니다.";

    }


    @GetMapping("/aaaa")
    public List<PrimaryTable> findAllPrimary() {
        return testService.findFirst();
    }

    @GetMapping("/bbbb")
    public List<SecondaryTable> findAllSecondary() {
        return testService.findSecond();
    }
}
