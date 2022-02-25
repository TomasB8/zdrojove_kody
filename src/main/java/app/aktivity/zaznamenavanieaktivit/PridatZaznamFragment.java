package app.aktivity.zaznamenavanieaktivit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class PridatZaznamFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private TextView nazov;
    private EditText datum, cas, poznamka;
    private Button vybrat, pridat;
    private ImageView datePicker, timePicker;

    private DataModel1 dm1;
    private DataModel dm;

    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_pridat_zaznam, container, false);

        nazov = (TextView)v.findViewById(R.id.tvAktivita);
        datum = (EditText)v.findViewById(R.id.etDatum);
        cas = (EditText)v.findViewById(R.id.etCas);
        poznamka = (EditText)v.findViewById(R.id.etPoznamka);
        vybrat = (Button)v.findViewById(R.id.btnVyberAktivity);
        pridat = (Button)v.findViewById(R.id.btnPridatZaznam);
        datePicker = (ImageView)v.findViewById(R.id.ivDatePicker);
        timePicker = (ImageView)v.findViewById(R.id.ivTimePicker);

        dm1 = new DataModel1(getActivity());
        dm = new DataModel(getActivity());

        Intent ii = getActivity().getIntent();
        if(ii.hasExtra("aktivita")){
            String nazovAkt = ii.getStringExtra("aktivita");
            nazov.setText(nazovAkt);
        }

        //získanie aktuálneho dátumu a jeho formátovanie
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        SimpleDateFormat tf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String formattedTime = tf.format(c);

        //aktuálny čas a dátum do TextView
        datum.setText(formattedDate);
        cas.setText(formattedTime);

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

        vybrat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dm.dajZaznamy().size() != 0){
                    Intent intent = new Intent(getActivity(), VybratAktivitu.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), "Nemáte žiadnu aktivitu!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        pridat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pridajZaznam()){
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("id", R.id.nav_kalendar);
                    startActivity(intent);
                }
            }
        });

        return v;
    }

    public boolean pridajZaznam(){
        HashMap<String, String> zaznamHM = new HashMap<String, String>();
        HashMap<String, String> aktivitaHM = new HashMap<String, String>();

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
            Toast.makeText(getActivity(), "Vyplňte názov, dátum a čas", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            zaznamHM.put("nazov", name);
            zaznamHM.put("datum", date);
            zaznamHM.put("cas", time);
            zaznamHM.put("poznamka", note);
            zaznamHM.put("epochDatum", String.valueOf(epoch));

            HashMap<String, String> aktivita = dm.dajZaznamyPodlaNazvu(name);
            int pocet = Integer.parseInt(aktivita.get("pocet"));
            int newPocet = ++pocet;
            aktivitaHM.put("_id", dm.dajIdPodlaNazvu(aktivita.get("nazov")));
            aktivitaHM.put("nazov", aktivita.get("nazov"));
            aktivitaHM.put("farba", dm.dajFarbuPodlaNazvu(aktivita.get("nazov")));
            aktivitaHM.put("pocet", String.valueOf(newPocet));
        }
        dm1.vlozZaznam(zaznamHM);
        dm.aktualizujAktivitu(aktivitaHM);
        return true;
    }

    //po stlačení ImageView kalendár sa otvorí dialógové okno
    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(), this, Calendar.getInstance().get(Calendar.YEAR),
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
                getActivity(), this, Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE), true
        );
        timePickerDialog.show();
    }

    //nastavenie aktuálneho času pre EditText
    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        cas.setText(String.format("%02d:%02d", hour, minute));
    }
}