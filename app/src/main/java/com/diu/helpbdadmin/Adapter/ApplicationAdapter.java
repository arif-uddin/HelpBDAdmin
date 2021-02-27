package com.diu.helpbdadmin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.provider.ContactsContract;
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

import com.diu.helpbdadmin.Activity.ApplicationStatusActivity;
import com.diu.helpbdadmin.Activity.NIDDetailActivity;
import com.diu.helpbdadmin.Model.ModelApplication;
import com.diu.helpbdadmin.Model.ModelNID;
import com.diu.helpbdadmin.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ViewHolder> implements Filterable {

    private final Context context;
    ArrayList<ModelApplication> modelApplications;
    ArrayList<ModelApplication> allModelApplications;

    public ApplicationAdapter(Context context, ArrayList<ModelApplication> modelApplications) {
        this.context = context;
        this.modelApplications = modelApplications;
        allModelApplications=new ArrayList<>(modelApplications);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.applications_list,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.name.setText("Name: "+modelApplications.get(i).getName());
        viewHolder.nidNo.setText("NID No: "+modelApplications.get(i).getNidNo());

        if (modelApplications.get(i).getStatus().equals("Confirmed")){
            viewHolder.color.setBackgroundColor(Color.parseColor("#FFFF00"));

            if (modelApplications.get(i).getGotFund().equals("yes")){
                viewHolder.color.setBackgroundColor(Color.parseColor("#006a4e"));
            }
        }


        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ApplicationStatusActivity.class);
                intent.putExtra("balance",modelApplications.get(i).getBalance());
                intent.putExtra("account",modelApplications.get(i).getAccount());
                intent.putExtra("fund",modelApplications.get(i).getGotFund());
                intent.putExtra("district",modelApplications.get(i).getDistrict());
                intent.putExtra("status",modelApplications.get(i).getStatus());
                intent.putExtra("name",modelApplications.get(i).getName());
                intent.putExtra("nidNo",modelApplications.get(i).getNidNo());
                intent.putExtra("union",modelApplications.get(i).getUnion());
                intent.putExtra("upazila",modelApplications.get(i).getUpazila());
                intent.putExtra("birthDate",modelApplications.get(i).getBirthDate());
                intent.putExtra("fatherName",modelApplications.get(i).getFatherName());
                intent.putExtra("motherName",modelApplications.get(i).getMotherName());
                intent.putExtra("id",modelApplications.get(i).getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelApplications.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter= new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<ModelApplication> filteredModelApplication= new ArrayList<>();
            if(charSequence==null || charSequence.length()==0){
                filteredModelApplication.addAll(allModelApplications);
            }
            else {
                String searchChr = charSequence.toString().toLowerCase().trim();
                for(ModelApplication modelApplication:allModelApplications){
                    if(modelApplication.getNidNo().toLowerCase().contains(searchChr)){
                        filteredModelApplication.add(modelApplication);
                    }
                }
            }

            FilterResults filterResults= new FilterResults();
            filterResults.values= filteredModelApplication;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            modelApplications.clear();
            modelApplications.addAll((Collection<? extends ModelApplication>) filterResults.values);
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

    public void setValues(ModelApplication modelApplication){
        modelApplications.add(0,modelApplication);
        allModelApplications.add(0,modelApplication);
        notifyDataSetChanged();
    }
}
