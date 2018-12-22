package com.project.goe.projectgeodbserver.startRun;

import com.project.goe.projectgeodbserver.server.EarnServerSchedul;
import com.project.goe.projectgeodbserver.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DoUpdatePerformance implements ApplicationRunner{
    @Autowired
    private RedisService redisService;

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
                        System.out.println("发现新增用户ID:" + userId + ",开始更新该用户上层所有用户业绩");
                        earnServerSchedul.updateUserPerformance(userId);
                        System.out.println("完成业绩更新,触发ID:" + userId);
                    }
                }
            } catch (Exception e) {
                System.out.println("更新业绩线程出错!");
            }
            Thread.sleep(60000);
        }
    }
}
