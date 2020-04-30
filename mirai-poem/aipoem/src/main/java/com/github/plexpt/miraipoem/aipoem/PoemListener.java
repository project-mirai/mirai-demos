package com.github.plexpt.miraipoem.aipoem;

import com.google.gson.Gson;

import net.dreamlu.mica.http.HttpRequest;
import net.dreamlu.mica.http.LogLevel;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.GroupMessage;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageUtils;
import net.mamoe.mirai.message.data.OfflineImage;
import net.mamoe.mirai.utils.MiraiLogger;

import org.apache.commons.lang3.StringUtils;

import java.util.Base64;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import okhttp3.MultipartBody;

/**
 * @author pt
 * @email plexpt@gmail.com
 * @date 2020-04-30 0:26
 */
public class PoemListener implements Consumer<GroupMessage> {

    public static MiraiLogger log = null;

    public static final AtomicBoolean isRunning = new AtomicBoolean(false);

    public static final AtomicReference<Long> runningQQ = new AtomicReference(0L);

    public static final AtomicReference<Long> runningGroup = new AtomicReference(0L);

    public static final AtomicReference<String> pic64 = new AtomicReference("");

    public static int step = 0;


    public static final String[] CMD = {
            "吟诗", "写诗", "作诗"
    };

    public static final String note = "\n发一张图片，就能激发小爱的灵感，从复杂的图像信息中寻找创作灵感";

    public static final String tip1 = "\n创作这首诗时，你还想提醒小爱什么？" +
            "\n供小爱替你创作初稿时参考，例如图片背后的故事，你的意象，甚至你的诗句" +
            "\n通过补充文字描述，给小爱的创作更多参考。你可以：" +
            "\n1.进一步描述图片意象，例如：“星空越辽阔，越显山峰的寂寞。”" +
            "\n2.描述图片背后的故事，例如：“三年前我在小村庄支教，这是当时拍的照片。直到现在，我还记得那些孩子天真的笑脸。”" +
            "\n3.直接将你自己创作的诗句贴给小爱作为参考。";

    public static final String tip3 = "为你写诗中" +
            "\n意象抽取 Extracting imagery ........................." +
            "\n灵感激发 Getting inspiration ........................." +
            "\n文学风格模型构思 Conceiving literary style ........................." +
            "\n试写第一句 Crafting the opening line ........................." +
            "\n第一句迭代一百次 Iterating the opening line ........................." +
            "\n完成全篇 Completing the full draft ........................." +
            "\n文字质量自评 Content self-evaluation ........................." +
            "\n尝试不同篇幅 Trying different forms ........................." +
            "\n";

    public static final String[] progress = {
            "为你写诗中",
            "意象抽取 Extracting imagery .........................",
            "灵感激发 Getting inspiration .........................",
            "文学风格模型构思 Conceiving literary style .........................",
            "试写第一句 Crafting the opening line .........................",
            "第一句迭代一百次 Iterating the opening line .........................",
            "完成全篇 Completing the full draft .........................",
            "文字质量自评 Content self-evaluation .........................",
            "尝试不同篇幅 Trying different forms ........................."

    };


    public static final String fail = "这张图片不好发挥，你懂的";


    @Override
    public void accept(GroupMessage event) {
        Group group = event.getGroup();
        String message = event.getMessage().toString();

        long groupId = group.getId();

        long qq = event.getSender().getId();
        log = event.getBot().getLogger();
        switch (step) {
            case 0:
                //等指令
                stepCmd(group, groupId, qq, message);

                break;
            case 1:
                //等图
                stepPic(group, event, qq, message);

                break;
            case 2:
                //等文字
                stepText(group, groupId, qq, event.getMessage().contentToString());

                break;

            default:

                break;
        }
    }

    private void stepText(Group group, long groupId, long qq, String message) {
        if (qq != runningQQ.get()) {
            return;
        }
        step = 3;
        log.info("开始作诗3");

        try {
            group.sendMessage(tip3);

//            for (String msg : progress) {
//                Thread.sleep(800);舔
//            }

            if (message.contains("[mirai:image:")) {
                message = message.replaceAll("\\[mirai.*?\\]", "");
            }

            PoemDTO poem = poem(pic64.get(), message);

            if (poem.isIsAdult() || poem.isIsPolitician()) {
                group.sendMessage(fail);
                return;
            }
            //   log.info(JSON.toJSONString(poem, true));

            for (PoemDTO.OpenPoemsBean openPoem : poem.getOpenPoems()) {
                group.sendMessage(openPoem.getPoemContent());

            }

            log.info("作诗完成");

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            step = 0;
        }

    }

    private void stepPic(Group api, GroupMessage bot, long qq, String message) {
        if (qq != runningQQ.get()) {
            return;
        }

        if (message.contains("[mirai:image:")) {
            log.info("开始作诗2");


            String url = StringUtils.substringBetween(message, "[mirai:image:", "]");
            OfflineImage image = MessageUtils.newImage(url);


            String imageUrl = bot.getBot().queryImageUrl(image);
            log.info(imageUrl);

            byte[] pic = HttpRequest.get(imageUrl)
                    .execute()
                    .asBytes();

            String encode = Base64.getEncoder().encodeToString(pic);

            pic64.set(encode);
            api.sendMessage(new At(bot.getSender())
                    .plus(tip1));

            step = 2;

        }
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

        isRunning.set(true);
        runningQQ.set(qq);
        runningGroup.set(groupId);
        step = 1;

        log.info("开始作诗1");
        api.sendMessage(new At(api.getOrNull(qq)).plus(note));


    }

    public PoemDTO poem(String base64, String text) {
        log.info("开始作诗 HttpRequest请求 ");

        MultipartBody body2 = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", base64)
                .addFormDataPart("userid", "79b688fd-7d23-4dff-b405-d0bba04083ed")
                .addFormDataPart("guid", "226466e9-e487-46cd-995e-f974514d77e9")
                .addFormDataPart("text", text)
                .build();

        String poem = HttpRequest.post("https://poem.msxiaobing.com/api/upload")
                .setHeader("Content-Type", "multipart/form-data;")
                .body(body2)
                .log(LogLevel.NONE)
                .execute()
                .asString();

        log.info(poem);
        log.info("==========HttpRequest End==========");

        return new Gson().fromJson(poem, PoemDTO.class);

    }
}
