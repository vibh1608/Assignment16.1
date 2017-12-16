package com.example.user.readwritefile;

import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    //creating reference of UI components
    EditText mtext;
    TextView mshow;
    Button add,delete;

    //onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting reference with their ID's
        mtext = findViewById(R.id.text);
        mshow = findViewById(R.id.showtext);
        add = findViewById(R.id.adddata);
        delete = findViewById(R.id.deletebtn);

        //on click of add button
        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                //getting string data from edittext
                String data = mtext.getText().toString();

                //creating object of async class which is extending AsyncTask class
                Async async = new Async();

                //executing task
                async.execute(data);

                //displaying toast after data successfully getting saved into the file
                Toast.makeText(MainActivity.this,"Text Added",Toast.LENGTH_SHORT).show();

                //string buffer object to clear the input buffer reader
                StringBuffer stringBuffer = new StringBuffer();

                try {

                    //datarow object to read each row of file
                    String datarow ="";

                    //storing file data into string to show in textview
                    String buffer="";

                    //creating mfile object to read the file
                    File mFile = new File(Environment.getExternalStorageDirectory() + "/" + "test.txt");
                    FileInputStream inputStream = new FileInputStream(mFile);

                    //creating BufferedReader object to read the file
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                    //reading each row from file and storing it into buffer object
                    while ((datarow = bufferedReader.readLine()) != null) {
                        buffer += datarow + "\n";

                    }

                    //setting the string data into textview object
                    mshow.setText(buffer);

                    //setting color of textview text
                    mshow.setTextColor(getResources().getColor(R.color.white));

                    //closing buffer
                    bufferedReader.close();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        //on click of delete button
        delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int result = 0;

                //opening file by creating file object
                File mFile = new File("/sdcard/"+"test.txt");

                try {

                    //checking if file exists or not
                    if(mFile.exists())
                    {
                        //deleting file
                        mFile.delete();
                        result = 1;
                        mshow.setText("");
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

                //checking whether file is deleted or not
                if(result == 1)
                {
                    //displaying toast message
                    Toast.makeText(MainActivity.this,"File deleted",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //displaying toast message
                    Toast.makeText(MainActivity.this,"File Does Not Exists",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }//end of method

    //class for doing reading and writing into file in background thread
    class Async extends AsyncTask<String, String ,String>
    {
        //doInBackground method to do tasks in background thread
        @Override
        protected String doInBackground(String... strings)
        {
            try
            {
                //creating file object to write into the file
                File file = new File(Environment.getExternalStorageDirectory()+"/"+"test.txt");

                //creating fileoutputStream object to write into the file
                FileOutputStream outputStream = new FileOutputStream(file,true);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);

                //writing into the file
                outputStreamWriter.write("\n"+strings[0]);

                //closing the file
                outputStreamWriter.close();
                outputStream.close();

            }catch (IOException e)
            {
                e.printStackTrace();
            }

            //returning buffer
            return null;

        }//end of doInBackground method

    }//end of Async class
}
//end of mainActivity class
