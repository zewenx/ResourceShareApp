package zewen.unimelb.common;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

import zewen.unimelb.VO.RequestVO;

/**
 * Created by Francis on 4/28/2017.
 */

public class RequestThread extends Thread {
    String port = "";
    String host = "";
    RequestVO vo;
    Handler handler;

    public RequestThread(String port, String host, RequestVO vo, Handler handler) {
        this.port = port;
        this.host = host;
        this.vo = vo;
        this.handler = handler;
    }

    @Override
    public void run() {
        Socket socket = null;
        ArrayList responseList = new ArrayList<String>();
        try {

            // InetSocketAddress address = new
            // InetSocketAddress("sunrise.cis.unimelb.edu.au", 3780);
            InetSocketAddress address = null;
            address = new InetSocketAddress(host, Integer.parseInt(port));
            socket = new Socket();
            socket.setSoTimeout(60000);
            socket.connect(address);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(vo.toJson());
            out.flush();

            l:
            while (true) {

                String response = in.readUTF();
                if (response != null && response.length() > 0) {
                    responseList.add(response);
                }

                switch (vo.getCommand().toLowerCase()) {
                    case "publish":
                    case "remove":
                    case "exchange":
                    case "share":
                        if (response.contains("response")) {
                            break l;
                        }
                        break;
                    case "query":
                    case "fetch":
                        if (response.contains("resultSize") || response.contains("error")) {
                            break l;
                        }
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            responseList.add(e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("data",responseList);
        Message i = new Message();
        i.setData(bundle);
        handler.sendMessage(i);
    }
}
