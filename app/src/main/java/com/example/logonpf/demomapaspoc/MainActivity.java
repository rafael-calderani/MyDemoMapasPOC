package com.example.logonpf.demomapaspoc;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.logonpf.demomapaspoc.adapter.MetroAdapter;
import com.example.logonpf.demomapaspoc.adapter.OnItemClickListener;
import com.example.logonpf.demomapaspoc.api.ApiUtils;
import com.example.logonpf.demomapaspoc.api.MetroAPI;
import com.example.logonpf.demomapaspoc.model.Estacao;
import com.example.logonpf.demomapaspoc.model.Metro;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvLinhas;
    private MetroAdapter mAdapter;
    private MetroAPI mService;
    private static final int GPS_PERMISSION = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvLinhas = (RecyclerView) findViewById(R.id.rvLinhas);

        mService = ApiUtils.getMetroAPI();

        mAdapter = new MetroAdapter(new ArrayList<Metro>(),
                new OnItemClickListener() {
            @Override
            public void onItemClick(Metro metro) {
                if(ContextCompat.checkSelfPermission(MainActivity.this,
                        ACCESS_FINE_LOCATION) != PERMISSION_GRANTED) {
                    Toast.makeText(
                            MainActivity.this,
                            R.string.gpsPermissionRequest,
                            Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION},
                            GPS_PERMISSION);

                }
                iniciarMapa(metro);
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvLinhas.setLayoutManager(layoutManager);
        rvLinhas.setAdapter(mAdapter);
        rvLinhas.setHasFixedSize(true);

        loadMetros();
    }

    private void iniciarMapa(Metro metro) {
        Intent telaMapa = new Intent(
                MainActivity.this,
                MapsDemoActivity.class
        );

        telaMapa.putExtra("METRO", metro);

        startActivity(telaMapa);
    }

    public void loadMetros() {
        mService.getLinhas()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Metro>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(), String.format(getString(R.string.mensagemErro), e.getMessage()),
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(List<Metro> metros) {
                        mAdapter.update(metros);
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case GPS_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED) {
                    // Sucesso!
                }
                break;
            }

        }
    }
}
