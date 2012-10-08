package ch.zhaw.android.photobyintent;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.example.android.photobyintent.R;

public class Browse_List extends Activity implements OnInitListener,
		android.view.View.OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browse_list_layout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_start, menu);
		return true;
	}

	@Override
	public void onInit(int args0) {
		Button button = (Button) findViewById(R.id.searching_for_Button);
		button.setOnClickListener(this);
	}

	public void onClick(View view) {
		finish();

	}
	
	
	

}
