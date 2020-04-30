package com.github.plexpt.miraipoem.lick;

import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.GroupMessage;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.utils.MiraiLogger;

import java.util.function.Consumer;

/**
 * @author pt
 * @email plexpt@gmail.com
 * @date 2020-04-30 0:26
 */
public class MyListener implements Consumer<GroupMessage> {

    public static MiraiLogger log = null;

    public static final String[] CMD = {
            "彩虹屁", "夸我"
    };


    @Override
    public void accept(GroupMessage event) {
        Group group = event.getGroup();
        String message = event.getMessage().toString();

        long groupId = group.getId();

        long qq = event.getSender().getId();
        log = event.getBot().getLogger();

        stepCmd(group, groupId, qq, message);
    }

    private void stepCmd(Group api, long groupId, long qq, String message) {


        boolean isCMD = false;
        for (String s : CMD) {
            if (message.contains(s)) {
                isCMD = true;
                break;
            }
        }
        if (!isCMD) {
            return;
        }


        log.info("开始舔 2");
        api.sendMessage(new At(api.getOrNull(qq))
                .plus(RainbowFart.getChp()));


    }

}
