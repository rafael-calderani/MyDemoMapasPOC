package com.example.logonpf.demomapaspoc;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.logonpf.demomapaspoc.adapter.MetroAdapter;
import com.example.logonpf.demomapaspoc.adapter.OnItemClickListener;
import com.example.logonpf.demomapaspoc.api.ApiUtils;
import com.example.logonpf.demomapaspoc.api.MetroAPI;
import com.example.logonpf.demomapaspoc.model.Estacao;
import com.example.logonpf.demomapaspoc.model.Metro;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MapsDemoActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Metro metro;
    private MetroAPI mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_demo);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if (getIntent() != null) {
            metro = getIntent().getParcelableExtra("METRO");
            mService = ApiUtils.getMetroAPI();

            //TODO: enviar latitude e lonitude da linha
            mService.getEstacoes(metro.getCor())
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List<Estacao>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(List<Estacao> estacoes) {
                            for (Estacao e:estacoes) {
                                Double latitude = Double.parseDouble(e.getLatitude());
                                Double longitude = Double.parseDouble(e.getLongitude());
                                // Add a marker in Sydney and move the camera
                                LatLng fiap = new LatLng(latitude, longitude);
                                mMap.addMarker(new MarkerOptions().position(fiap).title(e.getNome()));
                            }
                        }
                    });
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        //LatLng fiap = new LatLng(-23.5641085, -46.6524089);
        //mMap.addMarker(new MarkerOptions().position(fiap).title("FIAP"));

        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(fiap, 17));
    }
}
