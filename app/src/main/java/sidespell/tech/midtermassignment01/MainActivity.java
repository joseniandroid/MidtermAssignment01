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
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton mFabDeleteAll;

    private ArrayAdapter<String> mCurrencyListAdapter;

    private TypedArray mCurrencyList;
    private TypedArray mCountryList;
    private AutoCompleteTextView mAutoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //region Find all views by Id
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        ListView lvCurrencies = (ListView) findViewById(R.id.lvCurrencies);
        mFabDeleteAll = (FloatingActionButton) findViewById(R.id.fabDeleteAll);
        //endregion

        //region Initialize data source from arrays.xml
        mCountryList = getResources().obtainTypedArray(R.array.countries);
        mCurrencyList = getResources().obtainTypedArray(R.array.currencies);
        //endregion

        //region Initialize adapters for AutoCompleteTextView and the ListView
        ArrayAdapter<CharSequence> autoCompleteAdapter = ArrayAdapter.createFromResource(
                this, R.array.countries, android.R.layout.simple_list_item_1);

        mCurrencyListAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, new ArrayList<String>());

        mAutoCompleteTextView.setAdapter(autoCompleteAdapter);
        lvCurrencies.setAdapter(mCurrencyListAdapter);
        //endregion

        //region Implement event listener logic
        mAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                addCurrencyToList(position);
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

    private void addCurrencyToList(int position) {
        String currency = mCurrencyList.getString(position);
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
