package com.app.turingrobot.ui.fragment.chat

import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.turingrobot.R
import com.app.turingrobot.app.App
import com.app.turingrobot.entity.CoreEntity
import com.app.turingrobot.ui.adapter.ChatAdapter
import com.app.turingrobot.ui.core.BaseFragment
import com.app.turingrobot.ui.dialog.AuthDialogFragment
import com.app.turingrobot.utils.TUtil
import com.socks.library.KLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * 聊天
 * Created by Alpha on 2016/3/26 21:56.
 */
class ChatFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {


    val recyclerView by lazy { view!!.findViewById(R.id.recyclerView) as RecyclerView }

    val swipeRefresh by lazy { view!!.findViewById(R.id.swipeRefresh) as SwipeRefreshLayout }

    val inputLayoutExtra by lazy { view!!.findViewById(R.id.inputLayoutExtra) as TextInputLayout }

    val btnSend by lazy { view!!.findViewById(R.id.btn_send) as AppCompatButton }

    private var mAdapter: ChatAdapter? = null

    private var mDisp: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_chat, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        init()
    }

    private fun init() {
        swipeRefresh.setOnRefreshListener(this)
        btnSend.setOnClickListener(this)
        inputLayoutExtra.hint = "Message"

        mAdapter = ChatAdapter(activity!!)
        val layoutManager = LinearLayoutManager(activity,
                LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = mAdapter

        sendInfo("Hello!")

    }

    /**
     * 刷新
     */
    override fun onRefresh() {

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
                coreEntity.time = System.currentTimeMillis()
                coreEntity.isTarget = false
                mAdapter!!.addData(coreEntity, recyclerView!!)
                sendInfo(msg)
            }
        }
    }

    /**
     * 请求数据

     * @param msg
     */
    private fun sendInfo(msg: String) {
        mDisp = apiService!!.getText("2a397396709f674d8996787b5b0b0b12", msg)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.time = System.currentTimeMillis()
                    mAdapter!!.addData(it, recyclerView!!)
                }, KLog::e)
    }


    override fun onDestroyView() {
        mDisp?.dispose()
        super.onDestroyView()
    }

    companion object {

        fun newInstance(): ChatFragment {
            return ChatFragment()
        }
    }

}
