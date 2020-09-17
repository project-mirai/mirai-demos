package net.mamoe.mirai.bot;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.utils.BotConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 初始化机器人bean
 */
@Configuration
public class BotConfig {

    @Bean
    public Bot bot(@Value("${bot.qq}") Long qq, @Value("${bot.password}") String password) {
        // 使用自定义的配置
        return BotFactoryJvm.newBot(qq, password, new BotConfiguration() {
            {
                fileBasedDeviceInfo("deviceInfo.json"); //保存设备信息到文件
            }
        });
    }
}
