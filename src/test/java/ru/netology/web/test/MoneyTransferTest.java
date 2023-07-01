package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPageV2;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {

  @BeforeEach
  public void openPage() {

    open("http://localhost:9999");

    val loginPage = new LoginPageV2();
    val authInfo = DataHelper.getAuthInfo();
    val verificationPage = loginPage.validLogin(authInfo);
    val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
    verificationPage.validVerify(verificationCode);
  }


  @Test
  void shouldTransferMoneyBetweenOwnCardsV2() {

    val dashboardPage = new DashboardPage();

    int balanceFirstCard = dashboardPage.getFirstCardBalance();
    int balanceSecondCard = dashboardPage.getSecondCardBalance();
    val moneyTransfer = dashboardPage.secondCardButton();
    val infoCard = DataHelper.getFirstCardNumber();
    String sum = "2700";
    moneyTransfer.transferForm(sum, infoCard);

    assertEquals(balanceFirstCard - Integer.parseInt(sum), dashboardPage.getFirstCardBalance());
    assertEquals(balanceSecondCard + Integer.parseInt(sum), dashboardPage.getSecondCardBalance());
  }

}

