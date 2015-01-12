package com.example.pierre.if26davylefevre;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Contacts_Activity extends Activity {

    private String login;
    private String token;
    String[][] tabContact;
    String tabContact2[];
    HashMap<String, String> element, element2;
    List<HashMap<String, String>> liste, liste2;
    private ListAdapter adapter, adapter2;
    private ListView listView, listView2;
    Context myContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        myContext = getApplicationContext();

        Intent intent = getIntent();
        login = intent.getStringExtra("Login");
        token = intent.getStringExtra("token");


        //list view des amis
        listView = (ListView) findViewById(R.id.lVContact);
        liste = new ArrayList<HashMap<String, String>>();
        ThreadContactActivity contactTask = new ThreadContactActivity();
        contactTask.execute(login, token);

        try {
            synchronized (this) {
                //on attend 3 secondes que la tâche asynchrone finisse son travail de récupération des noms
                wait(3000);
            }
        } catch (InterruptedException ex) {
        }

        adapter = new SimpleAdapter(myContext, liste, android.R.layout.simple_list_item_2,
                new String[]{"text1", "text2"}, new int[]{android.R.id.text1, android.R.id.text2});
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + tabContact[position][1] + "," + tabContact[position][2] + "");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                finish();
                startActivity(mapIntent);
            }
        });


        //list view pending
        listView2 = (ListView) findViewById(R.id.lv_pending);
        liste2 = new ArrayList<HashMap<String, String>>();
        ThreadPending pendinglist = new ThreadPending();
        pendinglist.execute(login, "Pending");

        try {
            synchronized (this) {
                //on attend 3 secondes que la tâche asynchrone finisse son travail de récupération des noms
                wait(3000);
            }
        } catch (InterruptedException ex) {
        }

        adapter2 = new SimpleAdapter(myContext, liste2, android.R.layout.simple_list_item_1, new String[]{"text1"}, new int[]{android.R.id.text1});

        listView2.setAdapter(adapter2);
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {
                final String loginContact = tabContact2[position];
                AlertDialog.Builder boite;
                boite = new AlertDialog.Builder(Contacts_Activity.this);
                boite.setTitle("boite de dialogue ");
                boite.setIcon(R.drawable.ic_launcher);
                boite.setMessage("On vous a ajouté comme relation");
                boite.setPositiveButton("Refuser", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                ThreadSetRequest threadSet = new ThreadSetRequest();
                                threadSet.execute(loginContact, login, "Refused");
                                try {
                                    synchronized (this) {
                                        wait(2000);
                                    }
                                } catch (InterruptedException ex) {
                                }
                                Toast.makeText(getApplicationContext(), "Contact refusé", Toast.LENGTH_LONG).show();
                            }
                        }
                );

                boite.setNegativeButton("Accepter", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                ThreadSetRequest threadSet = new ThreadSetRequest();
                                threadSet.execute(loginContact, login, "Accepted");
                                try {
                                    synchronized (this) {
                                        wait(2000);
                                    }
                                } catch (InterruptedException ex) {
                                }
                                Toast.makeText(getApplicationContext(), "Contact ajouté...actualisation", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(getIntent());

                            }
                        }
                );

                boite.show();
            }
        });


        // Go to activité paramètres
        Button btnParam = (Button) findViewById(R.id.btnParam);
        btnParam.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent param_Activity = new Intent(getApplicationContext(), Param_Activity.class);
                param_Activity.putExtra("Login", login);
                finish();
                startActivity(param_Activity);
            }
        });

        Button btnDeco = (Button) findViewById(R.id.btnDeco);
        btnDeco.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent login = new Intent(getApplicationContext(), Login.class);
                finish();
                startActivity(login);
            }
        });

        // Go to activité map
        Button btnMap = (Button) findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent map_Activity = new Intent(getApplicationContext(), Map_Activity.class);
                map_Activity.putExtra("Login", login);
                finish();
                startActivity(map_Activity);
            }
        });

        // Ajouter une relation
        Button BaddRelation = (Button) findViewById(R.id.btn_AddRelation);
        BaddRelation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder boite;
                final EditText input = new EditText(Contacts_Activity.this);
                boite = new AlertDialog.Builder(Contacts_Activity.this);
                boite.setView(input);
                boite.setTitle("boite de dialogue ");
                boite.setIcon(R.drawable.ic_launcher);
                boite.setMessage("Saisissez le nom de la personne à ajouter");
                boite.setPositiveButton("Annuler", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //si on appuie sur annuler
                            }
                        }
                );

                boite.setNegativeButton("Ajouter", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                //Récupération de la valeur renseignée dans la boite du dialogue
                                String value = input.getText().toString();
                                Log.d("value:", value);

                                ThreadAddContactActivity async = new ThreadAddContactActivity();
                                async.execute(String.valueOf(login), String.valueOf(value));
                                try {
                                    synchronized (this) {
                                        //on attend 3 secondes que la tâche asynchrone finisse son travail de récupération des noms
                                        wait(2000);
                                    }
                                } catch (InterruptedException ex) {
                                }

                            }
                        }
                );

                boite.show();
            }
        });
    }

    //thread  pour l'envoi de la requete pour afficher les contacts
    public class ThreadContactActivity extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... params) {

            Uri.Builder uri = new Uri.Builder();
            uri.scheme("http").authority("pierredavy.com").appendPath("getrelations.php").appendQueryParameter("login", params[0]).appendQueryParameter("token", params[1]);
            String url = uri.build().toString();
            String content = null;
            try {
                HttpClient clientHTTP = new DefaultHttpClient();
                HttpResponse responseHTTP = clientHTTP.execute(new HttpGet(url));
                content = EntityUtils.toString(responseHTTP.getEntity(), "utf8");
            } catch (Exception e) {
                Log.e("httpGet ", e.toString(), e);
            }
            try {
                JSONObject contacts = new JSONObject(content);
                JSONArray conv = contacts.getJSONArray("user");
                tabContact = new String[conv.length()][3];
                for (int i = 0; i < conv.length(); i++) {

                    tabContact[i][0] = conv.getJSONObject(i).getString("login").toString();
                    tabContact[i][1] = conv.getJSONObject(i).getString("latitude").toString();
                    tabContact[i][2] = conv.getJSONObject(i).getString("longitude").toString();
                }
                for (int i = 0; i < tabContact.length; i++) {
                    element = new HashMap<String, String>();
                    element.put("text1", tabContact[i][0]);
                    element.put("text2", tabContact[i][1]);
                    liste.add(element);
                }
                Log.d("liste :", liste.toString());
            } catch (Exception e) {
                Log.e("json ", e.toString(), e);
            }
            return content;

        }

    }


    //thread  pour l'envoi de la requete pour ajouter une Requete
    public class ThreadAddContactActivity extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... params) {
            Uri.Builder uri = new Uri.Builder();
            uri.scheme("http").authority("pierredavy.com").appendPath("addRequest.php").appendQueryParameter("login1", params[0]).appendQueryParameter("login2", params[1]);
            String url = uri.build().toString();
            String result = null;
            try {
                HttpClient clientHTTP = new DefaultHttpClient();
                HttpResponse responseHTTP = clientHTTP.execute(new HttpGet(url));
                result = EntityUtils.toString(responseHTTP.getEntity(), "utf8");
            } catch (Exception e) {
                Log.e("httpGet ", e.toString(), e);
            }

            Log.d("Resul", result);
            String resultArray[] = result.split("\"");

            if (resultArray[3].equals("false")) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Contact inexistant", Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Contact ajouté", Toast.LENGTH_LONG).show();
                    }
                });
            }

            return result;

        }
    }


    //thread  pour la recupération des requetes pending
    public class ThreadPending extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... params) {
            Uri.Builder uri = new Uri.Builder();
            uri.scheme("http").authority("pierredavy.com").appendPath("getRequestPending.php").appendQueryParameter("login", params[0]).appendQueryParameter("status", params[1]);
            String url = uri.build().toString();
            String content2 = null;
            try {
                HttpClient clientHTTP = new DefaultHttpClient();
                HttpResponse responseHTTP = clientHTTP.execute(new HttpGet(url));
                content2 = EntityUtils.toString(responseHTTP.getEntity(), "utf8");
            } catch (Exception e) {
                Log.e("httpGet ", e.toString(), e);
            }
            try {
                JSONObject contacts = new JSONObject(content2);
                JSONArray conv = contacts.getJSONArray("users");
                tabContact2 = new String[conv.length()];
                for (int i = 0; i < conv.length(); i++) {

                    tabContact2[i] = conv.getJSONObject(i).getString("login_user_request").toString();
                    Log.d("tabContact2 :", tabContact2[i]);
                }
                for (int i = 0; i < tabContact2.length; i++) {
                    element2 = new HashMap<String, String>();
                    element2.put("text1", tabContact2[i]);
                    liste2.add(element2);
                }


                Log.d("liste2 :", liste2.toString());
            } catch (Exception e) {
                Log.e("json ", e.toString(), e);
            }
            return content2;

        }

    }


    //thread  pour confirmer ou rejeter une requete
    public class ThreadSetRequest extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... params) {
            Uri.Builder uri = new Uri.Builder();
            uri.scheme("http").authority("pierredavy.com").appendPath("setRequestStatus.php").appendQueryParameter("login1", params[0]).appendQueryParameter("login2", params[1]).appendQueryParameter("status", params[2]);
            String url = uri.build().toString();
            String result = null;
            try {
                HttpClient clientHTTP = new DefaultHttpClient();
                HttpResponse responseHTTP = clientHTTP.execute(new HttpGet(url));
                result = EntityUtils.toString(responseHTTP.getEntity(), "utf8");
            } catch (Exception e) {
                Log.e("httpGet ", e.toString(), e);
            }

            return result;

        }
    }

}