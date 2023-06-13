package com.example.sci;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sci.activity.BasicActivity;
import com.example.sci.activity.MainPageActivity;
import com.example.sci.activity.RegisterActivity;
import com.example.sci.api.LoginResponse;
import com.example.sci.model.LoginModel;
import com.example.sci.utils.JsonUtils;

public class MainActivity extends BasicActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 关联activity.xml
        setContentView(R.layout.activity_login);
        // 关联用户名、密码和登录、注册按钮
        TextView userName =  findViewById(R.id.username);
        TextView passWord =  findViewById(R.id.password);
        Button loginButton = findViewById(R.id.log);
        Button signUpButton = findViewById(R.id.rg);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userName.getText().toString();
                String password = passWord.getText().toString();
                if (password.length() < 6) {
                    showToast("密码不足6位");
                } else if(username.length()<1){
                    showToast("账号名不能为空");
                }else {
                    LoginModel loginModel = new LoginModel();
                    loginModel.login(username, password, new LoginModel.OnListener() {
                        @Override
                        public void onSuccess(String response) {
                            LoginResponse loginResponse = JsonUtils.fromJson(response, LoginResponse.class);
                            if (loginResponse.getStatus() == 0){
                                navigateTo(MainPageActivity.class);
                                SharedPreferences preferences = getSharedPreferences("username", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.remove("username");
                                editor.putString("username",username);
                                editor.apply();
                                showToastSync("登录成功");
                            }else{
                                showToastSync(loginResponse.getMessage());
                            }
                        }
                        @Override
                        public void onFailure(String errorMessage) {
                            showToastSync(errorMessage);
                        }
                    });
                }

            }
        });
        signUpButton.setOnClickListener(v -> {
            navigateTo(RegisterActivity.class);
        });
    }
}
