package com.gdcp.asus_.googleplay.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.gdcp.asus_.googleplay.R;
import com.gdcp.asus_.googleplay.manager.ThreadManager;
import com.gdcp.asus_.googleplay.utils.UIUtils;

/**
 * Created by asus- on 2017/12/13.
 */

public abstract class LoadingPage extends FrameLayout {
    private static final int STATE_LOAD_UNDO = 1;
    private static final int STATE_LOAD_LOADING = 2;
    private static final int STATE_LOAD_ERROR = 3;
    private static final int STATE_LOAD_EMPTY = 4;
    private static final int STATE_LOAD_SUCCESS = 5;
    private int currentState = STATE_LOAD_UNDO;
    private View loadingPage;
    private View errorPage;
    private View emptyPage;
    private View successPage;

    public LoadingPage(@NonNull Context context) {
        super(context);
        initView();
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        if (loadingPage == null) {
            loadingPage = UIUtils.inflate(R.layout.page_loading);
            addView(loadingPage);
        }
        if (errorPage == null) {
            errorPage = UIUtils.inflate(R.layout.page_error);
            Button btnRetry= (Button) errorPage.findViewById(R.id.btn_retry);
            btnRetry.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadData();
                }
            });
            addView(errorPage);
        }
        if (emptyPage == null) {
            emptyPage = UIUtils.inflate(R.layout.page_empty);
            addView(emptyPage);
        }

        showRightPage();
    }

    private void showRightPage() {
        loadingPage.setVisibility(currentState == STATE_LOAD_UNDO || currentState == STATE_LOAD_LOADING ? View.VISIBLE : View.GONE);
        emptyPage.setVisibility(currentState == STATE_LOAD_EMPTY ? View.VISIBLE : View.GONE);
        errorPage.setVisibility(currentState == STATE_LOAD_ERROR ? View.VISIBLE : View.GONE);
        // 当成功布局为空,并且当前状态为成功,才初始化成功的布局
        if (successPage == null && currentState == STATE_LOAD_SUCCESS) {
            successPage = onCreateSuccessView();
            if (successPage != null) {
                addView(successPage);
            }
        }
        if (successPage != null) {
            successPage.setVisibility(currentState == STATE_LOAD_SUCCESS ? View.VISIBLE : View.GONE);
        }
    }

    //加载成功后显示的布局，必须由调用者来实现
    public abstract View onCreateSuccessView();

    //开始加载数据
    public void loadData() {
        if (currentState != STATE_LOAD_LOADING) {
            currentState = STATE_LOAD_LOADING;
            /*new Thread(new Runnable() {
                @Override
                public void run() {
                    final ResultState resultState = onLoad();
                    UIUtils.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            if (resultState != null) {
                                currentState = resultState.getState();//网络加载结束，更新网络状态
                                showRightPage();
                            }
                        }
                    });
                }
            }).start();*/
            ThreadManager.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    final ResultState resultState = onLoad();
                    UIUtils.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            if (resultState != null) {
                                currentState = resultState.getState();//网络加载结束，更新网络状态
                                showRightPage();
                            }
                        }
                    });
                }
            });
        }
    }

    //加载网络数据，返回表示请求网络结束后的状态
    public abstract ResultState onLoad();

    public enum ResultState {
        //相当于类的构造方法
        STATE_SUCEESS(STATE_LOAD_SUCCESS),
        STATE_EMPTY(STATE_LOAD_EMPTY),
        STATE_ERROR(STATE_LOAD_ERROR);
        private int state;

        private ResultState(int state) {
            this.state = state;
        }

        public int getState() {
            return state;
        }
    }
}
