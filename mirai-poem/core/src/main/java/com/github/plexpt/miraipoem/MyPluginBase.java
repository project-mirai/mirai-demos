package com.github.plexpt.miraipoem;


import net.mamoe.mirai.console.plugins.PluginBase;

public class MyPluginBase extends PluginBase {

    @Override
    public void onLoad() {
        getLogger().info("Poem Core Plugin loadeding...");
    }

    @Override
    public void onEnable() {
        getLogger().info("Poem Core Plugin loaded!");

    }

}
