package com.mmall.task;

import com.mmall.service.IOrderService;
import com.mmall.util.PropertiesUtil;
import com.mmall.util.RedisShardedPoolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CloseOrderTask {

    private static final Logger logger = LoggerFactory.getLogger(RedisShardedPoolUtil.class);

    @Autowired
    private IOrderService iOrderService;

    // 没有分布式锁的版本
    @Scheduled(cron = "0 */1 * * * ?")
    public void closeOrderTaskV1() {
        logger.info("关闭订单,定时任务启动");
        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time.hour","2"));
        iOrderService.closeOrder(hour);
        logger.info("关闭订单,定时任务关闭");
    }

}
