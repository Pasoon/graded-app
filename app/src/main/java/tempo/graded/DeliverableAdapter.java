package tempo.graded;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Zarif on 2017-07-17.
 */

public class DeliverableAdapter extends BaseSwipeAdapter {

    private Context mContext;
    private Course course;
    private RealmList<Deliverable> mDataSource;
    private Realm realm;
    private AlertDialog dialog;


    public DeliverableAdapter (Context context, RealmList<Deliverable> items, Course course) {
        mContext = context;
        mDataSource = items;
        this.course =course;
        realm = Realm.getDefaultInstance();
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
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(final int position, final ViewGroup parent) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item_deliverables, null);
        SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.delete));
            }
        });
        v.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDeliverable(position);
            }
        });
        v.findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callEditDialog(position);

            }
        });
        return v;
    }

    private void callEditDialog(final int position) {
//        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);
//        View view = LayoutInflater.from(mContext).inflate(R.layout.edit_deliverable_frag,null);
//        TextView ViewTitle = (TextView) view.findViewById(R.id.EditInfo);
//        EditText newDeliverableName = (EditText) view.findViewById(R.id.newDnInput);
//        EditText newDeliverableWeight = (EditText) view.findViewById(R.id.newDwInput);
//        newDeliverableName.setText(selectedDeliverable.getName());
//        newDeliverableWeight.setText(Double.toString(selectedDeliverable.getWeight()));
//
//        Button EditDoneBtn = (Button) view.findViewById(R.id.DoneEdit);
//        EditDoneBtn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                editDeliverableBtnClicked();
//            }
//        });
//        ViewTitle.setText("Edit "+selectedDeliverable.getName());
//        alertBuilder.setView(view);
//        dialog = alertBuilder.create();
//        dialog.show();
    }

    private void deleteDeliverable(int position) {
        Log.i("Delete Deliverable Btn", "Clicked");
        realm.beginTransaction();
        Deliverable selectedDeliverable = getItem(position);
        selectedDeliverable.deleteFromRealm();
        course.calculateGrade();
        realm.commitTransaction();
        Log.i("Delete Deliverable Btn", "Deleted");
    }

    @Override
    public void fillValues(int position, View convertView) {
        ViewHolder holder;

        // check if the view already exists if so, no need to inflate and findViewById again!
        if (convertView.getTag() == null) {
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

    }

    private static class ViewHolder {
        public TextView deliverableName;
        public TextView deliverableWeight;
        public TextView deliverableGrade;
    }

}
