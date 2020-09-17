package net.mamoe.mirai.bot;

import net.mamoe.mirai.Bot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 用于初始化 bot 线程的类
 */
@Component
public class BotComponent {

    @Autowired
    private Bot bot;

    @Autowired
    private BotListener botListener;

    @PostConstruct
    public void init() {
        Thread t = new BotThread(bot, botListener);
        t.start();
    }
}
