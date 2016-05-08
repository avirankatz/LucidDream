package avirankatz.luciddream;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import DAL.DBHelper;

public class NewDreamDocument extends AppCompatActivity {

    private long date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dream_document);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.date = System.currentTimeMillis();
        SimpleDateFormat dateToday = new SimpleDateFormat("dd/MM/yy");
        TextView tvToday = (TextView) findViewById(R.id.textView_today);
        tvToday.setText(dateToday.format(date));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_dream_document, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuItem_saveNewDream) {
            if (saveCurrentDream()) {
                Toast.makeText(NewDreamDocument.this, getString(R.string.toast_dreamSavedSuccessfully), Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean saveCurrentDream()
    {
        EditText etDreamHeader = (EditText) findViewById(R.id.editText_dreamHeader);
        String dreamHeader = etDreamHeader.getText().toString();
        EditText etDreamScript = (EditText) findViewById(R.id.editText_dreamScript);
        String dreamScript = etDreamScript.getText().toString();
        DBHelper dbHelper = new DBHelper(this);
        if (dbHelper.insertNewDream(dreamHeader, dreamScript)) {
            return true;
        }
        else {
            return false;
        }
    }
}
