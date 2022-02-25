package app.aktivity.zaznamenavanieaktivit;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.List;

public class ZaznamyListAdapter extends ArrayAdapter<HashMap<String, String>> {

    private Activity context;
    private List<HashMap<String, String>> zaznamyList;

    //parametrický konštruktor
    public ZaznamyListAdapter(Activity context, List<HashMap<String, String>> zaznamyList){
        super(context, R.layout.polozka_zaznam_list, zaznamyList);
        this.context = context;
        this.zaznamyList = zaznamyList;
    }

    //vytváranie položiek do ListView
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.polozka_zaznam_list, null, true);

        //priradenie id
        TextView poradie = (TextView) listViewItem.findViewById(R.id.tvZaznamyPoradie);
        TextView datum = (TextView) listViewItem.findViewById(R.id.tvZaznamyDatum);
        TextView cas = (TextView) listViewItem.findViewById(R.id.tvZaznamyCas);
        TextView id = (TextView) listViewItem.findViewById(R.id.tvZaznamyId);
        DataModel1 dm1 = new DataModel1(getContext());

        HashMap<String, String> zaznamHM = zaznamyList.get(position);

        id.setText(String.valueOf(zaznamHM.get("_id")));
        datum.setText(zaznamHM.get("datum"));
        cas.setText(zaznamHM.get("cas"));
        poradie.setText(String.valueOf(zaznamyList.size() - position));

        return listViewItem;
    }
}
