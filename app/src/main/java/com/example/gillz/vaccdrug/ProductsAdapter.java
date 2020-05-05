package com.example.gillz.vaccdrug;

import android.app.Dialog;
import android.content.Context;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidstudy.daraja.Daraja;
import com.androidstudy.daraja.DarajaListener;
import com.androidstudy.daraja.model.AccessToken;
import com.androidstudy.daraja.model.LNMExpress;
import com.androidstudy.daraja.model.LNMResult;

import java.util.HashMap;
import java.util.List;


public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {
    private Context mCtx;
    private List<Product> productList;
    private FragmentManager supportFragmentManager;
    private Dialog dialog;
    PreferenceManager prefManager;
    Button pay,cancel;
    PatientSessionManager session;
    Daraja daraja;
    public ProductsAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
        dialog = new Dialog(mCtx);
    }
    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.serarchresults, null);
        return new ProductViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        final Product product = productList.get(position);
        holder.textViewvaccine.setText(product.getTitle());
        holder.textViewfacility.setText(product.getRating());
        holder.textViewPrice.setText(String.valueOf(product.getPrice()));
        holder.textViewaday.setText(product.getAday());
        holder.btnbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session = new PatientSessionManager(mCtx);
                //get user data from session
                HashMap<String, String> user = session.getUserDetails();
                //TODO:get name
                String idno = user.get(PatientSessionManager.KEY_PATIENT_ID_NUMBER);
                //
                dialog.setContentView(R.layout.customdialog);
                dialog.show();
                final TextView vaccine=dialog.findViewById(R.id.tvfname);
                final TextView facility=dialog.findViewById(R.id.tvlname);
                final TextView price=dialog.findViewById(R.id.tvid);
                final EditText paybill=dialog.findViewById(R.id.paybill);
                vaccine.setText(product.getTitle());
                facility.setText(product.getRating());
                price.setText(String.valueOf(product.getPrice()));
                pay = dialog.findViewById(R.id.btnpay);
                cancel=dialog.findViewById(R.id.cancel);
                pay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      final String phoneNumber = paybill.getText().toString().trim();
                        if (TextUtils.isEmpty(phoneNumber)) {
                            paybill.setError("Your Phone Number cant be Empty");
                            return;
                        }
                        else if (paybill.length()<10||paybill.length()>10) {
                            paybill.setError("Enter your Valid Phone Number");
                        }
                        else
                        {
                            //bookingBTask btask = new bookingBTask(mCtx);
                            //btask.execute(idno,vacci,facili,pri);
                            //Init Daraja
                            //TODO :: REPLACE WITH YOUR OWN CREDENTIALS  :: THIS IS SANDBOX DEMO
                            daraja = Daraja.with( "oHHh8Ua6iyi4sVduay0saOjWkHebAJmY", "VbJsaZDVT9bA5EiW", new DarajaListener<AccessToken>() {
                                //oHHh8Ua6iyi4sVduay0saOjWkHebAJmY////VbJsaZDVT9bA5EiW
                                @Override
                                public void onResult(@NonNull AccessToken accessToken) {
                                    Log.i( mCtx.getClass().getSimpleName(), accessToken.getAccess_token() );
                                    Toast.makeText( mCtx, "TOKEN : " + accessToken.getAccess_token(), Toast.LENGTH_SHORT ).show();
                                }
                                @Override
                                public void onError(String error) {
                                    Log.e( mCtx.getClass().getSimpleName(), error );
                                }
                            } );
                            //TODO :: THIS IS A SIMPLE WAY TO DO ALL THINGS AT ONCE!!! DON'T DO THIS :)
                            pay.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //Get Phone Number from User Input
                                    paybill.getText().toString().trim();

                                    //TODO :: REPLACE WITH YOUR OWN CREDENTIALS  :: THIS IS SANDBOX DEMO
                                    LNMExpress lnmExpress = new LNMExpress(
                                            "174379",
                                            "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",
                                             String.valueOf(product.getPrice()),
                                            "254714704628",
                                            "174379",
                                             phoneNumber,
                                            "http://meal.shulemall.com/api",
                                            "G04VACCINE",
                                            "BOOKING" );
                                    //This is the
                                    daraja.requestMPESAExpress( lnmExpress, new DarajaListener<LNMResult>() {
                                        @Override
                                        public void onResult(@NonNull LNMResult lnmResult) {
                                            Log.i( mCtx.getClass().getSimpleName(), lnmResult.ResponseDescription );

                                            Toast.makeText(mCtx, lnmResult.ResponseDescription,Toast.LENGTH_LONG).show();
                                        }
                                        @Override
                                        public void onError(String error) {
                                            Log.i( mCtx.getClass().getSimpleName(), error );
                                        }
                                    } );
                                };
                            });
 dialog.setContentView(R.layout.receipt);
                            dialog.show();
                       TextView print= dialog.findViewById(R.id.print);
                       TextView cancel=dialog.findViewById(R.id.cancel);

                            return;
                        }
                        }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
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
        TextView textViewvaccine, textViewfacility,textViewaday,textViewPrice;
        Button btnbook;
        public ProductViewHolder(View itemView) {
            super(itemView);
            textViewvaccine = itemView.findViewById(R.id.tvfname);
            textViewfacility = itemView.findViewById(R.id.tvlname);
            textViewPrice = itemView.findViewById(R.id.tvid);
            textViewaday = itemView.findViewById(R.id.textViewaday);
            btnbook=itemView.findViewById(R.id.Rbtnbook);
        }
    }
}