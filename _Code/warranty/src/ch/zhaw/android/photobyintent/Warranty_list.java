package ch.zhaw.android.photobyintent;

import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.photobyintent.R;

public class Warranty_list extends Activity implements OnInitListener,android.view.View.OnClickListener {
	private TextToSpeech tts;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_layout);
		tts = new TextToSpeech(this, this);
		 tts.speak("hello freund", TextToSpeech.QUEUE_FLUSH,null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		 getMenuInflater().inflate(R.menu.activity_start, menu);
		return true;
	}

	 @Override
	 public void onInit(int args0) {
	 tts.setLanguage(Locale.GERMAN);
	 Button button = (Button) findViewById(R.id.email_Button);
	 button.setOnClickListener(this);
//	
	 Button goBackButton = (Button) findViewById(R.id.Button_go_back);
	 goBackButton.setOnClickListener(new OnClickListener() {
//	
	 public void onClick(View v) {
//	 setContentView(R.layout.activity_start);
//	 // PhotoIntentActivity newphoto = new PhotoIntentActivity();
		 finish();
	
	 }
	 });
	 }

	
	 
	 
	 
	 public void onClick(View view) {
		 EditText et = (EditText) findViewById(R.id.editText1);
		
		 tts.speak(et.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);

	}

	


	
	
	
	
	
	
	
}
