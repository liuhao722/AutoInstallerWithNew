package top.wuhaojie.installer;

import android.Manifest;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.List;

import top.wuhaojie.installerlibrary.AutoInstaller;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //    private static String APK_FILE_PATH = "/storage/emulated/0/Android/data/yunzhe.plugin.staticinstaller/files/Download/ryh.apk";
    private String APK_FILE_PATH = "/storage/emulated/0/Android/data/yunzhe.plugin.staticinstaller/files/Download/qz.apk";

    //    public static final String APK_FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Download" + File.separator + "test.apk";
    public static final String CACHE_FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Download";
    public static final String APK_URL = "https://54a0bf2343ff38bdc347780545bd8c9e.dd.cdntips.com/imtt.dd.qq.com/16891/apk/364F479E2C19B0961A84BA9D037DF00A.apk?mkey=5eba481024707b0b&f=24c3&fsname=com.renmai.easymoney_2.3.0_18.apk&csr=1bbd&cip=36.112.93.254&proto=https";
//    public static final String APK_URL = "http://192.168.1.185:8080/app-release.apk";
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("正在下载");

        findViewById(R.id.btn_install).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        /* 方案一: 默认安装器 */
        AutoInstaller installer = AutoInstaller.getDefault(MainActivity.this);
//        installer.install(APK_FILE_PATH);
        installer.installFromUrl(APK_URL);
        installer.setOnStateChangedListener(new AutoInstaller.OnStateChangedListener() {
            @Override
            public void onStart() {
                mProgressDialog.show();
            }

            @Override
            public void onComplete() {
                mProgressDialog.dismiss();
            }

            @Override
            public void onNeed2OpenService() {
                Toast.makeText(MainActivity.this, "请打开辅助功能服务", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void needPermission() {
                Dexter.withActivity(MainActivity.this).withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
                Toast.makeText(MainActivity.this, "需要申请存储空间权限", Toast.LENGTH_SHORT).show();
            }
        });


//        /* 方案二: 构造器 */
//        AutoInstaller installer = new AutoInstaller.Builder(MainActivity.this)
//                .setMode(AutoInstaller.MODE.AUTO_ONLY)
//                .setCacheDirectory(CACHE_FILE_PATH)
//                .build();
//        installer.install(APK_FILE_PATH);
//        installer.installFromUrl(APK_URL);
//        installer.setOnStateChangedListener(new AutoInstaller.OnStateChangedListener() {
//            @Override
//            public void onStart() {
//                mProgressDialog.show();
//            }
//
//            @Override
//            public void onComplete() {
//                mProgressDialog.dismiss();
//            }
//
//            @Override
//            public void onNeed2OpenService() {
//                Toast.makeText(MainActivity.this, "请打开辅助功能服务", Toast.LENGTH_SHORT).show();
//            }
//        });

    }
}
