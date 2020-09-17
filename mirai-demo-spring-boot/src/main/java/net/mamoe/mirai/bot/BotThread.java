package net.mamoe.mirai.bot;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.Events;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * bot 线程
 */
public class BotThread extends Thread {

    private Logger log = LoggerFactory.getLogger(BotThread.class);

    private Bot bot;

    private BotListener botListener;

    public BotThread(Bot bot, BotListener botListener) {
        this.bot = bot;
        this.botListener = botListener;
    }

    public void run() {
        // 登录
        bot.login();

        // 输出好友
        bot.getFriends().forEach(friend -> log.info("{} {}", friend.getId(), friend.getNick()));
        // 输出群
        bot.getGroups().forEach(group -> log.info("{} {}", group.getId(), group.getName()));

        // 注册事件
        Events.registerEvents(bot, botListener);

        // 阻塞当前线程直到 bot 离线
        bot.join();
    }
}
