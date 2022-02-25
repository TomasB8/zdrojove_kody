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

public class ZaznamyList extends AppCompatActivity {

    TextView heading, id;
    ListView lvZaznamy;

    DataModel1 dm1 = new DataModel1(this);
    ArrayList<HashMap<String, String>> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zaznamy_list);

        //zmazanie názvu v ActionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        lvZaznamy = (ListView)findViewById(R.id.list);
        heading = (TextView)findViewById(R.id.tvZaznamyMeno);

        Intent ii = getIntent();
        String nazov = ii.getStringExtra("nazov");

        heading.setText("Záznamy pre " + nazov + ":");

        ArrayList<HashMap<String, String>> zaznamy = dm1.dajZaznamyPodlaDatumuDESC(nazov);

        for(int i = 0; i<zaznamy.size(); i++) {
            HashMap<String, String> hm = zaznamy.get(i);
            items.add(hm);

            try {
                ZaznamyListAdapter adapter = new ZaznamyListAdapter(ZaznamyList.this, items);
                lvZaznamy.setAdapter(adapter);
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }

            //akcia po kliknutí na položku ListView
            lvZaznamy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    id = (TextView) view.findViewById(R.id.tvZaznamyId);
                    String sZaznamId = id.getText().toString();
                    Intent theIntent = new Intent(ZaznamyList.this, UkazZaznamHome.class);
                    theIntent.putExtra("zaznamId", sZaznamId);
                    startActivity(theIntent);
                }
            });
        }
    }

    //metóda po stlačení tlačidla späť
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //metóda po stlačení tlačidla v ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
        }
        return false;
    }
}