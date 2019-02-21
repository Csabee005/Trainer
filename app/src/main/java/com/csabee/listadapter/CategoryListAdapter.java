package com.csabee.listadapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.csabee.trainer.Exercise;
import com.csabee.trainer.R;

import java.util.HashMap;
import java.util.List;

public class CategoryListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listDataHeader;
    private HashMap<String, List<Exercise>> listHashMap;


    public CategoryListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<Exercise>> listHashMap) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listHashMap = listHashMap;
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return listHashMap.get(listDataHeader.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return listDataHeader.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return listHashMap.get(listDataHeader.get(i)).get(i1); // i = Group Item , i1 = ChildItem
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headerTitle = (String) getGroup(i);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.group_category_name_mainpage, null);
        }
        TextView lblListHeader = (TextView) view.findViewById(R.id.txtCategoryNameMainPage);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final Exercise exercise = (Exercise) getChild(i, i1);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_exercise_definition_mainpage, null);
        }
        TextView txtExerciseName = (TextView) view.findViewById(R.id.txtExerciseDefinitionName);
        txtExerciseName.setText(exercise.getName());
        TextView txtExerciseSeries = view.findViewById(R.id.txtExerciseDefinitionSession);
        txtExerciseSeries.setText(exercise.getSeries() + " X ");
        TextView txtExerciseRepetitions = view.findViewById(R.id.txtExerciseDefinitionRepetition);
        txtExerciseRepetitions.setText(exercise.getRepetitions() + "");
        if(exercise.getWeight() != null){
            TextView txtExerciseWeight = view.findViewById(R.id.txtExerciseDefinitionWeight);
            txtExerciseWeight.setText(exercise.getWeight() + " kg");
        }
        if(exercise.getDuration() != 0){
            TextView txtExerciseDuration = view.findViewById(R.id.txtExerciseDefinitionDuration);
            txtExerciseDuration.setText(exercise.getDuration() + "s");
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
