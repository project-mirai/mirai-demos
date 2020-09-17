package net.mamoe.mirai.task;

import net.mamoe.mirai.Bot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Spring 定时任务示例
 */
@Component
public class TaskDemo {

    private Logger log = LoggerFactory.getLogger(TaskDemo.class);

    @Autowired
    private Bot bot;

    @Scheduled(cron = "${task.cron}")
    public void execute() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = sdf.format(new Date());
        bot.getFriend(11111).sendMessage("当前时间：" + now);
        log.info("定时任务执行完毕");
    }
}
