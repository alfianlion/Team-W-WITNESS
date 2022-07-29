package sg.edu.np.mad.WittnessFittness;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class favAdapter extends RecyclerView.Adapter<favAdapter.favViewHolder>{

    ArrayList<Exercise> datalist;

    public favAdapter(ArrayList<Exercise> dbdata) {
        this.datalist = dbdata;
    }

    public class favViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView timetaken;
        TextView workout;

        public favViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.fviewWorkoutTitle);
            timetaken = itemView.findViewById(R.id.ftimeTaken);
            workout = itemView.findViewById(R.id.fworkoutType);
        }
    }

    @NonNull
    @Override
    public favAdapter.favViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_rsv, parent, false);
        return new favViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull favAdapter.favViewHolder holder, int position) {
        Exercise exercise = datalist.get(position);
        holder.title.setText(exercise.getTitle());
        if(exercise.getTimeTaken() > 1){
            holder.timetaken.setText(exercise.getTimeTaken().toString() + " minutes");
        } else {
            holder.timetaken.setText(exercise.getTimeTaken().toString() + " minute");
        }
        holder.workout.setText(exercise.getType());
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }
}
