package gaia3d.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import gaia3d.security.Crypt;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class PasswordTest {

	@Test
	@DisplayName("BCrypt 비밀번호 테스트")
	void bcryptTest() {
		//String password = "test";
		//String salt = BCrypt.gensalt(10);
//		String dbSalt = "$2a$10$raxA9.ppTStr4t.sG.OtDu22322332232323";
//		String dbPassword = "$2a$10$raxA9.ppTStr4t.sG.OtDuHxtQRUvuUlZ/SEs67YC78KhXgdZVjhK";
		String dbSalt = "$2a$10$raxA9.ppTStr4t.sG.OtDu22322332232323";
		String dbPassword = "$2a$10$Z9NV3/6unrWGAsgRFSdmyuWJlGixA4Qw3MZmoTu9S2bM2MDYiKM92";

		//boolean isValidPassword = BCrypt.checkpw("test", "$2a$10$X/3qX75eyGZSp6jwUTpooe2DYjcTwJff0NYdkFWcTw09P9MbiHXWO");

		String encodedPassword = BCrypt.hashpw("Ltw@1234", dbSalt);
		
		log.info("password1 = {}", dbPassword);
		log.info("password2 = {}", encodedPassword);
		if(dbPassword.equals(encodedPassword)) {
			System.out.println("相同");
		} else {
			System.out.println("不同");
		}
	}
	
	@Test
	@DisplayName("비밀번호 테스트")
	void bCryptPasswordEncoderTest() throws Exception {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
		String encodePassword = bCryptPasswordEncoder.encode("$2a$10$KFr/2p5Og2jBy8NkTaEb/eoUna6AVlQ.A7s4YpPJ9A8dZwLYum5f.");
		log.info("encodePassword = {}", encodePassword);
		assertEquals(bCryptPasswordEncoder.matches("test", "$2a$10$7Y4jEH.GYaAaWuZxVt6Eq.EOUQBxbBk/I.7B3cURSR7BCuku3scjq"), true);
	}

	/*
	*将数据库的密码转码
	* password_decode()
	* */
	@Test
			public void password_decode(){
		Crypt.decrypt("“”");
	}

}
