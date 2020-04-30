package com.github.plexpt.miraipoem.aipoem;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author pt
 * @email plexpt@gmail.com
 * @date 2020-04-29 14:19
 */
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PoemDTO {

    /**
     * IsAdult : false
     * IsPolitician : false
     * ShowImage : true
     * OpenPoems : [{"OpenPoemID":"8bd2956e-d6f8-49cd-9d7d-38fc88e06477","PoemContent":"碧落的太阳晒得黄黄\n有时候纡回\n过去的流水像一只小鸟\n\n我俩是年轻的爸爸\n照着西山黄昏得到一个消息\n这水晶瓶里的菊花","PoemType":"three_three_lines","Score":1.2,"Optimum":true,"TimeStamp":"2020.4.29"},{"OpenPoemID":"cda07459-dfde-41ad-b3da-59151c922872","PoemContent":"不是全世界的尽头\n跑遍了甜蜜的时候了\n诗人们弹的是纷披的眼泪\n\n沉睡些贪睡的人们啊\n想劝别人们为什么迟迟的不来\n他的情人仿佛一个浮于没有\n\n等候天空的小鸟\n一个是为着别人的耳语","PoemType":"three_three_two_lines","Score":0.25,"Optimum":false,"TimeStamp":"2020.4.29"},{"OpenPoemID":"b90d2a5f-5ba4-4e6c-90fa-bc17e85fb06a","PoemContent":"问故家乔木凋零\r\n只剩一个空洞洞的世界了\r\n它的声音是低微的\r\n痴呆的人类啊\n\n明显地落在我脸上望\r\n你要打开所有的地方去\r\n不曾将我的心灵污抹\r\n它的声音是低微的音乐\n\n而且她是我的生命的消息\n你到别的时候你再想起\n只剩一个空洞洞的世界了\n一只鱼儿游戏在水中呢\n\n真没趣味的时候了\r\n在太阳的光中","PoemType":"four_four_four_two_lines","Score":1.2,"Optimum":false,"TimeStamp":"2020.4.29"}]
     */

    private boolean IsAdult;
    private boolean IsPolitician;
    private boolean ShowImage;
    private List<OpenPoemsBean> OpenPoems;


    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class OpenPoemsBean {
        /**
         * OpenPoemID : be2c0b08-038a-46e2-bbae-776faf3c3d37
         * PoemContent : 我每次看见了女人的面色
         我没有太阳没有珠子紫色的眼
         或是更鼓敲破了如梦之灰尘

         我愿为世界上的一对红蜡烛
         白鱼白色的水网在何处
         地球上没有不落的太阳洒下
         * PoemType : three_three_lines
         * Score : 0.75
         * Optimum : true
         * TimeStamp : 2020.4.29
         */

        private String OpenPoemID;
        private String PoemContent;
        private String PoemType;
        private double Score;
        private boolean Optimum;
        private String TimeStamp;
    }
}
