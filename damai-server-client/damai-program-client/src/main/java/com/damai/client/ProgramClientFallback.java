package com.damai.client;

import com.damai.common.ApiResponse;
import com.damai.dto.TicketCategoryListByProgramDto;
import com.damai.enums.BaseCode;
import com.damai.vo.TicketCategoryDetailVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description: 节目服务 feign 异常
 **/
@Component
public class ProgramClientFallback implements ProgramClient {
    
    @Override
    public ApiResponse<List<TicketCategoryDetailVo>> selectListByProgram(TicketCategoryListByProgramDto ticketCategoryListByProgramDto) {
        return ApiResponse.error(BaseCode.SYSTEM_ERROR);
    }
}
