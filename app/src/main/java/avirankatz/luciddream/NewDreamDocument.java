package avirankatz.luciddream;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import DAL.DBHelper;
import DAL.DreamContract;
import models.Dream;

public class NewDreamDocument extends AppCompatActivity {

    private long date;
    private String title;
    private String content;
    boolean isNewDream;
    private long dreamId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dream_document);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        isNewDream = tryGetExtras();
        if (date == 0) { date = System.currentTimeMillis(); }
        setViews();
    }

    private void setViews() {
        SimpleDateFormat dateToday = new SimpleDateFormat("dd/MM/yy");

        EditText etTitle = (EditText) findViewById(R.id.editText_dreamHeader);
        EditText etContent = (EditText) findViewById(R.id.editText_dreamScript);
        TextView tvDate = (TextView) findViewById(R.id.textView_today);

        etTitle.setText(title);
        etContent.setText(content);
        tvDate.setText(dateToday.format(date));

    }

    private boolean tryGetExtras() {
        Intent creator = getIntent();
        title = creator.hasExtra(DreamContract.Dream.COLUMN_NAME_DREAM_TITLE) ? creator.getStringExtra(DreamContract.Dream.COLUMN_NAME_DREAM_TITLE) : "";
        content = creator.hasExtra(DreamContract.Dream.COLUMN_NAME_DREAM_CONTENT) ? creator.getStringExtra(DreamContract.Dream.COLUMN_NAME_DREAM_CONTENT) : "";
        date = creator.getLongExtra(DreamContract.Dream.COLUMN_NAME_TIME_OF_CREATION, 0);
        dreamId = creator.getLongExtra(DreamContract.Dream._ID, 0);
        if (dreamId == 0) { return true; }
        return false;
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
                Intent result = new Intent();
                setResult(RESULT_OK, result);
                finish();
            }
            else {
                Toast.makeText(NewDreamDocument.this, "בעיה לא צפויה קרתה", Toast.LENGTH_SHORT).show();
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
        Dream dream = new Dream(dreamHeader, dreamScript, date);
        if (isNewDream) {
            return dbHelper.insertNewDream(dream);
        }
        else if (dreamId != 0) {
            return dbHelper.editDream(dreamId, dream);
        }
        return false;
    }
}
