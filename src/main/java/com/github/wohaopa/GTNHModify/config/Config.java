package com.github.wohaopa.GTNHModify.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

import com.github.wohaopa.GTNHModify.GTNHModifyMod;
import com.github.wohaopa.GTNHModify.tweakers.Tweakers;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class Config {

    public static Configuration config;

    private static boolean doSave;

    /**
     * 初始化config 加载config 注册该类到FML事件总线
     * 注册的事件为OnConfigChangedEvent
     * @param configFile preEvent所推荐的配置文件 File
     */
    public static void init(File configFile) {
        if (config == null) {
            config = new Configuration(configFile);
        }
        doSave = false;
        loadConfig();
        FMLCommonHandler.instance()
            .bus()
            .register(new Config());
    }

    /**
     * 如果事件中变化的配置是本Mod,则进行保存
     * @param event OnConfigChangedEvent
     */
    @SubscribeEvent
    public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equalsIgnoreCase(GTNHModifyMod.MODID)) {
            loadConfig();
        }
    }

    /**
     * 加载配置
     * 遍历 Tweakers 枚举类
     * 获取每个枚举对象的名字来读取配置, 再进行赋值<br>
     * ----获取值为: 所有类型 String, Boolean, Integer, String[], Float
     * ----将获取到的值赋给自己
     */
    private static void loadConfig() {

        for (Tweakers tweaker : Tweakers.values()) {
            tweaker.enabled = config
                .getBoolean(tweaker.name, Configuration.CATEGORY_GENERAL, false, tweaker.description); // 使用枚举中的每个名字作为键取出布尔值
            Object setting = tweaker.tweaker.getSettings();  // 通过枚举中的 ITweaker 类获取其String
            if (setting != null) {
                if (setting instanceof String) {
                    tweaker.tweaker.setSetting(
                        config.getString(
                            tweaker.name + "_setting",
                            Configuration.CATEGORY_GENERAL,
                            (String) setting,
                            tweaker.description));
                } else if (setting instanceof Boolean) {
                    tweaker.tweaker.setSetting(
                        config.getBoolean(
                            tweaker.name + "_setting",
                            Configuration.CATEGORY_GENERAL,
                            (Boolean) setting,
                            tweaker.description));
                } else if (setting instanceof Integer) {
                    tweaker.tweaker.setSetting(
                        config.getInt(
                            tweaker.name + "_setting",
                            Configuration.CATEGORY_GENERAL,
                            (int) setting,
                            Integer.MIN_VALUE,
                            Integer.MAX_VALUE,
                            tweaker.description));
                } else if (setting instanceof String[]) {
                    tweaker.tweaker.setSetting(
                        config.getStringList(
                            tweaker.name + "_setting",
                            Configuration.CATEGORY_GENERAL,
                            (String[]) setting,
                            tweaker.description));
                } else if (setting instanceof Float) {
                    tweaker.tweaker.setSetting(
                        config.getFloat(
                            tweaker.name + "_setting",
                            Configuration.CATEGORY_GENERAL,
                            (Float) setting,
                            Float.MIN_VALUE,
                            Float.MAX_VALUE,
                            tweaker.description));
                }

            }
        }

        if (config.hasChanged() || doSave) {
            config.save();
            doSave = false;
        }
    }

}
