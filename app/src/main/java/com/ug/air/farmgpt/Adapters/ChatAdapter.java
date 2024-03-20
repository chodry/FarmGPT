package com.ug.air.farmgpt.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ug.air.farmgpt.Models.Feedback;
import com.ug.air.farmgpt.Models.Question;
import com.ug.air.farmgpt.Models.GptResponse;
import com.ug.air.farmgpt.R;
import com.ug.air.farmgpt.Models.Item;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter {

    Context context;
    List<Item> items;

    private OnItemClickListener mListener;

    public ChatAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    public interface OnItemClickListener {
        void onLikeClick(int position);
        void onDisLikeClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_layout, parent, false);
            UserHolder holder = new UserHolder(view);
            return holder;
        }
        else if (viewType == 1){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_layout, parent, false);
            BotHolder holder = new BotHolder(view);
            return holder;
        }

        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback, parent, false);
            FeedbackHolder holder = new FeedbackHolder(view, mListener);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == 0) {
            Question question = (Question) items.get(position).getObject();
            UserHolder userHolder = (UserHolder) holder;
            userHolder.question.setText(question.getText());
        }
        else if (getItemViewType(position) == 1) {
            Item item = items.get(position);
            GptResponse response = (GptResponse) item.getObject();
            BotHolder botHolder = (BotHolder) holder;
            botHolder.response.setText(response.getText());

            if (item.getShowImage().equals("Helpful")){
                botHolder.feedback.setVisibility(View.VISIBLE);
                botHolder.feedback.setImageResource(R.drawable.read);
            }
            else if (item.getShowImage().equals("Not helpful")){
                botHolder.feedback.setVisibility(View.VISIBLE);
                botHolder.feedback.setImageResource(R.drawable.round_cancel_24);
            }
            else {
                botHolder.feedback.setVisibility(View.GONE);
            }

        }

        else {
            Feedback feedback = (Feedback) items.get(position).getObject();
            FeedbackHolder feedbackHolder = (FeedbackHolder) holder;

            if (feedback.getClicked()){
                feedbackHolder.feed_layout.setVisibility(View.VISIBLE);
            }
            else {
                feedbackHolder.feed_layout.setVisibility(View.GONE);
            }

        }
    }

    public void updateImage(int position, String value,  @DrawableRes int imageResource){
        if (position >= 0 && position < items.size()){
            Item item = items.get(position);

            item.setShowImage(value);
            item.setImageResource(imageResource);

            notifyItemChanged(position);
        }
    }

    public void deleteItem(int position) {
        if (position >= 0 && position < items.size()) {
            items.remove(position); // Remove the item from the list
            notifyItemRemoved(position); // Notify the adapter that the item has been removed
            notifyItemRangeChanged(position, items.size()); // Notify the adapter that the data set has changed
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class UserHolder extends RecyclerView.ViewHolder{

        TextView question;

        public UserHolder(@NonNull View itemView) {
            super(itemView);

            question = itemView.findViewById(R.id.question);
        }
    }

    static class BotHolder extends RecyclerView.ViewHolder{

        TextView response;
        ImageView feedback;

        public BotHolder(@NonNull View itemView) {
            super(itemView);

            response = itemView.findViewById(R.id.response);
            feedback = itemView.findViewById(R.id.feedback);
        }
    }

    static  class FeedbackHolder extends RecyclerView.ViewHolder {

        ImageView helpful, not_helpful;
        ConstraintLayout feed_layout;

        public FeedbackHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            helpful = itemView.findViewById(R.id.helpful);
            not_helpful = itemView.findViewById(R.id.not_helpful);
            feed_layout = itemView.findViewById(R.id.feed);

            helpful.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onLikeClick(position);
                        }
                    }
                }
            });

            not_helpful.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onDisLikeClick(position);
                        }
                    }
                }
            });

        }
    }
}
