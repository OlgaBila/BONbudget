package com.example.meduzzka.finalproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * This class represents a Profile Activity in the app
 *
 * Created by Olga Bila
 */
public class Profile extends AppCompatActivity {

    /**
     * ListView object, used for store data from user
     */
    private ListView listView;

    /**
     * Array of Strings that store user expenses
     */
    private String[] arrText;

    /**
     * Array of Strings that store user expenses
     */
    private String[] arrTemp;
    /**
     * GoogleApiClient client to implement the App Indexing API.
     */
    private GoogleApiClient client;

    /**
     * The system calls this when creating the activity
     *
     * @param savedInstanceState a reference to a Bundle object that is passed into the
     *                           onCreate method of every Android Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        arrText = new String[]{ getString(R.string.annualIncome),
                getString(R.string.monthlyRent),
                getString(R.string.monthlyMortgage),
                getString(R.string.monthlyCarLoan),
                getString(R.string.monthlySavings)
        };
        arrTemp = new String[arrText.length];

        MyListAdapter myListAdapter = new MyListAdapter();
        listView = (ListView) findViewById(R.id.listProfile);
        listView.setAdapter(myListAdapter);

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * Gets Index ApiAction
     * @return Action
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Profile Page")
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    /**
     * Called when the activity is first created
     */
    @Override
    public void onStart() {
        super.onStart();
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    /**
     * Called when the activity is no longer visible to the user,
     * because another activity has been resumed and is covering this one
     */
    @Override
    public void onStop() {
        super.onStop();
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    /**
     * This class used for notifying in listview
     */
    private class MyListAdapter extends BaseAdapter {

        /**
         * Returns length of array
         * @return
         */
        @Override
        public int getCount() {
            if (arrText != null && arrText.length != 0) {
                return arrText.length;
            }
            return 0;
        }

        /**
         * Gets item position
         * @param position in listview
         * @return position
         */
        @Override
        public Object getItem(int position) {
            return arrText[position];
        }

        /**
         * Gets item id position
         * @param position in listview
         * @return item id position
         */
        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * Returns the items in listview
         * @param position item position
         * @param convertView used to increase the performance of your Adapter
         * @param parent is a special view that can contain other views (called children)
         * @return view
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final ViewHolder holder;
            if (convertView == null) {

                holder = new ViewHolder();
                LayoutInflater inflater = Profile.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.listview_list, null);
                holder.textView1 = (TextView) convertView.findViewById(R.id.textView1);
                holder.editText1 = (EditText) convertView.findViewById(R.id.editText1);

                convertView.setTag(holder);

            } else {

                holder = (ViewHolder) convertView.getTag();
            }

            holder.ref = position;

            holder.textView1.setText(arrText[position]);
            holder.textView1.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    /** ListView Clicked item value*/
                    String  itemValue = (String) holder.textView1.getText();

                    /** Show Alert*/
                    Toast.makeText(getApplicationContext(),
                            "Position: " + holder.ref + " ListItem: " + itemValue , Toast.LENGTH_LONG)
                            .show();

                    /**
                     * Calling bundle for fragments
                     */
                    Bundle arguments = new Bundle();
                    arguments.putString(CommentsFragmentProfile.ARG_ITEM_ID, itemValue);
                    CommentsFragmentProfile fragment = new CommentsFragmentProfile();
                    fragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.comments_container, fragment)
                            .commit();

                }
            });

            holder.editText1.setText(arrTemp[position]);
            holder.editText1.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                              int arg3) {
                }

                @Override
                public void afterTextChanged(Editable arg0) {
                    arrTemp[holder.ref] = arg0.toString();
                }
            });

            return convertView;
        }

        /**
         * Class that holds textview and edittext objects
         */
        private class ViewHolder {
            TextView textView1;
            EditText editText1;

            int ref;
        }
    }

    /** Creting toolbar*/
    public boolean onCreateOptionsMenu (Menu m){
        getMenuInflater().inflate(R.menu.toolbar_profile, m );
        return true;
    }

    /**
     * Setting the action on each menu option
     * @param mi menu item
     * @return true if successful
     */
    public boolean onOptionsItemSelected(MenuItem mi) {
        int id = mi.getItemId();
        switch (id) {
            case R.id.action_wallet:
                Log.d("Toolbar", "Wallet menu selected");
                Profile.this.startActivity(new Intent(Profile.this, Wallet_activity.class));
                break;
            case R.id.action_target:
                Log.d("Toolbar", "Target menu selected");
                Profile.this.startActivity(new Intent(Profile.this, TargetsActivity.class));
                break;
            case R.id.action_reports:
                Log.d("Toolbar", "Repprts menu selected");
                Profile.this.startActivity(new Intent(Profile.this, ReportsActivity.class));
                break;
            case R.id.action_help:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                /** Get the layout inflater*/
                LayoutInflater inflater = this.getLayoutInflater();

                /** Inflate and set the layout for the dialog
                 * Pass null as the parent view because its going in the dialog layout*/
                builder2.setView(inflater.inflate(R.layout.message_dialog_profile, null))
                        /** Add action buttons*/
                        .setPositiveButton(R.string.okButton, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                TextView text = (TextView) ((AlertDialog) dialog).findViewById(R.id.textView);
                                Snackbar.make(findViewById(android.R.id.content), "Help manu is selected", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        });

                AlertDialog dialog2 = builder2.create();
                dialog2.show();

                break;
        }
        return true;
    }

}