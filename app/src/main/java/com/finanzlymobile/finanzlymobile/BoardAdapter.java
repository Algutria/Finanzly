package com.finanzlymobile.finanzlymobile;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.BoardViewHolder> {
    private ArrayList<Board> boards;
    private Resources res;
    private OnBoardClickListener clickListener;

    public BoardAdapter(Context context, ArrayList<Board> boards, OnBoardClickListener clickListener){
        this.boards = boards;
        res = context.getResources();
        this.clickListener = clickListener;
    }

    @Override
    public BoardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_board, parent, false);
        return new BoardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BoardAdapter.BoardViewHolder holder, int position) {
        final Board b = boards.get(position);

        holder.image.setImageDrawable(ResourcesCompat.getDrawable(res, b.getImage(), null));
        holder.name.setText(b.getName());
        holder.description.setText(b.getDescription());

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onBoardClick(b);
            }
        });
    }

    @Override
    public int getItemCount() {
        return boards.size();
    }


    public static class BoardViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView name;
        private TextView description;
        private View v;

        public BoardViewHolder(View itemView){
            super(itemView);
            v = itemView;

            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.lblName);
            description = itemView.findViewById(R.id.lblDescription);
        }
    }

    public interface OnBoardClickListener {
        void onBoardClick(Board b);
    }
}
