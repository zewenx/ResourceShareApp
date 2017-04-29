package zewen.unimelb.common;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;

import org.apache.commons.io.FileUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import zewen.unimelb.VO.RequestVO;
import zewen.unimelb.VO.SpecialResourceVO;

import static zewen.unimelb.common.Commands.debug;
import static zewen.unimelb.common.Commands.remove;

/**
 * Created by Francis on 4/29/2017.
 */

public class FetchRequestThread extends Thread{
    Context mContext;
    String port = "";
    String host = "";
    RequestVO vo;
    Handler handler;

    public FetchRequestThread(Context mContext, String port, String host, RequestVO vo, Handler handler) {
        this.mContext = mContext;
        this.port = port;
        this.host = host;
        this.vo = vo;
        this.handler = handler;
    }

    @Override
    public void run() {
        Socket socket = null;
        try {
            InetSocketAddress address = new InetSocketAddress(host, Integer.parseInt(port));
            socket = new Socket();
            socket.connect(address);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(vo.toJson());
            out.flush();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            ArrayList<String> responseList = new ArrayList<>();

            String response = in.readUTF();
            responseList.add(response);

            if (response.contains("error")) {
                sentMessage(responseList, handler);
                return;
            }
            SpecialResourceVO specialResourceVO = new Gson().fromJson(in.readUTF(), SpecialResourceVO.class);
            responseList.add(specialResourceVO.toJson());
            File parent = Environment.getExternalStorageDirectory();

            File file = new File(new URI(specialResourceVO.getUri()));
            String fileName = file.getName();
            File dataFile = new File(parent, fileName);
            if (dataFile.exists()) {
                dataFile.delete();
            }
            dataFile.createNewFile();
            byte[] datas = new byte[(int) specialResourceVO.getResourceSize() + 1];
            int count = 0;
            while (count < specialResourceVO.getResourceSize()) {
                count += in.read(datas, count, (int) (specialResourceVO.getResourceSize()) - count);
            }
            FileUtils.writeByteArrayToFile(dataFile, datas);

            responseList.add("File has been stored in " + dataFile.getAbsolutePath());

            response = in.readUTF();
            responseList.add(response);
            sentMessage(responseList, handler);
        } catch (IOException e) {
            e.printStackTrace();
            ArrayList<String> list = new ArrayList<String>();
            list.add(e.getMessage());
            list.add("Cannot get access to external storage. Permission denied.");
            sentMessage(list,handler);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            ArrayList<String> list = new ArrayList<String>();
            list.add(e.getMessage());
            sentMessage(list,handler);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
                ArrayList<String> list = new ArrayList<String>();
                list.add(e.getMessage());
                sentMessage(list,handler);
            }
        }
    }

    void sentMessage(ArrayList<String> responseList, Handler handler) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("data", responseList);
        Message i = new Message();
        i.setData(bundle);
        handler.sendMessage(i);
    }
}
