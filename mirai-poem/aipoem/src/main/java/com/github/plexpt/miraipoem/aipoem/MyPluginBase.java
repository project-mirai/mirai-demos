package com.github.plexpt.miraipoem.aipoem;//在这里创建你的PluginBase, 他应该是一个object


import net.mamoe.mirai.console.plugins.PluginBase;
import net.mamoe.mirai.message.GroupMessage;

public class MyPluginBase extends PluginBase {

    @Override
    public void onLoad() {
        getLogger().info("Poem Plugin loadeding...");
    }

    @Override
    public void onEnable() {
        getLogger().info("Poem Plugin loaded!");
        getEventListener().subscribeAlways(GroupMessage.class, new PoemListener());

    }

}
