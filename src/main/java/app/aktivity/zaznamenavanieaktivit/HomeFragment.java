package app.aktivity.zaznamenavanieaktivit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment {

    private TextView bezAktivity;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    DataModel dm;
    DataModel1 dm1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView)v.findViewById(R.id.recyclerView);
        bezAktivity = (TextView)v.findViewById(R.id.tvHomeBezAktivity);

        dm = new DataModel(getActivity());
        dm1 = new DataModel1(getActivity());

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<HashMap<String, String>> aktivity = dm.dajZaznamyOrdered();
        ArrayList<String> pocet = new ArrayList<>();

        if(aktivity.size() != 0){
            for(int i=0; i<aktivity.size(); i++){
                HashMap<String, String> hm = aktivity.get(i);
                pocet.add(String.valueOf(hm.get("pocet")));
                adapter = new RecyclerAdapter(aktivity, pocet);
                recyclerView.setAdapter(adapter);
            }
        }else{
            bezAktivity.setText("Nemáte žiadnu aktivitu!!!");
        }

        return v;
    }
}