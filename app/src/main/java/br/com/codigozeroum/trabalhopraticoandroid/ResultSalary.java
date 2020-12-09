package br.com.codigozeroum.trabalhopraticoandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.os.Bundle;
import android.view.View;

public class ResultSalary extends AppCompatActivity {

    private AppCompatTextView tvFullPaymentResult, tvInssResult, tvIrrfResult, tvOtherDiscountsResult, tvLiquidSalaryResult, tvDiscountsResult;
    private AppCompatButton btnBack;

    private String fullPayment, dependentsNumber, otherDiscounts = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_salary);

        tvFullPaymentResult = findViewById(R.id.tvFullPaymentResult);
        tvInssResult = findViewById(R.id.tvINSSResult);
        tvIrrfResult = findViewById(R.id.tvIRRFResult);
        tvOtherDiscountsResult = findViewById(R.id.tvOtherDiscountsResult);
        tvLiquidSalaryResult = findViewById(R.id.tvLiquidPaymentResult);
        tvDiscountsResult = findViewById(R.id.tvDiscountsResult);

        btnBack = findViewById(R.id.btnBack);

        fullPayment = getIntent().getStringExtra("full_payment");
        dependentsNumber = getIntent().getStringExtra("dependents_number");
        otherDiscounts = getIntent().getStringExtra("other_discounts");

        calculateValues(fullPayment, dependentsNumber);

        btnBack.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                ResultSalary.this.finish();
            }
        });

    }

    private void calculateValues(String fullPayment, String dependentsNumber){

        float fullPaymentValue = Float.parseFloat(fullPayment);
        int dependentsNumberValue = Integer.parseInt(dependentsNumber);

        float inssResult = 0F;
        float irrfResult = 0F;

        //Calculo de INSS
        inssResult = calculateINSS(fullPaymentValue);

        //Calculo IRRF
        irrfResult = calculateIRRF(fullPaymentValue, inssResult, dependentsNumberValue);

        showValues(fullPaymentValue, inssResult, irrfResult, Float.parseFloat(otherDiscounts.toString()));

    }


    private float calculateINSS(float payment){

        float inssResultValue = 713.10F;
        float aliquotaResult = 0F;

        if(payment <= 1045){
            aliquotaResult = payment * 0.075F;
            inssResultValue = aliquotaResult;
        }

        if(payment > 1045 && payment <= 2089.60){
            aliquotaResult = payment * 0.09F;
            inssResultValue = aliquotaResult - 15.67F;
        }

        if(payment > 2098.60 && payment <= 3134.4){
            aliquotaResult = payment * 0.12F;
            inssResultValue = aliquotaResult - 78.36F;
        }

        if(payment > 3134.4 && payment <= 6101.06){
            aliquotaResult = payment * 0.14F;
            inssResultValue = aliquotaResult - 141.05F;
        }

        return inssResultValue;
    }

    private float calculateIRRF(float payment, float inss, int numDependents){

        float calculateBase = payment - inss - (numDependents * 189.59F);

        float irrfResult = 0F;

        if(calculateBase > 1903.98 && calculateBase <= 2826.65){
            irrfResult = calculateBase * (7.5F / 100) - 142.80F;
        }

        if(calculateBase > 2826.65 && calculateBase <= 3751.05){
            irrfResult = calculateBase * (15F / 100) - 354.8F;
        }

        if(calculateBase > 3751.05 && calculateBase <= 4664.68){
            irrfResult = calculateBase * (22.5F / 100) - 636.13F;
        }

        if(calculateBase > 4664.68){
            irrfResult = calculateBase * (27.5F / 100) - 869.36F;
        }

        return irrfResult;
    }

    private void showValues(float fullPaymentValue, float inssResult, float irrfResult, float otherDiscounts) {

        tvFullPaymentResult.setText(String.format("%.2f", fullPaymentValue));
        tvInssResult.setText("-".concat(String.format("%.2f",inssResult)));
        tvIrrfResult.setText("-".concat(String.format("%.2f",irrfResult)));
        tvOtherDiscountsResult.setText(String.format("%.2f",otherDiscounts));

        float salaryResult = fullPaymentValue - inssResult - irrfResult - otherDiscounts;

        tvLiquidSalaryResult.setText(String.format("%.2f",salaryResult));

        float discounts = 100 - (salaryResult * 100) / fullPaymentValue;

        tvDiscountsResult.setText(String.format("%.2f",discounts).concat("%"));

    }
}















