package com.github.wohaopa.GTNHModify;

import com.github.wohaopa.GTNHModify.tweakers.Tweakers;
import com.github.wohaopa.GTNHModify.tweakers.gt.DynamicDuration;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

/**
 * 注册了一个ServerTick事件 动态计算TPS然后更新GT_Recipe的duration
 */
public class EventHandler {

    long lastUpdateTime = 0;
    long ticks = 0;

    @SubscribeEvent
    public void onTickPost(TickEvent.ServerTickEvent event) {
        if (Tweakers.Dynamic_Duration.enabled) if (event.phase == TickEvent.Phase.END) {
            ticks++;
            long now = System.currentTimeMillis();
            if (now - lastUpdateTime > 10000) { // 10秒
                if (lastUpdateTime != 0) {
                    if (ticks < 195) {   // 如果游戏发生卡顿, tick数在10秒内一定会少于195tick, 在MC内195tick约等于9.75s
                        DynamicDuration.instance.setF((float) (ticks * 50.0) / (now - lastUpdateTime));  // now-last 代表 实际tick数所消耗的时间 tick*50表示标准状况下tick的ms值
                        DynamicDuration.update();
                    }
                    ticks = 0;
                }
                lastUpdateTime = now;
            }
        }
    }
}
