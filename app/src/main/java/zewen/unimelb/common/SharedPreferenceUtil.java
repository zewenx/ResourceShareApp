package zewen.unimelb.common;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Francis on 4/29/2017.
 */

public class SharedPreferenceUtil {
   public void wirte(Context context, String key, Object value) {
        SharedPreferences.Editor editor = context.getSharedPreferences("data", MODE_PRIVATE).edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        }
        editor.commit();

    }

    public String read(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences("data", MODE_PRIVATE);
        return preferences.getString(key,null);
    }
}

