package sg.edu.np.mad.WittnessFittness;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class catViewHolder extends RecyclerView.ViewHolder{
    TextView title;
    TextView type;
    TextView timeTaken;

    public catViewHolder(View viewItem){
        super(viewItem);

        type = viewItem.findViewById(R.id.workoutType);
        title = viewItem.findViewById(R.id.workoutTitle);
        timeTaken = viewItem.findViewById(R.id.timeTaken);
    }
}
