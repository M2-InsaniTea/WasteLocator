package com.m2dl.helloandroid.wastelocator;

import org.codepond.wizardroid.WizardFlow;
import org.codepond.wizardroid.layouts.BasicWizardLayout;


public class WizardFragment extends BasicWizardLayout {

    public WizardFragment() {
        // Required empty public constructor
    }

    @Override
    public WizardFlow onSetup() {
        return new WizardFlow.Builder()
                .addStep(InfoStep.class)
                .addStep(PictureStep.class)
                .create();
    }
}
