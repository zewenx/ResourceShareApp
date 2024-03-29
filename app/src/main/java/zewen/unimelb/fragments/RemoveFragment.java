package zewen.unimelb.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import zewen.unimelb.VO.PublishVO;
import zewen.unimelb.VO.RemoveVO;
import zewen.unimelb.VO.ResourceVO;

/**
 * 类描述：
 * 创建人：zeven
 * 创建时间：15/11/19 下午8:06
 */
public class RemoveFragment extends BaseFragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        initData();
        return view;
    }

    @Override
    public void onClick(View v) {
        RemoveVO vo = new RemoveVO();
        vo.setCommand("REMOVE");

        ResourceVO resourceVO = getResource();
        vo.setResource(resourceVO);

        saveData();
        request(vo);
    }
}
