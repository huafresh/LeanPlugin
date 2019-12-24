package com.hua.leanplugin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.hua.leanplugin.core.IPluginApk;
import com.hua.leanplugin.core.PluginManager;
import com.hua.leanplugin.core.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {

    private static String apkName = "shike.apk";
    private String apkPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String dirPath = getExternalFilesDir(null).getAbsolutePath();
        final File outFile = new File(dirPath + File.separator + apkName);
        apkPath = outFile.getAbsolutePath();

        findViewById(R.id.copy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            InputStream assetApkStream = getAssets().open(apkName);

                            FileOutputStream outputStream = new FileOutputStream(outFile);
                            Utils.copyStream(assetApkStream, outputStream);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "复制成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        findViewById(R.id.load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PluginManager.get().loadPlugin(outFile.getAbsolutePath());
                Toast.makeText(MainActivity.this, "加载成功", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    IPluginApk pluginApk = PluginManager.get().loadPlugin(apkPath);
                    Class<?> appConstantClass = Class.forName("tv.focal.base.AppConsts");
                    Field filed = appConstantClass.getDeclaredField("CHANNEL_ID_KEY");
                    filed.setAccessible(true);
                    Log.d("@@@hua", "value = " + filed.get(null));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
