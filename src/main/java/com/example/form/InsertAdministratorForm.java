package com.example.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 管理者情報登録時に使用するフォーム.
 *
 * @author igamasayuki
 */
public class InsertAdministratorForm {
  /**
   * 名前
   */
  @NotBlank
  private String name;
  /**
   * メールアドレス
   */
  @NotBlank
  @Email
  private String mailAddress;
  /**
   * パスワード
   */
  @NotBlank
  @Size(min = 6, message = "6桁以上のパスワードに設定してください")
  private String password;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMailAddress() {
    return mailAddress;
  }

  public void setMailAddress(String mailAddress) {
    this.mailAddress = mailAddress;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "InsertAdministratorForm [name=" + name + ", mailAddress=" + mailAddress + ", password=" + password
            + "]";
  }

}
