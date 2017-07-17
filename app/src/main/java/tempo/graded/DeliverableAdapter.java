package tempo.graded;

/**
 * Created by Pasoon on 2017-07-16.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.realm.RealmResults;

public class DeliverableAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private RealmResults<Deliverable> mDataSource;

    public DeliverableAdapter(Context context, RealmResults<Deliverable> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Deliverable getItem(int i) {
        return mDataSource.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mDataSource.get(i).getID();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        // check if the view already exists if so, no need to inflate and findViewById again!
        if (convertView == null) {

            // Inflate the custom row layout from your XML.
            convertView = mInflater.inflate(R.layout.list_item_deliverables, parent, false);

            // create a new "Holder" with subviews
            holder = new ViewHolder();
            holder.deliverableName = (TextView) convertView.findViewById(R.id.deliverableList_name);
            holder.deliverableGrade = (TextView) convertView.findViewById(R.id.deliverableList_grade);
            holder.deliverableWeight = (TextView) convertView.findViewById(R.id.deliverableList_weight);
            holder.deliverableGradeColor = (RelativeLayout) convertView.findViewById(R.id.deliverableList_gradeColor);

            // hang onto this holder for future recyclage
            convertView.setTag(holder);
        }
        else {

            // skip all the expensive inflation/findViewById and just get the holder you already made
            holder = (ViewHolder) convertView.getTag();
        }

        // Get relevant subviews of row view
        TextView deliverableName = holder.deliverableName;
        RelativeLayout deliverableGradeColor = holder.deliverableGradeColor;
        TextView deliverableGrade = holder.deliverableGrade;
        TextView deliverableWeight = holder.deliverableWeight;


        //Get corresponding habit for row
        //Course course = getItem(position);
        //courseName.setText(course.getCourseCode());
        //courseGrade.setText("A+"); //Need to implement course.getGrade() and course.getColor().
        //Based on grade, we would set the color.
        //gradeColor.setBackgroundResource(R.color.grade_a);

        return convertView;
    }

    private static class ViewHolder {
        public TextView deliverableName;
        public RelativeLayout deliverableGradeColor;
        public TextView deliverableWeight;
        public TextView deliverableGrade;
    }

}

