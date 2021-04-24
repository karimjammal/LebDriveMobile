package com.karim.lebdrive;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TestTypeAdapter extends RecyclerView.Adapter<TestTypeAdapter.TypeViewHolder> {

    List<TestType> typeList;

    public TestTypeAdapter(List<TestType> typeList) {
        this.typeList = typeList;
    }

    @NonNull
    @Override
    public TypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new TypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestTypeAdapter.TypeViewHolder holder, int position) {
        TestType testType = typeList.get(position);
        holder.testType.setText(testType.getType());
        holder.description.setText(testType.getDescription());
        holder.startBtn.setText(testType.getBtnText());

        boolean isExpandable = typeList.get(position).isExpandable();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }

    public class TypeViewHolder extends RecyclerView.ViewHolder {
        TextView testType, description;
        Button startBtn;
        LinearLayout linearLayout;
        RelativeLayout expandableLayout;

        public TypeViewHolder(@NonNull View itemView) {
            super(itemView);

            testType = itemView.findViewById(R.id.test_type_text_view);
            description = itemView.findViewById(R.id.description_text_view);
            startBtn = itemView.findViewById(R.id.start_test_btn);

            linearLayout = itemView.findViewById(R.id.linear_layout);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TestType testType = typeList.get(getAdapterPosition());
                    testType.setExpandable(!testType.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
