package com.example.finalyoga.Adapter.SQL;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyoga.Interface.IClickItemListener;
import com.example.finalyoga.R;
import com.example.finalyoga.database.model.ClassInstance;
import com.example.finalyoga.database.model.YogaCourse;

import java.util.ArrayList;

public class ClassInstanceInClassAdapter extends RecyclerView.Adapter<ClassInstanceInClassAdapter.ViewHolder> {

    private final ArrayList<ClassInstance> filteredClassInstanceList;
    private final YogaCourse yogaCourse;
    private final IClickItemListener iItemClickListener;

    public ClassInstanceInClassAdapter(ArrayList<ClassInstance> classInstanceList,YogaCourse yogaCourse
            ,int courseId,IClickItemListener iItemClickListener){
        this.filteredClassInstanceList = new ArrayList<>();
        for (ClassInstance instance : classInstanceList) {
            if (instance.getCourse_id() == courseId) {
                this.filteredClassInstanceList.add(instance);
            }
        }
        this.yogaCourse=yogaCourse;
        this.iItemClickListener=iItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_instance_class_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClassInstance classInstance = filteredClassInstanceList.get(position);
        holder.txtTeacher_name_detail.setText(classInstance.getTeacher_name());
        holder.txtDate_detail.setText(classInstance.getDate_classInstance());
        holder.txtComment_detail.setText(classInstance.getAdditional_comments());

        // Handle item clicks
        holder.card_item_class_instance_in_class.setOnClickListener(v -> iItemClickListener.onClickItem(classInstance));
    }


    @Override
    public int getItemCount() {
        return filteredClassInstanceList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTeacher_name_detail, txtDate_detail, txtComment_detail;
        private CardView card_item_class_instance_in_class;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTeacher_name_detail = itemView.findViewById(R.id.txtTeacher_name_detail);
            txtDate_detail = itemView.findViewById(R.id.txtDate_detail);
            txtComment_detail = itemView.findViewById(R.id.txtComment_detail);
            card_item_class_instance_in_class=itemView.findViewById(R.id.card_item_class_instance_in_class);
        }
    }


}
