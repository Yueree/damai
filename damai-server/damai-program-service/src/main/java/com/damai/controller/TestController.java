package com.damai.controller;

import com.damai.common.ApiResponse;
import com.damai.dto.TestSendDto;
import com.damai.service.TestService;
import com.damai.service.scheduletask.ProgramDataTask;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: 极度真实还原大麦网高并发实战项目。 添加 阿星不是程序员 微信，添加时备注 大麦 来获取项目的完整资料 
 * @description: 节目订单 控制层
 * @author: 阿星不是程序员
 **/
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @Autowired
    private ProgramDataTask programDataTask;

    @Operation(summary  = "重置消息计数器")
    @PostMapping(value = "/reset")
    public ApiResponse<Boolean> reset(@Valid @RequestBody TestSendDto testSendDto) {
        return ApiResponse.ok(testService.reset(testSendDto));
    }

    @Operation(summary  = "定时任务逻辑执行")
    @PostMapping(value = "/task/execute")
    public ApiResponse<Boolean> taskExecute() {
        programDataTask.executeTask();
        return ApiResponse.ok();
    }
}
