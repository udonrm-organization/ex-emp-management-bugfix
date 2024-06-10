package com.example.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Administrator;
import com.example.repository.AdministratorRepository;

/**
 * 管理者情報を操作するサービス.
 *
 * @author igamasayuki
 */
@Service
@Transactional
public class AdministratorService {

  @Autowired
  private AdministratorRepository administratorRepository;

  @Autowired
  private HttpSession session;

  /**
   * 管理者情報を登録します.
   *
   * @param administrator 管理者情報
   */
  public void insert(Administrator administrator) {
    administratorRepository.insert(administrator);
  }

  public boolean authenticate(String mailAddress, String password) {
    Administrator administrator = administratorRepository.findByMailAddress(mailAddress);
    if (administrator == null || !BCrypt.checkpw(password, administrator.getPassword())) {
      return false;
    }
    session.setAttribute("administratorName", administrator.getName());
    return true;
  }

  /**
   * ログインをします.
   *
   * @param mailAddress メールアドレス
   * @param password    パスワード
   * @return 管理者情報 存在しない場合はnullが返ります
   */
  public Administrator login(String mailAddress, String password) {
    Administrator administrator = administratorRepository.findByMailAddressAndPassward(mailAddress, password);
    return administrator;
  }

  public Administrator findByMailAddress(String mailAddress) {
    Administrator administrator = administratorRepository.findByMailAddress(mailAddress);
    return administrator;
  }

  public String hashPassword(String password) {
    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
    return hashedPassword;
  }
}
