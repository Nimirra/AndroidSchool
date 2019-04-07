package ru.obessonova.lesson03_androidschool;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    EditText field1;
    EditText field2;

    RadioGroup operations;
    RadioGroup operations2;

    CheckBox floatValues;
    CheckBox signedValues;

    TextView resultField;

    Button calculateButton;

    float number1;
    float number2;
    float resultFloat;

    boolean floatIsChecked;
    boolean signedIsChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        field1 = findViewById(R.id.field1);
        field2 = findViewById(R.id.field2);

        operations = findViewById(R.id.operations);
        operations2 = findViewById(R.id.operations2);
        floatValues = findViewById(R.id.floatValues);
        signedValues = findViewById(R.id.signedValues);
        resultField = findViewById(R.id.resultField);
        calculateButton = findViewById(R.id.calculateButton);

        if (savedInstanceState != null) {
            field1.setText(savedInstanceState.getString("field1"));
            field2.setText(savedInstanceState.getString("field2"));
            resultField.setText(savedInstanceState.getString("result"));
            floatIsChecked = savedInstanceState.getBoolean("floatChecked", floatIsChecked);
            signedIsChecked = savedInstanceState.getBoolean("signedIsChecked", signedIsChecked);
        }

        operations.setOnCheckedChangeListener(this);
        operations2.setOnCheckedChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clear:
                field1.setText("");
                field2.setText("");
                resultField.setText(R.string.result_field);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //не срабатывает проверка - все радиобаттон при запуске этого кода неактивны
      /*  if (group == operations) {
            operations2.clearCheck();
        } else {
            operations.clearCheck();
        }*/

        if (field1.getText().toString().length() > 0 && field2.getText().toString().length() > 0) {
            number1 = Float.parseFloat(field1.getText().toString());
            number2 = Float.parseFloat(field2.getText().toString());
        }
        //не срабатывает обработка исключений, в поле resultField в случае деления на ноль выводится infinity
        try {
            switch (checkedId) {
                case R.id.plus:
                    resultFloat = number1 + number2;
                    break;
                case R.id.minus:
                    resultFloat = number1 - number2;
                    break;
                case R.id.divide:
                    resultFloat = number1 / number2;
                    break;
                case R.id.multiple:
                    resultFloat = number1 * number2;
                    break;
            }
        } catch (Exception e) {
            resultField.setText(e.toString());
        }

    }

    public void calculate(View view) {
        if (operations.getCheckedRadioButtonId() == -1 && operations2.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getApplicationContext(), R.string.operation_not_found_message,
                    Toast.LENGTH_SHORT).show();
        } else {
            resultField.setText("" + resultFloat);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("field1", field1.getText().toString());
        savedInstanceState.putString("field2", field2.getText().toString());
        savedInstanceState.putString("result", resultField.getText().toString());
        savedInstanceState.putBoolean("floatChecked", floatIsChecked);
        savedInstanceState.putBoolean("signedIsChecked", signedIsChecked);
    }

    /*при снятии чекбоксов и повторной их установке появляется обычная клавиатура,
    при снятии одного чекбокса программно снимаются оба
     */
    public void checkFloatClicked(View view) {
        floatIsChecked = ((CheckBox) view).isChecked();
        if (floatIsChecked) {
            field1.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            field2.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        } else {
            if (signedValues.isChecked()) {
                field1.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
                field2.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
            }
            field1.setInputType(InputType.TYPE_CLASS_NUMBER);
            field2.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
    }


    public void checkSignedClicked(View view) {
        signedIsChecked = ((CheckBox) view).isChecked();
        if (signedIsChecked) {
            field1.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
            field2.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
        } else {
            if (floatValues.isChecked()) {
                field1.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                field2.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            }
            field1.setInputType(InputType.TYPE_CLASS_NUMBER);
            field2.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
    }
}