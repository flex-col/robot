package com.app.turingrobot.ui.fragment.chat

import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.turingrobot.R
import com.app.turingrobot.app.App
import com.app.turingrobot.entity.CoreEntity
import com.app.turingrobot.helper.rx.RxResultHelper
import com.app.turingrobot.multitype.MultiTypeAdapter
import com.app.turingrobot.multitype.base.MultiTypePresenterImpl
import com.app.turingrobot.multitype.base.ViewModel
import com.app.turingrobot.ui.core.BaseFragment
import com.app.turingrobot.ui.dialog.AuthDialogFragment
import com.app.turingrobot.ui.fragment.chat.model.LinkModel
import com.app.turingrobot.ui.fragment.chat.model.TextMsgModel
import com.app.turingrobot.ui.fragment.chat.model.TextMsgTargetModel
import com.app.turingrobot.utils.TUtil
import com.socks.library.KLog
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_chat.*
import java.util.ArrayList

/**
 * 聊天
 * Created by Alpha on 2016/3/26 21:56.
 */
class ChatFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private var mAdapter: MultiTypeAdapter? = null

    private val mDisp = CompositeDisposable()

    private val mData: MutableList<ViewModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_chat, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        init()
    }

    private fun init() {

        swipeRefresh.setOnRefreshListener(this)
        btn_send.setOnClickListener(this)

        inputLayoutExtra.hint = "Message"

        val multiTypePresenter = MultiTypePresenterImpl
                .newBuilder()
                .addHolders(TextMsgModel.newPresenter())
                .addHolders(TextMsgTargetModel.newPresenter())
                .addHolders(LinkModel.newPresenter())
                .withViewModels(mData)
                .build()

        mAdapter = MultiTypeAdapter(multiTypePresenter)

        val layoutManager = LinearLayoutManager(activity,
                LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = mAdapter

        sendInfo("Hello")

    }

    /**
     * 刷新
     */
    override fun onRefresh() {

        Handler().postDelayed({
            swipeRefresh.isRefreshing = false
        }, 2000)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_send -> {

                if (App.getUser() == null) {
                    AuthDialogFragment.newInstance()
                            .show(activity?.fragmentManager,
                                    AuthDialogFragment::class.java.simpleName)
                    return
                }

                val msg = inputLayoutExtra!!.editText!!.text.toString().trim { it <= ' ' }
                if (TextUtils.isEmpty(msg)) {
                    TUtil.show("请输入信息...")
                    return
                }

                inputLayoutExtra!!.editText!!.setText("")
                val coreEntity = CoreEntity()
                coreEntity.text = msg
                mData.add(TextMsgModel(coreEntity))
                mAdapter?.notifyItemInserted(mData.size - 1)
                recyclerView.scrollToPosition(mData.size - 1)

                sendInfo(msg)
            }
        }
    }

    /**
     * 请求数据
     * @param msg
     */
    private fun sendInfo(msg: String) {
        mDisp.add(apiService.sendMsg("2a397396709f674d8996787b5b0b0b12", msg)
                .compose(RxResultHelper.transform())
                .subscribe({
                    mData.add(it)
                    mAdapter?.notifyItemInserted(mData.size - 1)
                    recyclerView.scrollToPosition(mData.size - 1)
                }, KLog::e))
    }


    override fun onDestroyView() {
        mDisp.dispose()
        super.onDestroyView()
    }

    companion object {

        fun newInstance(): ChatFragment {
            return ChatFragment()
        }
    }

}
