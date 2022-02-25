package app.aktivity.zaznamenavanieaktivit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import app.aktivity.zaznamenavanieaktivit.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class CalendarFragment extends Fragment {

    private CompactCalendarView compactCalendar;
    private String dateClick, formattedDate2;
    private ArrayList<String> dates= new ArrayList<>();
    private ArrayList<Event> events = new ArrayList<>();
    private ArrayList<HashMap<String, String>> zaznamy = new ArrayList<>();
    private long epoch;
    private TextView tvMesiac, tvDatum, tvZaznamId, bezZaznamu;
    private ImageView btnNext, btnPrevious;
    private ListView lvZaznamy;
    private View headerView;
    private SimpleDateFormat dateMonth = new SimpleDateFormat("MM/yyyy", Locale.getDefault());

    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

    DataModel dm;
    DataModel1 dm1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_calendar, container, false);

        //priradenie id do lokálnych premenných
        tvMesiac = (TextView)v.findViewById(R.id.tvMesiac);
        tvDatum = (TextView)v.findViewById(R.id.tvDatum);
        btnNext = (ImageView)v.findViewById(R.id.ivArrowRight);
        btnPrevious = (ImageView)v.findViewById(R.id.ivArrowLeft);
        lvZaznamy = (ListView)v.findViewById(R.id.lvZaznamy);
        bezZaznamu = (TextView)v.findViewById(R.id.tvBezZaznamu);

        dm = new DataModel(getActivity());
        dm1 = new DataModel1(getActivity());

        //prispôsobenie kalendára
        compactCalendar = (CompactCalendarView)v.findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);
        compactCalendar.displayOtherMonthDays(true);
        compactCalendar.shouldDrawIndicatorsBelowSelectedDays(true);

        //dočasné vyplnenie ListView ničím
        lvZaznamy.setAdapter(null);
        headerView = ((LayoutInflater)getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE)).inflate(R.layout.layout_list_header, null, false);

        //akcia po kliknutí na tlačidlo ĎALŠÍ a posunutie o mesiac dopredu
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = toCalendar(compactCalendar.getFirstDayOfCurrentMonth());
                calendar.add(Calendar.MONTH, 1);
                Date date1 = calendar.getTime();

                compactCalendar.setCurrentDate(date1);
                changeMonth();
            }
        });

        //akcia po kliknutí na tlačidlo PREDCHÁDZAJÚCI a posunutie o mesiac dozadu
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = toCalendar(compactCalendar.getFirstDayOfCurrentMonth());
                calendar.add(Calendar.MONTH, -1);
                Date date1 = calendar.getTime();

                compactCalendar.setCurrentDate(date1);
                changeMonth();
            }
        });

        //získanie aktuálneho dátumu kvôli zisteniu, či je pre aktuálny deň nejaký záznam a dátum pod kalendárom
        Date c = Calendar.getInstance().getTime();
        String formattedDate = dateMonth.format(c);
        String formatDate = dateFormatMonth.format(c);

        //preformátovanie dátumu
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        formattedDate2 = df.format(c);

        //zadanie textu v TextView pre mesiac kalendára a dátum pod kalendárom
        tvMesiac.setText(String.valueOf(formattedDate));
        tvDatum.setText(formatDate);

        //listener pre kalendár a akcia po kliknutí na nejaký dátum
        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

                //preformátovanie vybraného dátumu
                SimpleDateFormat timeStampFormat = new SimpleDateFormat("dd.MM.yyyy");
                String DateStr = timeStampFormat.format(dateClicked);
                dateClick = DateStr;

                //prepísanie TextView na vybraný dátum
                tvDatum.setText(dateClick);

                //ak sa v poli dates vybraný dátum nenachádza vypíše sa text
                if(!dates.contains(dateClick)){
                    bezZaznamu.setText("Tento deň nemáte žiadny záznam");
                    lvZaznamy.removeHeaderView(headerView);
                }else{
                    //ak sa nachádza, text sa zmaže
                    bezZaznamu.setText("");
                    if(lvZaznamy.getHeaderViewsCount() < 1) {
                        lvZaznamy.addHeaderView(headerView, null, false);
                    }
                }

                zaznamy.clear();
                lvZaznamy.setAdapter(null);

                //prechádzanie poľa dates
                for (int i = 0; i < dates.size(); i++) {

                    //ak sa vybraný dátum v poli nachádza, volá sa metóda setListView()
                    if (DateStr.equals(dates.get(i))) {
                        //volanie metódy pre vyplnenie ListView
                        setListView();
                    }
                }
//                setListView();
            }

            //po scrollovaní sa zmení mesiac
            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                tvMesiac.setText(String.valueOf(dateMonth.format(firstDayOfNewMonth)));
            }
        });

        dajEventy();
        //volanie metódy pre dnešný dátum
        todayData();

        return v;
    }

    public void dajEventy(){
        ArrayList<String> nazvy = dm.dajNazvy();
        ArrayList<String> farby = dm.dajFarby();
//        ArrayList<HashMap<String, String>> datumy= new ArrayList<>();
        ArrayList<String> datumy = new ArrayList<>();
        for(int i=0; i<nazvy.size(); i++){
            //DataModel1 dm1 = new DataModel1(this);
            String farba = farby.get(i);
            datumy = dm1.dajZaznamyPodlaNazvu(nazvy.get(i));
//            Toast.makeText(CalendarActivity.this, datumy.get(0).get("datum"), Toast.LENGTH_SHORT).show();
            for(int j=0; j<datumy.size(); j++){
//                HashMap<String, String> hm = datumy.get(j);
//                String datum = hm.get("datum");
//                String nazov = hm.get("nazov");
//                Toast.makeText(CalendarActivity.this, nazov, Toast.LENGTH_SHORT).show();
//                Toast.makeText(CalendarActivity.this, nazvy.get(i).trim(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(CalendarActivity.this, nazvy.get(i), Toast.LENGTH_SHORT).show();
                //preformátovanie dátumu do epoch, aby to kalendár vedel prečítať
//                if(nazov.equals(nazvy.get(i).trim())){

                //ak pole dates ešte neobsahuje daný dátum, vloží sa tam
                if(!dates.contains(datumy.get(j))) {
                    dates.add(datumy.get(j));
                }

                    Date dat = null;
                    try {
                        dat = dateFormatMonth.parse(datumy.get(j));
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
//                Toast.makeText(CalendarActivity.this, datumy.get(j), Toast.LENGTH_SHORT).show();
                    epoch = dat.getTime();
                    Event ev = new Event(Integer.parseInt(farba), epoch, nazvy.get(i));
                    events.add(ev);
//                }
            }
        }
        compactCalendar.addEvents(events);
        //defaultné načítanie textu alebo záznamu pre aktuálny deň
        if(dates.contains(formattedDate2)){
            bezZaznamu.setText("");
            lvZaznamy.addHeaderView(headerView, null, false);
        }else{
            bezZaznamu.setText("Tento deň nemáte žiadny záznam");
            lvZaznamy.removeHeaderView(headerView);
        }
    }

    //metóda, ktorá nastavuje ListView, aby ukazoval záznamy s vybraným dátumom
    public void setListView() {
        setQuery(dateClick);

//        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
//
//                if (queryDocumentSnapshots != null) {
//                    for (DocumentSnapshot ds : queryDocumentSnapshots.getDocuments()) {
//                        KupanieData date = ds.toObject(KupanieData.class);
//                        zaznamy.add(date);
//                    }
//
//                    try {
//                        ZaznamList adapter = new ZaznamList(getActivity(), zaznamy);
//                        lvZaznamy.setAdapter(adapter);
//                    }catch(NullPointerException ex){
//                        ex.printStackTrace();
//                    }
//
//                    //akcia po kliknutí na položku ListView
//                    lvZaznamy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                            tvZaznamId = (TextView) view.findViewById(R.id.zaznamId);
//                            String sZaznamId = tvZaznamId.getText().toString();
//                            Intent theIntent = new Intent(getContext(), UkazZaznam.class);
//                            theIntent.putExtra("zaznamId", sZaznamId);
//                            startActivity(theIntent);
//                        }
//                    });
//                }
//            }
//        });

    }

    private void setQuery(String datum){
        ArrayList<HashMap<String, String>> date = dm1.dajDatumyPodlaDatumu(datum);

        for(int i = 0; i<date.size(); i++) {
            HashMap<String, String> hm = date.get(i);
            zaznamy.add(hm);

            try {
                ZaznamList adapter = new ZaznamList(getActivity(), zaznamy);
                lvZaznamy.setAdapter(adapter);
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }

            //akcia po kliknutí na položku ListView
            lvZaznamy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    tvZaznamId = (TextView) view.findViewById(R.id.tvUkazId);
                    String sZaznamId = tvZaznamId.getText().toString();
                    Intent theIntent = new Intent(getActivity(), UkazZaznam.class);
                    theIntent.putExtra("zaznamId", sZaznamId);
                    startActivity(theIntent);
                }
            });
        }
    }

    //metóda, ktorá zisťuje, či sa v databáze nachádzajú záznamy s aktuálnym dátumom
    private void todayData() {
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String formattedDate1 = df.format(c);

        setQuery(formattedDate1);
    }

    //metóda, ktorá mení typ Date na Calendar, ktorý vracia
    public static Calendar toCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    //metóda, ktorá mení mesiac v hlavičke kalendára
    private void changeMonth(){
        tvMesiac.setText(String.valueOf(dateMonth.format(compactCalendar.getFirstDayOfCurrentMonth())));
    }
}