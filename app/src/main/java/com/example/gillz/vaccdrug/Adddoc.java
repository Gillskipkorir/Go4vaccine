package com.example.gillz.vaccdrug;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Adddoc.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Adddoc#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Adddoc extends Fragment {

    EditText etfname,etlname,etidno,etphoneno,tipass,ticpass;
    Button btnregister;
    Spinner spinnerfacility;
    ArrayAdapter<CharSequence> arrayAdapter;
    String facility;
    AlertDialog.Builder builder;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Adddoc() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Adddoc.
     */
    // TODO: Rename and change types and number of parameters
    public static Adddoc newInstance(String param1, String param2) {
        Adddoc fragment = new Adddoc();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_adddoc, container, false);

        final  Context c = getActivity().getApplicationContext();
        etfname=v.findViewById(R.id.dfname);
        etlname=v.findViewById(R.id.dlnmae);
        spinnerfacility=v.findViewById(R.id.sfacility);
        etidno=v.findViewById(R.id.dno);
        tipass=v.findViewById(R.id.dpass);
        ticpass=v.findViewById(R.id.dconpass);
        btnregister=v.findViewById(R.id.btnadddoc);


        //Todo:populate facility spinner.
        arrayAdapter= ArrayAdapter.createFromResource(c,R.array.Facility, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerfacility.setAdapter(arrayAdapter);
        //todo: Facility selected
        spinnerfacility.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                facility = (String) adapterView.getItemAtPosition(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            String fname,lname,idno,pass,cpass;
            @Override
            public void onClick(View view) {

                fname = etfname.getText().toString().trim();
                lname = etlname.getText().toString().trim();
                idno= etidno.getText().toString().trim();
                pass = tipass.getText().toString().trim();
                cpass = ticpass.getText().toString().trim();
                facility.trim();

                if (TextUtils.isEmpty(facility)&&TextUtils.isEmpty(fname)&&TextUtils.isEmpty(lname)&& TextUtils.isEmpty(idno)&&TextUtils.isEmpty
                    (pass)&& TextUtils.isEmpty(cpass))

                {
                ShowToast("Fill all the fields");
                }
                else if (TextUtils.isEmpty(fname))
                {
                    etfname.setError("Please Enter First name");
                }

                else if (TextUtils.isDigitsOnly(fname))
                {
                    etfname.setError("Your First Name Is Incorrect");
                }
                else if (TextUtils.isEmpty(lname))
                {
                    etlname.setError("Please Enter Last name");
                }
                else if (TextUtils.isDigitsOnly(lname))
                {
                    etlname.setError("Your Last Name Is Incorrect");
                }

                else if (!TextUtils.isDigitsOnly(idno))
                {
                    etidno.setError("Your Id number Name Is Incorrect");
                }
                else if (idno.length()<7||idno.length()>8)
                {
                    etidno.setError("You have entered a wrong Id number format");
                }
                else if (pass.length()<4)
                {
                    tipass.setError("Your Password is too Short");
                }
                else if (!pass.equals(cpass))
                {
                    ShowToast("Your Passords doesnt Match!, Please try Again");
                }
                else
                    {

                        builder=new AlertDialog.Builder(getActivity());
                        builder.setTitle("Submit Details for" + fname+"");
                        builder.setMessage("The following Credentials will be submitted:\n\n" +
                                "1. First Name:  "+fname+ "\n\n2. Last Name:  "+lname+"\n\n3.Facility from:  "+facility+
                                "\n\n4. Doctor's IDNO:  "+idno +"\n\n5.Password: "+pass);

                        builder.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                adddocBTask backgroundTask = new adddocBTask(getActivity());
                                backgroundTask.execute(fname,lname,facility,idno,pass);

                            }
                        }).setNegativeButton("REVIEW", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog , int i) {
                                dialog.dismiss();

                            }
                        });
                        AlertDialog alertDialog=builder.create();
                        alertDialog.show();

                }



                }
            });
         return v;
    }
    private void ShowToast(String  msg) {
        Toast.makeText(getActivity(),msg, Toast.LENGTH_LONG).show();

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
