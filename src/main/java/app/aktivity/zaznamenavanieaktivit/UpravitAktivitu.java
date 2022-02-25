package app.aktivity.zaznamenavanieaktivit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import yuku.ambilwarna.AmbilWarnaDialog;

public class UpravitAktivitu extends AppCompatActivity {

    private EditText nazov;
    private TextView pocet;
    private Button farba, ulozit, zmazat;

    private String aktivitaId, oldName;
    private int defaultColor;
    DataModel dm = new DataModel(this);
    DataModel1 dm1 = new DataModel1(this);

    private HashMap<String, String> aktivita = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upravit_aktivitu);

        //zmazanie názvu v ActionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        nazov = (EditText)findViewById(R.id.etUpravaNazov);
        pocet = (TextView)findViewById(R.id.tvUpravaPocet);
        farba = (Button)findViewById(R.id.btnUpravaFarba);
        ulozit = (Button)findViewById(R.id.btnUpravaUlozit);
        zmazat = (Button)findViewById(R.id.btnUpravaZmazat);

        Intent intent = getIntent();
        aktivitaId = intent.getStringExtra("aktivitaId");

        aktivita = dm.dajZazanam(aktivitaId);

        defaultColor = Integer.parseInt(aktivita.get("farba"));
        oldName = aktivita.get("nazov");

        nazov.setText(aktivita.get("nazov"));
        farba.setBackgroundColor(defaultColor);
        pocet.setText(String.valueOf(dm1.dajZaznamyCount(aktivita.get("nazov"))));

        farba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPicker();
            }
        });

        ulozit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(aktualizujAktivitu(aktivitaId)){
                    Intent intent = new Intent(UpravitAktivitu.this, SpravcaAktivit.class);
                    startActivity(intent);
                }
            }
        });

        zmazat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(UpravitAktivitu.this)
                        .setMessage("Naozaj chcete zmazať túto aktivitu?")
                        .setPositiveButton("Áno", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.finishAffinity(UpravitAktivitu.this);
                                dm.vymazAktivitu(aktivitaId);
                                dm1.vymazZaznamy(aktivita.get("nazov"));
                                Toast.makeText(UpravitAktivitu.this, "Aktivita bola zmazaná.", Toast.LENGTH_SHORT).show();
                                finish();
                                Intent intent = new Intent(UpravitAktivitu.this, SpravcaAktivit.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }

    private boolean aktualizujAktivitu(String id){
        HashMap<String, String> aktivitaHM = new HashMap<String, String>();

        String name = nazov.getText().toString().trim();

        if (!name.equals("")){
            aktivitaHM.put("_id", id);
            aktivitaHM.put("nazov", name);
            aktivitaHM.put("farba", String.valueOf(defaultColor));
            aktivitaHM.put("pocet", pocet.getText().toString());
        }else{
            Toast.makeText(UpravitAktivitu.this, "Vyplňte meno, prosím", Toast.LENGTH_SHORT).show();
            return false;
        }

        dm.aktualizujAktivitu(aktivitaHM);
        dm1.aktualizujNazov(oldName, name);
        return true;
    }

    public void openColorPicker(){
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(UpravitAktivitu.this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
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
}