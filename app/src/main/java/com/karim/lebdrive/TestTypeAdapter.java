package com.karim.lebdrive;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TestTypeAdapter extends RecyclerView.Adapter<TestTypeAdapter.TypeViewHolder> {

    List<TestType> typeList;
    String test_type = "";

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

            startBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    test_type = startBtn.getText().toString();
                    switch (test_type) {
                        case "Start Full Test":
                            test_type = "full";
                            break;
                        case "Start Questions Test":
                            test_type = "questions";
                            break;
                        case "Start Signs Test":
                            test_type = "signs";
                            break;
                    }
                    String language = "";
                    Spinner spinner = itemView.findViewById(R.id.language_spinner);
                    String languageAb = String.valueOf(spinner.getSelectedItem());
                    switch (languageAb) {
                        case "AR":
                            language = "arabic";
                            break;
                        case "EN":
                            language = "english";
                            break;
                        case "FR":
                            language = "french";
                            break;
                    }
                    Intent intent = new Intent(v.getContext(), TestActivity.class);
                    intent.putExtra("Test Type", test_type);
                    intent.putExtra("Language", language);
                    v.getContext().startActivity(intent);
                    System.out.println("testtype: " + test_type);
                }
            });
        }
    }
}
