package zewen.unimelb.resourceshare;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zewen.unimelb.common.Commands;
import zewen.unimelb.common.ResponseListener;
import zewen.unimelb.common.SharedPreferenceUtil;
import zewen.unimelb.fragments.BaseFragment;
import zewen.unimelb.fragments.ExchangeFragment;
import zewen.unimelb.fragments.QueryFragment;
import zewen.unimelb.fragments.ShareFragment;

public class MainActivity extends AppCompatActivity {

    HashMap<String, BaseFragment> fragments;
    @Bind(R.id.fetch)
    Button fetch;
    @Bind(R.id.exchange)
    Button exchange;
    @Bind(R.id.remove)
    Button remove;
    @Bind(R.id.publish)
    Button publish;
    @Bind(R.id.share)
    Button share;
    @Bind(R.id.query)
    Button query;
    @Bind(R.id.main_content)
    FrameLayout mainContent;

    public Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
           onResponse(msg.getData().getStringArrayList("data"));
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initData();
        initView();

    }

    private void initData() {
        fragments = new HashMap<>();

    }

    private void initView() {

        Bundle bundle = new Bundle();

        ShareFragment shareFragment = new ShareFragment();
        fragments.put(Commands.share,shareFragment);
        bundle.putString("name",Commands.share);
        shareFragment.setArguments(bundle);

        QueryFragment queryFragment = new QueryFragment();
        fragments.put(Commands.query,queryFragment);
        bundle.putString("name",Commands.query);
        queryFragment.setArguments(bundle);

        ExchangeFragment exchangeFragment = new ExchangeFragment();
        fragments.put(Commands.exchange,exchangeFragment);
        bundle.putString("name",Commands.exchange);
        exchangeFragment.setArguments(bundle);

    }
    private void setDefault(String command) {
        BaseFragment fragment = fragments.get(command);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_content, fragment);
        transaction.commit();
    }

    @OnClick(R.id.share)
    public void shareCommand(){
        setDefault(Commands.share);
    }
    @OnClick(R.id.query)
    public void queryCommand(){
        setDefault(Commands.query);
    }
    @OnClick(R.id.exchange)
    public void exchangeCommand(){
        setDefault(Commands.exchange);
    }

    public void onResponse(ArrayList<String> responseList) {

        Intent intent = new Intent(MainActivity.this,ResultActivity.class);
        intent.putStringArrayListExtra("response",responseList);
        startActivity(intent);
    }

}
