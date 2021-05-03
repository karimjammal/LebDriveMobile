package com.karim.lebdrive;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsFragment extends Fragment {

    private GoogleMap mMap;
    ArrayList<LatLng> latLngs = new ArrayList<>();
    String [] strings = {"Driving Test Center - Tripoli", "Driving Test Center - Jounieh", "Driving Test Center - Saida"};
    LatLng tripoli = new LatLng(34.433107, 35.824561);
    LatLng jounieh = new LatLng(33.984319, 35.637066);
    LatLng saida = new LatLng(33.572639256142544, 35.389029357874676);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        latLngs.add(tripoli);
        latLngs.add(jounieh);
        latLngs.add(saida);

        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                mMap = googleMap;

                for(int i =0; i < latLngs.size(); i++){
                    mMap.addMarker(new MarkerOptions().position(latLngs.get(i)).
                            title(strings[i]));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLngs.get(i)));
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}