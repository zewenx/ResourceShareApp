package zewen.unimelb.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import zewen.unimelb.VO.FetchVO;
import zewen.unimelb.VO.RequestVO;
import zewen.unimelb.VO.ResourceVO;
import zewen.unimelb.common.FetchRequestThread;
import zewen.unimelb.common.RequestThread;
import zewen.unimelb.resourceshare.MainActivity;

/**
 * 类描述：
 * 创建人：zeven
 * 创建时间：15/11/19 下午8:06
 */
public class FetchFragment extends BaseFragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        initData();
        return view;
    }

    @Override
    public void onClick(View v) {
        FetchVO vo = new FetchVO();
        vo.setCommand("Fetch");

        ResourceVO resourceVO = getResource();
        vo.setResource(resourceVO);

        saveData();
        request(vo);
    }

    @Override
    void request(RequestVO vo) {
        port = portEdit.getText().toString();
        host = hostEdit.getText().toString();
        ((MainActivity)getActivity()).dialogShow();
        new FetchRequestThread(getActivity(),port,host,vo,((MainActivity)getActivity()).handler).start();
    }
}
