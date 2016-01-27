package com.m2dl.helloandroid.wastelocator;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.m2dl.helloandroid.wastelocator.backend.wasteApi.WasteApi;
import com.m2dl.helloandroid.wastelocator.backend.wasteApi.model.Tag;

import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.persistence.ContextVariable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class InfoStep extends WizardStep {

    @ContextVariable
    private List<Tag> tags;

    private HashMap<Integer,Tag> selected;
    private Integer defaultBackgroundColor;


    public InfoStep() {
        // Required empty public constructor
    }

    ListView listView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info_step, container, false);
        listView = (ListView) v.findViewById(R.id.listView);
        selected = new HashMap<>();

        new GetTagsTask(getContext()).execute();
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

        tags = new ArrayList<>(selected.values());
    }

    private class GetTagsTask extends AsyncTask<Void, Void, List<Tag>> {
        private Context context;
        private WasteApi wasteApi;

        public GetTagsTask(Context context) {
            super();
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            wasteApi = CloudEndpointBuilderHelper.getEndpoints();
        }

        @Override
        protected List<Tag> doInBackground(Void... params) {
            try {
                return wasteApi.tags().list().execute().getItems();
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Tag> result) {
            String msg;
            if (result == null) {
                msg = "Connection failed";
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
            } else {
                tags = result;
                Integer index = 0;
                ArrayList<String> entries = new ArrayList<>();
                for(Tag tag : result) {
                    entries.add(index,tag.getName());
                    index++;
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_selectable_list_item, entries);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(selectionHandler);
            }
        }
    }

    private AdapterView.OnItemClickListener selectionHandler = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(defaultBackgroundColor == null) {
                defaultBackgroundColor = view.getSolidColor();
            }

            Object entry = selected.get(position);
            if(entry == null) {
                selected.put(position, tags.get(position));
                view.setBackgroundColor(Color.rgb(160,0,0));
            } else {
                selected.remove(position);
                view.setBackgroundColor(defaultBackgroundColor);
            }
        }
    };



}
