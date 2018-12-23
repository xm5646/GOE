package com.project.goe.projectgeodbserver.startRun;

import com.project.goe.projectgeodbserver.server.EarnServerSchedul;
import com.project.goe.projectgeodbserver.service.RedisService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DoUpdatePerformance implements ApplicationRunner{
    @Autowired
    private RedisService redisService;

    private static final Logger sLogger = Logger.getLogger(DoUpdatePerformance.class);

    @Autowired
    private EarnServerSchedul earnServerSchedul;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        System.out.println("开始连接Redis, 查询是否有需要更新业绩的用户列表");
        while(true) {
            try{
                Long count = redisService.getListSize("userIDList");
                if (count > 0){
                    for (int i=0;i<count; i++) {
                        Long userId = Long.valueOf(redisService.getObjFromList("userIDList").toString());
                        sLogger.info("发现新增用户ID:" + userId + ",开始更新该用户上层所有用户业绩");
                        earnServerSchedul.updateUserPerformance(userId);
                        sLogger.info("完成业绩更新,触发ID:" + userId);
                    }
                }
            } catch (Exception e) {
                sLogger.error("更新业绩线程出错!");
                sLogger.error(e);
            }
            Thread.sleep(60000);
        }
    }
}
