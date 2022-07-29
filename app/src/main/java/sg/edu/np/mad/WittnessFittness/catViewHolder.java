package sg.edu.np.mad.WittnessFittness;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class catViewHolder extends RecyclerView.ViewHolder{
    TextView title;
    TextView timetaken;
    TextView workout;

    public catViewHolder(View viewItem){
        super(viewItem);

        title = viewItem.findViewById(R.id.viewWorkoutTitle);
        timetaken = viewItem.findViewById(R.id.timeTaken);
        workout = viewItem.findViewById(R.id.workoutType);
    }
}
