package com.ug.air.farmgpt.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ug.air.farmgpt.Models.Question;
import com.ug.air.farmgpt.Models.GptResponse;
import com.ug.air.farmgpt.R;
import com.ug.air.farmgpt.Utils.Item;

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
        void onClick(int position);
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
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_layout, parent, false);
            BotHolder holder = new BotHolder(view);
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
        else {
            GptResponse response = (GptResponse) items.get(position).getObject();
            BotHolder botHolder = (BotHolder) holder;
            botHolder.response.setText(response.getText());
            if (response.getFeedback().isEmpty()){
                botHolder.feedback.setVisibility(View.GONE);
            }
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
}
