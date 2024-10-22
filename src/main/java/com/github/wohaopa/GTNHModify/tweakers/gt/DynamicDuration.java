package com.github.wohaopa.GTNHModify.tweakers.gt;

import com.github.wohaopa.GTNHModify.GTNHModifyMod;

import gregtech.api.util.GTRecipe;

/**
 * 包含一个静态方法 设置线程为守护线程 设置线程名称为"GTNHModify DynamicDuration UpdateThread"
 */
public class DynamicDuration extends GT_RecipeTweaker {

    public static DynamicDuration instance = new DynamicDuration();
    // TODO: 需要将多线程改为队列到主线程执行
    private static final Thread updateThread = new Thread(() -> {
        // 单独线程内循环更新
        while (true) {
            if (DynamicDuration.isNeedUpdate()) {  // 判断是否需要更新
                GTNHModifyMod.LOG.info("Updating DynamicDuration: f = {}", instance.aFloat);
                long a = System.currentTimeMillis();
                instance.apply();
                DynamicDuration.needsUpdate = false;
                GTNHModifyMod.LOG.info("Updating DynamicDuration: use time {}ms", System.currentTimeMillis() - a);
            }
            Thread.yield();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });
    private static boolean running = false;
    private static boolean needsUpdate = false;

    /** 使用同步锁获取字段
     * @return needsUpdate
     */
    private static synchronized boolean isNeedUpdate() {
        return needsUpdate;
    }

    static {
        updateThread.setDaemon(true);
        updateThread.setName("GTNHModify DynamicDuration UpdateThread");
    }

    public static void update() { // 该函数被订阅ServerTickEvent的函数在tick超过50ms时每10s调用一次
        needsUpdate = true;
        if (!running) { // 判断线程
            running = true;
            updateThread.start();  // 执行线程进行更改
        }
    }

    public void setF(float f) { // 传进来的是 tick*50.0/now-last  (游戏内10s tick数)/10s = tick/ms
        f0 = f / f0 * aFloat;  // 新tick/ms = (curTick/ms / 旧tick/ms) * 系数 -> 每十秒就会计算一次
    }

    private float aFloat = 0.9f;  // 该系数由配置文件读取
    private float f0 = 1f;  // 动态TPS系数

    @Override
    protected void modifyGT_Recipe(GTRecipe aRecipe) {
        aRecipe.mDuration *= (int) f0;
        if (aRecipe.mDuration < 1) aRecipe.mDuration = 1;
    }

    @Override
    protected void modifyGT_Recipe_AssemblyLine(GTRecipe.RecipeAssemblyLine aRecipe) {
        aRecipe.mDuration *= (int) f0;
        if (aRecipe.mDuration < 1) aRecipe.mDuration = 1;
    }

    @Override
    public Object getSettings() {
        return aFloat;
    }

    @Override
    public void setSetting(Object s) {
        aFloat = (float) s;
    }
}
