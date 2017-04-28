package zewen.unimelb.resourceshare;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ResultActivity extends Activity {


    @Bind(R.id.result)
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
       List list =  getIntent().getStringArrayListExtra("response");
        String resultdata="";
        for (Object o : list){
            resultdata+=o.toString();
        }
        result.setText(resultdata);
    }
}
