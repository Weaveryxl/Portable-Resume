package com.example.weaver.minilinkedin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.weaver.minilinkedin.models.Education;
import com.example.weaver.minilinkedin.utils.myDateUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Weaver on 2016/8/26.
 */
public class EducationEditActivity extends AppCompatActivity{

    public static final String KEY_EDUCATION = "education";

    private Education education;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_edit);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        education = getIntent().getParcelableExtra(KEY_EDUCATION);
        if (education != null){
            ((TextView) findViewById(R.id.education_school_edit)).setText(education.name);
            ((TextView) findViewById(R.id.education_major_edit)).setText(education.major);
            ((TextView) findViewById(R.id.start_date_edit)).setText(myDateUtils.dateToString(education.startDate));
            ((TextView) findViewById(R.id.end_date_edit)).setText(myDateUtils.dateToString(education.endDate));
            ((TextView) findViewById(R.id.education_courses_edit)).setText(detailsToString(education.details));
        }
    }

    private String detailsToString(List<String> courses){
        StringBuilder sb = new StringBuilder();
        for (String course : courses){
            sb.append(course).append("\n");
        }
        if (sb.length() > 0){
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // menu response
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.edu_save:
                saveAndExit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void saveAndExit(){
        //save data
        if (education == null){
            education = new Education();
        }

        education.name = ((EditText) findViewById(R.id.education_school_edit)).getText().toString();
        education.major = ((EditText) findViewById(R.id.education_major_edit)).getText().toString();
        education.startDate = myDateUtils.stringToDate(((EditText) findViewById(R.id.start_date_edit)).getText().toString());
        education.endDate = myDateUtils.stringToDate(((EditText) findViewById(R.id.end_date_edit)).getText().toString());
        education.details = Arrays.asList(TextUtils.split(((EditText) findViewById(R.id.education_courses_edit)).getText().toString(),"\n"));

        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_EDUCATION , education);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
