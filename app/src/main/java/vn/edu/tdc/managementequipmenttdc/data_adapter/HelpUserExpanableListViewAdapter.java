package vn.edu.tdc.managementequipmenttdc.data_adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

import vn.edu.tdc.managementequipmenttdc.R;

public class HelpUserExpanableListViewAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listQuestion;
    private HashMap<String, List<String>> hashMapAnswer;

    public HelpUserExpanableListViewAdapter(Context context, List<String> listQuestion, HashMap<String, List<String>> hashMapAnswer) {
        this.context = context;
        this.listQuestion = listQuestion;
        this.hashMapAnswer = hashMapAnswer;
    }

    @Override
    public int getGroupCount() {
        return listQuestion.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return hashMapAnswer.get(listQuestion.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listQuestion.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return hashMapAnswer.get(listQuestion.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.expanable_help_user_list_question, null);
        }
        TextView txtHeader = (TextView) convertView.findViewById(R.id.expanded_question);
        txtHeader.setTypeface(null, Typeface.BOLD);
        txtHeader.setText(headerTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String chilText = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.expanable_help_user_list_answer, null);
        }
        TextView txtHeader = (TextView) convertView.findViewById(R.id.expanded_answer);
        txtHeader.setText(chilText);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
