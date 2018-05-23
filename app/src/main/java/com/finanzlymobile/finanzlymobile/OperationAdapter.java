package com.finanzlymobile.finanzlymobile;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;

public class OperationAdapter extends RecyclerView.Adapter<OperationAdapter.OperationViewHolder> {
    private static final String TAG = "===============";
    private ArrayList<Operation> operations;
    private Resources res;
    private OperationAdapter.OnOperationClickListener clickListener;
    private TextView lblPaid;
    private Context context;

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

        if(op != null) {
            holder.image.setImageDrawable(ResourcesCompat.getDrawable(res, op.getImage(), null));
            holder.name.setText(op.getName());

            String formattedValue = "$" + NumberFormat.getNumberInstance().format(op.getValue());
            holder.value.setText(formattedValue);

            holder.v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onOperationClick(op);
                }
            });

            String paid = "";

            if(op.getType() == Operation.Type.EXPENSE && op.isPaid()){
                paid = "PAID";
            }
            holder.paid.setText(paid);
        }

    }

    @Override
    public int getItemCount() {
        if(operations == null){
            return 0;
        }
        return operations.size();
    }

    public static class OperationViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView name;
        private TextView value;
        private TextView type;
        private TextView paid;
        private View v;

        public OperationViewHolder(View itemView) {
            super(itemView);

            v = itemView;

            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.lblName);
            value = itemView.findViewById(R.id.lblValue);
            paid = itemView.findViewById(R.id.lblPaid);
        }
    }

    public interface OnOperationClickListener {
        void onOperationClick(Operation op);
    }
}
