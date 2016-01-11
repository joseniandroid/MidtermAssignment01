package sidespell.tech.midtermassignment01;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sidespell.tech.midtermassignment01.widgets.ClearableAutoCompleteTextView;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_NAME     = "country_name";
    private static final String KEY_FLAG     = "country_flag";
    private static final String KEY_CURRENCY = "country_currency";

    private FloatingActionButton          mFabDeleteAll;
    private ClearableAutoCompleteTextView mAutoCompleteTextView;

    private SimpleAdapter        mAutoCompleteAdapter;
    private ArrayAdapter<String> mCurrencyListAdapter;

    private TypedArray mCurrencyList;
    private TypedArray mCountryList;
    private TypedArray mFlagList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //region Find all views by Id
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAutoCompleteTextView =
                (ClearableAutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        final ListView lvCurrencies = (ListView) findViewById(R.id.lvCurrencies);
        mFabDeleteAll = (FloatingActionButton) findViewById(R.id.fabDeleteAll);
        //endregion

        //region Initialize data source from arrays.xml
        mCountryList = getResources().obtainTypedArray(R.array.countries);
        mFlagList = getResources().obtainTypedArray(R.array.flags);
        mCurrencyList = getResources().obtainTypedArray(R.array.currencies);
        //endregion

        //region Initialize adapters for AutoCompleteTextView and the ListView

        // Create a HashMap data used to hold all the data
        List<HashMap<String, String>> dataMap = new ArrayList<>();
        for (int i = 0; i < mCountryList.length(); i++) {
            HashMap<String, String> data = new HashMap<>();
            data.put(KEY_NAME, mCountryList.getString(i));
            data.put(KEY_FLAG, Integer.toString(mFlagList.getResourceId(i, R.drawable.philippines)));
            data.put(KEY_CURRENCY, mCurrencyList.getString(i));

            dataMap.add(data);
        }

        // Keys used in HashMap
        String[] from = {KEY_FLAG, KEY_NAME};

        // Ids of views in the list_item_country.xml layout
        int[] to = {R.id.imgFlag, R.id.tvCountryName};

        mAutoCompleteAdapter = new SimpleAdapter(this, dataMap, R.layout.list_item_country, from, to);
        mAutoCompleteTextView.setAdapter(mAutoCompleteAdapter);

        mCurrencyListAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, new ArrayList<String>());

        lvCurrencies.setAdapter(mCurrencyListAdapter);

        //endregion

        //region Implement event listener logic
        mAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                addCurrencyToList(position);
                lvCurrencies.smoothScrollToPosition(mCurrencyListAdapter.getCount() - 1);
            }
        });

        mAutoCompleteTextView.setOnConvertSelectionToStringListener(
                new ClearableAutoCompleteTextView.OnConvertSelectionToStringListener() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public CharSequence convertSelectionToString(Object selectedItem) {
                        return ((HashMap<String, String>) selectedItem).get(KEY_NAME);
                    }
                });

        mFabDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAllCurrencies();
            }
        });
        //endregion
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("unchecked")
    private void addCurrencyToList(int position) {
        HashMap<String, String> item =
                (HashMap<String, String>) mAutoCompleteAdapter.getItem(position);

        String currency = item.get(KEY_CURRENCY);
        mCurrencyListAdapter.add(currency);
    }

    private void removeAllCurrencies() {
        if (mCurrencyListAdapter.isEmpty()) {
            Snackbar.make(mFabDeleteAll, R.string.list_is_empty, Snackbar.LENGTH_LONG).show();
        } else {
            mAutoCompleteTextView.setText("");
            mCurrencyListAdapter.clear();
        }
    }
}
