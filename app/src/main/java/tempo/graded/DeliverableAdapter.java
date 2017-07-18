package tempo.graded;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import io.realm.RealmList;

/**
 * Created by Zarif on 2017-07-17.
 */

public class DeliverableAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private RealmList<Deliverable> mDataSource;

    public DeliverableAdapter (Context context, RealmList<Deliverable> items) {
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
        return i;
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
            holder.deliverableName= (TextView) convertView.findViewById(R.id.itemName);
            holder.deliverableWeight = (TextView) convertView.findViewById(R.id.itemWeight);
            holder.deliverableGrade = (TextView) convertView.findViewById(R.id.itemGrade);
            // hang onto this holder for future recyclage
            convertView.setTag(holder);
        }
        else {
            // skip all the expensive inflation/findViewById and just get the holder you already made
            holder = (ViewHolder) convertView.getTag();
        }

        // Get relevant subviews of row view
        TextView dName = holder.deliverableName;
        TextView dWeight = holder.deliverableWeight;
        TextView dGrade = holder.deliverableGrade;


        //Get corresponding deliverable for row
        Deliverable deliverable = getItem(position);
        String name = deliverable.getName();
        String weight = Double.toString(deliverable.getWeight());
        String grade = Double.toString(deliverable.getGrade());
        dName.setText(name);
        dWeight.setText(weight+"%");
        dGrade.setText(grade+"%");
        return convertView;
    }

    private static class ViewHolder {
        public TextView deliverableName;
        public TextView deliverableWeight;
        public TextView deliverableGrade;
    }

}
