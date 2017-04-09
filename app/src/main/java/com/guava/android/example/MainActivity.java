package com.guava.android.example;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null)
                        .show();
            }
        });

        TextView textView = (TextView) findViewById(R.id.text_view_exp);

        List<Option> options= generateMockOptions();

        List<OptionBook> optionsBook = generateMockOptionBooks(options);

        String text = "OptionBook size: " + optionsBook.size() + "\n" + "OptionBook Name: "
                + optionsBook.get(0).getName() + "\n" + "OptionBook Type: " + optionsBook.get(0).getDescription()
                + "\n";
        textView.setText(text);

    }

    @SuppressWarnings("unchecked")
    private ArrayList<OptionBook> generateMockOptionBooks(List<Option> options) {

        ImmutableList books = FluentIterable
                .from(options)
                .filter(filterByBookType())
                .transform(transformToOptionBook())
                .toList();


        return new ArrayList<OptionBook>(books);

    }

    /**
     * this method generates a mock option list
     * @return
     */
    private List<Option> generateMockOptions() {
        List<Option> options = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Option option = new Option();
            option.setId(i);
            option.setName("option " + i);
            option.setDescription("description example");
            option.setSerial("1234567890");
            option.setType("generic");
            if (i == 4) {
                option.setType(Option.BOOK_TYPE);
                option.setDescription("description book");
            }

            options.add(option);
        }

        return options;

    }


    /**
     * this predicate {@link Predicate} filters Option Object by specific type like "book"
     */
    private Predicate filterByBookType() {
        return new Predicate<Option>() {
            @Override
            public boolean apply(Option input) {
                return input != null && input.getType().equals(Option.BOOK_TYPE);
            }
        };
    }


    /**
     * this function {@link com.google.common.base.Function} tranforms from the Option Object to OptionBook Object
     */

    private Function<Option, OptionBook> transformToOptionBook() {
        return new Function<Option, OptionBook>() {
            @Override
            public OptionBook apply(Option input) {
                OptionBook book = new OptionBook();
                book.setId(input.getId());
                book.setName(input.getName());
                book.setDescription(input.getDescription());
                book.setSerial(input.getSerial());
                book.setProvider("new provider");
                return book;
            }
        };
    }

}
