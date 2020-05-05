package com.example.gillz.vaccdrug;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class vaccinedetailadapter extends RecyclerView.Adapter<vaccinedetailadapter.ProductViewHolder> {


    private Context mCtx;
    private List<vaccinedetail> vaccinelist;

    public vaccinedetailadapter(Context mCtx, List<vaccinedetail> vaccinelist) {
        this.mCtx = mCtx;
        this.vaccinelist = vaccinelist;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.vaccinedetail, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        vaccinedetail vaccinedet = vaccinelist.get(position);

        holder.textViewabout.setText(vaccinedet.about());
        holder.textViewtype.setText(vaccinedet.vtype());
        holder.textViewusage.setText(vaccinedet.vusage());
        holder.textViewprecautions.setText(vaccinedet.precautions());


    }

    @Override
    public int getItemCount() {
        return vaccinelist.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewabout,textViewtype,textViewusage,textViewprecautions;


        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewabout = itemView.findViewById(R.id.textViewabout);
            textViewtype = itemView.findViewById(R.id.textViewtype);
            textViewusage = itemView.findViewById(R.id.textViewusage);
            textViewprecautions = itemView.findViewById(R.id.textViewprecautions);

        }
    }
}
