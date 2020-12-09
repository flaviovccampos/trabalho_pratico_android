package br.com.codigozeroum.trabalhopraticoandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AppCompatEditText fullPayment, dependentsNumber, otherDiscounts;
    private AppCompatButton btnCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fullPayment = findViewById(R.id.etFullPayment);
        dependentsNumber = findViewById(R.id.etDependentsNumber);
        otherDiscounts = findViewById(R.id.etOtherDiscounts);
        btnCalculate = findViewById(R.id.btnCalculate);

        btnCalculate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if(fullPayment.getText().toString().isEmpty()){
                    toastValidation("Salário");
                    return;
                }

                if(dependentsNumber.getText().toString().isEmpty()){
                    toastValidation("Qtd de Dependentes");
                    return;
                }

                if(otherDiscounts.getText().toString().isEmpty()){
                    toastValidation("Outros Descontos");
                    return;
                }

                Intent intent = new Intent(getApplicationContext(), ResultSalary.class);
                intent.putExtra("full_payment", fullPayment.getText().toString());
                intent.putExtra("dependents_number", dependentsNumber.getText().toString());
                intent.putExtra("other_discounts", otherDiscounts.getText().toString());
                startActivity(intent);

            }
        });

    }

    private void toastValidation(String type){
        Toast.makeText(getApplicationContext(), "Favor Preencher " + type, Toast.LENGTH_LONG).show();
    }
}