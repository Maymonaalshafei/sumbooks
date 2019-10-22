package com.springMay.sumbooks.webServices;

 import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.CheckBox;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.springMay.sumbooks.Models.BooksModel;
import com.springMay.sumbooks.activities.App;
import com.springMay.sumbooks.fragments.Books;
import com.springMay.sumbooks.fragments.Favorite;
import com.springMay.sumbooks.utilities.Config;
import com.springMay.sumbooks.utilities.MySingleTon;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static java.util.Objects.requireNonNull;

public class WS {


    private ArrayList<BooksModel> books = new ArrayList<>();
    private ProgressDialog pd;

     public void search(final Context c, final String filter, final Fragment f) {

        final ProgressDialog pd;
        pd = new ProgressDialog(c);
        pd.setTitle("Loading");
        pd.setMessage("Please wait...");
        pd.show();

       // RequestQueue queue = Volley.newRequestQueue(Objects.requireNonNull(c));

        // in php///
        String url = Config.urlbooks + "getbooks.php";
        //
        StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {


                // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                try {

                    //define the lsist
                    books = new ArrayList<>();

                    //Refresh the list to clear
                    // lvUsers.invalidate();

                    //convert from string to JSONObject
                    JSONObject o = new JSONObject(s);

                    //Always I ask myself 2 questions
                    //Who is this and who is his father

                /*
                    {
                        "result":[
                        {"id":"1","Username":"Jareer","Password":"123"},
                        {"id":"2","Username":"Shaban","Password":"aaa"},
                        {"id":"3","Username":"osama","Password":"125"},
                        {"id":"4","Username":"osama2222","Password":"125"}
	                           ]
                    }
                    */

                    JSONArray arr = o.getJSONArray("result");


                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject x = arr.getJSONObject(i);

                        String bookName = x.getString("bookName");
                        String bookURL = x.getString("bookURL");
                        //Adding data to the new User Class
                        BooksModel u = new BooksModel();
                        u.setBookName(bookName);
                        u.setBookPdf("pdfbooks/" + bookURL);
                        books.add(u);
                        //users.add (name + password);
                    }
                    //This is old uses with List
                    // ArrayAdapter<User> a=new ArrayAdapter<User>(getActivity(),android.R.layout.simple_list_item_1,users);
                    //lvUsers.setAdapter(a);

                    //  BooksRecycleAdapter a=new BooksRecycleAdapter( c, books);
                    //   rec.setAdapter(a);
                    ((Books) f).getData(books);// to call the interface

                    //This is new used With RecyclerAdapter

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pd.dismiss();
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pd.dismiss();
                String errorDescription = "";
                if (volleyError instanceof NetworkError) {
                    errorDescription = "NetworkError";

                } else if (volleyError instanceof ServerError) {
                    errorDescription = "Server Error";
                } else if (volleyError instanceof AuthFailureError) {
                    errorDescription = "AuthFailureError";
                } else if (volleyError instanceof ParseError) {
                    errorDescription = "Parse Error";
                } else if (volleyError instanceof TimeoutError) {
                    errorDescription = "Time Out";
                }
                Toast.makeText(c, errorDescription, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<>();

                param.put("filter", filter);

                return param;
            }
        };
        req.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
       // queue.add(req);
         MySingleTon.getInstance(c).addToRequestQueue(req);

    }
     //
     public void logIn(final Context c, final CheckBox cbRm, final String etName, final String etPassword) {


        pd = new ProgressDialog(c);
        pd.setTitle("Loading");
        pd.setMessage("Please wait...");
        pd.show();

       // RequestQueue queue = Volley.newRequestQueue(c);
        String url = Config.url + "login.php";
        StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();


                //{"result":"1"}

                try {
                    JSONObject o = new JSONObject(s);
                    String data = o.getString("result");
                    if (data.equals("1")) {

                        ///////////////////////////////////////////////////////////////////////




                        // Toast.makeText(getApplicationContext(),"Sign Up Successfully",Toast.LENGTH_LONG).show();}
                        Intent i = new Intent(c, App.class);
                        c.startActivity(i);


                        if (cbRm.isChecked()) {
                            SharedPreferences userSettings = PreferenceManager.getDefaultSharedPreferences(c);
                            SharedPreferences.Editor pen = userSettings.edit();
                            pen.putBoolean("remember me", cbRm.isChecked());
                            pen.putString("uName",etName);
                            pen.putString("uPassword",etPassword);
                            pen.apply();
                            pen.commit();

                        }



                        WS ws = new WS();
                        ws.getUserInfo(c,etName,etPassword);


                    } else {
                        Toast.makeText(c, "Login Failed", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                pd.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pd.dismiss();
                String errorDescription = "";
                if (volleyError instanceof NetworkError) {
                    errorDescription = "Network Error";
                } else if (volleyError instanceof ServerError) {
                    errorDescription = "Server Error";
                } else if (volleyError instanceof AuthFailureError) {
                    errorDescription = "AuthFailureError";
                } else if (volleyError instanceof ParseError) {
                    errorDescription = "Parse Error";
                } else if (volleyError instanceof NoConnectionError) {
                    errorDescription = "No Conenction";
                } else if (volleyError instanceof TimeoutError) {
                    errorDescription = "Time Out";
                } else {
                    errorDescription = "Connection Error";
                }
                Toast.makeText(c, errorDescription, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("uNameAndroid", etName);
                param.put("uPasswordAndroid", etPassword);

                return param;
            }
        };
        req.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //queue.add(req);
         MySingleTon.getInstance(c).addToRequestQueue(req);

    }
     //
     public void signUpToDatabase(final Context c, final String etName, final String etPassword) {


        pd = new ProgressDialog(c);
        pd.setTitle("Loading");
        pd.setMessage("Please wait...");
        pd.show();

      //  RequestQueue queue = Volley.newRequestQueue(c);
        String url = Config.url + "signUp.php";


        StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();


                try {
                    JSONObject o = new JSONObject(s);
                    String data = o.getString("result");
                    if (data.equals("1")) {
                        Toast.makeText(c, "Sign Up Successfully", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(c, App.class);
                        c.startActivity(i);

                        SharedPreferences userSettings = PreferenceManager.getDefaultSharedPreferences(c);
                        SharedPreferences.Editor pen = userSettings.edit();
                        pen.putString("uName",etName);
                        pen.putString("uPassword",etPassword);
                        pen.apply();
                        pen.commit();

                                 WS ws = new WS();
                                 ws.getUserInfo(c,etName,etPassword);

                    } else {
                        Toast.makeText(c, "Failed", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                pd.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pd.dismiss();
                String errorDescription = "";
                if (volleyError instanceof NetworkError) {
                    errorDescription = "Network Error";
                } else if (volleyError instanceof ServerError) {
                    errorDescription = "Server Error";
                } else if (volleyError instanceof AuthFailureError) {
                    errorDescription = "AuthFailureError";
                } else if (volleyError instanceof ParseError) {
                    errorDescription = "Parse Error";
                } else if (volleyError instanceof NoConnectionError) {
                    errorDescription = "No Conenction";
                } else if (volleyError instanceof TimeoutError) {
                    errorDescription = "Time Out";
                } else {
                    errorDescription = "Connection Error";
                }
                Toast.makeText(c, errorDescription, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("Name", requireNonNull(etName));
                param.put("Password", requireNonNull(etPassword));

                return param;
            }
        };
        req.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
       // queue.add(req);
         MySingleTon.getInstance(c).addToRequestQueue(req);
    }
     //
     public void getFav(final Context c, final Fragment f, final String id) {

        final ProgressDialog pd;
        pd = new ProgressDialog(c);
        pd.setTitle("Loading");
        pd.setMessage("Please wait...");
        pd.show();

       // RequestQueue queue = Volley.newRequestQueue(Objects.requireNonNull(c));

        // in php///
        String url = Config.urlbooks + "getFav.php";
        //
        StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {


                // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                try {

                    //define the lsist
                    books = new ArrayList<>();

                    //Refresh the list to clear
                    // lvUsers.invalidate();

                    //convert from string to JSONObject
                    JSONObject o = new JSONObject(s);

                    //Always I ask myself 2 questions
                    //Who is this and who is his father

                /*
                    {
                        "result":[
                        {"id":"1","Username":"Jareer","Password":"123"},
                        {"id":"2","Username":"Shaban","Password":"aaa"},
                        {"id":"3","Username":"osama","Password":"125"},
                        {"id":"4","Username":"osama2222","Password":"125"}
	                           ]
                    }
                    */

                    JSONArray arr = o.getJSONArray("result");


                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject x = arr.getJSONObject(i);

                        String bookName = x.getString("bookName");
                        String bookURL = x.getString("bookURL");
                        //Adding data to the new User Class
                        BooksModel u = new BooksModel();
                        u.setBookName(bookName);
                        u.setBookPdf( bookURL);
                        books.add(u);
                        //users.add (name + password);
                    }
                    //This is old uses with List
                    // ArrayAdapter<User> a=new ArrayAdapter<User>(getActivity(),android.R.layout.simple_list_item_1,users);
                    //lvUsers.setAdapter(a);

                    //  BooksRecycleAdapter a=new BooksRecycleAdapter( c, books);
                    //   rec.setAdapter(a);
                    ((Favorite) f).getData(books);// to call the interface

                    //This is new used With RecyclerAdapter

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pd.dismiss();
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pd.dismiss();
                String errorDescription = "";
                if (volleyError instanceof NetworkError) {
                    errorDescription = "NetworkError";

                } else if (volleyError instanceof ServerError) {
                    errorDescription = "Server Error";
                } else if (volleyError instanceof AuthFailureError) {
                    errorDescription = "AuthFailureError";
                } else if (volleyError instanceof ParseError) {
                    errorDescription = "Parse Error";
                } else if (volleyError instanceof TimeoutError) {
                    errorDescription = "Time Out";
                }
                Toast.makeText(c, errorDescription, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<>();


              param.put("id", id);

                return param;
            }
        };
        req.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
       // queue.add(req);
         MySingleTon.getInstance(c).addToRequestQueue(req);

    }
     //
     public void addFav(final Context c, final String bookName, final String bookURL, final String id ) {


      //  RequestQueue queue = Volley.newRequestQueue(Objects.requireNonNull(c));

        // in php///
        String url = Config.urlbooks + "updateFav.php";
        //
        StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {


                // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                try {

                    JSONObject o = new JSONObject(s);
                    String data = o.getString("result");
                    if (data.equals("1")) {

                        ///////////////////////////////////////////////////////////////////////


                        Toast.makeText(c, "ALREADY ADDED", Toast.LENGTH_LONG).show();



                    }


                    //This is old uses with List
                    // ArrayAdapter<User> a=new ArrayAdapter<User>(getActivity(),android.R.layout.simple_list_item_1,users);
                    //lvUsers.setAdapter(a);

                    //  BooksRecycleAdapter a=new BooksRecycleAdapter( c, books);
                    //   rec.setAdapter(a);


                    //This is new used With RecyclerAdapter

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pd.dismiss();
                String errorDescription = "";
                if (volleyError instanceof NetworkError) {
                    errorDescription = "NetworkError";

                } else if (volleyError instanceof ServerError) {
                    errorDescription = "Server Error";
                } else if (volleyError instanceof AuthFailureError) {
                    errorDescription = "AuthFailureError";
                } else if (volleyError instanceof ParseError) {
                    errorDescription = "Parse Error";
                } else if (volleyError instanceof TimeoutError) {
                    errorDescription = "Time Out";
                }
                Toast.makeText(c, errorDescription, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<>();
                param.put("bookName", bookName);

                 param.put("bookURL", bookURL);

                param.put("id",id);



                return param;
            }
        };
        req.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
      //  queue.add(req);
         MySingleTon.getInstance(c).addToRequestQueue(req);



     }
     //
     public void deleteFav(final Context c, final String bookName, final String bookURL, final String id ) {


        //  RequestQueue queue = Volley.newRequestQueue(Objects.requireNonNull(c));

        // in php///
        String url = Config.urlbooks + "deleteFav.php";
        //
        StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {


                // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pd.dismiss();
                String errorDescription = "";
                if (volleyError instanceof NetworkError) {
                    errorDescription = "NetworkError";

                } else if (volleyError instanceof ServerError) {
                    errorDescription = "Server Error";
                } else if (volleyError instanceof AuthFailureError) {
                    errorDescription = "AuthFailureError";
                } else if (volleyError instanceof ParseError) {
                    errorDescription = "Parse Error";
                } else if (volleyError instanceof TimeoutError) {
                    errorDescription = "Time Out";
                }
                Toast.makeText(c, errorDescription, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<>();
                param.put("bookName", bookName);

                param.put("bookURL", bookURL);

                param.put("id",id);



                return param;
            }
        };
        req.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //  queue.add(req);
        MySingleTon.getInstance(c).addToRequestQueue(req);



    }
     //
     public void getUserInfo(final Context c, final String s, final String s2) {

        final ProgressDialog pd;
        pd = new ProgressDialog(c);
        pd.setTitle("Loading");
        pd.setMessage("Please wait...");
        pd.show();

    //    RequestQueue queue = Volley.newRequestQueue(Objects.requireNonNull(c));

        // in php///
        String url = Config.url + "getUserInfo.php";
        //
        StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {


                // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                try {

                    //define the lsist


                    //Refresh the list to clear
                    // lvUsers.invalidate();

                    //convert from string to JSONObject
                    JSONObject o = new JSONObject(s);

                    //Always I ask myself 2 questions
                    //Who is this and who is his father

                /*
                    {
                        "result":[
                        {"id":"1","Username":"Jareer","Password":"123"},
                        {"id":"2","Username":"Shaban","Password":"aaa"},
                        {"id":"3","Username":"osama","Password":"125"},
                        {"id":"4","Username":"osama2222","Password":"125"}
	                           ]
                    }
                    */

                    JSONArray arr = o.getJSONArray("result");


                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject x = arr.getJSONObject(i);

                        String name = x.getString("Name");
                        String password = x.getString("Password");
                        String id = x.getString("ID");


                        SharedPreferences userSettings = PreferenceManager.getDefaultSharedPreferences(c);
                        SharedPreferences.Editor pen = userSettings.edit();

                        pen.putString("uName",name);
                        pen.putString("uPassword",password);
                        pen.putString("id",id);
                        pen.apply();
                        pen.commit();


                        //users.add (name + password);
                    }
                    //This is old uses with List
                    // ArrayAdapter<User> a=new ArrayAdapter<User>(getActivity(),android.R.layout.simple_list_item_1,users);
                    //lvUsers.setAdapter(a);

                    //  BooksRecycleAdapter a=new BooksRecycleAdapter( c, books);
                    //   rec.setAdapter(a);


                    //This is new used With RecyclerAdapter

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pd.dismiss();
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pd.dismiss();
                String errorDescription = "";
                if (volleyError instanceof NetworkError) {
                    errorDescription = "NetworkError";

                } else if (volleyError instanceof ServerError) {
                    errorDescription = "Server Error";
                } else if (volleyError instanceof AuthFailureError) {
                    errorDescription = "AuthFailureError";
                } else if (volleyError instanceof ParseError) {
                    errorDescription = "Parse Error";
                } else if (volleyError instanceof TimeoutError) {
                    errorDescription = "Time Out";
                }
                Toast.makeText(c, errorDescription, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<>();

                  param.put("uNameAndroid", s);
                  param.put("uPasswordAndroid", s2);

                return param;
            }
        };
        req.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
       // queue.add(req);
       // Get a RequestQueue
         MySingleTon.getInstance(c).addToRequestQueue(req);

    }


}


