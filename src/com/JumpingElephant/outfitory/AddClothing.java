package com.JumpingElephant.outfitory;

import java.io.ByteArrayOutputStream;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AddClothing extends Activity{
	
	ImageButton picture;
	Bitmap curBitmap;
	
	EditText name;
	EditText tags;
	
	/* Clothing Type */
	ToggleButton top;
	ToggleButton bottom;
	ToggleButton dress;
	ToggleButton shoes;
	ToggleButton other;
	String curType;
	
	/* Clothing Color */
	ToggleButton black;
	ToggleButton white;
	ToggleButton blue;
	ToggleButton green;
	ToggleButton red;
	String curColor;
	
	Button save_btn;
	Button cancel_btn;
	Button saveContinue_btn;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.add_clothing);
		
		picture = (ImageButton)findViewById(R.id.picture);
		
		save_btn = (Button)findViewById(R.id.save_button);
		cancel_btn = (Button)findViewById(R.id.cancel_button);
		saveContinue_btn = (Button)findViewById(R.id.save_continue_button);
		
		name = (EditText)findViewById(R.id.name_text);
		tags = (EditText)findViewById(R.id.tag_text);
		
		top = (ToggleButton)findViewById(R.id.top_toggle);
		bottom = (ToggleButton)findViewById(R.id.bottom_toggle);
		dress = (ToggleButton)findViewById(R.id.dress_toggle);
		shoes = (ToggleButton)findViewById(R.id.shoes_toggle);
		other = (ToggleButton)findViewById(R.id.other_toggle);
		
		black = (ToggleButton)findViewById(R.id.black_toggle);
		white = (ToggleButton)findViewById(R.id.white_toggle);
		blue = (ToggleButton)findViewById(R.id.blue_toggle);
		green = (ToggleButton)findViewById(R.id.green_toggle);
		red = (ToggleButton)findViewById(R.id.red_toggle);
		
		curType = top.getTextOn().toString();
		curColor = black.getTextOn().toString();
		
		super.onCreate(savedInstanceState);
	}
	
	public void saveClothing(View v){
		TableHelper th = new TableHelper(this);
		Clothing clothing = new Clothing(name.getText().toString(), curType, curColor, null, tags.getText().toString(), getPicture());
		th.addClothing(clothing);
		//Toast.makeText(getApplicationContext(), th.getAllClothes().toString(), Toast.LENGTH_LONG).show();
		if ((( Button ) v).getText().toString().equals("Save")){
			goToWardrobe(null);
		} else {clearFields();}
	}
	
	public void setType(View v){
		ToggleButton b = (ToggleButton) v;
		curType = b.getTextOn().toString();
		top.setChecked(false);
		bottom.setChecked(false);
		dress.setChecked(false);
		shoes.setChecked(false);
		other.setChecked(false);
		b.setChecked(true);
	}
	
	public void setColor(View v){
		ToggleButton b = (ToggleButton) v;
		curColor = b.getTextOn().toString();
		black.setChecked(false);
		white.setChecked(false);
		blue.setChecked(false);
		red.setChecked(false);
		green.setChecked(false);
		b.setChecked(true);
	}
	
	static final int REQUEST_IMAGE_CAPTURE = 1;

	public void dispatchTakePictureIntent(View v) {
	    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
	        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
	    }
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
	    	picture = (ImageButton) findViewById(R.id.picture);
	        Bundle extras = data.getExtras();
	        Bitmap imageBitmap = (Bitmap) extras.get("data");
	        picture.setImageBitmap(imageBitmap);
	        curBitmap = imageBitmap;
	    }
	}
	
	protected byte[] getPicture(){
		Bitmap photo = curBitmap;
		if (photo == null){
			return null;
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		photo.compress(Bitmap.CompressFormat.PNG, 100, bos);
		return bos.toByteArray();
	}
	
	public void goToWardrobe(View v){
		Intent myIntent = new Intent(this, Wardrobe.class);
		startActivity(myIntent);
	}
	
	public void clearFields(){
		Intent myIntent = new Intent(this, AddClothing.class);
		startActivity(myIntent);
	}
}
