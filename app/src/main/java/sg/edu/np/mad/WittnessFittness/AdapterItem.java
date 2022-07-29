package sg.edu.np.mad.WittnessFittness;


import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;


//adapter for the RV (activity_calendar.xml)
@RequiresApi(api = Build.VERSION_CODES.N)
public class AdapterItem extends RecyclerView.Adapter<AdapterItem.ItemViewHolder> {

    //Variables
    Context context;
    ArrayList<CalendarEvent> calEventArrayList; //arrayList containing DataUser objects (from constructor)

    //
    Locale id = new Locale("in", "ID");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", id);


    //Constructor (for Adapter)
    public AdapterItem(Context context, ArrayList<CalendarEvent> calEventArrayList) {
        this.context = context;
        this.calEventArrayList = calEventArrayList;
    }



    //onCreateViewHolder, called when a new view is required
    // to inflate the context(CalendarActivity.js) with this ViewHolders
    @NonNull
    @Override
    public AdapterItem.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //inflate the context view (CalendarActivity.js) with the viewHolders
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cal_layout, parent, false);

        //return inflated view, by calling ItemViewHolder
        return new ItemViewHolder(itemView);
    }




    //onBindViewHolder, to Bind the data to View Holder
    @Override
    public void onBindViewHolder(@NonNull AdapterItem.ItemViewHolder holder, int position) {
        holder.viewBind(calEventArrayList.get(position));
    }



    //getItemCount, retrieve the total number of items to bind
    @Override
    public int getItemCount() {
        return calEventArrayList.size();
    }



    //View Holder, to retrieve and access all the items used during binding process
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name,
                tv_exerciseType,
                tv_timeAllocated,
                tv_dateOfEvent;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_exerciseType = itemView.findViewById(R.id.tv_exerciseType);
            tv_timeAllocated = itemView.findViewById(R.id.tv_timeAllocated);
            tv_dateOfEvent = itemView.findViewById(R.id.tv_dateOfEvent);
        }

        //Bind, the retrieved item, to the view data (dataUser).
        public void viewBind(CalendarEvent calendarEvent) {

            tv_name.setText(calendarEvent.getName());
            tv_exerciseType.setText(calendarEvent.getType());
            tv_timeAllocated.setText(calendarEvent.getTimeAllocated());
            tv_dateOfEvent.setText(simpleDateFormat.format(calendarEvent.getDateOfEvent()));
        }

    }
}