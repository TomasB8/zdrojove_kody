package app.aktivity.zaznamenavanieaktivit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class VybratAktivitu extends AppCompatActivity {

    private RadioGroup rg;
    RadioButton rb;
    private Button vybrat;
    private TextView bezAktivity;

    DataModel dm = new DataModel(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vybrat_aktivitu);

        //zmazanie názvu v ActionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        rg = (RadioGroup)findViewById(R.id.rgNazvy);
        vybrat = (Button)findViewById(R.id.btnVybrate);
        bezAktivity = (TextView)findViewById(R.id.tvVybratBezAktivity);

        ArrayList<String> nazvy = dm.dajNazvyOrdered();

        if(nazvy.size() != 0){
            createRadioButton(nazvy);
        }else{
            bezAktivity.setText("Nemáte žiadnu aktivitu!!!");
            vybrat.setVisibility(View.GONE);
        }

        vybrat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkButton(view);
            }
        });
    }

    public void checkButton(View v){
        int radioId = rg.getCheckedRadioButtonId();
        rb = findViewById(radioId);
        Intent intent = new Intent(VybratAktivitu.this, MainActivity.class);
        intent.putExtra("id", R.id.nav_pridat_zaznam);
        intent.putExtra("aktivita", rb.getText());
        startActivity(intent);
    }

    private void createRadioButton(ArrayList<String> nazvy) {
        int pocetAktivit = dm.dajZazanamyCount();
        final RadioButton[] rb = new RadioButton[pocetAktivit];
        for(int i=0; i<pocetAktivit; i++){
            rb[i]  = new RadioButton(this);
            rb[i].setText(" " + nazvy.get(i));
            rb[i].setId(i + 100);
            rb[i].setTextSize(20);
            rg.addView(rb[i]);
        }
    }

    //metóda po stlačení tlačidla späť
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("id", R.id.nav_pridat_zaznam);
        startActivity(intent);
    }

    //metóda po stlačení tlačidla v ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("id", R.id.nav_pridat_zaznam);
                startActivity(intent);
                return true;
        }
        return false;
    }
}