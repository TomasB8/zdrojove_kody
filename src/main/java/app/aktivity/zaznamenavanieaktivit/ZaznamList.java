package app.aktivity.zaznamenavanieaktivit;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.List;

//trieda, ktorá je potomkom ArrayAdapter
public class ZaznamList extends ArrayAdapter<HashMap<String, String>> {

    private Activity context;
    private List<HashMap<String, String>> zaznamyList;

    //parametrický konštruktor
    public ZaznamList(Activity context, List<HashMap<String, String>> zaznamyList){
        super(context, R.layout.polozka_zaznam, zaznamyList);
        this.context = context;
        this.zaznamyList = zaznamyList;
    }

    //vytváranie položiek do ListView
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.polozka_zaznam, null, true);

        //priradenie id
        TextView tvCas = (TextView)listViewItem.findViewById(R.id.zaznamCas);
        TextView tvNazov = (TextView)listViewItem.findViewById(R.id.zaznamNazov);
        TextView tvId = (TextView)listViewItem.findViewById(R.id.tvUkazId);
        Button farba = (Button)listViewItem.findViewById(R.id.btnListFarba);
        DataModel dm = new DataModel(getContext());

        HashMap<String, String> zaznamHM = zaznamyList.get(position);

        tvId.setText(String.valueOf(zaznamHM.get("_id")));
        tvCas.setText(zaznamHM.get("cas"));
        tvNazov.setText(zaznamHM.get("nazov"));
        farba.setBackgroundColor(Integer.parseInt(dm.dajFarbuPodlaNazvu(zaznamHM.get("nazov"))));
        //defaultné zadanie ak sú prázdne
//        if(!String.valueOf(kupanieData.getMinuty()).equals("0") || !String.valueOf(kupanieData.getSekundy()).equals("0")) {
//            tvCas.setText(String.format("%02d", kupanieData.getMinuty()) + ":" + String.format("%02d", kupanieData.getSekundy()));
//        }else{
//            tvCas.setText("00:00");
//        }

//        if(!String.valueOf(kupanieData.getVoda()).equals("NaN")) {
//            tvVoda.setText(String.valueOf(kupanieData.getVoda()).replace(".",",") + "°C");
//        }else{
//            tvVoda.setText("0 °C");
//        }

        return listViewItem;
    }
}
