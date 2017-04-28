package zewen.unimelb.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import zewen.unimelb.VO.RequestVO;
import zewen.unimelb.common.Commands;
import zewen.unimelb.common.RequestThread;
import zewen.unimelb.common.ResponseListener;
import zewen.unimelb.common.SharedPreferenceUtil;
import zewen.unimelb.resourceshare.MainActivity;
import zewen.unimelb.resourceshare.R;

/**
 * 类描述：
 * 创建人：zeven
 * 创建时间：15/11/19 上午11:16
 */
public class BaseFragment extends Fragment implements View.OnClickListener {
    String name;
    String port;
    String host;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        name = (String) args.get("name");
    }

    EditText nameEdit;
    EditText desEdit;
    EditText tagsEdit;
    EditText uriEdit;
    EditText channelEdit;
    EditText ownerEdit;
    EditText hostEdit;
    EditText portEdit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_share, null);

        ((TextView) view.findViewById(R.id.commandName)).setText(name);

        hostEdit = (EditText) view.findViewById(R.id.hostEdit);
        portEdit = (EditText) view.findViewById(R.id.portEdit);

        View nameLine = view.findViewById(R.id.nameResource);
        TextView nameText = (TextView) nameLine.findViewById(R.id.textView);
        nameText.setText(Commands.name);
        nameEdit = (EditText) nameLine.findViewById(R.id.editText);

        View desLine = view.findViewById(R.id.descriptionResource);
        TextView desText = (TextView) desLine.findViewById(R.id.textView);
        desText.setText(Commands.description);
        desEdit = (EditText) desLine.findViewById(R.id.editText);

        View tagsLine = view.findViewById(R.id.tagsResource);
        TextView tagsText = (TextView) tagsLine.findViewById(R.id.textView);
        tagsText.setText(Commands.tags);
        tagsEdit = (EditText) tagsLine.findViewById(R.id.editText);


        View uriLine = view.findViewById(R.id.URIResource);
        TextView uriText = (TextView) uriLine.findViewById(R.id.textView);
        uriText.setText(Commands.uri);
        uriEdit = (EditText) uriLine.findViewById(R.id.editText);

        View channelLine = view.findViewById(R.id.channelResource);
        TextView channelText = (TextView) channelLine.findViewById(R.id.textView);
        channelText.setText(Commands.channel);
        channelEdit = (EditText) channelLine.findViewById(R.id.editText);

        View ownerLine = view.findViewById(R.id.ownerResource);
        TextView ownerText = (TextView) ownerLine.findViewById(R.id.textView);
        ownerText.setText(Commands.owner);
        ownerEdit = (EditText) ownerLine.findViewById(R.id.editText);

        view.findViewById(R.id.submit).setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {

    }



    void request(RequestVO vo) {
        port = portEdit.getText().toString();
        host = hostEdit.getText().toString();
       new RequestThread(port,host,vo,((MainActivity)getActivity()).handler).start();
    }

    void saveData(){
        SharedPreferenceUtil util = new SharedPreferenceUtil();
        util.wirte(getActivity(),Commands.name,nameEdit.getText().toString());
        util.wirte(getActivity(),Commands.description,desEdit.getText().toString());
        util.wirte(getActivity(),Commands.tags,tagsEdit.getText().toString());
        util.wirte(getActivity(),Commands.uri,uriEdit.getText().toString());
        util.wirte(getActivity(),Commands.channel,channelEdit.getText().toString());
        util.wirte(getActivity(),Commands.owner,ownerEdit.getText().toString());

        util.wirte(getActivity(),Commands.host,hostEdit.getText().toString());
        util.wirte(getActivity(),Commands.port,portEdit.getText().toString());

  }

   void initData(){
       SharedPreferenceUtil util = new SharedPreferenceUtil();

       nameEdit.setText(util.read(getActivity(),Commands.name));
       desEdit.setText(util.read(getActivity(),Commands.description));
       tagsEdit.setText(util.read(getActivity(),Commands.tags));
       uriEdit.setText(util.read(getActivity(),Commands.uri));
       channelEdit.setText(util.read(getActivity(),Commands.channel));
       ownerEdit.setText(util.read(getActivity(),Commands.owner));
       String host = util.read(getActivity(),Commands.host);
       String port = util.read(getActivity(),Commands.port);
       if (host!=null &&host.length()>0)
           hostEdit.setText(host);
       if (port!=null&&port.length()>0)
           portEdit.setText(port);
   }
}
