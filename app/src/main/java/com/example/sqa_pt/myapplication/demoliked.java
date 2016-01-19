package com.example.sqa_pt.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class demoliked extends Activity {

    @VisibleForTesting
    protected static final String ROW_TEXT = "ROW_TEXT";
    protected static final String Foooter = "FOOTER";

    @VisibleForTesting
    protected static final String ROW_ENABLED = "ROW_ENABLED";

    @VisibleForTesting
    protected static final int NUMBER_OF_ITEMS = 100;

    @VisibleForTesting
    protected static final String ITEM_TEXT_FORMAT = "item: %d";

    private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

    private LayoutInflater layoutInflater;

    private Button bt_addlike;

    ListAdapter adapter;
    ListView listView;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_demoliked);
        populateData();

        listView = (ListView) findViewById(R.id.likedlist);
        String[] from = new String[]{ROW_TEXT, ROW_ENABLED};
        int[] to = new int[]{R.id.rowContentTextView, R.id.rowToggleButton};
        layoutInflater = getLayoutInflater();

        // Create the adapter for the list.

        bt_addlike= (Button) findViewById(R.id.addlike);
        bt_addlike.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                //data.add(makeItem(data.size(), Long.toHexString(Double.doubleToLongBits(Math.random()))));
                data.add(makeItem(data.size(), "kkbox"));
                // Send the data to the list.

                listView.setAdapter(adapter);
            }
        });
        adapter = new LongListAdapter(from, to);
        listView.setAdapter(adapter);
    }

    String randomString(final int length) {
        Random r = new Random(); // perhaps make it a class variable so you don't make a new one every time
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < length; i++) {
            char c = (char)(r.nextInt((int)(Character.MAX_VALUE)));
            sb.append(c);
        }
        return sb.toString();
    }


    @VisibleForTesting
    protected static Map<String, Object> makeItem(int forRow,String str) {
        Map<String, Object> dataRow = new HashMap();
        //dataRow.put(ROW_TEXT, String.format(ITEM_TEXT_FORMAT, forRow));
        dataRow.put(ROW_TEXT, String.format(ITEM_TEXT_FORMAT, forRow)+"  "+str);
        dataRow.put(ROW_ENABLED, forRow == 1);
        return dataRow;
    }

    private void populateData() {
        for (int i = 0; i < NUMBER_OF_ITEMS; i++) {
            data.add(makeItem(i,"kkbox"));
        }
    }

    private class LongListAdapter extends SimpleAdapter {

        public LongListAdapter(String[] from, int[] to) {
            super(demoliked.this, demoliked.this.data, R.layout.listitem, from, to);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // Inflate list items.
            if (null == convertView) {
                convertView = layoutInflater.inflate(R.layout.listitem, null);
            }

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((TextView) findViewById(R.id.selection_row_value)).setText(
                            String.valueOf(position));
                }
            });

            return super.getView(position, convertView, parent);
        }
    }
}
