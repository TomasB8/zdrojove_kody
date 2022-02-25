package app.aktivity.zaznamenavanieaktivit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class UpravitZaznam extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private TextView nazov;
    private EditText datum, cas, poznamka;
    private Button ulozit;
    private ImageView datePicker, timePicker;

    private DataModel1 dm1 = new DataModel1(this);
    private DataModel dm = new DataModel(this);
    private HashMap<String, String> zaznam = new HashMap<>();

    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

    private String sZaznamId, path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upravit_zaznam);

        //zmazanie názvu v ActionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        nazov = (TextView)findViewById(R.id.tvUpravitNazov);
        datum = (EditText) findViewById(R.id.etUpravitDatum);
        cas = (EditText) findViewById(R.id.etUpravitCas);
        poznamka = (EditText) findViewById(R.id.etUpravitPoznamka);
        ulozit = (Button)findViewById(R.id.btnUpravitUlozit);
        datePicker = (ImageView)findViewById(R.id.ivEditDatePicker);
        timePicker = (ImageView)findViewById(R.id.ivEditTimePicker);

        //intent a získanie id vybraného záznamu
        Intent ii = getIntent();
        sZaznamId = ii.getStringExtra("zaznamId");
        path = ii.getStringExtra("path");

        zaznam = dm1.dajZaznam(sZaznamId);
        nazov.setText(zaznam.get("nazov"));
        datum.setText(zaznam.get("datum"));
        cas.setText(zaznam.get("cas"));
        poznamka.setText(zaznam.get("poznamka"));
        nazov.setTextColor(Integer.parseInt(dm.dajFarbuPodlaNazvu(zaznam.get("nazov"))));

        //akcia po kliknutí na ImageView kalendár a zobrazenie okna pre vybratie dátumu
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        //akcia po kliknutí na ImageView čas a zobrazenie okna pre vybratie času
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();
            }
        });

        ulozit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aktualizujZaznam(sZaznamId);
                Class go;
                switch(path){
                    case "ukazZaznam":
                        go = UkazZaznam.class;
                        break;
                    case "ukazZaznamHome":
                        go = UkazZaznamHome.class;
                        break;
                    default:
                        go = UkazZaznam.class;
                }
                Intent theIntent = new Intent(UpravitZaznam.this, go);
                theIntent.putExtra("zaznamId", sZaznamId);
                startActivity(theIntent);
            }
        });
    }

    public void aktualizujZaznam(String id){
        HashMap<String, String> zaznamHM = new HashMap<String, String>();

        String name = nazov.getText().toString().trim();
        String date = datum.getText().toString();
        String time = cas.getText().toString();
        String note = poznamka.getText().toString();

        Date dat = null;
        long epoch;
        try {
            dat = dateFormatMonth.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        epoch = dat.getTime();

        if(name.equals("") || date.equals("") || time.equals("")){
            Toast.makeText(UpravitZaznam.this, "Vyplňte názov, dátum a čas", Toast.LENGTH_SHORT).show();
        }else{
            zaznamHM.put("_id", id);
            zaznamHM.put("nazov", name);
            zaznamHM.put("datum", date);
            zaznamHM.put("cas", time);
            zaznamHM.put("poznamka", note);
            zaznamHM.put("epochDatum", String.valueOf(epoch));
        }
        dm1.aktualizujZaznam(zaznamHM);
    }

    //po stlačení ImageView kalendár sa otvorí dialógové okno
    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                UpravitZaznam.this, this, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    //nastavenie aktuálneho dátumu pre EditText
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, dayOfMonth);
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String cdate = formatter.format(c.getTime());
        datum.setText(cdate);
    }

    //po stlačení ImageView čas sa otvorí dialógové okno
    private void showTimePickerDialog(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                UpravitZaznam.this, this, Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE), true
        );
        timePickerDialog.show();
    }

    //nastavenie aktuálneho času pre EditText
    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        cas.setText(String.format("%02d:%02d", hour, minute));
    }

    //metóda po stlačení tlačidla späť
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Class go;
        switch(path){
            case "ukazZaznam":
                go = UkazZaznam.class;
                break;
            case "ukazZaznamHome":
                go = UkazZaznamHome.class;
                break;
            default:
                go = UkazZaznam.class;
        }
        Intent theIntent = new Intent(UpravitZaznam.this, go);
        theIntent.putExtra("zaznamId", sZaznamId);
        startActivity(theIntent);
    }

    //metóda po stlačení tlačidla v ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                Class go;
                switch(path){
                    case "ukazZaznam":
                        go = UkazZaznam.class;
                        break;
                    case "ukazZaznamHome":
                        go = UkazZaznamHome.class;
                        break;
                    default:
                        go = UkazZaznam.class;
                }
                Intent theIntent = new Intent(UpravitZaznam.this, go);
                theIntent.putExtra("zaznamId", sZaznamId);
                startActivity(theIntent);
                return true;
        }
        return false;
    }
}