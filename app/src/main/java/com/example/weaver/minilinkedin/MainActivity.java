package com.example.weaver.minilinkedin;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.weaver.minilinkedin.models.BasicInfo;
import com.example.weaver.minilinkedin.models.Education;
import com.example.weaver.minilinkedin.models.Experience;
import com.example.weaver.minilinkedin.models.Project;
import com.example.weaver.minilinkedin.utils.ModelUtils;
import com.example.weaver.minilinkedin.utils.myDateUtils;
import com.google.gson.reflect.TypeToken;


import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE_EDUCATION_EDIT = 100;
    private BasicInfo basicInfo;
    private List<Education> educations;
    private Experience experience;
    private Project project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fakeData();
        loadData();
        setupUI();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_EDUCATION_EDIT && resultCode == Activity.RESULT_OK){
            Education education = data.getParcelableExtra(EducationEditActivity.KEY_EDUCATION);
            updateEducation(education);
        }
    }

    private void updateEducation (Education newEducation){
        boolean found = false;
        for (int i = 0; i < educations.size(); i++){
            Education item = educations.get(i);
            if (TextUtils.equals(item.id, newEducation.id)){
                educations.set(i, newEducation);
                found = true;
                break;
            }
        }
        if (!found){
            educations.add(newEducation);
        }

        ModelUtils.saveModel(this, "educations", educations);

        setupEducations();
    }

    private void setupUI(){
        findViewById(R.id.add_education_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,EducationEditActivity.class);
                startActivityForResult(intent, REQ_CODE_EDUCATION_EDIT);
                getSupportActionBar().setDisplayShowTitleEnabled(true);


            }
        });
        setupBasicInfo();
        setupEducations();
        setupExperience();
        setupProject();
    }

    private void setupProject(){
        String dateString = myDateUtils.dateToString(project.startDate) + " ~ "
                + myDateUtils.dateToString(project.endDate);
        ((TextView) findViewById(R.id.Project_name)).setText(project.name + "(" +
            dateString + ")");
        ((TextView) findViewById(R.id.Project_details)).setText(formatItems(project.details));
    }

    private void setupExperience(){
        String dateString = myDateUtils.dateToString(experience.startDate) + " ~ "
                + myDateUtils.dateToString(experience.endDate);
        ((TextView) findViewById(R.id.exp_company)).setText(experience.company + "(" + dateString
                + ")");
        ((TextView) findViewById(R.id.exp_title)).setText(experience.title);
    }

    private void setupEducations(){
        LinearLayout educationLayout = (LinearLayout) findViewById(R.id.education_layout);
        educationLayout.removeAllViews();
        for (Education education : educations) {
            View view = getEducationView(education);
            educationLayout.addView(view);
        }
    }

    private View getEducationView(final Education education){
        View view = getLayoutInflater().inflate(R.layout.education_item, null); // what is the null?

        String dateString = myDateUtils.dateToString(education.startDate)
                + " ~ " + myDateUtils.dateToString(education.endDate);
        ((TextView) view.findViewById(R.id.edu_name)).setText(education.name + "(" + dateString + ")");
        ((TextView) view.findViewById(R.id.courses)).setText(formatItems(education.details));

        view.findViewById(R.id.edit_education_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EducationEditActivity.class);
                intent.putExtra(EducationEditActivity.KEY_EDUCATION, education);
                startActivityForResult(intent, REQ_CODE_EDUCATION_EDIT);

            }
        });
        return view;
    }

    private void setupBasicInfo(){
        ((TextView) findViewById(R.id.name)).setText(basicInfo.name);
        ((TextView) findViewById(R.id.email)).setText(basicInfo.email);
    }

    private void fakeData(){
        basicInfo = new BasicInfo();
        basicInfo.name = "Xiaolong Yang";
        basicInfo.email = "weaveryxl549@gmail.com";

        Education education1 = new Education();
        education1.name = "OSU";
        education1.major = "Mechanical Engineering";
        education1.startDate = myDateUtils.stringToDate("03/2013");
        education1.endDate = myDateUtils.stringToDate("06/2016");

        education1.details = new ArrayList<>();
        education1.details.add("ME538 Autonomous Agents");
        education1.details.add("CS534 Machine Learning");
        education1.details.add("CS519 Deep Learning");
        education1.details.add("CS533 Artifact Intelligence");

        Education education2 = new Education();
        education2.name = "USTB";
        education2.major = "Mechanical Engineering";
        education2.startDate = myDateUtils.stringToDate("09/2008");
        education2.endDate = myDateUtils.stringToDate("06/2012");

        education2.details = new ArrayList<>();
        education2.details.add("Mathematics");
        education2.details.add("Design");
        education2.details.add("Metal crafting");
        education2.details.add("Simulation");

        Education education3 = new Education();
        education3.name = "No. 171 Middle School";
        education3.major = "Science and Technology";
        education3.startDate = myDateUtils.stringToDate("09/2005");
        education3.endDate = myDateUtils.stringToDate("06/2008");

        education3.details = new ArrayList<>();
        education3.details.add("Language");
        education3.details.add("Mathematics");
        education3.details.add("English");
        education3.details.add("Physics");

        educations = new ArrayList<>();
        educations.add(education1);
        educations.add(education2);
        educations.add(education3);

        experience = new Experience();
        experience.company = "AA company";
        experience.title = "Engineer";
        experience.startDate = myDateUtils.stringToDate("07/2015");
        experience.endDate = myDateUtils.stringToDate("09/2015");
        List<String> experienceDetails = new ArrayList<>();
        experienceDetails.add("Built A");
        experienceDetails.add("Built B");
        experienceDetails.add("Built C");
        experience.details = experienceDetails;

        project = new Project();
        project.name = "Cell Segmentation";
        project.startDate = myDateUtils.stringToDate("11/2015");
        project.endDate = myDateUtils.stringToDate("02/2016");

        List<String> projectDetails = new ArrayList<>();
        projectDetails.add("Pre-processing");
        projectDetails.add("Design structure");
        projectDetails.add("Observation");
        project.details = projectDetails;
    }
    public static String formatItems(List<String> items){
        StringBuilder strbuilder = new StringBuilder();
        for (String item : items){
            strbuilder.append(" ").append("-").append(" ").append(item).append("\n");
        }
        if (strbuilder.length() > 0){
            strbuilder.deleteCharAt(strbuilder.length() - 1);
        }
        return strbuilder.toString();
    }

    private void loadData(){
        educations = ModelUtils.readModel(this,"educations", new TypeToken<List<Education>>(){});
        if (educations == null){
            educations = new ArrayList<>();
        }
    }
}
