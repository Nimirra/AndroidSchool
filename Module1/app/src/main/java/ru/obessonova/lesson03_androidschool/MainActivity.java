package ru.obessonova.lesson03_androidschool;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener {
    private final static String RESULT = "result";
    private EditText field1;
    private EditText field2;
    private RadioGroup operations;
    private RadioGroup operations2;
    private CheckBox floatValues;
    private CheckBox signedValues;

    private TextView resultField;
    private float number1;
    private float number2;
    private float resultFloat;

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

        if (savedInstanceState != null) {
            resultField.setText(savedInstanceState.getString(RESULT));
        }

        operations.setOnCheckedChangeListener(this);
        operations2.setOnCheckedChangeListener(this);

        floatValues.setOnCheckedChangeListener(this);
        signedValues.setOnCheckedChangeListener(this);
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
        if (group.getId() == operations.getId()) {
            operations2.setOnCheckedChangeListener(null);
            operations2.clearCheck();
            operations2.setOnCheckedChangeListener(this);
        } else {
            operations.setOnCheckedChangeListener(null);
            operations.clearCheck();
            operations.setOnCheckedChangeListener(this);
        }

        if (field1.getText().toString().length() > 0 && field2.getText().toString().length() > 0) {
            number1 = Float.parseFloat(field1.getText().toString());
            number2 = Float.parseFloat(field2.getText().toString());
        }
        switch (checkedId) {
            case R.id.plus:
                resultFloat = number1 + number2;
                break;
            case R.id.minus:
                resultFloat = number1 - number2;
                break;
            case R.id.divide:
                if (number2 == 0) {
                    resultField.setText(R.string.arithmeticException);
                } else {
                    resultFloat = number1 / number2;
                }
                break;
            case R.id.multiple:
                resultFloat = number1 * number2;
                break;
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
        savedInstanceState.putString(RESULT, resultField.getText().toString());

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (floatValues.isChecked() && signedValues.isChecked()) {
                    field1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                    field2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                } else if (floatValues.isChecked()) {
                    field1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    field2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                } else if (signedValues.isChecked()) {
                    field1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
                    field2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
                } else {
                    field1.setInputType(InputType.TYPE_CLASS_NUMBER);
                    field2.setInputType(InputType.TYPE_CLASS_NUMBER);
                }
    }
}