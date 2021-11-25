package com.example.museo2021.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.museo2021.R;
import com.example.museo2021.ReservaVisita;

public class HomeFragment extends Fragment {
    private ImageView ivIniciar, ivDetener, ivContinuar, ivPausar;
    private VideoView vvVideo;
    int posicion=0;
    Button btnReserva, btnUbicaccion;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View vista = inflater.inflate(R.layout.fragment_home, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
        /*homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        ivIniciar=vista.findViewById(R.id.ivIniciar);
        ivDetener=vista.findViewById(R.id.ivDetener);
        ivPausar=vista.findViewById(R.id.ivPausar);
        ivContinuar=vista.findViewById(R.id.ivContinuar);
        vvVideo=vista.findViewById(R.id.vvVideo);
        btnReserva=vista.findViewById(R.id.btnReserva);
        btnUbicaccion=vista.findViewById(R.id.btnUbicacion);
        btnReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ReservaVisita.class);
                startActivity(i);
            }
        });

        ivIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.introduccion;
                vvVideo.setVideoURI(Uri.parse(path));
                vvVideo.seekTo(0); vvVideo.start();
            }
        });
        ivPausar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posicion=vvVideo.getCurrentPosition();
                vvVideo.pause();
            }
        });
        ivContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vvVideo.seekTo(posicion);
                vvVideo.start();
            }
        });
        ivDetener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vvVideo.suspend();
            }
        });





        return vista;
    }
}