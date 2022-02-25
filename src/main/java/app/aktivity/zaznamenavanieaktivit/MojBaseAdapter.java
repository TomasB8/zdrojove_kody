package app.aktivity.zaznamenavanieaktivit;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MojBaseAdapter extends BaseAdapter {

    Context context;
    List<PolozkaAktivita> rowItems;

    public MojBaseAdapter(Context context, List<PolozkaAktivita> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    private class ViewHolder{
        Button farba;
        TextView nazov;
        TextView pocet;
        TextView id;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder hld = null;

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_polozka, null);
            hld = new ViewHolder();
            hld.farba = (Button) convertView.findViewById(R.id.btnSpravcaFarba);
            hld.nazov = (TextView)convertView.findViewById(R.id.tvSpravcaNazov);
            hld.pocet = (TextView)convertView.findViewById(R.id.tvSpravcaPocet);
            hld.id = (TextView)convertView.findViewById(R.id.tvAktivitaId);
            convertView.setTag(hld);
        }else{
            hld = (ViewHolder)convertView.getTag();
        }
        PolozkaAktivita polozka = (PolozkaAktivita)getItem(position);
        hld.farba.setBackgroundColor(Integer.parseInt(polozka.getFarba()));
        hld.nazov.setText(polozka.getNazov());
        hld.pocet.setText(polozka.getPocet());
        hld.id.setText(polozka.getId());
        return convertView;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int i) {
        return rowItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return rowItems.indexOf(getItem(i));
    }

    @Override
    public boolean isEnabled(int position)
    {
        return true;
    }
}
