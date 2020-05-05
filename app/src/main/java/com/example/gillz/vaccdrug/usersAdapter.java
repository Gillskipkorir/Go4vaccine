package com.example.gillz.vaccdrug;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.androidstudy.daraja.Daraja;

import java.util.List;


public class usersAdapter extends RecyclerView.Adapter<usersAdapter.ProductViewHolder> {


    private Context mCtx;
    private List<usersmodel> productList;
    private FragmentManager supportFragmentManager;
    private Dialog dialog;
    Button locate;
    PreferenceManager prefManager;

    public usersAdapter(Context mCtx, List<usersmodel> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
        dialog = new Dialog(mCtx);
    }



    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.viewusers,null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        final usersmodel product = productList.get(position);

        holder.fname.setText(product.getFname());
        holder.lname.setText(product.getLname());
        holder.idno.setText(String.valueOf(product.getIdno()));
        holder.phone.setText(String.valueOf(product.getPhoneno()));
            }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public FragmentManager getSupportFragmentManager() {
        return supportFragmentManager;
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView fname,lname,idno,phone;

        public ProductViewHolder(View itemView) {
            super(itemView);
               fname= itemView.findViewById(R.id.tvfname);
               lname= itemView.findViewById(R.id.tvlname);
               idno= itemView.findViewById(R.id.tvid);
               phone= itemView.findViewById(R.id.tvphone);

        }
    }
}
