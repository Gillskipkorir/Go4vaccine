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


public class FacilityAdapter extends RecyclerView.Adapter<FacilityAdapter.ProductViewHolder> {


    private Context mCtx;
    private List<facilitymodel> productList;
    private FragmentManager supportFragmentManager;
    private Dialog dialog;
    Button locate;
    PreferenceManager prefManager;


    Daraja daraja;

    public FacilityAdapter(Context mCtx, List<facilitymodel> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
        dialog = new Dialog(mCtx);
    }



    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.facilityresults,null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        final facilitymodel product = productList.get(position);

        holder.textViewfacility.setText(product.getFacility());
        holder.etphone1.setText(String.valueOf(product.getPhone()));
        holder.etemail.setText(product.getEmail());
        holder.etadress.setText(product.getAdress());
        holder.etstreet.setText(product.getStreet());
        holder.ettown.setText(product.getTown());
        holder.etfrom.setText(product.getOpenfrom());
        holder.etto.setText(product.getClosedat());
        holder.etpaybill.setText(String.valueOf(product.getPaybill()));
        locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mCtx,MapboxF.class);
                mCtx.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public FragmentManager getSupportFragmentManager() {
        return supportFragmentManager;
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView textViewfacility,etphone1,etemail,etadress,etstreet,ettown,etfrom,etto,etpaybill;

        public ProductViewHolder(View itemView) {
            super(itemView);
               textViewfacility = itemView.findViewById(R.id.facilitytitle);
               etphone1= itemView.findViewById(R.id.etphone1);
               etemail= itemView.findViewById(R.id.etemail);
               etadress= itemView.findViewById(R.id.etadress);
               etstreet= itemView.findViewById(R.id.etstreet);
               ettown= itemView.findViewById(R.id.ettown);
               etfrom= itemView.findViewById(R.id.etfrom);
               etto= itemView.findViewById(R.id.etto);
               etpaybill= itemView.findViewById(R.id.etpaybill);
               locate=itemView.findViewById(R.id.locate);
        }
    }
}
