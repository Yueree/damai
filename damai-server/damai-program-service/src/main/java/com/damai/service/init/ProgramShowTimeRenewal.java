package com.damai.service.init;

import com.damai.core.SpringUtil;
import com.damai.initialize.base.AbstractApplicationPostConstructHandler;
import com.damai.service.ProgramService;
import com.damai.service.ProgramShowTimeService;
import com.damai.util.BusinessEsHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @program: 极度真实还原大麦网高并发实战项目。 添加 阿宽不是程序员 微信，添加时备注 大麦 来获取项目的完整资料 
 * @description: 节目演出时间更新
 * @author: 阿宽不是程序员
 **/
@Component
public class ProgramShowTimeRenewal extends AbstractApplicationPostConstructHandler {
    
    @Autowired
    private ProgramShowTimeService programShowTimeService;
    
    @Autowired
    private ProgramService programService;
    
    @Autowired
    private BusinessEsHandle businessEsHandle;
    
    @Override
    public Integer executeOrder() {
        return 2;
    }
    
    /**
     * 项目启动将库中的节目演出时间进行更新，真实生产环境不会这么做的
     * */
    @Override
    public void executeInit(final ConfigurableApplicationContext context) {
        Set<Long> programIdSet = programShowTimeService.renewal();
        if (programIdSet.size() > 0) {
            businessEsHandle.deleteIndex(SpringUtil.getPrefixDistinctionName() + "-" +
                    ProgramDocumentParamName.INDEX_NAME);
            for (Long programId : programIdSet) {
                programService.delRedisData(programId);
            }
        }
    }
}
