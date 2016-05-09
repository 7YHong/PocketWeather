package cn.qiyanghong.pocketweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

import cn.qiyanghong.pocketweather.entity.City;

/**
 * Created by QYH on 2016/5/9.
 */
public class MyExpandableLVAdapter extends BaseExpandableListAdapter {
    private static final java.lang.String TAG = MyExpandableLVAdapter.class.getName();
    Context context;
    String[] provinces;      //列表大小跟cities是一样的
    List<List<City>> cities;

    public MyExpandableLVAdapter(Context context, String[] provinces, List<List<City>> cities) {
        this.context = context;
        this.provinces = provinces;
        this.cities = cities;
    }

    @Override
    public int getGroupCount() {
        return cities.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return cities.get(groupPosition).size();
    }

    /**
     * 返回对应的省名(String类型)
     *
     * @param
     * @return
     */
    @Override
    public Object getGroup(int groupPosition) {
        return provinces[groupPosition];
    }

    /**
     * 返回一个City(City类型)
     *
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return cities.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return cities.get(groupPosition).get(childPosition).getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_expandable_list_item_1, parent, false);
            vh = new ViewHolder();
            vh.mTextView = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.mTextView.setText((String) getGroup(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
            vh = new ViewHolder();
            vh.mTextView = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.mTextView.setText(((City) getChild(groupPosition, childPosition)).toString());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private final class ViewHolder {
        public TextView mTextView;
    }
}
