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


//adapter for the RV
@RequiresApi(api = Build.VERSION_CODES.N)
public class AdapterItem extends RecyclerView.Adapter<AdapterItem.ItemViewHolder> {

    //Variables
    Context context;
    ArrayList<dataUser> dataUserArrayList; //arrayList containing DataUser objects (from constructor)

    //
    Locale id = new Locale("in", "ID");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", id);


    //Constructor (for Adapter)
    public AdapterItem(Context context, ArrayList<dataUser> dataUserArrayList) {
        this.context = context;
        this.dataUserArrayList = dataUserArrayList;
    }



    //onCreateViewHolder, called when a new view is required
    // to inflate the context(MainActivity.js) with this ViewHolders
    @NonNull
    @Override
    public AdapterItem.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //inflate the context view (MainActivity.js) with the viewHolders
        //View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);

        //return inflated view, by calling ItemViewHolder
        //return new ItemViewHolder(itemView);
        return null;
    }




    //onBindViewHolder, to Bind the data to View Holder
    @Override
    public void onBindViewHolder(@NonNull AdapterItem.ItemViewHolder holder, int position) {
        holder.viewBind(dataUserArrayList.get(position));

    }



    //getItemCount, retrieve the total number of items to bind
    @Override
    public int getItemCount() {
        return dataUserArrayList.size();
    }



    //View Holder, to retrieve and access all the items used during binding process
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name,
                tv_gender,
                tv_major,
                tv_dateOfRegistration;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            //tv_name = itemView.findViewById(R.id.tv_name);
            //tv_gender = itemView.findViewById(R.id.tv_gender);
            //tv_major = itemView.findViewById(R.id.tv_major);
            //tv_dateOfRegistration = itemView.findViewById(R.id.tv_dateOfRegistration);
        }

        //Bind, the retrieved item, to the view data (dataUser).
        public void viewBind(dataUser dataUser) {

            tv_name.setText(dataUser.getName());
            tv_gender.setText(dataUser.getGender());
            tv_major.setText(dataUser.getMajor());
            tv_dateOfRegistration.setText(simpleDateFormat.format(dataUser.getDateOfRegistration()));
        }

    }
}