package com.app.turingrobot.ui.fragment.chat;

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

import com.app.turingrobot.R;
import com.app.turingrobot.common.Constants;
import com.app.turingrobot.core.App;
import com.app.turingrobot.entity.CoreEntity;
import com.app.turingrobot.ui.adapter.ChatAdapter;
import com.app.turingrobot.ui.core.BaseFragment;
import com.app.turingrobot.ui.dialog.AuthDialogFragment;
import com.app.turingrobot.utils.TUtil;
import com.socks.library.KLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 聊天
 * Created by Alpha on 2016/3/26 21:56.
 */
public class ChatFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    @BindView(R.id.inputLayoutExtra)
    TextInputLayout inputLayoutExtra;

    @BindView(R.id.btn_send)
    AppCompatButton btnSend;

    private ChatAdapter chatAdapter;

    private Disposable mDisp;

    private Unbinder unbind;


    public static ChatFragment newInstance() {
        ChatFragment chatFragment = new ChatFragment();

        return chatFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        unbind = ButterKnife.bind(this, view);

        init();

        return view;
    }

    private void init() {
        swipeRefresh.setOnRefreshListener(this);
        btnSend.setOnClickListener(this);
        inputLayoutExtra.setHint("");
        inputLayoutExtra.setHint("Message");

        chatAdapter = new ChatAdapter(activity);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(chatAdapter);

        sendInfo("Hello!");

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

                if (App.getUser() == null) {
                    AuthDialogFragment.newInstance()
                            .show(activity.getFragmentManager(), AuthDialogFragment.class.getSimpleName());
                    return;
                }

                String msg = inputLayoutExtra.getEditText().getText().toString().trim();
                if (TextUtils.isEmpty(msg)) {
                    TUtil.show("请输入信息...");
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
        mDisp = apiService.getText(Constants.APP_KEY, msg)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(val -> {
                    val.setTime(System.currentTimeMillis());
                    chatAdapter.addData(val, recyclerView);
                }, KLog::e);
    }


    @Override
    public void onDestroyView() {
        unbind.unbind();
        if (mDisp != null) {
            mDisp.dispose();
        }
        super.onDestroyView();
    }

}