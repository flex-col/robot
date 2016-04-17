package com.app.turingrobot.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.turingrobot.R;
import com.app.turingrobot.common.Constants;
import com.app.turingrobot.entity.CoreEntity;
import com.app.turingrobot.ui.adapter.ChatAdapter;
import com.app.turingrobot.ui.core.BaseFragment;
import com.app.turingrobot.utils.ToastUtil;
import com.socks.library.KLog;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 聊天
 * Created by Alpha on 2016/3/26 21:56.
 */
public class ChatFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {


    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @Bind(R.id.inputLayoutExtra)
    TextInputLayout inputLayoutExtra;
    @Bind(R.id.btn_send)
    AppCompatButton btnSend;

    private ChatAdapter chatAdapter;
    private AppCompatActivity _activity;

    private Subscription _subScription;


    public static ChatFragment newInstance() {
        ChatFragment chatFragment = new ChatFragment();

        return chatFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        _activity = (AppCompatActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, view);

        init();

        return view;
    }

    private void init() {
        swipeRefresh.setOnRefreshListener(this);
        btnSend.setOnClickListener(this);
        inputLayoutExtra.setHint("");
        inputLayoutExtra.setHint("Message");

        chatAdapter = new ChatAdapter(_activity);
        LinearLayoutManager layoutManager = new LinearLayoutManager(_activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(chatAdapter);

        sendInfo("Hello!");

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 刷新
     */
    @Override
    public void onRefresh() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                String msg = inputLayoutExtra.getEditText().getText().toString().trim();
                if (TextUtils.isEmpty(msg)) {
                    ToastUtil.show(mApplication, "请输入信息...", Toast.LENGTH_SHORT);
                    return;
                }
                inputLayoutExtra.getEditText().setText("");
                CoreEntity coreEntity = new CoreEntity();
                coreEntity.setText(msg);
                coreEntity.setTime(System.currentTimeMillis());
                coreEntity.setTarget(false);
                chatAdapter.addData(coreEntity, recyclerView);
                sendInfo(msg);
                break;
        }
    }

    /**
     * 请求数据
     *
     * @param msg
     */
    private void sendInfo(String msg) {
        _subScription = _apiService.getText(Constants.APP_KEY, msg)
                .doOnUnsubscribe(() -> _subScription.unsubscribe())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(val -> {
                    val.setTime(System.currentTimeMillis());
                    chatAdapter.addData(val, recyclerView);
                }, throwable -> {
                    KLog.e(throwable.getMessage());
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (_subScription != null && !_subScription.isUnsubscribed()) {
            _subScription.unsubscribe();
        }
    }
}
