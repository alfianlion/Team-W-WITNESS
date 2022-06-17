package sg.edu.np.mad.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class catalogueAdapter extends RecyclerView.Adapter<catViewHolder>{

    catalogue cat;
    ArrayList<Exercise> data;

    public catalogueAdapter(ArrayList<Exercise> exerciseList,catalogue cat){
        data = exerciseList;
        this.cat = cat;
    }

    @NonNull
    @Override
    public catViewHolder onCreateViewHolder(
            ViewGroup parent,
            int viewType
    ){
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.catalogue_block,
                        parent,
                        false);

        return new catViewHolder(item);
    }

    @Override
    public void onBindViewHolder(
            @NonNull catViewHolder holder,
            int position
    ){
        Exercise e = data.get(position);

        holder.timeTaken.setText(e.TimeTaken);
        holder.title.setText(e.Title);
        holder.type.setText(e.Type);

    }

    @Override
    public int getItemCount(){
        return data.size();
    }

}
