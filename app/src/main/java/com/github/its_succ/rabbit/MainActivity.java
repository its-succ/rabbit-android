package com.github.its_succ.rabbit;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcA;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String action = intent.getAction();

        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)
                ||  NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                ||  NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
            TextView idmView = (TextView) findViewById(R.id.idm);
            idmView.setText("onCreate:"
                    + action + "\n"
                    + getIdm(getIntent()) + "\n"
                    + getIdmFromTag(intent));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)
                ||  NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                ||  NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
            TextView idmView = (TextView) findViewById(R.id.idm);
            idmView.setText("onNewIntent:"
                    + action + "\n"
                    + getIdm(getIntent()) + "\n"
                    + getIdmFromTag(intent));
        }
    }

    /**
     * IDmを取得する
     * @param intent 受信インテント
     * @return IDm文字列
     */
    private String getIdm(Intent intent) {
        byte[] rawIdm = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
        return toHexString(rawIdm);
    }

    private String getIdmFromTag(Intent intent) {
        Tag tag = (Tag) intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag == null) {
            return null;
        }
        byte[] rawIdm = tag.getId();
        return toHexString(rawIdm);
    }

    private String toHexString(byte[] rawIdm) {
        if (rawIdm == null) {
            return null;
        }
        StringBuffer idmByte = new StringBuffer();
        for (int i = 0; i < rawIdm.length; i++) {
            idmByte.append(Integer.toHexString(rawIdm[i] & 0xff));
        }
        return idmByte.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
