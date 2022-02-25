package app.aktivity.zaznamenavanieaktivit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class UkazZaznam extends AppCompatActivity {

    private TextView nazov, datum, cas, poznamka;
    private Button upravit, zmazat;
    private DataModel dm = new DataModel(this);
    private DataModel1 dm1 = new DataModel1(this);
    private HashMap<String, String> zaznam = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ukaz_zaznam);

        //zmazanie názvu v ActionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        nazov = (TextView)findViewById(R.id.tvUkazNazov);
        datum = (TextView)findViewById(R.id.tvUkazDatum);
        cas = (TextView)findViewById(R.id.tvUkazCas);
        poznamka = (TextView)findViewById(R.id.tvUkazPoznamka);
        zmazat = (Button)findViewById(R.id.btnZmazat);
        upravit = (Button)findViewById(R.id.btnUpravit);

        //intent a získanie id vybraného záznamu
        Intent ii = getIntent();
        final String sZaznamId = ii.getStringExtra("zaznamId");

        zaznam = dm1.dajZaznam(sZaznamId);
        nazov.setText(zaznam.get("nazov"));
        datum.setText(zaznam.get("datum"));
        cas.setText(zaznam.get("cas"));
        poznamka.setText(zaznam.get("poznamka"));
        nazov.setTextColor(Integer.parseInt(dm.dajFarbuPodlaNazvu(zaznam.get("nazov"))));

        zmazat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(UkazZaznam.this)
                        .setMessage("Naozaj chcete zmazať tento záznam?")
                        .setPositiveButton("Áno", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.finishAffinity(UkazZaznam.this);
                                dm1.vymazZaznam(sZaznamId);

                                HashMap<String, String> aktivitaHM = new HashMap<String, String>();
                                int pocet = Integer.parseInt(dm.dajPocet(nazov.getText().toString()));
                                int newPocet = --pocet;
                                aktivitaHM.put("_id", dm.dajIdPodlaNazvu(nazov.getText().toString()));
                                aktivitaHM.put("nazov", nazov.getText().toString());
                                aktivitaHM.put("farba", dm.dajFarbuPodlaNazvu(nazov.getText().toString()));
                                aktivitaHM.put("pocet", String.valueOf(newPocet));
                                dm.aktualizujAktivitu(aktivitaHM);

                                Toast.makeText(UkazZaznam.this, "Záznam bol zmazaný.", Toast.LENGTH_SHORT).show();
                                finish();
                                Intent intent = new Intent(UkazZaznam.this, MainActivity.class);
                                intent.putExtra("id", R.id.nav_kalendar);
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

        upravit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent theIntent = new Intent(UkazZaznam.this, UpravitZaznam.class);
                theIntent.putExtra("zaznamId", sZaznamId);
                theIntent.putExtra("path", "ukazZaznam");
                startActivity(theIntent);
            }
        });
    }

    //metóda po stlačení tlačidla späť
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("id", R.id.nav_kalendar);
        startActivity(intent);
    }

    //metóda po stlačení tlačidla v ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("id", R.id.nav_kalendar);
                startActivity(intent);
                return true;
        }
        return false;
    }
}