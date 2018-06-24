package test.ua.khpi.krasov.db.validation;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ua.khpi.krasov.db.entity.Tariff;
import ua.khpi.krasov.db.entity.User;
import ua.khpi.krasov.db.validation.ValidationUtil;

public class ValidationUtilTest {

	@Test
	public void testValidateTariffPrice() {
		Tariff tariff = new Tariff();
		tariff.setPrice(100);
		Assertions.assertTrue(ValidationUtil.validateTariffPrice(tariff));
	}

	@Test
	public void testValidateTariffDescription() {
		Tariff tariff = new Tariff();
		tariff.setDescription("asdasd");
		Assertions.assertTrue(ValidationUtil.validateTariffDescription(tariff));
	}

	@Test
	public void testValidateUser() {
		User user = new User();
		user.setFirstName("ddsa");
		user.setLastName("asd");
		user.setLogin("asdasd");
		user.setPassword("asd23DSAd");
		Assertions.assertTrue(ValidationUtil.validateUser(user));
	}
	
	@Test
	public void testValidateName() {
		String name = "ads";
		Assertions.assertTrue(ValidationUtil.validateName(name));
	}

	@Test
	public void testValidateLogin() {
		String login = "assdd";;
		Assertions.assertTrue(ValidationUtil.validateLogin(login));
	}

	@Test
	public void testValidatePassword() {
		String password = "12345Ff";
		Assertions.assertTrue(ValidationUtil.validatePassword(password));
	}
}
