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

public class OperationAdapter extends RecyclerView.Adapter<OperationAdapter.OperationViewHolder> {
    private ArrayList<Operation> operations;
    private Resources res;
    private OperationAdapter.OnOperationClickListener clickListener;

    public OperationAdapter(Context context, ArrayList<Operation> operations, OnOperationClickListener clickListener){
        this.operations = operations;
        res = context.getResources();
        this.clickListener = clickListener;
    }

    @Override
    public OperationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_operation, parent, false);
        return new OperationAdapter.OperationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(OperationAdapter.OperationViewHolder holder, int position) {
        final Operation op = operations.get(position);

        holder.name.setText(op.getName());
        holder.value.setText(""+op.getValue());
        holder.type.setText(""+op.getType());

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onOperationClick(op);
            }
        });
    }

    @Override
    public int getItemCount() {
        return operations.size();
    }

    public static class OperationViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView value;
        private TextView type;
        private View v;

        public OperationViewHolder(View itemView) {
            super(itemView);

            v = itemView;

            name = itemView.findViewById(R.id.lblName);
            value = itemView.findViewById(R.id.lblValue);
            type = itemView.findViewById(R.id.lblType);
        }
    }

    public interface OnOperationClickListener {
        void onOperationClick(Operation op);
    }
}
