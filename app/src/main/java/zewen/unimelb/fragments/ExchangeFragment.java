package zewen.unimelb.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zewen.unimelb.VO.ExchangeVO;
import zewen.unimelb.VO.PublishVO;
import zewen.unimelb.VO.ResourceVO;
import zewen.unimelb.VO.ServerVO;
import zewen.unimelb.common.Commands;
import zewen.unimelb.common.SharedPreferenceUtil;
import zewen.unimelb.resourceshare.R;

/**
 * 类描述：
 * 创建人：zeven
 * 创建时间：15/11/19 下午8:06
 */
public class ExchangeFragment extends BaseFragment{
    EditText servers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_exchange, null);

        ((TextView) view.findViewById(R.id.commandName)).setText(name);

        hostEdit = (EditText) view.findViewById(R.id.hostEdit);
        portEdit = (EditText) view.findViewById(R.id.portEdit);

        servers  = (EditText) view.findViewById(R.id.serversEdit);

        view.findViewById(R.id.submit).setOnClickListener(this);
        initData();
        return view;
    }

    @Override
    void initData() {
        SharedPreferenceUtil util = new SharedPreferenceUtil();

        servers.setText(util.read(getActivity(),Commands.servers));
        String host = util.read(getActivity(),Commands.host);
        String port = util.read(getActivity(),Commands.port);
        if (host!=null &&host.length()>0)
            hostEdit.setText(host);
        if (port!=null&&port.length()>0)
            portEdit.setText(port);

    }

    @Override
    public void onClick(View v) {
        String services = servers.getText().toString();
        ExchangeVO vo = new ExchangeVO();
        vo.setCommand("EXCHANGE");
        String[] serversArray = services.split(",");
        List<ServerVO> serverList = new ArrayList<ServerVO>();
        for (String server : serversArray) {
            ServerVO serverVO = new ServerVO();
            String[] serversData = server.split(":");
            serverVO.setHostname(serversData[0]);
            serverVO.setPort(Integer.parseInt(serversData[1]));
            serverList.add(serverVO);
        }
        vo.setServerList(serverList);

        saveData();
        request(vo);
    }

    @Override
    void saveData() {
        SharedPreferenceUtil util = new SharedPreferenceUtil();
        util.wirte(getActivity(),Commands.servers,servers.getText().toString());

        util.wirte(getActivity(),Commands.host,hostEdit.getText().toString());
        util.wirte(getActivity(),Commands.port,portEdit.getText().toString());
    }
}
