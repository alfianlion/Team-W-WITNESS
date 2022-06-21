package sg.edu.np.mad.WittnessFittness;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class catalogueAdapter extends RecyclerView.Adapter<catalogueAdapter.ViewHolder>{

    public ArrayList<Exercise> datalist;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView title,timetaken,workout;

        public ViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.viewWorkoutTitle);
            timetaken = (TextView) view.findViewById(R.id.timeTaken);
            workout = (TextView) view.findViewById(R.id.workoutType);
        }

        public TextView getTitle() {
            return title;
        }
        public TextView getTimetaken() {
            return timetaken;
        }
        public TextView getWorkout() {
            return workout;
        }
    }

    public catalogueAdapter(ArrayList<Exercise> data, catalogue catalogue) {
        this.datalist = data;
        System.out.println(data.toString());
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.workout_rsv;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_rsv, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){

        String title = datalist.get(position).Title;
        holder.getTitle().setText(title);
        String t = datalist.get(position).Type;
        holder.getWorkout().setText(t);
        String tt = datalist.get(position).TimeTaken.toString();
        holder.getTimetaken().setText(tt);

    }

    @Override
    public int getItemCount(){
        return datalist.size();
    }

}
