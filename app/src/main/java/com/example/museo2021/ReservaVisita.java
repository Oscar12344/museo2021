package com.example.museo2021;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.museo2021.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.museo2021.entidades.Opciones;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReservaVisita extends AppCompatActivity  {
    Spinner spDias, spHoras;
    String op1dia, op2dia, op3dia, op4dia,op5dia,op6dia, op1hora,op2hora;
    String diamuseo, horamuseo;
    TextView tvprueba;
    Button btnenviar;
    EditText edtDato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_visita);
        spDias=findViewById(R.id.spDia);
        btnenviar=findViewById(R.id.btnEnviar);
        edtDato=findViewById(R.id.edDato);
        spHoras=findViewById(R.id.spHorario);
        tvprueba=findViewById(R.id.tvprueba);
        spDias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                diamuseo=spDias.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spHoras.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                horamuseo=spHoras.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llamarWebServiceReserva();
            }
        });

        llamarWebServiceOpciones();

    }

    private void llamarWebServiceReserva() {
        RequestQueue requestReserva;
        JsonObjectRequest jsonObjectRequestReserva;
        ProgressDialog progresoRes;

        requestReserva = Volley.newRequestQueue(this);
        progresoRes = new ProgressDialog(this);
        progresoRes.setMessage("Consultando reserva...");
        progresoRes.show();
        String urlReserva="http://192.168.1.195:80/museo/registroReserva.php?nombre="+edtDato.getText().toString()+"&dia="
                +diamuseo+"&hora="
                +horamuseo;
        urlReserva=urlReserva.replace(" ","%20");
        jsonObjectRequestReserva = new JsonObjectRequest(Request.Method.GET, urlReserva, null, new Response.Listener<JSONObject>()
        {

            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_SHORT).show();
                progresoRes.hide();
                edtDato.setText("");


                spDias.setSelection(0);
                spHoras.setSelection(0);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progresoRes.hide();
                Toast.makeText(getApplicationContext(), "No se pudo registrar "+error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("Error", error.toString());
            }
        });
        requestReserva.add(jsonObjectRequestReserva);

    }

    private void llamarWebServiceOpciones() {
        RequestQueue requestOpcion;
        JsonObjectRequest jsonObjectRequestOpcion;
        ProgressDialog progresoOp;

        String urlOpcion = "http://192.168.1.195:80/museo/consultarOpciones.php?id="+1;

        requestOpcion = Volley.newRequestQueue(this);
        progresoOp = new ProgressDialog(this);
        progresoOp.setMessage("Consultando secciones...");
        progresoOp.show();
        jsonObjectRequestOpcion = new JsonObjectRequest(Request.Method.GET, urlOpcion, null, new Response.Listener<JSONObject>()
        {

            @Override
            public void onResponse(JSONObject response) {
                progresoOp.hide();
                Opciones miopcion = new Opciones();
                JSONArray json=response.optJSONArray("idopciones");
                JSONObject jsonObject=null;
                try {
                    jsonObject=json.getJSONObject(0);
                    miopcion.setOp1Dia(jsonObject.optString("op1Dia"));
                    miopcion.setOp2Dia(jsonObject.optString("op2Dia"));
                    miopcion.setOp3Dia(jsonObject.optString("op3Dia"));
                    miopcion.setOp4Dia(jsonObject.optString("op4Dia"));
                    miopcion.setOp5Dia(jsonObject.optString("op5Dia"));
                    miopcion.setOp6Dia(jsonObject.optString("op6Dia"));
                    miopcion.setOp1Hora(jsonObject.optString("op1Hora"));
                    miopcion.setOp2Hora(jsonObject.optString("op2Hora"));


                } catch ( JSONException e) {
                    e.printStackTrace();
                }


                op1dia=miopcion.getOp1Dia().toString();
                op2dia=miopcion.getOp2Dia().toString();
                op3dia=miopcion.getOp3Dia().toString();
                op4dia=miopcion.getOp4Dia().toString();
                op5dia=miopcion.getOp5Dia().toString();
                op6dia=miopcion.getOp6Dia().toString();
                op1hora=miopcion.getOp1Hora().toString();
                op2hora=miopcion.getOp2Hora().toString();





                String [] dias = {"Elija una opción",op1dia,op2dia,op3dia,op4dia,op5dia,op6dia};
                String [] horas = {"Elija una opción",op1hora,op2hora};
                ArrayAdapter<String> adapter1= new
                        ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_spinner_item,dias);
                spDias.setAdapter(adapter1);
                ArrayAdapter<String> adapter2= new
                        ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_spinner_item,horas);
                spHoras.setAdapter(adapter2);


            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progresoOp.hide();
                Toast.makeText(getApplicationContext(), "No se pudo consultar las opciones "+error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("Error", error.toString());
            }
        });
        requestOpcion.add(jsonObjectRequestOpcion);
    }
}