package sg.edu.np.mad.WittnessFittness;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class catalogueAdapter extends RecyclerView.Adapter<catViewHolder>{

    ArrayList<Exercise> datalist;

    public catalogueAdapter(ArrayList<Exercise> dbdata) {
        this.datalist = dbdata;
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.workout_rsv;
    }

    @NonNull
    @Override
    public catViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_rsv, parent, false);
        return new catViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull catViewHolder holder, int position) {
        Exercise exercise = datalist.get(position);
        holder.title.setText(exercise.getTitle());
        holder.timetaken.setText(exercise.getTimeTaken().toString());
        holder.workout.setText(exercise.getType());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context currentContext = view.getContext();
                Intent intent = new Intent(currentContext,ViewActivity.class);
                intent.putExtra("exerciseTitle", exercise.getTitle());
                intent.putExtra("exerciseTimeTaken", exercise.getTimeTaken().toString() + "minutes");
                intent.putExtra("exerciseType", exercise.getType());
                currentContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount(){
        return datalist.size();
    }

    //Define your Interface method here
    public interface ItemClickListener {
        void onItemClick(int position);
    }

}
