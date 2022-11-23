package com.example.lpamvil.sprint0laura.Fragments;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lpamvil.sprint0laura.Logica;
import com.example.lpamvil.sprint0laura.R;
import com.example.lpamvil.sprint0laura.databinding.FragmentEdditarBinding;

import java.util.Objects;


public class EdditarFragment extends Fragment {




    //----------------------------------------EDITARRRRRRR---------------------
    EditText editnombre, editmail,edittelefono; //como = findViewById(R.id.correoInfo);
    String usuario;
    SharedPreferences preferencias;
    SharedPreferences.Editor editorpreferencias;
    //----------------------------------------EDITARRRRRRR---------------------


    private static final int RESULT_OK = 0;
    private FragmentEdditarBinding binding;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //----------------------------------------EDITARRRRRRR---------------------

        editnombre = binding.editnombre;
        editmail = binding.editmail;
        edittelefono = binding.edittelefono;
        //fotoUser = root.findViewById(R.id.fotoUser);


        binding = FragmentEdditarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        //----------------------------------------EDITARRRRRRR---------------------

        //Mostrar en los TextView los valores actuales de datos del usuario
        usuario = preferencias.getString("usuario","prueba");
        editnombre.setText(preferencias.getString("nombre","prueba"));
        editmail.setText(preferencias.getString("mail","prueba"));
        edittelefono.setText(preferencias.getString("telefono","prueba"));






        Logica logica = new Logica();
        logica.editPerfil(usuario,editnombre.getText().toString(),editmail.getText().toString(),edittelefono.getText().toString());

        //Guardamos las preferencias con los nuevos cambios y aplicamos.
        editorpreferencias.putString("nombre",editnombre.getText().toString());
        editorpreferencias.putString("mail",editmail.getText().toString());
        editorpreferencias.putString("telefono",edittelefono.getText().toString());
        editorpreferencias.putString("usuario",usuario);
        editorpreferencias.apply();


        //--------------------------------------------------------------------------------------
        return root;
    }




}

/*package com.example.proyectoiot.ui.gallery;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyectoiot.AplicacionActivity;
import com.example.proyectoiot.IniciarSesionActivity;
import com.example.proyectoiot.R;
import com.example.proyectoiot.UploadProfilePicActivity;
import com.example.proyectoiot.databinding.FragmentGalleryBinding;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class GalleryFragment extends Fragment {


    //-----------------------------------------------------------------------------------------------
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //-------------------------------------------------------------------------------------------
        //-------------------------------------------------------------------------------------------

        //-------------------------------------------------------------------------------------------
        //-------------------------------------------------------------------------------------------

        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        final TextView textView = binding.textGallery;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        //-----------------------------------------------------------------------------------------------
        nombreInfo = binding.nombreInfo;
        correoInfo = binding.correoInfo;
        fotoUser = root.findViewById(R.id.fotoUser);
        //-----------------------------------------------------------------------------------------------
        Uri uri = firebaseAuth.getCurrentUser().getPhotoUrl();
        Picasso.with(fotoUser.getContext()).load(uri).into(fotoUser);

        //-----------------------------------------------------------------------------------------------
        //-----------------------------------------------------------------------------------------------
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String correo =  Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail();
        //-----------------------------------------------------------------------------------------------
        //meter el nombre en info
        db.collection("users").document(firebaseAuth.getCurrentUser().getUid()).get()
                .addOnCompleteListener(
                        new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task){
                                if (task.isSuccessful()) {
                                    nombreInfo.setText(task.getResult().getString("nombre"));

                                    Log.e("Firestore", task.getResult().getString("nombre"), task.getException());
                                } else {
                                    Log.e("Firestore", "Error al leer", task.getException());
                                }
                            }
                        });


        //meter el correo en info
        correoInfo.setText(correo.toString());

        //--------------------------------------------------------------------------------------
        return root;
    }
//----------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}*/