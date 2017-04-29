package zewen.unimelb.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import zewen.unimelb.VO.PublishVO;
import zewen.unimelb.VO.QueryVO;
import zewen.unimelb.VO.ResourceVO;
import zewen.unimelb.common.Commands;

/**
 * 类描述：
 * 创建人：zeven
 * 创建时间：15/11/19 下午8:06
 */
public class PublishFragment extends BaseFragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        initData();
        return view;
    }

    @Override
    public void onClick(View v) {
        PublishVO vo = new PublishVO();
        vo.setCommand("PUBLISH");

        // sets values for data resources
        ResourceVO resourceVO = getResource();
        vo.setResource(resourceVO);

        saveData();
        request(vo);
    }
}
