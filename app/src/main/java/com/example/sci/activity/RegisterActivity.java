package com.example.sci.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sci.api.RegisterResponse;
import com.example.sci.model.RegisterModel;
import com.example.sci.MainActivity;
import com.example.sci.R;
import com.example.sci.utils.JsonUtils;

public class RegisterActivity extends BasicActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //关联activity_register.xml
        setContentView(R.layout.activity_register);
        // 关联用户名、密码
        TextView userName = findViewById(R.id.username);
        TextView passWord =  findViewById(R.id.password);
        Button signUpButton = findViewById(R.id.sign);
        signUpButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String username = userName.getText().toString().trim();
                        String password = passWord.getText().toString().trim();
                        //注册格式粗检
                        if(username.length()<1){
                            showToast("用户名不能为空");
                        }else if(password.length()<6){
                            showToast("密码不能少于6位");
                        }else{
                            RegisterModel registerModel = new RegisterModel();
                            registerModel.register(username, password, new RegisterModel.OnListener() {
                                @Override
                                public void onSuccess(String response) {
                                    RegisterResponse registerResponse = JsonUtils.fromJson(response, RegisterResponse.class);
                                    if (registerResponse.getStatus()==0){
                                        navigateTo(MainActivity.class);
                                        showToastSync("注册成功");
                                    }else{
                                        showToastSync(registerResponse.getMessage());
                                    }
                                }
                                @Override
                                public void onFailure(String errorMessage) {
                                    showToastSync(errorMessage);
                                }
                            });
                        }
                    }
                }
        );



    }}