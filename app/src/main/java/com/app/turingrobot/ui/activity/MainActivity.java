package com.app.turingrobot.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.turingrobot.R;
import com.app.turingrobot.ui.core.BaseActivity;
import com.app.turingrobot.ui.fragment.ChatFragment;
import com.app.turingrobot.utils.GlideUtils;
import com.app.turingrobot.utils.StatusBarUtil;
import com.socks.library.KLog;
import com.umeng.update.UmengUpdateAgent;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;

    private DrawerLayout drawer;

    private ActionBarDrawerToggle toggle;

    private ChatFragment chatFragment;

    private ImageView img_head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setColorForDrawerLayout(this, (DrawerLayout) findViewById(R.id.drawer_layout), ContextCompat
                .getColor(this, R.color.colorPrimary));
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //检查更新
        UmengUpdateAgent.update(this);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        img_head = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView);
        TextView tv_name = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_name);
        TextView tv_signature = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_signature);

        tv_name.setText("RxJava");
        tv_signature.setText("世界这么大，我只想多走走！");

        GlideUtils.displayCircleHeader(img_head, "http://www.jcodecraeer.com/uploads/160106/1-160106013A9345.png");

        initFragments();
    }

    private void initFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (chatFragment == null) {
            chatFragment = ChatFragment.newInstance();
            transaction.add(R.id.fm_container, chatFragment).hide(chatFragment);
        }

        transaction.show(chatFragment);

        transaction.commit();

        KLog.i(getDeviceInfo(this));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_chat) {
            //聊天
        } else if (id == R.id.nav_share) {
            //分享
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @SuppressLint("NewApi")
    public static boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= 23) {
            if (context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        } else {
            PackageManager pm = context.getPackageManager();
            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }

    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = null;
            if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                device_id = tm.getDeviceId();
            }
            android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            String mac = wifi.getConnectionInfo().getMacAddress();
            json.put("mac", mac);
            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }
            if (TextUtils.isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
            }
            json.put("device_id", device_id);
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
