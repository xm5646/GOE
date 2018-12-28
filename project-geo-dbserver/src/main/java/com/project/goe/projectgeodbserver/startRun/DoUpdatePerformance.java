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
        new Thread(() ->{
            sLogger.info("connection Redis, find if has need update user id");
            while(true) {
                try{
                    Long count = redisService.getListSize("userIDList");
                    if (count > 0){
                        for (int i=0;i<count; i++) {
                            Long userId = Long.valueOf(redisService.getObjFromList("userIDList").toString());
                            sLogger.info("find new user ID:" + userId + ",starting update...");
                            earnServerSchedul.updateUserPerformance(userId);
                            sLogger.info("update complete, source ID:" + userId);
                        }
                    }
                } catch (Exception e) {
                    sLogger.error("更新业绩线程出错!");
                    sLogger.error(e.getMessage());
                }
                try{
                    Thread.sleep(6000);
                }catch (InterruptedException e){
                    sLogger.error(e.getMessage());
                }

            }
        }).start();
    }
}
