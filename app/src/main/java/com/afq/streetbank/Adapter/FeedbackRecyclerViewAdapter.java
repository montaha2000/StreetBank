package com.afq.streetbank.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afq.streetbank.Model.Feedback;
import com.afq.streetbank.R;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class FeedbackRecyclerViewAdapter extends RecyclerView.Adapter<FeedbackRecyclerViewAdapter.ViewHolder> {

    private final List<Feedback> mValues;
    private Context mContext;


    public FeedbackRecyclerViewAdapter(ArrayList<Feedback> mValues, Context mContext) {
        this.mValues = mValues;
        this.mContext = mContext;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feedback_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.txtName.setText(mValues.get(position).getDisplayName() +" : ");
        holder.txtFeedback.setText(mValues.get(position).getFeedback());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtName;
        TextView txtFeedback;

        public ViewHolder(View view) {
            super(view);

            txtFeedback = view.findViewById(R.id.txtFeedback);
            txtName = view.findViewById(R.id.txtName);

        }

    }
}
