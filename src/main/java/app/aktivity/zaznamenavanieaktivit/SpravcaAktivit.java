package app.aktivity.zaznamenavanieaktivit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SpravcaAktivit extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    List<PolozkaAktivita> polozkyZoznamu;
    private TextView aktivitaId, bezZaznamu;

    DataModel dm = new DataModel(this);
    DataModel1 dm1 = new DataModel1(this);
    ArrayList<HashMap<String, String>> aktivity = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spravca_aktivit);

        //zmazanie názvu v ActionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        bezZaznamu = (TextView)findViewById(R.id.tvSpravcaBezZaznamu);

        polozkyZoznamu = new ArrayList<>();
        aktivity = dm.dajZaznamyOrdered();
        if(aktivity.size() != 0){
            for(int i=0; i<aktivity.size(); i++){
                HashMap<String, String> hm = aktivity.get(i);
                PolozkaAktivita item = new PolozkaAktivita(hm.get("_id"), hm.get("nazov"), String.valueOf(dm1.dajZaznamyCount(hm.get("nazov"))), hm.get("farba"));
                polozkyZoznamu.add(item);
            }
        }else{
            bezZaznamu.setText("Nemáte žiadnu aktivitu!!!");
        }


        listView = (ListView)findViewById(R.id.listView);
        MojBaseAdapter adapter = new MojBaseAdapter(this, polozkyZoznamu);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);
    }

    //metóda po stlačení tlačidla späť
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("id", R.id.nav_pridat_aktivitu);
        startActivity(intent);
    }

    //metóda po stlačení tlačidla v ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("id", R.id.nav_pridat_aktivitu);
                startActivity(intent);
                return true;
        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        aktivitaId = (TextView) view.findViewById(R.id.tvAktivitaId);
        String sAktivitaId = aktivitaId.getText().toString().trim();
        Intent theIntent = new Intent(SpravcaAktivit.this, UpravitAktivitu.class);
        theIntent.putExtra("aktivitaId", sAktivitaId);
        startActivity(theIntent);
    }
}