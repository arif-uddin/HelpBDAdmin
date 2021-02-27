package com.diu.helpbdadmin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.diu.helpbdadmin.Activity.NIDDetailActivity;
import com.diu.helpbdadmin.Model.ModelNID;
import com.diu.helpbdadmin.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NIDListAdapter extends RecyclerView.Adapter<NIDListAdapter.ViewHolder> implements Filterable {

    private final Context context;
    List<ModelNID> modelNIDArrayList;
    List<ModelNID> allModelNIDArrayList;

    public NIDListAdapter(Context context, List<ModelNID> modelNIDArrayList) {
        this.context = context;
        this.modelNIDArrayList = modelNIDArrayList;
        allModelNIDArrayList=new ArrayList<>(modelNIDArrayList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.nid_list,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.name.setText("Name: "+modelNIDArrayList.get(i).getName());
        viewHolder.nidNo.setText("NID No: "+modelNIDArrayList.get(i).getNidNo());

        if (modelNIDArrayList.get(i).getApplied().equals("yes")){
            viewHolder.color.setBackgroundColor(Color.parseColor("#FFFF00"));
        }
        if (modelNIDArrayList.get(i).getGotFund().equals("yes")){
            viewHolder.color.setBackgroundColor(Color.parseColor("#006a4e"));
        }

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, NIDDetailActivity.class);
                intent.putExtra("applied",modelNIDArrayList.get(i).getApplied());
                intent.putExtra("balance",modelNIDArrayList.get(i).getBalance());
                intent.putExtra("account",modelNIDArrayList.get(i).getBankAccount());
                intent.putExtra("district",modelNIDArrayList.get(i).getDistrict());
                intent.putExtra("fund",modelNIDArrayList.get(i).getGotFund());
                intent.putExtra("name",modelNIDArrayList.get(i).getName());
                intent.putExtra("nidNo",modelNIDArrayList.get(i).getNidNo());
                intent.putExtra("union",modelNIDArrayList.get(i).getUnion());
                intent.putExtra("upazila",modelNIDArrayList.get(i).getUpazila());
                intent.putExtra("birthDate",modelNIDArrayList.get(i).getBirthDate());
                intent.putExtra("fatherName",modelNIDArrayList.get(i).getFatherName());
                intent.putExtra("motherName",modelNIDArrayList.get(i).getMotherName());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelNIDArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter= new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<ModelNID> filteredModelNID= new ArrayList<>();
            if(charSequence==null || charSequence.length()==0){
                filteredModelNID.addAll(allModelNIDArrayList);
            }
            else {
                String searchChr = charSequence.toString().toLowerCase().trim();
                for(ModelNID modelNID:allModelNIDArrayList){
                    if(modelNID.getNidNo().toLowerCase().contains(searchChr)){
                        filteredModelNID.add(modelNID);
                    }
                }
            }

            FilterResults filterResults= new FilterResults();
            filterResults.values= filteredModelNID;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            modelNIDArrayList.clear();
            modelNIDArrayList.addAll((Collection<? extends ModelNID>) filterResults.values);
            notifyDataSetChanged();

        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,nidNo;
        CardView cardView;
        ImageView color;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=(TextView) itemView.findViewById(R.id.name);
            nidNo=(TextView) itemView.findViewById(R.id.nIdNo);
            cardView=(CardView) itemView.findViewById(R.id.cardView);
            color=(ImageView) itemView.findViewById(R.id.color);
        }
    }

    public void setValues(ModelNID nid){
        modelNIDArrayList.add(0,nid);
        allModelNIDArrayList.add(0,nid);
        notifyDataSetChanged();
    }
}
