package zewen.unimelb.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import zewen.unimelb.VO.RemoveVO;
import zewen.unimelb.VO.ResourceVO;
import zewen.unimelb.VO.ShareVO;
import zewen.unimelb.common.Commands;
import zewen.unimelb.common.SharedPreferenceUtil;
import zewen.unimelb.resourceshare.R;

/**
 * 类描述：
 * 创建人：zeven
 * 创建时间：15/11/19 下午8:06
 */
public class ShareFragment extends BaseFragment{
    EditText secretEidt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        View secretLine = view.findViewById(R.id.secretLine);
        ((TextView)secretLine.findViewById(R.id.textView)).setText(Commands.secret);
        secretEidt = (EditText) secretLine.findViewById(R.id.editText);

        initData();
        secretLine.setVisibility(View.VISIBLE);

        return view;
    }

    @Override
    public void onClick(View v) {
        ShareVO vo = new ShareVO();
        vo.setCommand("SHARE");
        vo.setSecret(secretEidt.getText().toString());

        ResourceVO resourceVO = getResource();
        vo.setResource(resourceVO);

        saveData();
        request(vo);
    }

    @Override
    void initData() {
        super.initData();
        secretEidt.setText(new SharedPreferenceUtil().read(getActivity(),Commands.secret));
    }

    @Override
    void saveData() {
        super.saveData();
        new SharedPreferenceUtil().wirte(getActivity(),Commands.secret,secretEidt.getText().toString());

    }
}
