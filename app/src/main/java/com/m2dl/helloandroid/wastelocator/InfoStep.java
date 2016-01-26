package com.m2dl.helloandroid.wastelocator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.persistence.ContextVariable;


public class InfoStep extends WizardStep {

    @ContextVariable
    private String picName;
    @ContextVariable
    private String tags;


    public InfoStep() {
        // Required empty public constructor
    }

    EditText pic_Name;
    EditText pic_tags;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info_step, container, false);
        TextView tv = (TextView) v.findViewById(R.id.textView);
        tv.setText("Please start by filling the needed information ");

        pic_Name = (EditText) v.findViewById(R.id.picName);
        pic_tags = (EditText) v.findViewById(R.id.tags);

        //and set default values by using Context Variables
        pic_Name.setText(picName);
        pic_tags.setText(tags);

        return v;
    }

    @Override
    public void onExit(int exitCode) {
        switch (exitCode) {
            case WizardStep.EXIT_NEXT:
                bindDataFields();
                break;
            case WizardStep.EXIT_PREVIOUS:
                //Do nothing...
                break;
        }
    }

    private void bindDataFields() {

        picName = pic_Name.getText().toString();
        tags = pic_tags.getText().toString();
    }


}
