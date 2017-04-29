package zewen.unimelb.resourceshare;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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
        result.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    protected void onResume() {
        super.onResume();
        List list = getIntent().getStringArrayListExtra("response");
        String response = "";
        for (Object o : list) {
            response += o.toString() + "\n\n";
        }
        result.setText(response);
    }
}
