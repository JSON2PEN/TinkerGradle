package com.cytx.tinkergradle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.cytx.tinkergradle.Tinker.TinkerManager;
import com.cytx.tinkergradle.Utils.OkDownUtil;

import java.io.File;

public class MainActivity extends AppCompatActivity implements OkDownUtil.DownloadCallBack {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void fixBug(View view) {
        Toast.makeText(this, "开始修复啦", Toast.LENGTH_SHORT).show();
        new OkDownUtil().downloadFile3("http://192.168.1.206:8080/tinker.apk", this);
    }

    public void openAct(View view) {
        FrameLayout flContent =findViewById(R.id.fl_content);
        flContent.setVisibility(View.VISIBLE);
        getFragmentManager().beginTransaction().replace(R.id.fl_content,new TinkerFrag()).commitAllowingStateLoss();
    }

    @Override
    public void onSuccess(String path) {
        File mFile = new File(path);
        System.out.println("下载成功:" + mFile.exists());
        if (mFile.exists()) {
            TinkerManager.loadPatch(mFile.getPath());
        }
    }

    @Override
    public void onFail() {
        System.out.println("下载失败");
    }
}
