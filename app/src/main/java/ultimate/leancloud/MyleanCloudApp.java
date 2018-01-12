package ultimate.leancloud;

import android.app.Application;
import android.util.Log;

import com.avos.avoscloud.AVOSCloud;
import com.example.postman.ultimate.R;

/**
 * Created by user on 2016/7/15.
 * leancloud的初始化，作用不明，参照demo。
 */
public class MyleanCloudApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("MyleanCloudApp.java","初始化leadncloud");
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this, getString(R.string.applicationId), getString(R.string.clientKey));
        Log.i("MyleanCloudApp.java","初始化leadncloud完成");
    }
}
