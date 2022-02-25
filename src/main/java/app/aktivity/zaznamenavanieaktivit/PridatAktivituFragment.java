package app.aktivity.zaznamenavanieaktivit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import yuku.ambilwarna.AmbilWarnaDialog;

public class PridatAktivituFragment extends Fragment {

    private EditText nazov;
    private TextView pocet;
    private Button farba, pridatAktivitu, spravovat;
    private int defaultColor;

    DataModel dm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_pridat_aktivitu, container, false);

        nazov = (EditText)v.findViewById(R.id.etMenoAktivity);
        farba = (Button)v.findViewById(R.id.btnFarba);
        spravovat = (Button)v.findViewById(R.id.btnSpravovatAktivity);
        pridatAktivitu = (Button)v.findViewById(R.id.btnPridat);
        defaultColor = Color.WHITE;

        dm = new DataModel(getActivity());

        farba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPicker();
            }
        });

        pridatAktivitu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pridajAktivitu()){
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("id", R.id.nav_pridat_zaznam);
                    startActivity(intent);
                }
            }
        });

        spravovat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dm.dajZaznamy().size() != 0){
                    Intent intent = new Intent(getActivity(), SpravcaAktivit.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), "Nemáte žiadnu aktivitu!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

    public void openColorPicker(){
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(getActivity(), defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                defaultColor = color;
                farba.setBackgroundColor(color);
            }
        });
        colorPicker.show();
    }

    public boolean pridajAktivitu(){
        HashMap<String, String> aktivitaHM = new HashMap<String, String>();

        if(nazov.getText().toString().equals("")){
            Toast.makeText(getActivity(), "Zadajte názov aktivity", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            aktivitaHM.put("nazov", nazov.getText().toString());
            aktivitaHM.put("farba", String.valueOf(defaultColor));
            aktivitaHM.put("pocet", String.valueOf(0));
        }
        dm.vlozZaznam(aktivitaHM);
        return true;
    }
}