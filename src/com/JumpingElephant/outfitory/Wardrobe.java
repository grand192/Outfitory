package com.JumpingElephant.outfitory;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Wardrobe extends Activity {
	
	public String typeFilter;
	public String colorFilter;
	public String seasonFilter;
	public String tagFilter;
	
	GridView grid;
	TableHelper th = new TableHelper(this);
	List<Clothing> clothes;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wardrobe);
		clothes = th.getAllClothes();
		
		grid = (GridView) findViewById(R.id.clothes_grid);
        grid.setAdapter(new MyAdapter(this, clothes, typeFilter, colorFilter, seasonFilter, tagFilter));

	}
	
	public void resetAdapter(){
		grid.setAdapter(new MyAdapter(this, clothes, typeFilter, colorFilter, seasonFilter, tagFilter));
	}
	
	
	
	public String getTypeFilter() {
		return typeFilter;
	}



	public void setTypeFilter(View v) {
		if (this.typeFilter != null && this.typeFilter.equals(((ToggleButton)v).getTextOn().toString())){
			this.typeFilter = null;
		} else {this.typeFilter = ((ToggleButton)v).getTextOn().toString();}
		
		resetAdapter();
	}



	public String getColorFilter() {
		return colorFilter;
	}



	public void setColorFilter(String colorFilter) {
		this.colorFilter = colorFilter;
	}



	public String getSeasonFilter() {
		return seasonFilter;
	}



	public void setSeasonFilter(String seasonFilter) {
		this.seasonFilter = seasonFilter;
	}



	public String getTagFilter() {
		return tagFilter;
	}



	public void setTagFilter(String tagFilter) {
		this.tagFilter = tagFilter;
	}



	/***
	 * Adapter Class for grid layout
	 * @author Devon
	 *
	 */
	
	private class MyAdapter extends BaseAdapter
    {
        private List<Item> items = new ArrayList<Item>();
        private LayoutInflater inflater;

        public MyAdapter(Context context, List<Clothing> clothes, String type, String color, String season, String tags)
        {
            inflater = LayoutInflater.from(context);
            
            int i = 0;
            for (Clothing clothing : clothes) {
            	if ((type == null || type.equals(clothing.getType())) &&
            		(color == null || color.equals(clothing.getColor())) &&
            		(season == null || season.equals(clothing.getSeason())) &&
            		(tags == null || tags.equals(clothing.getTags()))  ) {
            		
            		items.add(new Item(clothing.getType(), 
            			BitmapFactory.decodeByteArray(clothing.getPicture() , 0, clothing.getPicture().length),
            			i));
            		i++;
            	}
            			
            }
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int i)
        {
            return items.get(i);
        }

        @Override
        public long getItemId(int i)
        {
        	return items.get(i).drawableid;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup)
        {
            View v = view;
            ImageView picture;
            TextView name;

            if(v == null)
            {
               v = inflater.inflate(R.layout.gridview_item, viewGroup, false);
               v.setTag(R.id.picture, v.findViewById(R.id.picture));
               v.setTag(R.id.text, v.findViewById(R.id.text));
            }

            picture = (ImageView)v.getTag(R.id.picture);
            name = (TextView)v.getTag(R.id.text);

            Item item = (Item)getItem(i);

            picture.setImageBitmap(item.drawable);
            name.setText(item.name);

            return v;
        }

        private class Item
        {
            final String name;
            final Bitmap drawable;
            final long drawableid;

            Item(String name, Bitmap drawable, long drawableid)
            {
                this.name = name;
                this.drawable = drawable;
                this.drawableid = drawableid;
            }
        }
    }
	
	/***
	 * End Adapter Class
	 */

}
