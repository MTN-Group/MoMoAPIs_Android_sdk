package com.momo.gsma;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class CustomUseCaseAdapter extends RecyclerView.Adapter<CustomUseCaseAdapter.ViewHolder> {

    private Context context;
    private int status;
    private boolean statusVisibility;
    private String[] useCaseArray;
    private ItemClickListener mClickListener;
    ArrayList<Status> mStatusList;


    CustomUseCaseAdapter(Context context, boolean statusVisibility, String[] useCaseArray) {
        this.context = context;
        this.useCaseArray = useCaseArray;
        this.statusVisibility = statusVisibility;
        mStatusList = new ArrayList<>();
        for (int i = 0; i < useCaseArray.length; i++) {
            Status status = new Status();
            status.setStatus(0);
            mStatusList.add(status);
        }
    }

    @NonNull
    @Override
    public CustomUseCaseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.usecase_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.useCase.setText(useCaseArray[position]);
        if (!statusVisibility) {
            holder.ivStatus.setVisibility(View.INVISIBLE);
        }
        int status = mStatusList.get(position).getStatus();
        if (status == 0) {
            holder.ivStatus.setImageResource(R.drawable.ic_pending);
        } else if (status == 1) {
            holder.ivStatus.setImageResource(R.drawable.ic_check);
        } else {
            holder.ivStatus.setImageResource(R.drawable.ic_fail);
        }

    }

    @Override
    public int getItemCount() {
        return useCaseArray.length;
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView useCase;
        private ImageView ivStatus;

        public ViewHolder(final View itemView) {
            super(itemView);
            useCase = itemView.findViewById(R.id.useCaseItem);
            ivStatus = itemView.findViewById(R.id.ivTestStatus);
            ivStatus.setVisibility(View.VISIBLE);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public void setStatus(int status, int position) {
        this.status = status;
        Status statusModel = mStatusList.get(position);
        statusModel.setStatus(status);
        mStatusList.remove(position);
        mStatusList.add(position, statusModel);
        notifyItemChanged(position);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
