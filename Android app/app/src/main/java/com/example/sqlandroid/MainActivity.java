package com.example.sqlandroid;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText host,password,username,port;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        host=findViewById(R.id.editText1);
        password=findViewById(R.id.editText2);
        username=findViewById(R.id.editText);
        port=findViewById(R.id.editText3);
    }


    public void DatabaseScreen_Intent()
    {
        Intent intent = new Intent(this, Database_screen.class);
        intent.putExtra("host",host.getText().toString());
        intent.putExtra("password", password.getText().toString());
        intent.putExtra("username",username.getText().toString());
        intent.putExtra("port",port.getText().toString());
        startActivity(intent);

    }
    public  void onClick(@NonNull View v)
    {
        int id=v.getId();
        if (id==R.id.button) {
            DatabaseScreen_Intent();
        }
    }

}
