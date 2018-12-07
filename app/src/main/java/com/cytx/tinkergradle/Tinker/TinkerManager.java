package com.cytx.tinkergradle.Tinker;

import android.content.Context;

import com.tencent.tinker.lib.listener.DefaultPatchListener;
import com.tencent.tinker.lib.patch.AbstractPatch;
import com.tencent.tinker.lib.patch.UpgradePatch;
import com.tencent.tinker.lib.reporter.DefaultLoadReporter;
import com.tencent.tinker.lib.reporter.DefaultPatchReporter;
import com.tencent.tinker.lib.reporter.LoadReporter;
import com.tencent.tinker.lib.reporter.PatchReporter;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.ApplicationLike;

/**
 * Created by user on 2018/11/8.
 */

public class TinkerManager {
    private static boolean isInstalled = false;//是否已经初始化标志位
    private static ApplicationLike mApplicationLike;

    /**
     * 完成Tinker初始化
     *
     * @param applicationLike
     */
    public static void installedTinker(ApplicationLike applicationLike) {
        System.out.println("执行"+"installedTinker");
        mApplicationLike = applicationLike;
        if (isInstalled) {
            return;
        }
//        TinkerInstaller.install(mApplicationLike);
        DefaultPatchListener mPatchListener = new DefaultPatchListener(getApplicationContext());//一些补丁文件的校验工作
        //这两个是监听patch文件安装的日志上报结果 也就是补丁文件安装监听
        LoadReporter loadReporter = new DefaultLoadReporter(getApplicationContext());//一些在加载补丁文件时的回调
        PatchReporter patchReporter = new DefaultPatchReporter(getApplicationContext());//补丁文件在合成时一些事件的回调

        AbstractPatch abstractPatch = new UpgradePatch();//决定patch文件安装策略  不会去修改与自定义
        TinkerInstaller.install(mApplicationLike,
                loadReporter,
                patchReporter,
                mPatchListener,
                CustomResultService.class,//我们自定义的
                abstractPatch);
        isInstalled = true;
    }

    /**
     * 完成patch文件的加载
     *
     * @param path 补丁文件路径
     */
    public static void loadPatch(String path) {
        System.out.println("执行安装" + Tinker.isTinkerInstalled());
        if (Tinker.isTinkerInstalled()) {//是否已经安装过
            TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), path);
        }
    }

    /**
     * 利用Tinker代理Application 获取应用全局的上下文
     * @return 全局的上下文
     */
    private static Context getApplicationContext() {
        if (mApplicationLike != null)
            return mApplicationLike.getApplication().getApplicationContext();
        return null;
    }
}
