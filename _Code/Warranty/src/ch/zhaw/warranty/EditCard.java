package ch.zhaw.warranty;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class EditCard extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_card);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_edit_card, menu);
        return true;
    }
}
