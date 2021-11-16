package com.rrpvm.subsidioninformator.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.rrpvm.subsidioninformator.R;
import com.rrpvm.subsidioninformator.objects.ComparatorDn;
import com.rrpvm.subsidioninformator.objects.ComparatorUp;
import com.rrpvm.subsidioninformator.objects.RecivierFilter;
import com.rrpvm.subsidioninformator.handlers.RecivierSubsidionHandler;
import com.rrpvm.subsidioninformator.objects.SubsidingRecivier;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    //app_objects_start
    private RecivierSubsidionHandler recivierSubsidionHandler;
    //app_objects_end
    //android_objects_start
    private DrawerLayout drawerLayoutMenu;
    private Toolbar toolbar;
    private ListView subsidionRecivierList;
    private SearchView searchView;

    //android_objects_end
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //init
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recivierSubsidionHandler = new RecivierSubsidionHandler(this, R.layout.subsidion_recivier_item);//bind by ctx +  layout of item in list
        this.drawerLayoutMenu = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.subsidionRecivierList = (ListView) findViewById(R.id.receivirs_list);
        //end_init;
        /* side_menu_init*/
        setSupportActionBar(toolbar); // устанавливаем тулбар как экшн бар
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, this.drawerLayoutMenu, this.toolbar, 0, 0);//вспомогательная кнопка для навбара
        drawerLayoutMenu.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView view = (NavigationView) findViewById(R.id.navigation_view);
        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_switch_all_genders: {
                        recivierSubsidionHandler.getR_filter().getGenderFilter().state = RecivierFilter.statement.CLEAR;
                        break;
                    }
                    case R.id.menu_switch_only_men: {
                        recivierSubsidionHandler.getR_filter().getGenderFilter().state = RecivierFilter.statement.WORK;
                        recivierSubsidionHandler.getR_filter().getGenderFilter().object = true;
                        break;
                    }
                    case R.id.menu_switch_only_women: {
                        recivierSubsidionHandler.getR_filter().getGenderFilter().state = RecivierFilter.statement.WORK;
                        recivierSubsidionHandler.getR_filter().getGenderFilter().object = false;
                        break;
                    }
                    default:
                        break;
                }
                recivierSubsidionHandler.filter();
                updateCounters();
                return true;
            }
        });
        Menu nav_menu = view.getMenu();
        this.setupNavigationViewItems(nav_menu);
        this.subsidionRecivierList.setAdapter(recivierSubsidionHandler.getAdapter()); //set data of list
    }

    /*nav_header_menu method*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.header_menu, menu);
        searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.isEmpty())
                    recivierSubsidionHandler.getR_filter().getNameFilter().state = RecivierFilter.statement.CLEAR;
                else
                    recivierSubsidionHandler.getR_filter().getNameFilter().state = RecivierFilter.statement.WORK;

                recivierSubsidionHandler.getR_filter().getNameFilter().object = s.toLowerCase(Locale.ROOT);//update the filter
                recivierSubsidionHandler.filter();//do filtering
                updateCounters();
                return true;
            }
        });
        return true;
    }

    /*nav_header_menu method*/
    @Override
    public boolean onOptionsItemSelected(MenuItem menu_item) {
        int id = menu_item.getItemId();
        switch (id) {
            case R.id.app_bar_sort: {
                this.recivierSubsidionHandler.setAZ_sortMode(!this.recivierSubsidionHandler.getaZ_sortMode());
                ArrayList<SubsidingRecivier> _tmpdata = new ArrayList<>(this.recivierSubsidionHandler.getDataList());
                if (this.recivierSubsidionHandler.getaZ_sortMode()) {//we love shit code
                    _tmpdata.sort(new ComparatorUp());
                } else {
                    this.recivierSubsidionHandler.getDataList().sort(new ComparatorDn());
                    _tmpdata.sort(new ComparatorDn());
                }
                this.recivierSubsidionHandler.setDataList(_tmpdata);
                this.recivierSubsidionHandler.getAdapter().notifyDataSetChanged();
                break;
            }
            default: {
                break;
            }
        }
        return super.onOptionsItemSelected(menu_item);
    }

    private void setupNavigationViewItems(Menu menu) {
        MenuItem city_selector = menu.findItem(R.id.menu_city_selector);
        MenuItem region_selector = menu.findItem(R.id.menu_region_selector);
        MenuItem year_selector = menu.findItem(R.id.menu_birthdate_fill_year);
        MenuItem month_selector = menu.findItem(R.id.menu_birthdate_fill_month);
        TextInputLayout til_city_selector = (TextInputLayout) city_selector.getActionView();
        TextInputLayout til_region_selector = (TextInputLayout) region_selector.getActionView();
        TextInputLayout til_year_selector = (TextInputLayout) year_selector.getActionView();
        TextInputLayout til_month_selector = (TextInputLayout) month_selector.getActionView();
        AutoCompleteTextView view_month_selector = til_month_selector.findViewById(R.id.menu_birthdate_month_autocomplete);
        til_region_selector.setHint(R.string.nav_menu_region_hint);
        til_city_selector.setHint(R.string.nav_menu_city_hint);
        til_year_selector.setHint(R.string.nav_menu_yearHint);
        view_month_selector.setHint(R.string.nav_menu_monthHint);
        til_year_selector.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.nav_menu_month_select, android.R.layout.simple_dropdown_item_1line);
        view_month_selector.setAdapter(adapter);//set data to spinner
        til_city_selector.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String _str = charSequence.toString().trim();
                if (_str.length() > 0) {
                    recivierSubsidionHandler.getR_filter().getCityFilter().object = _str;
                    recivierSubsidionHandler.getR_filter().getCityFilter().state = RecivierFilter.statement.WORK;
                } else
                    recivierSubsidionHandler.getR_filter().getCityFilter().state = RecivierFilter.statement.CLEAR;
                recivierSubsidionHandler.filter();
                updateCounters();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        til_region_selector.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String _str = charSequence.toString().trim();
                if (_str.length() > 0) {
                    recivierSubsidionHandler.getR_filter().getOblastFilter().object = _str;
                    recivierSubsidionHandler.getR_filter().getOblastFilter().state = RecivierFilter.statement.WORK;
                } else
                    recivierSubsidionHandler.getR_filter().getOblastFilter().state = RecivierFilter.statement.CLEAR;
                recivierSubsidionHandler.filter();
                updateCounters();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        til_year_selector.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String _str = charSequence.toString().trim();
                if(_str.length() >9){
                    _str = _str.substring(0,9);
                    til_year_selector.getEditText().setText(_str);
                }
                if (_str.length() > 0) {
                    recivierSubsidionHandler.getR_filter().getBirth_year().object = Integer.parseInt(_str);
                    recivierSubsidionHandler.getR_filter().getBirth_year().state = RecivierFilter.statement.WORK;
                } else
                    recivierSubsidionHandler.getR_filter().getBirth_year().state = RecivierFilter.statement.CLEAR;
                recivierSubsidionHandler.filter();
                updateCounters();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        view_month_selector.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String _str = charSequence.toString().trim();
                String obj = new String();
                String _return = new String();
                String[] allMonths = getResources().getStringArray(R.array.nav_menu_month_select);
                if (_str.length() > 0) {
                    for (int x = 1; x < allMonths.length; x++) {
                        final String regex = "(.*)" + allMonths[x] + "(.*)";//меняем имя месяца на его порядковый номер
                        _return = _str.replaceAll(regex, Integer.toString(x - 1));
                        if (!_return.equals(_str)) {
                            obj += _return+",";//была произведена замена
                        }
                    }
                    recivierSubsidionHandler.getR_filter().getBirth_month().object = obj;
                    recivierSubsidionHandler.getR_filter().getBirth_month().state = RecivierFilter.statement.WORK;
                } else
                    recivierSubsidionHandler.getR_filter().getBirth_month().state = RecivierFilter.statement.CLEAR;
                recivierSubsidionHandler.filter();
                updateCounters();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void updateCounters() {
        TextView menu_counter_current_elements = findViewById(R.id.menu_counter_current);
        TextView menu_counter_all_elements = findViewById(R.id.menu_counter_all);
        if (menu_counter_all_elements != null && menu_counter_current_elements != null) {
            menu_counter_all_elements.setText("all elements: " + recivierSubsidionHandler.getPure_data().size());
            menu_counter_current_elements.setText("displayed elements: " + recivierSubsidionHandler.getDataList().size());
        }
    }
}
