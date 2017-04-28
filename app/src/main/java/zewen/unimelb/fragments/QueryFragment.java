package zewen.unimelb.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import zewen.unimelb.VO.QueryVO;
import zewen.unimelb.VO.ResourceVO;

/**
 * 类描述：
 * 创建人：zeven
 * 创建时间：15/11/19 下午8:06
 */
public class QueryFragment extends BaseFragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        initData();
        return view;
    }

    @Override
    public void onClick(View v) {
        QueryVO vo = new QueryVO();
        vo.setCommand("QUERY");

        ResourceVO resourceVO = new ResourceVO();
        resourceVO.setChannel(channelEdit.getText().toString().trim());
        resourceVO.setDescription(desEdit.getText().toString().trim());
        resourceVO.setName(nameEdit.getText().toString().trim());
        resourceVO.setOwner(ownerEdit.getText().toString().trim());
        String tags = tagsEdit.getText().toString().trim();
        ArrayList<String> taglist = new ArrayList<String>();
        if (tags != null & tags != "") {
            for (String string : tags.split(",")) {
                taglist.add(string);
            }
        }
        resourceVO.setTags(taglist);
        resourceVO.setUri(uriEdit.getText().toString().trim());
        resourceVO.setEzserver(null);
        vo.setResourceTemplate(resourceVO);

        vo.setRelay(true);

        saveData();
        request(vo);
    }
}
