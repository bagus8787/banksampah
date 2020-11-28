package com.sahitya.banksampahsahitya.admin.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.admin.HomeAdminActivity;
import com.sahitya.banksampahsahitya.camera.Potrait;

import org.json.JSONException;
import org.json.JSONObject;

public class ScanAdminFragment extends Fragment implements View.OnClickListener{
    // View Object
    private Button buttonScan;
    private TextView textViewNama, textViewTinggi;

    private IntentIntegrator intentIntegrator;

    // Required empty public constructor
    public ScanAdminFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_scan, container, false);

//       initialize object
        buttonScan = rootView.findViewById(R.id.btn_Scan);
        textViewNama = rootView.findViewById(R.id.txt_textViewNama);
        textViewTinggi = rootView.findViewById(R.id.txt_textViewTinggi);
        // attaching onclickListener
        buttonScan.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new IntentIntegrator(this.getActivity());
        intentIntegrator = IntentIntegrator.forSupportFragment(this);
        // use forSupportFragment or forFragment method to use fragments instead of activity
//        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        intentIntegrator.setCaptureActivity(Potrait.class);
        intentIntegrator.setPrompt("Scan a barcode");
        intentIntegrator.setCameraId(0);  // Use a specific camera of the device
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null){
            if (result.getContents() == null){
                Toast.makeText(getActivity(), "Hasil tidak ditemukan", Toast.LENGTH_SHORT).show();
            }else{
                // jika qrcode berisi data
                try{
                    // converting the data json
                    JSONObject object = new JSONObject(result.getContents());
                    // atur nilai ke textviews
                    String hasil = result.getContents();

                    textViewNama.setText(hasil);
//                    textViewNama.setText(object.getString("nama"));
                    textViewTinggi.setText(object.getString("tinggi"));
                }catch (JSONException e){

                    e.printStackTrace();
                    // jika format encoded tidak sesuai maka hasil
                    // ditampilkan ke toast
                    Toast.makeText(getActivity(), result.getContents(), Toast.LENGTH_SHORT).show();

                    String hasil = result.getContents();

                    textViewNama.setText(hasil);
                    Log.i("hasil" , hasil);
                }
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View v) {
        // inisialisasi IntentIntegrator(scanQR)
        intentIntegrator = new IntentIntegrator(this.getActivity()).forSupportFragment(this);
        // use forSupportFragment or forFragment method to use fragments instead of activity
//        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        intentIntegrator.setCaptureActivity(Potrait.class);
        intentIntegrator.setPrompt("Scan a barcode");
        intentIntegrator.setCameraId(0);  // Use a specific camera of the device
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.initiateScan();
    }
}