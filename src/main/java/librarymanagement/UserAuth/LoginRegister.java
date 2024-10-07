package librarymanagement.UserAuth;

import java.util.*;

public class LoginRegister {
    private HashMap<String, Account> DataAccount;

    //Kiểm tra nhập tên và mật khẩu tài khoản có đúng không
    public boolean checkLogin(String name, String password ,Account userAccount) {
        for (String key : DataAccount.keySet()) {
            if (key == name && DataAccount.get(name).getPassword() == password) {
                userAccount = DataAccount.get(name);
                return true;
            }
        }
        return false;
    }

    // Kiểm tra tên đã có người dùng nào đăng kí trong database chưa
    public boolean checkUserNameAvaibility(String name){
        for(String key : DataAccount.keySet()){
            if (key == name ){
                return false;
            }
        }
        return true;
    }
    // Kiểm tra tên có đúng mẫu kí tự không
    public boolean checkCharaterUserName(String name){
        if (3 <name.length() && name.length() <20){
            for(int i =0 ; i < name.length() ; i++){
                if (!((name.charAt(i) > '0' && name.charAt(i) < '9')||('a' < name.charAt(i) && name.charAt(i) < 'z')||('A'<name.charAt(i) && name.charAt(i) < 'Z'))){
                    return false;
                }
            }
        }
        return false;
    }

    //Kiểm tra khi đăng kí mật khẩu nhập lại hai lần có giống nhau không
    public boolean checkTwoPassword(String password1 , String password2){
        if(password1 == password2){
            return true;
        }
        return false;
    }

    // Đăng kí
    public Account AccountRegister(String name , String password){
        Account registerAccount = new Account(name,password);
        DataAccount.put(name,registerAccount);
        return registerAccount;
    }
}
