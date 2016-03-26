package pragmaticdevelopment.com.honeydue.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import java.util.ArrayList;

import pragmaticdevelopment.com.honeydue.DBSource.TaskModel;
import pragmaticdevelopment.com.honeydue.R;

/**
 * Created by Admin on 12/2/2015.
 */
public class TasksListAdapter extends BaseExpandableListAdapter{
    private ArrayList<TaskModel> elements;
    private LayoutInflater inflater;

    public TasksListAdapter(ArrayList<TaskModel> elements, LayoutInflater inflater){
        setElements(elements);
        setInflater(inflater);
    }

    public void setElements(ArrayList<TaskModel> newElements){
        elements = newElements;
    }

    public void setInflater(LayoutInflater newInflater){
        inflater = newInflater;
    }

    @Override
    public int getGroupCount() {
        return elements.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_groupview, null);
        }

        TaskModel current = elements.get(groupPosition);
        ((CheckedTextView) convertView).setText(current.getTitle());
        ((CheckedTextView) convertView).setChecked(isExpanded);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_childview, null);
        }

        TextView taskDescription = (TextView) convertView.findViewById(R.id.txtTaskDescription);
        TaskModel current = elements.get(groupPosition);

        taskDescription.setText(current.getDescription());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
