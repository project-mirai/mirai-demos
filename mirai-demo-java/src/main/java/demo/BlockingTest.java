package demo;

import kotlin.Unit;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.QQ;
import net.mamoe.mirai.japt.Events;
import net.mamoe.mirai.message.FriendMessage;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageUtils;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.SystemDeviceInfoKt;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

class BlockingTest {

    public static void main(String[] args) throws InterruptedException {
        // 使用自定义的配置
        final Bot bot = BotFactoryJvm.newBot(123, "", new BotConfiguration() {
            {
                setDeviceInfo(context ->
                        SystemDeviceInfoKt.loadAsDeviceInfo(new File("deviceInfo.json"), context)
                );
            }
        });

        // 使用默认的配置
        // BlockingBot bot = BlockingBot.newInstance(123456, "");

        bot.login();

        bot.getFriends().forEach(friend -> {
            System.out.println(friend.getNick());
            return Unit.INSTANCE; // kotlin 的所有函数都有返回值. Unit 为最基本的返回值
        });

        Events.subscribeAlways(FriendMessage.class, (FriendMessage message) -> {
            final QQ sender = message.getSender();
            final Future<MessageReceipt<Contact>> future = sender.sendMessageAsync("Async");
            sender.sendMessage("Hello World!"); // 阻塞式发送

            sender.sendMessage(MessageUtils.newChain(List.of(
                    Image.fromId("{xxxx}.jpg")
            )).plus("Hello"));

            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        bot.join();
    }
}
